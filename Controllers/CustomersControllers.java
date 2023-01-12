package Controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.imageio.ImageIO;

import DBConnection.Configs;
import application.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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



public class CustomersControllers {
	// Get the singleton instance of Configs
    Configs db = Configs.getInstance();
    
    private int customerId;
    @FXML
    private ImageView imageConnect;
    
    @FXML
    private ImageView CustomerImageView;

    @FXML
    private TextField firstNamefld;
    
    @FXML
    private TextField lastNamefld;
    
    @FXML
    private TextField phonefld;

    @FXML
    private TextField emailfld;
    
    @FXML
    private TextField cityfld;
    @FXML
    private TableColumn<Customer, String> cityc;

    @FXML
    private TableColumn<Customer, String> emailc;

    @FXML
    private TableColumn<Customer, String> firstNamec;

    @FXML
    private TableColumn<Customer, String> lastNamec;

    @FXML
    private TableColumn<Customer, String> phonec;

    @FXML
    private TableView<Customer> tb_Customers;
    
    ObservableList<Customer> data = FXCollections.observableArrayList();

    
    @FXML
    private Button btnCloseWindow;

    @FXML
    void initialize() {
    	fillTableCustomers();
    } 
    

	
	 @FXML
	    void onCustomerImage_Clicked(MouseEvent event) {
		 FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Select an image file");
	        fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
	        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
	        File selectedFile = fileChooser.showOpenDialog(CustomerImageView.getScene().getWindow());
	        if (selectedFile != null) {
	            Image image = new Image(selectedFile.toURI().toString());
	            CustomerImageView.setImage(image);
	        }
	      
	    }

