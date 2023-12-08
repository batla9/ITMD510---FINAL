package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CustomerFunctions implements Initializable{
	
	@FXML
	private Label username, userid;
	String userName;
	int userId;
	
	@FXML
	private ImageView customerfuncimg;
	
	@FXML
	public void setUserName(String uname) {
		username.setText(uname);
		userName = username.getText();
		System.out.println("username in cust func page: "+username);
	}
	
	@FXML
	public void setUserId(int id) {
		userid.setText(id+"");
		userId = Integer.parseInt(userid.getText());
		System.out.println("userid in cust func page: "+userid);
		System.out.println("uid in cust func page: "+userId);
	}
	
	@FXML
	public void addToCart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddToCart.fxml"));
		Parent parent =(Parent) loader.load();
		AddToCartController addtocart = loader.getController();
		addtocart.setUserName(userName);
		addtocart.setUserId(userId);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);  
	}
	
	@FXML
	public void orderStatusCheck(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerOrderPage.fxml"));
		Parent parent =(Parent) loader.load();
		CustomerOrderPageController custOrderObj = loader.getController();
		custOrderObj.setUserName(userName);
		custOrderObj.setUserId(userId);
		custOrderObj.fetchOrderDetails(userName,userId);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2); 
	}
	
	@FXML
	private void homeLinkAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Home link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML
	private void menuLinkAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Menu link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerMenuPage.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	private void contactUsLinkAction(ActionEvent event) throws IOException {
		System.out.println("Clicked on Contact Us link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/ContactUs.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML
	private void updateProfile(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerUpdateProfile.fxml"));
		Parent parent =(Parent) loader.load();
		CustomerUpdateProfileController custUpdateProfileObj = loader.getController();
		System.out.println("userId:" + userId);
		custUpdateProfileObj.idPass(userId);
		custUpdateProfileObj.namePass(username.getText());
		custUpdateProfileObj.fetchUserDetails(userId);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML
	private void logout(ActionEvent event) throws IOException {
		System.out.println("Clicked on Logout link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image custimg = new Image("/images/tricolorDosa.jpg");
		this.customerfuncimg.setImage(custimg);
	}
}
