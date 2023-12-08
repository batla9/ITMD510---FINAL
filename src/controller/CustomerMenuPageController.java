package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CustomerMenuPageController implements Initializable{
	@FXML
	private Hyperlink home;
	@FXML
	private Hyperlink aboutus;
	@FXML
	private Hyperlink contactus;
	@FXML
	private Hyperlink customer;
	@FXML
	private Hyperlink admin;

	@FXML
	private ImageView pav ,masalap , dahip ,cheesy ,idli , bhel , medu , cabbage , sabudana;
	
	// Event Listener on Hyperlink[#home].onAction
	@FXML
	public void Home(ActionEvent event) throws IOException {
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
	// Event Listener on Hyperlink[#aboutus].onAction
	@FXML
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
	// Event Listener on Hyperlink[#contactus].onAction
	@FXML
	public void ContactUs(ActionEvent event) throws IOException {
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
	// Event Listener on Hyperlink[#customer].onAction
	@FXML
	public void CustomerLogin(ActionEvent event) throws IOException {
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
	// Event Listener on Hyperlink[#admin].onAction
	@FXML
	public void AdminLogin(ActionEvent event) throws IOException {
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image pavb = new Image("/images/Pavbhaji.jpg");
		this.pav.setImage(pavb);
		
		Image masalapuri = new Image("/images/MasalaPuri.jpg");
		this.masalap.setImage(masalapuri);
		
		Image dahipoori = new Image("/images/DahiPuri.jpg");
		this.dahip.setImage(dahipoori);
		
		Image cheesee = new Image("/images/CheeseSandwich.jpg");
		this.cheesy.setImage(cheesee);
		
		Image idlis = new Image("/images/IdliSambar.jpg");
		this.idli.setImage(idlis);
		
		Image bhelpoori = new Image("/images/BhelPuri.jpg");
		this.bhel.setImage(bhelpoori);
		
		Image meduvada = new Image("/images/MeduVada.jpg");
		this.medu.setImage(meduvada);
		
		Image cabbagevada = new Image("/images/CabbageVada.jpg");
		this.cabbage.setImage(cabbagevada);
		
		Image sabudanavada = new Image("/images/SabudanaVada.jpg");
		this.sabudana.setImage(sabudanavada);
	}
}