    @FXML
    void btnAddCustomers_Clicked(ActionEvent event) {
    	 if(firstNamefld.getText().isEmpty()||lastNamefld.getText().isEmpty() ||phonefld.getText().isEmpty()||emailfld.getText().isEmpty()||cityfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error add Customer");
             alert.setHeaderText("Please Complete the form before adding new Customer");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	 
    	 Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
         imageConnect.setImage(imag);

         // Insert a new person into the database
         String insertQuery = "INSERT INTO customers ( FirstName, LastName, phone, email, city, img) VALUES (?, ?, ?,?,?,?)";
         try {
             PreparedStatement pst = db.execQueryPrep(insertQuery);
             pst.setString(1, firstNamefld.getText());
             pst.setString(2, lastNamefld.getText());
             pst.setString(3, phonefld.getText());
             pst.setString(4, emailfld.getText());
             pst.setString(5, cityfld.getText());
             // Get the image from the ImageView
             Image image = CustomerImageView.getImage();
             if (image != null) {
                 // Convert the image to a byte array
                 byte[] byteArray = convertImageToByteArray(image);
                 // Set the byte array as the input for the img column
                 pst.setBytes(6, byteArray);
             } else {
                 // If no image is selected, insert null into the img column
                 pst.setBytes(6, null);
             }
             pst.execute();
             
             //
           
             
           //confirme add
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Add");
             alert.setHeaderText("new Customer has been added successfully");
             alert.setContentText("great job man/women!");
             alert.showAndWait();
             
             // Refresh the table view
         	data.clear();
         	tb_Customers.refresh();
         	fillTableCustomers();
         	imageConnect.setImage(null);
         	
             
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }

    @FXML
    void btnCloseWindow_Clicked(ActionEvent event) {
    	Stage stage = (Stage) btnCloseWindow.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void btnDeleteCustomer_Clicked(ActionEvent event) {
    	 if(firstNamefld.getText().isEmpty()||lastNamefld.getText().isEmpty() ||phonefld.getText().isEmpty()||emailfld.getText().isEmpty()||cityfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("delet Customer");
             alert.setHeaderText("HHHHHHHHHHhh ! there is no thing to delet\nplease Select item an try again if you want");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	 Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
         imageConnect.setImage(imag);

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
        String deleteQuery = "DELETE FROM customers WHERE idCustomer = "+this.customerId;
        if (db.execAction(deleteQuery)) {
            System.out.println("Successfully deleted person with id "+this.customerId);
            data.clear();
        	tb_Customers.refresh();
        	fillTableCustomers();
        	imageConnect.setImage(null);
        } else {
            System.out.println("Error deleting person with id "+this.customerId);
        }
    }

    @FXML
    void btnGoDown_clicked(ActionEvent event) {
    	tb_Customers.getSelectionModel().selectNext();
    	selectedItem();	
    }

    @FXML
    void btnGoUp_Clicked(ActionEvent event) {
    	tb_Customers.getSelectionModel().selectPrevious();
    	selectedItem();
    }

    @FXML
    void btnUpdateCustomers_Clicked(ActionEvent event) {
    	 if(firstNamefld.getText().isEmpty()||lastNamefld.getText().isEmpty() ||phonefld.getText().isEmpty()||emailfld.getText().isEmpty()||cityfld.getText().isEmpty()) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("update Customer");
             alert.setHeaderText("HHHHHHHHHHhh ! there is no thing to Update\nplease Select item an try again if you want");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

             return;
         }
    	Image imag = new Image(getClass().getResource("/FXML/images/Facebook.gif").toExternalForm());
        imageConnect.setImage(imag);
      	
    	 byte[] byteArrayImg= convertImageToByteArray(CustomerImageView.getImage());    	
     	// Update
         String updateQuery = "UPDATE customers SET FirstName = '"+firstNamefld.getText()+"' ,LastName = '"+lastNamefld.getText()+"' ,phone= '"+phonefld.getText()+"' ,email='"+emailfld.getText()+"',img=? WHERE idCustomer="+this.customerId;
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
             alert.setTitle("Update");
             alert.setHeaderText(" Customer has been Updated successfully");
             alert.setContentText("great job man/women!");
             alert.showAndWait();
        	  
         	// Refresh the table view
         	data.clear();
         	tb_Customers.refresh();
         	//imageConnect.setImage(null);
         	fillTableCustomers();
         	imageConnect.setImage(null);
         	
    }

    @FXML
    void onTableRowClicked(MouseEvent event) {
    	selectedItem();
    }
    
    //---------------------------------------------------------------------//
    private void fillTableCustomers() {
		// TODO Auto-generated method stub
    	 // Select all people from the database
        String selectQuery = "SELECT idCustomer, FirstName, LastName, phone, email, city, img FROM customers";
        try {
            ResultSet rs = db.execQuery(selectQuery);
            // Iterate over the result set and add the data to the list
            while (rs.next()) {
                int id = rs.getInt("idCustomer");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String city = rs.getString("city");
                byte[] imgbyteArray = rs.getBytes("img");


                Image image=convertByteArrayToImage(imgbyteArray);
                data.add(new Customer(id, FirstName, LastName,phone,email,city, image));
            }
            
            // make sure that these match the actual ids of the columns in the table
            //cUserid.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNamec.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            lastNamec.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            phonec.setCellValueFactory(new PropertyValueFactory<>("phone"));
            emailc.setCellValueFactory(new PropertyValueFactory<>("email"));
            cityc.setCellValueFactory(new PropertyValueFactory<>("city"));
           // cimge.setCellValueFactory(new PropertyValueFactory<>("image"));

            // Set the data for the table
            tb_Customers.setItems(data);
            //
//            if(data.size()>0) {
//            	Image image=data.get(0).getImgCustomer();
//        	   	CustomerImageView.setImage(image);
//            }
        	
            
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
	    	Customer customer=tb_Customers.getSelectionModel().getSelectedItem();
	    	int index=tb_Customers.getSelectionModel().getSelectedIndex();

	    		try {
	    				this.customerId=customer.getIdCustomer();
	    				firstNamefld.setText(customer.getFirstName());
	    				lastNamefld.setText(customer.getLastName());
	    				phonefld.setText(customer.getPhone());
	    				emailfld.setText(customer.getEmail());
	    				cityfld.setText(customer.getCity());
	            	   	CustomerImageView.setImage(data.get(index).getImgCustomer());
	    		}catch (Exception e) {
					// TODO: handle exception
	    			e.printStackTrace();
	    			return;
				}
	 }
    
   
}
