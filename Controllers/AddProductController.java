package Controllers;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import DBConnection.Configs;
import application.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddProductController {

	// Get the singleton instance of Configs
    Configs db = Configs.getInstance();
    
    public int idcat;
    
   
    @FXML
    private Button btnAdd_Update;
    
    @FXML
    private Button btnExit;

    @FXML
    public ComboBox<String> comboBoxCat;
    
    ObservableList<String> categoriesNames = FXCollections.observableArrayList();


    @FXML
    public TextArea descriptionfld;

    @FXML
    public ImageView imgV1;

    @FXML
    public ImageView imgV2;

    @FXML
    public ImageView imgV3;

    @FXML
    public TextField pricefld;

    @FXML
    public TextField productnamfld;

    @FXML
    public TextField quantityfld;
    
    @FXML
    public Label title;
    
    @FXML
    void initialize() {
    	//
    	fillComboBoxByCategoriesNames();	
//    	if(title.getText()=="Update Product") {
//    		System.out.println("uupdate product");
//    	}
    }

    private void fillComboBoxByCategoriesNames() {
		// TODO Auto-generated method stub
    	String query = "SELECT name FROM categories";
   	 ResultSet resultSet = db.execQuery(query);
   	 try {
			while (resultSet.next()) {
				categoriesNames.add(resultSet.getString("name"));
			 }
			
	    	 comboBoxCat.setItems(categoriesNames);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
    void btnAddNew_Clicked(ActionEvent event) {
    	if(comboBoxCat.getSelectionModel().isEmpty()||productnamfld.getText().isEmpty()||quantityfld.getText().isEmpty()
    			||pricefld.getText().isEmpty()||descriptionfld.getText().isEmpty()) {
    		  Alert alert = new Alert(AlertType.ERROR);
              alert.setTitle("Error add product");
              alert.setHeaderText("Please Complete the form before adding new Product");
              alert.setContentText("Ooops, there was an error!");

              alert.showAndWait();
    		return;
    	}
    	///////////////////////////////////////////////////////////////////
    	//select id
    	String queryidCat = "SELECT idCat FROM categories where name='"+comboBoxCat.getSelectionModel().getSelectedItem()+"'";
      	 ResultSet resultSet = db.execQuery(queryidCat);
      	 
      	 try {
            	resultSet.next();
   				this.idcat=resultSet.getInt("idCat");
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    
    	
    	/////////////////////////////////////////////////////////////////
      	String Query = null;
      	 if(btnAdd_Update.getText()=="Add New") {
    	  Query = "INSERT INTO products ( NameProduct, discription, QteOnStock, Price, img1, img2, img3, idCat) VALUES (?, ?, ?,?,?,?,?,?)";
         
      	 }else if(btnAdd_Update.getText()=="Update") {
      		 System.out.println("update Product");
        	  Query = "update products set  NameProduct=?, discription=?, QteOnStock=?, Price=?, img1=?, img2=?, img3=?, idCat=?";

      	 }
    	 try {
             PreparedStatement pst = db.execQueryPrep(Query);
             pst.setString(1, productnamfld.getText());
             pst.setString(2, descriptionfld.getText());
             pst.setInt(3,  Integer.parseInt(quantityfld.getText()));
             pst.setFloat(4,  Float.parseFloat(quantityfld.getText()));
            // Get the image from the ImageView
             Image image1 = imgV1.getImage();
             Image image2 = imgV2.getImage();
             Image image3 = imgV3.getImage();
             if (image1 != null && image1 != null && image1 != null) {
                 // Convert the image to a byte array
                 byte[] byteArray1 = convertImageToByteArray(image1);
                 byte[] byteArray2 = convertImageToByteArray(image2);
                 byte[] byteArray3 = convertImageToByteArray(image3);


                 // Set the byte array as the input for the img column
                 pst.setBytes(5, byteArray1);
                 pst.setBytes(6, byteArray2);
                 pst.setBytes(7, byteArray3);
             } else {
                 // If no image is selected, insert null into the img column
                 pst.setBytes(5, null);
                 pst.setBytes(6, null);
                 pst.setBytes(7, null);
             }
             
             pst.setInt(8,  this.idcat);
             pst.execute();
   
           
             
             if(btnAdd_Update.getText()=="Add New") {
            	//confirme add
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Add Product");
                 alert.setHeaderText("new Product has been added successfully");
                 alert.setContentText("great job man/women!");
                 alert.showAndWait(); 
                
             	 }else if(btnAdd_Update.getText()=="Update") {
             		//confirme add
                     Alert alert = new Alert(AlertType.INFORMATION);
                     alert.setTitle("update Product");
                     alert.setHeaderText(" Product has been updated successfully");
                     alert.setContentText("great job man/women!");
                     alert.showAndWait(); 
             	 }
             
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
      	

    }
	
	
	 private byte[] convertImageToByteArray(Image image) {
		    if (image == null) {
		        System.out.println("image is null, can't convert it to byte array");
		        return null;
		    }
		
		    int width = (int) image.getWidth();
		    int height = (int) image.getHeight();
		    int[] pixels = new int[width * height];
		    PixelReader pixelReader = image.getPixelReader();
		    pixelReader.getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
		
		    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		    bufferedImage.setRGB(0, 0, width, height, pixels, 0, width);
		
		    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
		        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
		        return byteArrayOutputStream.toByteArray();
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
		}

    @FXML
    void onBtnExit_Clicked(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void onImgV1_clicked(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
        File selectedFile = fileChooser.showOpenDialog(imgV1.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgV1.setImage(image);
        }

    }
    
    @FXML
    void onImgV2_clicked(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
        File selectedFile = fileChooser.showOpenDialog(imgV2.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgV2.setImage(image);
        }
    }
    
    @FXML
    void onImgV3_clicked(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
        File selectedFile = fileChooser.showOpenDialog(imgV3.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgV3.setImage(image);
        }

    }
    
  

	public void setData(Product product) {
// TODO Auto-generated method stub
	   	
//    	AddProductController p=new AddProductController();
    	productnamfld.setText(product.getNameProduct());
    	descriptionfld.setText(product.getDiscription());
    	
    	//idcat=this.selectedProductCatid;
    	
    	pricefld.setText(String.valueOf(product.getPrice()));
    	quantityfld.setText(String.valueOf(product.getQteOnStock()));
    	imgV1.setImage(product.getImg1());
    	imgV2.setImage(product.getImg2());
    	imgV3.setImage(product.getImg3());
    	title.setText("Update Product");
    	btnAdd_Update.setText("Update");

		
	}

}

