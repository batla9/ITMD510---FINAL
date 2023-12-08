package controller;

public final class FOS_Constants {
	//Defining table names
	private static final String MENU_TABLE_NAME = "fooditemdb_11";
	private static final String CUSTOMER_TABLE_NAME = "OROS_UserDetailDB_02";
	private static final String ORDER_TABLE_NAME = "OROS_OrderDB_01";
	
	//Getters & Setters
	//1. For table names
	public static String getMenuTableName() {
		return MENU_TABLE_NAME;
	}

	public static String getCustomerTableName() {
		return CUSTOMER_TABLE_NAME;
	}

	public static String getOrderTableName() {
		return ORDER_TABLE_NAME;
	}	
}
