package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

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
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.CustomerDetailDB;

public class CustomerSignUp implements Initializable {

	@FXML
	private TextField name, email, address, phone;

	@FXML
	private PasswordField password, password2;

	@FXML
	private Button signup;

	@FXML
	private Label nameerror, emailerror, addresserror, phoneerror, passworderror, cpassworderror;

//	DBConnect conn = null;
//	Statement stmt = null;
//
//	public CustomerSignUp() {
//		conn = new DBConnect();
//	}

	// Name Validation
	boolean nameIsNull = false;

	public void ValidateName(TextField name, Label nameerror, String message) {
		if (name.getText().isEmpty()) {
			nameIsNull = true;
			nameerror.setText(message);
		}else {
			nameIsNull = false;
			nameerror.setText("");
		}

	}

	// Email Validation
	boolean emailisNull = false;

	public void ValidateEmail(TextField email, Label emailerror, String message) {

		if (email.getText().isEmpty()) {
			emailisNull = true;
			emailerror.setText(message);
		} else {
			emailisNull = false;
			ValidateEmailFormat(email, emailerror);
		}
	}

	boolean isemail = false;
	public void ValidateEmailFormat(TextField email, Label emailerror) {
		if (email.getText().endsWith("@gmail.com") || email.getText().endsWith("@yahoo.com")
				|| email.getText().endsWith("@hawk.iit.edu")) {
			emailerror.setText("");
			isemail = true;
		} else {
			isemail = false;
			emailerror.setText("Please enter a valid e-mail address");
		}
	}

	// Address Validation
	boolean addressIsNull = false;

	public void ValidateAddress(TextField address2, Label addresserror2, String string) {
		// TODO Auto-generated method stub
		if (address2.getText().isEmpty()) {
			addressIsNull = true;
			addresserror.setText(string);
		} else {
			addressIsNull = false;
			addresserror.setText("");
		}
	}

	// Phone Validation
	boolean phoneIsNull = false;

	public void ValidatePhone(TextField phone, Label phoneerror, String message) throws SQLException {
		if (phone.getText().isEmpty()) {
			phoneIsNull = true;
			phoneerror.setText(message);
		} else {
			phoneIsNull = false;
			ValidatePhone1(phone, phoneerror);
		}
	}

	boolean isphone = true;
	
	public void ValidatePhone1(TextField phone2, Label phoneerror2) throws SQLException {
		if(!phone.getText().matches("^[0-9]{10}$")) {
			isphone = false;
			phoneerror2.setText("Please enter a valid phone number");
		}
		else if(phone.getText().matches("^[0-9]{10}$")) {
			isphone = true;
			phoneerror2.setText("");
		}
	}

	boolean passwordIsNull = false;

	public void ValidatePassword(PasswordField password, Label passworderror, String string) {
		if (password.getText().isEmpty()) {
			passwordIsNull = true;
			passworderror.setText(string);
		} else {
			passwordIsNull = false;
			ValidateConfirmPassword(password2, cpassworderror);
			passworderror.setText("");
		}
	}

	boolean confirmPassword = true;
	public void ValidateConfirmPassword(PasswordField password2, Label cpassworderror) {
		if (!(password2.getText().equals(password.getText()))) {
			confirmPassword = false;
			cpassworderror.setText("Please re-enter your password");
		} else {
			confirmPassword = true;
			cpassworderror.setText("");
		}
	}

	public void signUp(ActionEvent event) throws IOException, SQLException {
		CustomerDetailDB custDBObj = new CustomerDetailDB();

		ValidateName(name, nameerror, "Please enter a name");
		ValidateEmail(email, emailerror, "Please enter an e-email address");
		ValidateAddress(address, addresserror, "Please enter a billing address");
		ValidatePhone(phone, phoneerror, "Please enter a contact number");
		ValidatePassword(password, passworderror, "Please enter a password");

		boolean flag2;
		if (nameIsNull == false && emailisNull == false && addressIsNull == false
				&& phoneIsNull == false && passwordIsNull == false && isphone == true && isemail == true && confirmPassword == true) {
			
			//String hashedPassword = BcryptHash.hashPassword("password);
			//String hashedPassword = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
			
			flag2 = custDBObj.insertUserDetailRecords(name.getText(), email.getText(), address.getText(), phone.getText(),
					password.getText());
			
			if(flag2) {
				Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
				Scene scene = new Scene(parent, 600, 500);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
	
				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
				stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			CustomerDetailDB custDBObj = new CustomerDetailDB();
			custDBObj.createUserDetailDBTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int maxLength = 10;
		phone.textProperty().addListener(new PhoneValidationController(phone, 10));
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

}
