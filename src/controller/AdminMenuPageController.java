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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.MenuDB;

public class AdminMenuPageController implements Initializable {

	@FXML
	private TableView foodtable;

	@FXML
	private TableColumn foodid, foodname, fooddesc, foodprice;

	@FXML
	private TextField userfoodid, userfoodname, userfoodprice, userfooddesc;

	@FXML
	private Label dbOperationsMsg;

	ObservableList<Map> allData = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MenuDB fItemDBObj = new MenuDB();
    	fItemDBObj.createMenuTableIfNotExists();
		try {
			refreshMenuFxTable(fItemDBObj.fetchMenuItemsFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		foodtable.setOnMouseClicked((MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				System.out.println("clicked menu table");
				setCellValueFromTableToTextField();
			}
		});
	}

	@FXML
	public void addToMenuAction(ActionEvent event) throws Exception {
		MenuDB fItemDBObj = new MenuDB();

		if (!(userfoodname.getText().equalsIgnoreCase("") && userfooddesc.getText().equalsIgnoreCase("")
				&& userfoodprice.getText().equalsIgnoreCase(""))) {
			fItemDBObj.addToFoodTable(userfoodname.getText(), userfooddesc.getText(), userfoodprice.getText());
			refreshMenuFxTable(fItemDBObj.fetchMenuItemsFromDB());

			dbOperationsMsg.setText("Menu item added Successfully!");
			refreshTextFields();
		} else {
			dbOperationsMsg.setText("Please enter an item name, description and price");
		}
	}

	@FXML
	public void updateBtnAction(ActionEvent event) throws SQLException {
		String name = userfoodname.getText();
		String desc = userfooddesc.getText();
		String price = userfoodprice.getText();

		try {
			// updating in database
			if (!(name.equalsIgnoreCase("") && desc.equalsIgnoreCase("") && price.equalsIgnoreCase(""))) {
				int id = Integer.parseInt(userfoodid.getText());
				MenuDB fItemDBObj = new MenuDB();
				fItemDBObj.updateData(id, name, desc, price);

				// refresh FX tableview
				refreshMenuFxTable(fItemDBObj.fetchMenuItemsFromDB());
				dbOperationsMsg.setText("Menu item details have been updated successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("Please select a record from the table.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteBtnAction(ActionEvent event) {
		MenuDB fItemDBObj;
		
		try {
			// deleting from database
			if (!(userfoodid.getText().equalsIgnoreCase(""))){
				fItemDBObj = new MenuDB();
				fItemDBObj.deleteData(Integer.parseInt(userfoodid.getText()));
			
				// deleting from FX tableview
				refreshMenuFxTable(fItemDBObj.fetchMenuItemsFromDB());
				dbOperationsMsg.setText("Deleted Successfully!");
				refreshTextFields();
			} else {
				dbOperationsMsg.setText("Please select a record from the table.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void backBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctions.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	public void logoutBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	    stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
	    stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public void refreshMenuFxTable(ResultSet rs) {
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("fooditem_id", rs.getInt(1) + "");
				dataRow.put("fooditem_name", rs.getString(2));
				dataRow.put("food_desc", rs.getString(3));
				dataRow.put("food_price", rs.getString(4));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		foodid.setCellValueFactory(new MapValueFactory("fooditem_id"));
		foodname.setCellValueFactory(new MapValueFactory("fooditem_name"));
		fooddesc.setCellValueFactory(new MapValueFactory("food_desc"));
		foodprice.setCellValueFactory(new MapValueFactory("food_price"));
		foodtable.setItems(allData);
	}

	private void setCellValueFromTableToTextField() {
		if (foodtable.getSelectionModel().getSelectedItem() != null) {
			Object selectedItems = foodtable.getSelectionModel().getSelectedItem();

			userfoodid.setText((selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			userfoodname.setText((selectedItems.toString().split(",")[3].substring(
					selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedItems.toString().split(",")[3].indexOf("}"))).replaceFirst("\\s+", ""));
			userfoodprice.setText((selectedItems.toString().split(",")[1]
					.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+", ""));
			userfooddesc.setText((selectedItems.toString().split(",")[2]
					.substring(selectedItems.toString().split(",")[2].lastIndexOf("=") + 1)).replaceFirst("\\s+", ""));
		}
	}

	private void refreshTextFields() {
		userfoodid.setText("");
		userfoodname.setText("");
		userfoodprice.setText("");
		userfooddesc.setText("");
	}
}
