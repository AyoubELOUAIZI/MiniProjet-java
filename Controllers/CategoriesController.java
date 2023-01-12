package Controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.imageio.ImageIO;

import DBConnection.Configs;
import application.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CategoriesController {
	// Get the singleton instance of Configs
    Configs db = Configs.getInstance();
    
    private int categorieId;
    

    @FXML
    private ImageView ImageCategorie;

    @FXML
    private Button btnClose;

    @FXML
    private TableColumn<Categorie, String> cdescription;

    @FXML
    private TableColumn<Categorie, String> cname;

    @FXML
    private ImageView connectCategorieImage;

    @FXML
    private TextArea descriptionfld;

    @FXML
    private TextField namefld;

    @FXML
    private TableView<Categorie> tb_Categories;
    
    ObservableList<Categorie> data = FXCollections.observableArrayList();

    @FXML
    void initialize() {
    	fillTableCategories();
    } 

    @FXML
    void onAddBtnClicked(ActionEvent event) {
    	
    	 if(namefld.getText().isEmpty()||descriptionfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error add Categorie");
             alert.setHeaderText("Please Complete the form before adding new Categorie");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	 
    	 Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
    	 connectCategorieImage.setImage(imag);

         // Insert a new person into the database
         String insertQuery = "INSERT INTO categories ( name,description, img) VALUES (?, ?, ?)";
         try {
             PreparedStatement pst = db.execQueryPrep(insertQuery);
             pst.setString(1, namefld.getText());
             pst.setString(2, descriptionfld.getText());
            // Get the image from the ImageView
             Image image = ImageCategorie.getImage();
             if (image != null) {
                 // Convert the image to a byte array
                 byte[] byteArray = convertImageToByteArray(image);
                 // Set the byte array as the input for the img column
                 pst.setBytes(3, byteArray);
             } else {
                 // If no image is selected, insert null into the img column
                 pst.setBytes(3, null);
             }
             pst.execute();
             
             //
           
             
           //confirme add
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Add");
             alert.setHeaderText("new Categorie has been added successfully");
             alert.setContentText("great job man/women!");
             alert.showAndWait();
             
             // Refresh the table view
         	data.clear();
         	tb_Categories.refresh();
         	fillTableCategories();
         	connectCategorieImage.setImage(null);
         	
             
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }

    @FXML
    void onBtnClose_Clicked(ActionEvent event) {
    	Stage stage = (Stage) btnClose.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void onDeleteBtn_Clicked(ActionEvent event) {
   	 if(namefld.getText().isEmpty()||descriptionfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("delet Categorie");
             alert.setHeaderText("HHHHHHHHHHhh ! there is no thing to delete\nplease Select item an try again if you want");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	 Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
         connectCategorieImage.setImage(imag);

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
        String deleteQuery = "DELETE FROM categories WHERE idCat = "+this.categorieId;
        if (db.execAction(deleteQuery)) {
            System.out.println("Successfully deleted person with id ");
            data.clear();
        	tb_Categories.refresh();
        	fillTableCategories();
        	connectCategorieImage.setImage(null);
        } else {
            System.out.println("Error deleting person with id ");
        	connectCategorieImage.setImage(null);

        }
    }

    @FXML
    void onUpdateBtn_Clicked(ActionEvent event) {
      	 if(namefld.getText().isEmpty()||descriptionfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("update Customer");
             alert.setHeaderText("HHHHHHHHHHhh ! there is no thing to Update\nplease Select item an try again if you want");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
    	connectCategorieImage.setImage(imag);
      	
    	 byte[] byteArrayImg= convertImageToByteArray(ImageCategorie.getImage());    	
     	// Update
         String updateQuery = "UPDATE categories SET name = '"+namefld.getText()+"' ,description = '"+descriptionfld.getText()+"',img=? WHERE idCat="+this.categorieId;
         PreparedStatement pst = db.execQueryPrep(updateQuery);
         try {
			pst.setBytes(1, byteArrayImg);
	         pst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //this is wheere it was if
        	 //
        	
        	 //confirme update customer
        	 Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Update Categorie");
             alert.setHeaderText(" Categorie has been Updated successfully");
             alert.setContentText("great job man/women!");
             alert.showAndWait();
        	  
         	// Refresh the table view
         	data.clear();
         	tb_Categories.refresh();
         	//imageConnect.setImage(null);
         	fillTableCategories();
         	connectCategorieImage.setImage(null);

    }
    @FXML
    void onTableRow_Clicked(MouseEvent event) {
    	selectedItem();
    }
    
    @FXML
    void onImageCategorie_Clicked(MouseEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
        File selectedFile = fileChooser.showOpenDialog(ImageCategorie.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageCategorie.setImage(image);
        }
      
    }
    
   
    
    //-------------------------------------------------------//
    void fillTableCategories(){
   	 // Select all Categories from the database
       String selectQuery = "SELECT idCat, name, description, img FROM categories";
       try {
           ResultSet rs = db.execQuery(selectQuery);
           // Iterate over the result set and add the data to the list
           while (rs.next()) {
               int id = rs.getInt("idCat");
               String name = rs.getString("name");
               String description = rs.getString("description");
               byte[] imgbyteArray = rs.getBytes("img");
               
               Image image=convertByteArrayToImage(imgbyteArray);
               data.add(new Categorie(id, name,description, image));
           }
           
           // make sure that these match the actual ids of the columns in the table
           cname.setCellValueFactory(new PropertyValueFactory<>("name"));
           cdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
          
           // Set the data for the table
           tb_Categories.setItems(data);         
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
	
    private void selectedItem() {
    	Categorie categorie=tb_Categories.getSelectionModel().getSelectedItem();
    	int index=tb_Categories.getSelectionModel().getSelectedIndex();

    		try {
    				this.categorieId=categorie.getId();
    				namefld.setText(categorie.getName());
    				descriptionfld.setText(categorie.getDescription());	
    				ImageCategorie.setImage(data.get(index).getImg());
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
    			return;
			}
 }

}

