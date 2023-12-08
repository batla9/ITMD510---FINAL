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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AdminLogin extends AdminInterface implements Initializable{

	@FXML
	private TextField user;

	@FXML
	private PasswordField password;

	@FXML
	private Label label;

	@FXML
	private Button button;

	@FXML
	private Button home;
	
	@FXML
	private ImageView adminpass , adminuser ;  


	@Override
	@FXML
	public void Authorize(ActionEvent event) {
			try {
			if (("admin".equals(user.getText()) && ("admin123".equals(password.getText())))) {
				Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctions.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
	
				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	
			} else {
				label.setText("Invalid Credentials! Please try again!");
				user.setText("");
				password.setText("");
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	@FXML
	public void Home(ActionEvent event) {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image user = new Image("/images/UserIcon.png");
		this.adminuser.setImage(user);
		Image passwo = new Image("/images/Password.png");
		this.adminpass.setImage(passwo);
	}
}
