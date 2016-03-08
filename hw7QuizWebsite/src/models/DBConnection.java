// Connection class to Manage retrieving data from the Database
package models;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {
	private String account = DBInfo.MYSQL_USERNAME;
	private String password = DBInfo.MYSQL_PASSWORD;
	private String server = DBInfo.MYSQL_DATABASE_SERVER;
	private String database = DBInfo.MYSQL_DATABASE_NAME;
	private java.sql.Connection con;
	
	// Establish a connection
	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection
					("jdbc:mysql://" + server, account, password); 
			con.createStatement().executeQuery("USE " + database);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Close established connection
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//get Data currently stored inside of the table
	public ResultSet grabTable(String name) {
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM " + name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void addToTable(String table, String values) {
		try {
			con.createStatement().executeUpdate("INSERT into " + table + " VALUES(" + values + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTable(String table, String index, String values) {
		try {
			con.createStatement().executeUpdate("UPDATE " + table + " SET " + values + " WHERE " + index);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public ResultSet grabQuizes() {
//		ResultSet rs = null;
//		try {
//			rs = con.createStatement().executeQuery("SELECT * FROM quizes");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return rs;
//	}
	
	//public void fillCatalog
}
