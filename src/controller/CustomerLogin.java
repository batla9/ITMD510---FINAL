 package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.CustomerDetailDB;

public class CustomerLogin implements Initializable{
	@FXML
	private TextField username, password;
	
	@FXML 
	private Label loginStatus;

	@FXML
	private Button loginBtn;

	@FXML
	private Hyperlink signup;
	
	int user_id;
	@FXML
	private ImageView pass , user ;

	public void signUp(ActionEvent event) throws IOException, SQLException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerSignUp.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}
	
	public String getusername() {
		return username.getText();
	}

	@FXML
	public void loginButtonAction(ActionEvent event) throws IOException, SQLException {
		System.out.println("Clicked on Customer Login button");
		CustomerDetailDB custDbObj = new CustomerDetailDB();
		
		boolean flag = custDbObj.searchUserDetailDB(username.getText(), password.getText());
		System.out.println("flag = "+flag);
		
		if(flag) {
			user_id = custDbObj.getIdFromUserDetailDB(username.getText(), password.getText());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFunctions.fxml"));
			Parent parent =(Parent) loader.load();
			CustomerFunctions cfunc = loader.getController();
			cfunc.setUserName(username.getText());
			System.out.println("user_id in customer login page: "+user_id);
			cfunc.setUserId(user_id);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(parent));
			stage.show();
			
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
		    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

			}
		else {
			loginStatus.setText("Invalid credentials! Please try again!");
		}
	}

	public void Home(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		Scene scene = new Scene(parent, 600, 500);
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
		Image usericon = new Image("/images/UserIcon.png");
		this.user.setImage(usericon);
		
		Image passw = new Image("/images/Password.png");
		this.pass.setImage(passw);
		
		
	}

}
