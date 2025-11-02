package com.iispl.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;

public class DBConnection {
	private static final String DBNAME = "iispl";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Nayan@2002"; 
	
	private static Connection con = null;
	
	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			} catch (SQLException | ClassNotFoundException e) {
				Clients.showNotification("Failed to established DB Connection.!!", true);
				e.printStackTrace();
			}
		}
		return con;
	}
	
}
