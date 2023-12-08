package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import controller.FOS_Constants;

public class OrderDB {
	DBConnect dbc = null;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public OrderDB() throws SQLException {
		dbc = new DBConnect();
	}
	
	// Create table for storing Customer's Order Details
	public void createOrderDBTable() {
		try {

			// Open a connection
			System.out.println("Connecting to database to create OrderDB Table...");
			System.out.println("Connected database successfully...");

			if (!isOrderTableExists()){
				stmt = dbc.connect().createStatement();
				// Execute create query
			System.out.println("Creating OrderDB table in given database...");
			String sql = "CREATE TABLE " + FOS_Constants.getOrderTableName()
					+ "(order_id INTEGER NOT NULL AUTO_INCREMENT, " + "total_price float, "
					+ "total_price_with_tax float, " + "user_id INTEGER ," + "order_date_time TIMESTAMP ," + "order_status VARCHAR(30)," + "FOREIGN KEY (user_id) REFERENCES "
					+ FOS_Constants.getMenuTableName() + " (menu_id)," + " PRIMARY KEY(order_id))";

			stmt.executeUpdate(sql);
			System.out.println("Created OrderDB table in given database...");
			} else {
				System.out.println("OrderDB table already exists...");
			}
			
			dbc.connect().close(); // close db connection
		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	// Check if the Order table already exists
	private boolean isOrderTableExists() throws SQLException {
		ResultSet resultSet = dbc.connect().getMetaData().getTables(null, null, FOS_Constants.getOrderTableName(), null);
	
		return resultSet.next();
	}

	// Insert records into Order DB table
	public void insertOrderDetailRecords(float totalPrice, int userid, String username, float totalPriceWithTax) {
		try {
			stmt = dbc.connect().createStatement();
			String orderStatus = "Order Placed";
			
			String currentDateString = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
//			java.util.Date currentDate = Calendar.getInstance().getTime();
//			String currentDateString = currentDate.toString();

			String sql = null;
			sql = "INSERT INTO " + FOS_Constants.getOrderTableName() + " SET total_price = '" + totalPrice
					+ "', total_price_with_tax = '" + totalPriceWithTax + "', order_status = '" + orderStatus
					+ "', order_date_time = '" + currentDateString + "', user_id = (SELECT " + userid + " FROM "
					+ FOS_Constants.getCustomerTableName() + " WHERE username = '" + username + "')";

			stmt.executeUpdate(sql);
			System.out.println("Records Inserted into UserDetailDB.");
			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public ResultSet fetchOrderDetailsFromDB(int userId) {
		ResultSet rs = null;

		try {
			stmt = dbc.connect().createStatement();

			String sql = null;
			sql = "SELECT order_id, user_id, total_price, total_price_with_tax, order_date_time, order_status FROM "
					+ FOS_Constants.getOrderTableName() + " WHERE user_id = '" + userId + "'";
			System.out.println("fetch order sql statment: " + sql);

			rs = stmt.executeQuery(sql);
			System.out.println("rs: " + rs);
			System.out.println("Successfully fetch details from OrderDB");

			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return rs;
	}

	public ResultSet fetchOrderDetailsFromDB() {
		ResultSet rs = null;

		try {
			stmt = dbc.connect().createStatement();

			String sql = null;
			sql = "SELECT order_id, user_id, total_price, total_price_with_tax, order_date_time, order_status FROM "
					+ FOS_Constants.getOrderTableName();
			System.out.println("fetch order sql statment: " + sql);

			rs = stmt.executeQuery(sql);
			System.out.println("rs: " + rs);
			System.out.println("Successfully fetch details from OrderDB");

			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return rs;
	}

	public ResultSet fetchOrderFromDB(int orderId) {
		ResultSet rs = null;

		try {
			stmt = dbc.connect().createStatement();

			String sql = null;
			sql = "SELECT order_id, user_id, total_price, total_price_with_tax, order_date_time, order_status FROM "
					+ FOS_Constants.getOrderTableName() + " WHERE order_id = " + orderId;
			System.out.println("fetch order sql statment: " + sql);

			rs = stmt.executeQuery(sql);
			System.out.println("rs: " + rs);
			System.out.println("Successfully fetch details from OrderDB");

			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}

		return rs;
	}

	public void updateOrderStatus(int orderId, String orderStatus) throws SQLException {
		Connection conn = null;
		dbc = new DBConnect();

		stmt = dbc.connect().createStatement();
		String query = "Update " + FOS_Constants.getOrderTableName() + " SET order_status = '" + orderStatus
				+ "' WHERE order_id = ' " + orderId + "'";
		stmt.executeUpdate(query);
		dbc.connect().close();
	}
}
