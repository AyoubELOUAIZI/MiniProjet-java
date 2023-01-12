package Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


import DBConnection.Configs;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductsController {
	// Get the singleton instance of Configs
    Configs db = Configs.getInstance();
    
    private int selectedProductid;
    private int selectedProductCatid;


    @FXML
    private ImageView ImaConProduct;

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn<Product,String> categoriec;

    @FXML
    private TableColumn<Product,String> descriptionfc;

    @FXML
    private ImageView imgV1;

    @FXML
    private ImageView imgV2;

    @FXML
    private ImageView imgV3;

    @FXML
    private TableColumn<Product,String> namec;

    @FXML
    private TableColumn<Product,Float> pricec;

    @FXML
    private TableColumn<Product,Integer> quantityfc;

    @FXML
    private TableView<Product> tb_Product;
    
    ObservableList<Product> data = FXCollections.observableArrayList();


    @FXML
    void initialize() {
    	fillTableProducts();
    	//selectedItem();
    } 
    
    

	@FXML
    void btnAddProduct_Clicked(ActionEvent event) {
    	 try {
 			Stage stage = new Stage();
 		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/AddProduct.fxml"));
 		    stage.setScene(new Scene(root));
 		    stage.setTitle("My modal window");
 		    stage.initModality(Modality.WINDOW_MODAL);
 		    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
 		    stage.show();    		   
     		  } catch(Exception e) {
     		   e.printStackTrace();
     		  }	
    }

    @FXML
    void btnClose_Clicked(ActionEvent event) {
    	Stage stage = (Stage) btnClose.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void btnDelete_Clicked(ActionEvent event) {
    	 Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
         ImaConProduct.setImage(imag);

    	//=========
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirm Delete");
    	alert.setHeaderText("Are you sure you want to delete this item?");
    	alert.setContentText("This action cannot be undone.");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.isPresent() && result.get() == ButtonType.OK) {
    	    // Delete the item
    	} else {
    	    // Don't delete the item
    		return;
    	}

    	
    	 //delete
        String deleteQuery = "DELETE FROM products WHERE idProduct = "+this.selectedProductid;
        if (db.execAction(deleteQuery)) {
            data.clear();
        	tb_Product.refresh();
        	fillTableProducts();
        	ImaConProduct.setImage(null);
        } else {
            ImaConProduct.setImage(null);

        }
    }

    @FXML
    void btnGoDown_Clicked(ActionEvent event) {
    	tb_Product.getSelectionModel().selectNext();
    	selectedItem();	
    }

    @FXML
    void btnGoUp_Clicked(ActionEvent event) {
    	tb_Product.getSelectionModel().selectPrevious();
    	selectedItem();
    }

    //*****************************************************************************//
    @FXML
    void btnUpdate_Clicked(ActionEvent event) {
    	if(selectedProductid==0) {
    		 Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error Update product");
             alert.setHeaderText("Please Select item before and try again");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();
    		return;
    	}
    	
    	
       	Product product=tb_Product.getSelectionModel().getSelectedItem();

    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/AddProduct.fxml"));
             Parent root = loader.load();
             AddProductController controller = loader.getController();
             controller.setData(product);
             Scene scene = new Scene(root);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(scene);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    	
    	
    
    	
    	
    @FXML
    void onSearchTextChanged(InputMethodEvent event) {

    }

    @FXML
    void onTableProductRow_Clicked(MouseEvent event) {
    	selectedItem();
    }
    
    //-------------------------------------------------------//
   
    void fillTableProducts(){
      	 // Select all Products from the database
          String selectQuery = "SELECT idProduct, NameProduct, P.discription, QteOnStock, Price, img1, img2, img3, P.idCat,C.name FROM categories C,products P"
      	                        +" where P.idCat=C.idCat ";
          try {
              ResultSet rs = db.execQuery(selectQuery);
              // Iterate over the result set and add the data to the list
              while (rs.next()) {
                  int id = rs.getInt("idProduct");
                  String NameProduct = rs.getString("NameProduct");
                  String discription = rs.getString("discription");
                  int QteOnStock = rs.getInt("QteOnStock");
                  float Price = rs.getFloat("Price");
                  byte[] imgbyteArray1 = rs.getBytes("img1");
                  byte[] imgbyteArray2 = rs.getBytes("img2");
                  byte[] imgbyteArray3 = rs.getBytes("img3");
                  int idCat = rs.getInt("P.idCat");
                  String nameCat = rs.getString("C.name");
 
                  Image image1=convertByteArrayToImage(imgbyteArray1);
                  Image image2=convertByteArrayToImage(imgbyteArray2);
                  Image image3=convertByteArrayToImage(imgbyteArray3);

                  data.add(new Product(id, NameProduct,discription, QteOnStock,Price,image1,image2,image3,idCat,nameCat));
              }
              
              // make sure that these match the actual ids of the columns in the table
              namec.setCellValueFactory(new PropertyValueFactory<>("NameProduct"));
              descriptionfc.setCellValueFactory(new PropertyValueFactory<>("discription"));
              quantityfc.setCellValueFactory(new PropertyValueFactory<>("QteOnStock"));
              pricec.setCellValueFactory(new PropertyValueFactory<>("Price"));
              categoriec.setCellValueFactory(new PropertyValueFactory<>("Categorie"));

             
              // Set the data for the table
              tb_Product.setItems(data);         
          } catch (SQLException ex) {
              ex.printStackTrace();
          }
       }
       
       public Image convertByteArrayToImage(byte[] imageData) {
           try (InputStream inputStream = new ByteArrayInputStream(imageData)) {
               return new Image(inputStream);
           } catch (IOException e) {
               e.printStackTrace();
               return null;
           }
       }
       
      
   	
       private void selectedItem() {
       	Product product=tb_Product.getSelectionModel().getSelectedItem();
       	int index=tb_Product.getSelectionModel().getSelectedIndex();

       		try {
       				this.selectedProductid=product.getIdProduct();
       				this.selectedProductCatid=product.getIdCat();

       				imgV1.setImage(data.get(index).getImg1());
       				imgV2.setImage(data.get(index).getImg2());
       				imgV3.setImage(data.get(index).getImg3());


       		}catch (Exception e) {
   				// TODO: handle exception
       			e.printStackTrace();
       			return;
   			}
    }

}
