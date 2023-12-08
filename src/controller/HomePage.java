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
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.*;

public class HomePage implements Initializable{
	
	@FXML
	Hyperlink admin , home , contactus , aboutus , customer;
	@FXML
	private ImageView orderfood ,pavbhaji ,masalapuri , dahipuri ,cheesesand ,idlisambar , bhelpuri;
	
	public void AdminLogin(ActionEvent event) throws IOException {
	   Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
	   Scene scene = new Scene(parent,600,500);
	   Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   stage.setScene(scene);
	   stage.show();
	   
	   Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	   stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	   stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	public void ContactUs(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/ContactUs.fxml"));
	    Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	public void CustomerLogin(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
	    Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public void AboutUs(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AboutUsPage.fxml"));
	    Scene aboutUs = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(aboutUs);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image foodorder = new Image("/images/OnlineFoodOrdering.jpg");
		this.orderfood.setImage(foodorder);
		
		Image pavbhaji = new Image("/images/Pavbhaji.jpg");
		this.pavbhaji.setImage(pavbhaji);
		
		Image masala = new Image("/images/MasalaPuri.jpg");
		this.masalapuri.setImage(masala);
		
		Image dahipuri = new Image("/images/DahiPuri.jpg");
		this.dahipuri.setImage(dahipuri);
		
		Image cheese = new Image("/images/CheeseSandwich.jpg");
		this.cheesesand.setImage(cheese);
		
		Image idli = new Image("/images/IdliSambar.jpg");
		this.idlisambar.setImage(idli);
		
		Image bhelpuri = new Image("/images/BhelPuri.jpg");
		this.bhelpuri.setImage(bhelpuri);

	}
}
