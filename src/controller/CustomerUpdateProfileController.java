package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.CustomerDetailDB;

public class CustomerUpdateProfileController implements Initializable {
	@FXML
	private Label custid;
	@FXML
	private Label custname;
	@FXML
	private Label phoneerror;
	@FXML
	private Label addresserror;
	@FXML
	private Label emailerror;
	@FXML
	private Label nameerror, updatemessage;
	@FXML
	private Button updateprofile;
	@FXML
	private TextField phone;
	@FXML
	private TextField address;
	@FXML
	private TextField email;
	@FXML
	private TextField name;

	@FXML
	int cid;
	ResultSet rs;
	String u_name;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int maxLength = 10;
		phone.textProperty().addListener(new PhoneValidationController(phone, 10));
	}

	@FXML
	public void namePass(String cname) {
		custname.setText(cname);
		u_name = custname.getText();
	}

	@FXML
	public void idPass(int id) {
		custid.setText(id + "");
		cid = Integer.parseInt(custid.getText());
		System.out.println("cid inside idpass method: " + cid);
	}

	// Name Validation
	boolean nameIsNull = false;

	public void ValidateName(TextField name, Label nameerror, String message) {
		if (name.getText().isEmpty()) {
			nameIsNull = true;
			nameerror.setText(message);
		} else {
			nameerror.setText("");
		}
	}

	// Email Validation
	boolean emailisNull = false;

	public void ValidateEmail(TextField email, Label emailerror, String message) {
		if (email.getText().isEmpty()) {
			emailisNull = true;
			emailerror.setText(message);
		} else
			ValidateEmailFormat(email, emailerror);
	}

	boolean isemail = false;

	public void ValidateEmailFormat(TextField email, Label emailerror) {
		if (email.getText().endsWith("@gmail.com") || email.getText().endsWith("@yahoo.com")
				|| email.getText().endsWith("@hawk.iit.edu")) {
			emailerror.setText("");
			isemail = true;
		} else {
			emailerror.setText("Enter a valid e-mail address");
		}

	}

	// Address Validation
	boolean addressIsNull = false;

	public void ValidateAddress(TextField address2, Label addresserror2, String string) {
		if (address2.getText().isEmpty()) {
			addressIsNull = true;
			addresserror.setText(string);
		} else {
			addresserror.setText("");
		}
	}

	// Phone Validation
	boolean phoneIsNull = false;

	public void ValidatePhone(TextField phone, Label phoneerror, String message) throws SQLException {
		if (phone.getText().isEmpty()) {
			phoneIsNull = true;
			phoneerror.setText(message);
		} else
			ValidatePhone1(phone, phoneerror);
	}

	boolean isphone = false;

	public void ValidatePhone1(TextField phone2, Label phoneerror2) throws SQLException {
		if (!phone.getText().matches("^[0-9]{10}$")) {
			isphone = true;
			phoneerror2.setText("Please enter a valid phone number");
		}

		else if (phone.getText().matches("^[0-9]{10}$")) {
			isphone = false;
			phoneerror2.setText("");
		}
	}

	@FXML
	public void updateProfile(ActionEvent event) throws SQLException, IOException {
		CustomerDetailDB custDb = new CustomerDetailDB();
		System.out.println("In Update Profile!!!");

		ValidateName(name, nameerror, "Please enter your name");
		ValidateEmail(email, emailerror, "Please enter your e-mail address");
		ValidateAddress(address, addresserror, "Please enter your address");
		ValidatePhone(phone, phoneerror, "Please enter your phone number");

		// System.out.println("cid: "+cid+"name.getText():
		// "+name.getText()+"email.getText(): "+email.getText()+"address.getText():
		// "+address.getText()+"phone.getText(): "+phone.getText());
		System.out.println("nameIsNull: " + nameIsNull);
		System.out.println("emailisNull: " + emailisNull);
		System.out.println("addressIsNull: " + addressIsNull);
		System.out.println("phoneIsNull: " + phoneIsNull);
		System.out.println("isphone: " + isphone);
		System.out.println("isemail: " + isemail);
		if (nameIsNull == false && emailisNull == false && addressIsNull == false && phoneIsNull == false
				&& isphone == false && isemail == true) {
			custDb.updateCustomerDetails(cid, name.getText(), email.getText(), address.getText(), phone.getText());
			updatemessage.setText("Your profile has been updated successfully!");
		} else {
			updatemessage.setText("Sorry, your profile could not be updated! Please fix the errors.");
		}
	}

	@FXML
	public void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFunctions.fxml"));
		Parent parent = (Parent) loader.load();
		CustomerFunctions cfunc = loader.getController();
		cfunc.setUserName(u_name);
		cfunc.setUserId(cid);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	boolean flag;

	@FXML
	public void fetchUserDetails(int cid) {
		CustomerDetailDB custDb;
		try {
			custDb = new CustomerDetailDB();
			System.out.println("cid:" + cid);
			rs = custDb.retrieveValues(cid);
			System.out.println("Result Set:" + rs);
			while (rs.next()) {
				name.setText(rs.getString(2));
				address.setText(rs.getString(4));
				email.setText(rs.getString(3));
				phone.setText(rs.getString(5));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
