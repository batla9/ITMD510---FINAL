package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ContactUs extends Application implements Initializable{
	
	@FXML
	private ImageView contactUs;

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
	
	public void AboutUs(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AboutUsPage.fxml"));
	    Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML
	private void AdminLogin(ActionEvent event) throws IOException {
		System.out.println("Clicked on Admin Login link");
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
		Scene scene = new Scene(parent);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	@FXML
	private void CustomerLogin(ActionEvent event) throws IOException {
		System.out.println("Clicked on Customer Login link");
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
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Scene scene = new Scene(root,650,650);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ContactUs.fxml"));
		scene.setRoot((Parent) loader.load());
		primaryStage.setTitle("Contact Us");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2); 
		primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image imgcontact = new Image("/images/Contact.png");
		this.contactUs.setImage(imgcontact);
	}
}
