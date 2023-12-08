package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.OrderDB;

public class CustomerOrderPageController{
	@FXML
	private Label username, userid, order, orderCancelledMsg;
	
	@FXML
	private TableView ordertable;

	@FXML
	private TableColumn orderid, totalprice, totalpriceplustax, orderdate, orderstatus;
	
	int userId;
	String userName;
	ObservableList<Map> allData = null;
	
	@FXML
	public void setUserName(String name) {
		System.out.println("name in order page: "+ name);
		username.setText(name);
		userName = username.getText();
		System.out.println("userName in order page: "+ userName);
	}
	
	@FXML
	public void setUserId(int id) {
		System.out.println("userid in order page: "+ id);
		userid.setText(id+"");
		userId = Integer.parseInt(userid.getText());
	    System.out.println("userId in add to cart page: "+ userId);
	}
	
	@FXML
	public void fetchOrderDetails(String name, int id) {
		System.out.println("userid in order page (fetchOrderDetails method): "+ id);
		try {
			OrderDB oDbObj = new OrderDB();
			refreshOrderFxTable(oDbObj.fetchOrderDetailsFromDB(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void logout(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/view/CustomerLogin.fxml"));
		Scene scene = new Scene(parent,600,500);
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2); 
		stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
	}

	@FXML
	public void customerDashboard(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomerFunctions.fxml"));
		Parent parent =(Parent) loader.load();
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
	
	@FXML
	public void cancelOrder(ActionEvent event) {
		Object selectedRowStatusUpdate = ordertable.getSelectionModel().getSelectedItem();
		System.out.println("selectedRowStatusUpdate: "+selectedRowStatusUpdate);
		String selectedOrderId = "";
		try {
			if (selectedRowStatusUpdate != null) {
				selectedOrderId = ((selectedRowStatusUpdate.toString().split(",")[4]
						.substring(selectedRowStatusUpdate.toString().split(",")[4].lastIndexOf("=") + 1,
								selectedRowStatusUpdate.toString().split(",")[4].indexOf("}"))
						.replaceFirst("\\s+", "")));
				String selectedOrderStatus = (selectedRowStatusUpdate.toString().split(",")[1]
						.substring(selectedRowStatusUpdate.toString().split(",")[1].lastIndexOf("=") + 1));
				System.out.println("selectedOrderStatus: " + selectedOrderStatus);
				if(selectedOrderStatus.equalsIgnoreCase("Order Placed")) {
					String cancelStatus = "Cancelled";
					System.out.println("Entered if block to cancel order");
					OrderDB oDbObj = new OrderDB();
					oDbObj.updateOrderStatus(Integer.parseInt(selectedOrderId), cancelStatus);
					refreshOrderFxTable(oDbObj.fetchOrderDetailsFromDB(userId));
					orderCancelledMsg.setText("Your Order #"+selectedOrderId+" has been cancelled.");
				}else {
					orderCancelledMsg.setText("Order #"+selectedOrderId+" cannot be cancelled.");
				}
			}else {
				orderCancelledMsg.setText("Select an order.");
			}
		} catch (SQLException e) {
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
		totalprice.setCellValueFactory(new MapValueFactory("total_price"));
		totalpriceplustax.setCellValueFactory(new MapValueFactory("total_price_with_tax"));
		orderdate.setCellValueFactory(new MapValueFactory("order_date_time"));
		orderstatus.setCellValueFactory(new MapValueFactory("order_status"));
		ordertable.setItems(allData);
	}
}
