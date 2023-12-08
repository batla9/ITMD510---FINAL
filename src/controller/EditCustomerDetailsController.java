package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CustomerDetailDB;
import model.MenuDB;

public class EditCustomerDetailsController implements Initializable {
	@FXML
	private TableView customertable;
	@FXML
	private TableColumn custid, custname, custemail, custaddr, custphone;
	@FXML
	private TextField usercustid, usercustname, usercustemail, usercustaddr, usercustphone;
	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		int maxLength = 10;
		usercustphone.textProperty().addListener(new PhoneValidationController(usercustphone, 10));

		CustomerDetailDB custDetailDBObj;
		try {
			custDetailDBObj = new CustomerDetailDB();
			refreshCustomerFxTable(custDetailDBObj.fetchCustDetailsFromDB());

			customertable.setOnMouseClicked((MouseEvent event) -> {
				if (event.getClickCount() == 1) {
					System.out.println("clicked cust table");
					setCellValueFromTableToTextField();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setCellValueFromTableToTextField() {
		System.out.println("Entered setCellValueFromTableToTextField method");
		if (customertable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = customertable.getSelectionModel().getSelectedItem();
			System.out.println("selectedItems: " + selectedItems);

			usercustid.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			usercustname.setText((selectedItems.toString().split(",")[4].substring(
					selectedItems.toString().split(",")[4].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[4].indexOf("}"))).replaceFirst("\\s+", ""));
			usercustemail.setText((selectedItems.toString().split(",")[3]
					.substring(selectedItems.toString().split(",")[3].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			usercustaddr.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
			usercustphone.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void updateCustDbAction(ActionEvent event) throws SQLException {

		String name = usercustname.getText();
		String email = usercustemail.getText();
		String addr = usercustaddr.getText();
		String phone = usercustphone.getText();

		try {
			if (!(name.equalsIgnoreCase("") && email.equalsIgnoreCase("") && addr.equalsIgnoreCase("")
					&& phone.equalsIgnoreCase(""))) {
				int id = Integer.parseInt(usercustid.getText());
				// updating in database
				CustomerDetailDB custDBObj = new CustomerDetailDB();
				custDBObj.updateCustomerDetails(id, name, email, addr, phone);

				// refresh FX tableview
				refreshCustomerFxTable(custDBObj.fetchCustDetailsFromDB());

				dbOperationsMsg.setText("Updated Successfully!");
			} else {
				dbOperationsMsg.setText("Please select a record!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void deleteCustDbAction(ActionEvent event) throws SQLException {
		try {
			if (!usercustid.getText().equalsIgnoreCase("")) {
				// deleting from database
				CustomerDetailDB custDBObj = new CustomerDetailDB();
				custDBObj.deleteCustomer(Integer.parseInt(usercustid.getText()));
				
				// deleting from FX tableview
				refreshCustomerFxTable(custDBObj.fetchCustDetailsFromDB());

				dbOperationsMsg.setText("Deleted Successfully!");
			}else {
				dbOperationsMsg.setText("Please select a record!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void backBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctions.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	// Event Listener on Button.onAction
	@FXML
	public void logoutBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void refreshCustomerFxTable(ResultSet rs) {
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {
				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("user_id", rs.getInt(1) + "");
				dataRow.put("username", rs.getString(2));
				dataRow.put("email", rs.getString(3));
				dataRow.put("address", rs.getString(4));
				dataRow.put("phone", rs.getString(5));
				allData.add(dataRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		custid.setCellValueFactory(new MapValueFactory("user_id"));
		custname.setCellValueFactory(new MapValueFactory("username"));
		custemail.setCellValueFactory(new MapValueFactory("email"));
		custaddr.setCellValueFactory(new MapValueFactory("address"));
		custphone.setCellValueFactory(new MapValueFactory("phone"));
		customertable.setItems(allData);
	}
}
