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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import model.OrderDB;

public class AdminOrderPageController implements Initializable {
	@FXML
	private TableView ordertable;
	@FXML
	private TableColumn orderid;
	@FXML
	private TableColumn custid;
	@FXML
	private TableColumn totalprice;
	@FXML
	private TableColumn totalpriceplustax;
	@FXML
	private TableColumn orderdate;
	@FXML
	private TableColumn orderstatus;
	@FXML
	private ChoiceBox selectStatus;
	@FXML
	private Label errorMsg, successMsg;

	ObservableList<Map> allData = null;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadStatusValues();
		try {
			OrderDB oDbObj = new OrderDB();
			refreshOrderFxTable(oDbObj.fetchOrderDetailsFromDB());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadStatusValues() {
		selectStatus.setItems(
				FXCollections.observableArrayList("Processing Order", "Out for Delivery", "Delivered", "Cancelled"));
	}

	@FXML
	public void backBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminFunctions.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	public void logoutBtnAction(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"));
		Scene scene = new Scene(parent, 600, 500);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	public void updateOrderStatus(ActionEvent event) {
		Object selectedRowStatusUpdate = ordertable.getSelectionModel().getSelectedItem();
//		System.out.println("selectedRowStatusUpdate: " + selectedRowStatusUpdate);

		try {
			if (selectedRowStatusUpdate != null) {
				
				String selectedOrder = ((selectedRowStatusUpdate.toString().split(",")[5]
						.substring(selectedRowStatusUpdate.toString().split(",")[5].lastIndexOf("=") + 1,
								selectedRowStatusUpdate.toString().split(",")[5].indexOf("}"))
						.replaceFirst("\\s+", "")));
				System.out.println("selectedOrder: " + selectedOrder);

				if (selectStatus.getValue() != null) {
					OrderDB oDbObj = new OrderDB();
					oDbObj.updateOrderStatus(Integer.parseInt(selectedOrder), selectStatus.getValue().toString());
					successMsg.setText("Order status has been updated for Order #" + selectedOrder);
					refreshOrderFxTable(oDbObj.fetchOrderDetailsFromDB());
					errorMsg.setText("");
					selectStatus.valueProperty().set(null);
				} else {
					successMsg.setText("");
					errorMsg.setText("Select status");
				}
			}else {
				errorMsg.setText("Select an order");
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void refreshOrderFxTable(ResultSet rs) {
		System.out.println("entered refreshOrderFxTable method");
		allData = FXCollections.observableArrayList();

		try {
			while (rs.next()) {

				Map<String, String> dataRow = new HashMap<>();
				dataRow.put("order_id", rs.getInt(1) + "");
				dataRow.put("user_id", rs.getInt(2) + "");
				dataRow.put("total_price", rs.getString(3));
				dataRow.put("total_price_with_tax", rs.getString(4));
				dataRow.put("order_date_time", rs.getString(5));
				dataRow.put("order_status", rs.getString(6));

				allData.add(dataRow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		orderid.setCellValueFactory(new MapValueFactory("order_id"));
		custid.setCellValueFactory(new MapValueFactory("user_id"));
		totalprice.setCellValueFactory(new MapValueFactory("total_price"));
		totalpriceplustax.setCellValueFactory(new MapValueFactory("total_price_with_tax"));
		orderdate.setCellValueFactory(new MapValueFactory("order_date_time"));
		orderstatus.setCellValueFactory(new MapValueFactory("order_status"));
		ordertable.setItems(allData);
	}
}
