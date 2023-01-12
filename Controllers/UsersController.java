package Controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import DBConnection.Configs;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.swing.SwingUtilities;


public class UsersController {
	 // Get the singleton instance of Configs
     Configs db = Configs.getInstance();
     private int userid;
   
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnexit;
    
    @FXML
    private Button btnexit1;
    
    @FXML
    private Button btnUp;
    
    @FXML
    private Button btndown;
    
    @FXML
    private TextField Usernamefld;


    @FXML
    private TextField passWordfld;

    @FXML
    private TextField sellernamefld;

    @FXML
    private TextField typeUserfld;

    @FXML
    private ImageView userImgView;
    
   // ObservableList<User> data;
    ObservableList<User> data = FXCollections.observableArrayList();

    
    @FXML
    private TableView<User> tb_Users;

    
    @FXML
    private TableColumn<User, String> cPassWord;

    @FXML
    private TableColumn<User, String> cSellerName;

    @FXML
    private TableColumn<User, String> cTypeUser;

    @FXML
    private TableColumn<User, String> cUserName;

    @FXML
    private TableColumn<User, String> cUserid;
    
    @FXML
    private TableColumn<User, Image> cimge;
    
    
    @FXML
    void initialize() {
    	fillTableUsers();
    } 
    	
    	
    
    
    void fillTableUsers() {
    	 // Select all people from the database
        String selectQuery = "SELECT idUser, userName, passWord, TypeUser,img,  SellerName FROM users";
        try {
            ResultSet rs = db.execQuery(selectQuery);
            // Iterate over the result set and add the data to the list
            while (rs.next()) {
                int id = rs.getInt("idUser");
                String username = rs.getString("userName");
                String password = rs.getString("passWord");
                String type = rs.getString("TypeUser");
                byte[] imgbyteArray = rs.getBytes("img");
                String seller = rs.getString("SellerName");

                Image image=convertByteArrayToImage(imgbyteArray);
                data.add(new User(id, username, password, type, image, seller));
            }
            
            // make sure that these match the actual ids of the columns in the table
            cUserid.setCellValueFactory(new PropertyValueFactory<>("id"));
            cUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            cPassWord.setCellValueFactory(new PropertyValueFactory<>("passWord"));
            cTypeUser.setCellValueFactory(new PropertyValueFactory<>("TypeUser"));
            cSellerName.setCellValueFactory(new PropertyValueFactory<>("SellerName"));
           // cimge.setCellValueFactory(new PropertyValueFactory<>("image"));

            // Set the data for the table
            tb_Users.setItems(data);
            //
//            if(data.size()>0) {
//            	Image image=data.get(0).getUserImg();
//            	userImgView.setImage(image);
//            }
//        	
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

    @FXML
    void btnAdd_Clicked(ActionEvent event) {
        if(Usernamefld.getText().isEmpty()||passWordfld.getText().isEmpty() ||typeUserfld.getText().isEmpty()||sellernamefld.getText().isEmpty()) {
            //
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error add user");
            alert.setHeaderText("Please Complete the form before adding new User");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();

            return;
        }

        // Insert a new person into the database
        String insertQuery = "INSERT INTO users ( userName, passWord, TypeUser, img, SellerName) VALUES (?, ?, ?,?,?)";
        try {
            PreparedStatement pst = db.execQueryPrep(insertQuery);
            pst.setString(1, Usernamefld.getText());
            pst.setString(2, passWordfld.getText());
            pst.setString(3, typeUserfld.getText());
            // Get the image from the ImageView
            Image image = userImgView.getImage();
            if (image != null) {
                // Convert the image to a byte array
                byte[] byteArray = convertImageToByteArray(image);
                // Set the byte array as the input for the img column
                pst.setBytes(4, byteArray);
            } else {
                // If no image is selected, insert null into the img column
                pst.setBytes(4, null);
            }
            pst.setString(5, sellernamefld.getText());
            pst.execute();
            
            //confirme add
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Add");
            alert.setHeaderText("new User has been added successfully");
            alert.setContentText("great job man/women!");
            alert.showAndWait();
            
            // Refresh the table view
        	data.clear();
        	tb_Users.refresh();
        	fillTableUsers();
        	
        } catch (SQLException ex) {
        	System.out.println("oooooooooo");
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
    void btnDelete_Clicked(ActionEvent event) {
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
        String deleteQuery = "DELETE FROM users WHERE idUser = "+this.userid;
        if (db.execAction(deleteQuery)) {
            System.out.println("Successfully deleted person with id "+this.userid);
            data.clear();
        	tb_Users.refresh();
        	fillTableUsers();
        } else {
            System.out.println("Error deleting person with id "+this.userid);
        }
    	

    }

    @FXML
    void btnUpdate_Clicked(ActionEvent event) {
        byte[] byteArrayImg= convertImageToByteArray(userImgView.getImage());
    	
    	// Update
        String updateQuery = "UPDATE users SET userName = '"+Usernamefld.getText()+"' ,passWord = '"+passWordfld.getText()+"' ,TypeUser= '"+typeUserfld.getText()+"',img= ? ,SellerName='"+sellernamefld.getText()+"' WHERE idUser="+this.userid+" ";
        PreparedStatement pst = db.execQueryPrep(updateQuery);
        try {
			pst.setBytes(1, byteArrayImg);
	         pst.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
        
        	 //confirme add
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("confirme Update");
            alert.setHeaderText("User has been Updated successfully");
            alert.setContentText("great job man/women!");
            alert.showAndWait();
        	
        	// Refresh the table view
        	data.clear();
        	tb_Users.refresh();
        	fillTableUsers();
       
    }
    
   
    @FXML
    void onUserImageView_Clicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Pass the window of the scene that contains the ImageView to the showOpenDialog method
        File selectedFile = fileChooser.showOpenDialog(userImgView.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            userImgView.setImage(image);
        }
    }


   
    
    @FXML
    void btnUp_clicked(ActionEvent event) {
    	tb_Users.getSelectionModel().selectPrevious();
    	selectedItem();
    }
    
    @FXML
    void btndown_Clicked(ActionEvent event) {
    	tb_Users.getSelectionModel().selectNext();
    	selectedItem();	
    }
    
    @FXML
    void btnexit_Cliked(ActionEvent event) {
    	Stage stage = (Stage) btnexit.getScene().getWindow();
    	stage.close();


    } 
    
    @FXML
    void onMouseClickRow(MouseEvent event) {
    	selectedItem();

    }
    
    
    private void selectedItem() {
    	User user=tb_Users.getSelectionModel().getSelectedItem();
    	int index=tb_Users.getSelectionModel().getSelectedIndex();

    		try {
    				this.userid=user.getId();
    			    Usernamefld.setText(user.getUserName());
    	        	passWordfld.setText(user.getPassWord());
    	        	typeUserfld.setText(user.getTypeUser());
    	        	sellernamefld.setText(user.getSellerName());
            	   	userImgView.setImage(data.get(index).getUserImg());
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
    			return;
			}
	   	
    }
}
