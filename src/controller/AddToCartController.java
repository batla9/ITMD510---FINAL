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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.DBConnect;
import model.MenuDB;
import model.OrderDB;

public class AddToCartController implements Initializable {
	@FXML
	private TableView foodtable;
	@FXML
	private TableView cartTable;
	@FXML
	private TableColumn foodid, foodname, foodprice, fooddesc; // menu table
	@FXML
	private TableColumn fid, itemid, quantityid, removeid; // cart table
	@FXML
	private ChoiceBox quantity;
	@FXML
	private TextField price, tax, pricePlusTax;

	@FXML
	private Label userid, username, orderConfirmMsg, orderConfirmId, errorMsg;
	int userId;
	String userName;

	Double p = 0.0, pt = 0.0;

	ObservableList<Map> allData = null;
	ObservableList<Map> cartData = FXCollections.observableArrayList();
	ObservableList<Map> cartDataQty = FXCollections.observableArrayList();
	ObservableList list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadData();

		MenuDB fItemDBObj = new MenuDB();
		try {
			refreshMenuFxTable(fItemDBObj.fetchMenuItemsFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void addToCart(ActionEvent event) {
		Object selectedItems = foodtable.getSelectionModel().getSelectedItem();

		if (quantity.getValue() != null && selectedItems != null) {
			Map<String, String> cartdatarow = new HashMap<>();
			cartdatarow.put("foodid", (selectedItems.toString().split(",")[0]
					.substring(selectedItems.toString().split(",")[0].lastIndexOf("=") + 1)));
			cartdatarow.put("foodprice",
					((selectedItems.toString().split(",")[1]
							.substring(selectedItems.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+",
									"")));
			cartdatarow.put("foodname",
					(selectedItems.toString().split(",")[3].substring(
							selectedItems.toString().split(",")[3].lastIndexOf("=") + 1,
							selectedItems.toString().split(",")[3].indexOf("}"))));
			cartdatarow.put("foodqty", quantity.getValue().toString());
			cartData.add(cartdatarow);

			fid.setCellValueFactory(new MapValueFactory("foodid"));
			itemid.setCellValueFactory(new MapValueFactory("foodname"));
			quantityid.setCellValueFactory(new MapValueFactory("foodqty"));
			cartTable.setItems(cartData);

			p += (Double.parseDouble(cartdatarow.get("foodprice")) * Integer.parseInt(cartdatarow.get("foodqty")));
			Double pr = Math.floor(p * 100) / 100;
			price.setText(Double.toString(pr));
			pt = Double.parseDouble(price.getText())
					+ (Double.parseDouble(price.getText()) * Double.parseDouble(tax.getText()) / 100);
			Double ptr = Math.floor(pt * 100) / 100;
			pricePlusTax.setText(Double.toString(ptr));

			// Resetting quantity choice box
			quantity.valueProperty().set(null);
			errorMsg.setText("");
		} else if(quantity.getValue() == null){
			errorMsg.setText("Please select a quantity");
		} else if(selectedItems == null) {
			errorMsg.setText("Please select a menu item");
		}
	}

	@FXML
	public void setUserName(String name) {
		username.setText(name);
		userName = username.getText();
	}

	@FXML
	public void setUserId(int id) {
		userid.setText(id + "");
		userId = Integer.parseInt(userid.getText());
		System.out.println("user id in add to cart page: " + userId);
	}

	@FXML
	private void removeFromCart(ActionEvent event) {
		System.out.println("cartTable.getSelectionModel().getSelectedItem(): "+cartTable.getSelectionModel().getSelectedItem());
		Object selectedRowForDeletion = cartTable.getSelectionModel().getSelectedItem();
		
		if(selectedRowForDeletion != null) {
			String foodPrice = ((selectedRowForDeletion.toString().split(",")[1]
					.substring(selectedRowForDeletion.toString().split(",")[1].lastIndexOf("=") + 1)).replaceAll("\\s+",
							""));
			String foodQty = ((selectedRowForDeletion.toString().split(",")[3]
					.substring(selectedRowForDeletion.toString().split(",")[3].lastIndexOf("=") + 1,
					selectedRowForDeletion.toString().split(",")[3].indexOf("}")).replaceAll("\\s+",
							"")));
			System.out.println("Food price = "+ foodPrice);
			System.out.println("Food Qty = "+ foodQty);
			
			p -= (Double.parseDouble(foodPrice) * Integer.parseInt(foodQty));
			Double pr = Math.floor(p * 100) / 100;
			price.setText(Double.toString(pr));
			pt = Double.parseDouble(price.getText())
					+ (Double.parseDouble(price.getText()) * Double.parseDouble(tax.getText()) / 100);
			Double ptr = Math.floor(pt * 100) / 100;
			pricePlusTax.setText(Double.toString(ptr));
			
			cartTable.getItems().removeAll(cartTable.getSelectionModel().getSelectedItem());
			errorMsg.setText("");
		} else {
			errorMsg.setText("Please select a menu item to cart.");
		}
	}

	private void loadData() {
		quantity.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
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

	@FXML
	public void placeOrder(ActionEvent event) throws NumberFormatException, SQLException {
		if (!((price.getText().equalsIgnoreCase("") && (pricePlusTax.getText().equalsIgnoreCase(""))))
				&& Double.parseDouble(price.getText()) > 0.0 && Double.parseDouble(pricePlusTax.getText()) > 0.0) {
			OrderDB od = new OrderDB();
			od.insertOrderDetailRecords(Float.parseFloat(price.getText()), userId, userName,
					Float.parseFloat(pricePlusTax.getText()));
			orderConfirmMsg.setText("Order placed successfully!");
			ResultSet rs = od.fetchOrderDetailsFromDB(Integer.parseInt(userid.getText()));

			int placedOrderNumber = 0;
			while (rs.next()) {
				placedOrderNumber = rs.getInt(1);
			}
			orderConfirmId.setText("Your order number is #" + placedOrderNumber);
		} else {
			errorMsg.setText("Please add a menu item to cart.");
		}
	}

	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	public void customerDashboard(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFunctions.fxml"));
		Parent parent = (Parent) loader.load();
		CustomerFunctions cfunc = loader.getController();
		cfunc.setUserName(userName);
		cfunc.setUserId(userId);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(parent));
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

}
