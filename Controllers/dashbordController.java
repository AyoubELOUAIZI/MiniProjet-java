package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class dashbordController {
	private Stage stage;
	 private Scene scene;
	 private Parent root;
	  static boolean iscorrect;
	 
	 //
	   @FXML
	    public Label disclab1;

	    @FXML
	    public Label disclab2;

	    @FXML
	    public Label disclab3;

	    @FXML
	    public Label disclab4;

	    

	    @FXML
	    public Label typeuserlabe;

	    @FXML
	    public ImageView userImage;

	    @FXML
	    public Label usernamelab;

	 //
	 
	  @FXML
	    public Button btnSignOut;
	  
	 @FXML
	    private Button btnClose;
	 
    @FXML
    public Button btnSignIn;

    @FXML
    private Pane righPanelId;
    
    @FXML
    public Button BtnUsers;

    @FXML
    public Button btmOrders;

    @FXML
    public Button btnCategories;

        @FXML
        public Button btnCustomers;

    @FXML
    public Button btnProducts;
    
    @FXML
    void btnSignOut_clicked(ActionEvent event) {
    	//SetButtonsInVisible();
    }

    
    @FXML
    void initialize() {
    	//SetButtonsInVisible();
    } 
    
    public void SetUserInfo() {
    	
    }


    private void SetButtonsInVisible() {
		// TODO Auto-generated method stub
    	BtnUsers.setVisible(false);	
    	btnProducts.setVisible(false);	
    	btnCustomers.setVisible(false);
    	btnCategories.setVisible(false);	
    	btmOrders.setVisible(false);
    	btnSignOut.setVisible(false);
    	btnSignIn.setVisible(true);
    	
	}
    
    public void SetButtonsVisible() {
		// TODO Auto-generated method stub
    	BtnUsers.setVisible(true);	
    	btnProducts.setVisible(true);	
    	btnCustomers.setVisible(true);
    	btnCategories.setVisible(true);	
    	btmOrders.setVisible(true);
    	btnSignOut.setVisible(true);
    	btnSignIn.setVisible(false);
    	
	}


	

   
	@FXML
    void btnSignIn_clicked(ActionEvent event) {
    	 try {
			Stage stage = new Stage();
		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/LOGIN.FXML"));
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
	    void btnCategories_Clicked(ActionEvent event) {
		  try {
	 			Stage stage = new Stage();
	 		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/Categories.fxml"));
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
	    void btnCustomers_Clicked(ActionEvent event) {
	    	 try {
		 			Stage stage = new Stage();
		 		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/Customers.fxml"));
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
	    void btnOrders_Clicked(ActionEvent event) {

	    }

	    @FXML
	    void btnProducts_Clicked(ActionEvent event) {
	    	 try {
		 			Stage stage = new Stage();
		 		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/Products.fxml"));
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
	    void onBtnClose_clicked(ActionEvent event) {
	    	Stage stage = (Stage) btnClose.getScene().getWindow();
	    	stage.close();
	    }

	   

	    @FXML
	    void btnUsers_Clicked(ActionEvent event) {
	    	 try {
	 			Stage stage = new Stage();
	 		    Parent root = FXMLLoader.load(getClass().getResource("../FXML/Users.fxml"));
	 		    stage.setScene(new Scene(root));
	 		    stage.setTitle("My modal window");
	 		    stage.initModality(Modality.WINDOW_MODAL);
	 		    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
	 		    stage.show();    		   
	     		  } catch(Exception e) {
	     		   e.printStackTrace();
	     		  }	
	    	 
	    	 
	    }

}
//-----------------------------------------------------------//
/*
  this code works but it closes the first window 
  root = FXMLLoader.load(getClass().getResource("../FXML/LOGIN.FXML"));
    		  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		  scene = new Scene(root);
    		  stage.setScene(scene);
    		  stage.show();
 */

/*
 * this one works without closing the window behinde it
 Stage stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("../FXML/LOGIN.FXML"));
    stage.setScene(new Scene(root));
    stage.setTitle("My modal window");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
    stage.show();
 */

	//Pane root1=new Pane();
	//Scene scene1=new Scene(root1);
	//Stage primaryStage = null;
	//primaryStage.setScene(scene1);
	//

