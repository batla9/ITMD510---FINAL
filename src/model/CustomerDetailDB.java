package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.FOS_Constants;

//import controller.CustomerLogin;

public class CustomerDetailDB {
	DBConnect dbc;
	Connection conn;
	Statement stmt = null;
	ResultSet result = null;

	public CustomerDetailDB() throws SQLException {
		dbc = new DBConnect();
	}

	// Create table for storing User Details
	public void createUserDetailDBTable() {
		try {
			// Open a connection
			System.out.println("Connecting to database to create UserDetailDB Table...");
			System.out.println("Connected database successfully...");

			// Execute create query
			System.out.println("Creating UserDetailDB table in given database...");

			stmt = dbc.connect().createStatement();

			/*
			 * String sql = "CREATE TABLE OFOS_UserDetailDB_02 " +
			 * "(user_id INTEGER NOT NULL AUTO_INCREMENT, " + " username VARCHAR(20), " +
			 * " email VARCHAR(30), " + " address VARCHAR(50), " + " phone VARCHAR(10), " +
			 * " password VARCHAR(15), " + " PRIMARY KEY(user_id))";
			 */
			String sql = "CREATE TABLE " + FOS_Constants.getCustomerTableName()
					+ "(user_id INTEGER NOT NULL AUTO_INCREMENT, " + " username VARCHAR(20), " + " email VARCHAR(30), "
					+ " address VARCHAR(50), " + " phone VARCHAR(10), " + " password VARCHAR(300), "
					+ " PRIMARY KEY(user_id))";
			stmt.executeUpdate(sql);
			System.out.println("Created UserDetailDB table in given database...");
			dbc.connect().close(); // close db connection

		} catch (SQLException se) { // Handle errors for JDBC
			se.printStackTrace();
		}
	}

	public boolean insertUserDetailRecords(String username, String email, String address, String phone,
			String password) {
		Boolean flag = false;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;

			// CustomerLogin clpcObj = new CustomerLogin();

			sql = "INSERT INTO " + FOS_Constants.getCustomerTableName() + "(username,email,address,phone,password) "
					+ "VALUES ('" + username + "', '" + email + "', '" + address + "', '" + phone + "', '"
					+ password + "' )";

			stmt.executeUpdate(sql);

			System.out.println("Records Inserted into UserDetailDB.");
			flag = true;
			dbc.connect().close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		System.out.println("flag value after insert: " + flag);
		return flag;
	}

	public boolean searchUserDetailDB(String username, String password) {
		boolean flag = false;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;
			sql = "SELECT username, password FROM " + FOS_Constants.getCustomerTableName() + " WHERE username = '"
					+ username + "' AND password = '" + password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("sql: "+sql);
			System.out.println("rs: "+rs);
			if (rs.next())
				flag = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	public int getIdFromUserDetailDB(String username, String password) {
		boolean flag = false;
		int userId = 0;
		try {
			stmt = dbc.connect().createStatement();
			String sql = null;
			sql = "SELECT user_id FROM " + FOS_Constants.getCustomerTableName() + " WHERE username = '"
					+ username + "' AND password = '" + password + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				userId = rs.getInt(1);
				System.out.println("userId: "+userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userId;
	}

	public ResultSet fetchCustDetailsFromDB() throws SQLException {
		dbc = new DBConnect();
		stmt = dbc.connect().createStatement();

		String query = "SELECT * FROM " + FOS_Constants.getCustomerTableName();

		ResultSet rs = stmt.executeQuery(query);
		dbc.connect().close();

		return rs;
	}

	public void updateCustomerDetails(int custid, String custname, String custemail, String custaddr, String custphone)
			throws SQLException {
		Connection conn = null;
		dbc = new DBConnect();
		System.out.println("Entered update method");
		System.out.println("custid: "+custid+"custname: "+custname+"custemail: "+custemail+"custaddr: "+custaddr+"custphone: "+custphone);

		stmt = dbc.connect().createStatement();
		String query = "Update " + FOS_Constants.getCustomerTableName() + " SET username = '" + custname
				+ "' , email = '" + custemail + "' , address = '" + custaddr + "' , phone = '" + custphone
				+ "' WHERE user_id = '" + custid + "'";
		System.out.println("update query: "+query);
		stmt.executeUpdate(query);
		dbc.connect().close();
	}

	public void deleteCustomer(int id) {
		Connection conn = null;
		dbc = new DBConnect();

		try {
			stmt = dbc.connect().createStatement();
			String sql = "DELETE FROM " + FOS_Constants.getCustomerTableName() + " where user_id = '" + id + "'";
			stmt.executeUpdate(sql);
			dbc.connect().close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public ResultSet retrieveValues(int id) throws SQLException {
		System.out.println("Retrieve Values in Customer Detail DB!!!"+ id);
		Connection conn = null;
		dbc = new DBConnect();
		
		stmt = dbc.connect().createStatement();
		String sql = "Select * from "+ FOS_Constants.getCustomerTableName() +" where user_id = '" + id + "'";
		result = stmt.executeQuery(sql);
		dbc.connect().close();
		return result;
		}
	
	/* public String getHashedPassword(String username) {
        String hashedPassword = null;

        try (Connection connection = dbc.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT password FROM " + FOS_Constants.getCustomerTableName() + " WHERE username = ?")) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    } */
	

}
