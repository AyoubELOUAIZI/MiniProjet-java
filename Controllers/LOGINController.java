package Controllers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnection.Configs;
import application.Categorie;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LOGINController {
	// Get the singleton instance of Configs
    Configs db = Configs.getInstance();
    
   
    @FXML
    private Button btnSignin;

    @FXML
    private PasswordField passwordfailed;

    @FXML
    private TextField userNamefailed;
    
    ObservableList<User> dataUser = FXCollections.observableArrayList();
    
   


	@FXML
    void btnSignInClicked(ActionEvent event) {
    	 if(userNamefailed.getText().isEmpty()||passwordfailed.getText().length()<4) {
             //
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error in Sign in");
             alert.setHeaderText("Please Complete the form before log in");
             alert.setContentText("Ooops, there was an error!");
             alert.showAndWait();
             return;
    }
    	 String Query="SELECT idUser, userName, passWord, TypeUser, img, SellerName FROM users where userName=? and passWord=?";
    	 PreparedStatement pstmt = db.execQueryPrep(Query);
    	 try {
			pstmt.setString(1, userNamefailed.getText());
			 pstmt.setString(2, passwordfailed.getText());
	    	 ResultSet rs = pstmt.executeQuery();
		
    	

    	 if(!rs.next()) {
    	     Alert alert = new Alert(AlertType.ERROR);
    	     alert.setTitle("Log in failed");
    	     alert.setHeaderText("User name or passWord incorect");
    	     alert.setContentText("Ooops, there was an error!");
    	     alert.showAndWait();
    	     return;
    	 }
    	 int id = rs.getInt("idUser");
    	 String userName = rs.getString("userName");
    	 String passWord = rs.getString("passWord");
    	 String TypeUser = rs.getString("TypeUser");
    	 byte[] imgbyteArray = rs.getBytes("img");  
    	 Image image=convertByteArrayToImage(imgbyteArray);
    	 String SellerName = rs.getString("SellerName");        
    	 dataUser.add(new User(id, userName, passWord, TypeUser, image, SellerName));
    	
    	 // Close the ResultSet and the PreparedStatement
    	 rs.close();
    	 pstmt.close();
    	 
    	 // Close the stage
    	 Stage stage = (Stage) btnSignin.getScene().getWindow();
    	 stage.close();
    	 
    	 
    	
    	// dashbordController.iscorrect=true;
    	 
    	// dashbordController c=new dashbordController();
     	//c.SetButtonsVisible();
     // TODO Auto-generated method stub
    	//c.BtnUsers.setVisible(false);	
//    	btnProducts.setVisible(false);	
//    	btnCustomers.setVisible(false);
//    	btnCategories.setVisible(false);	
//    	btmOrders.setVisible(false);
//    	btnSignOut.setVisible(false);
//    	btnSignIn.setVisible(true);
    	
    	// c.btmOrders.setManaged(true);
    	 } catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
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

}
