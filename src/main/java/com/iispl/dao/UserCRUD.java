package com.iispl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.zkoss.zk.ui.util.Clients;

import com.iispl.Model.User;

/**
 * Performing all DB operation for user
 */
public class UserCRUD {

	/**
	 * Inserting new User record
	 * 
	 * @param name
	 * @param mobNo
	 * @param email
	 * @param password
	 * @return
	 */
	public static boolean insertUser(String name, String mobNo, String email, String password) {
		try {
			Connection con = DBConnection.getConnection(); 
			if (con == null) return false;
			PreparedStatement pstm = con
					.prepareStatement("Insert Into User(name, mobileNo, email, password) Values(?, ?, ?, ?)");
		
			pstm.setString(1, name);
			pstm.setString(2, mobNo);
			pstm.setString(3, email);
			pstm.setString(4, password);

			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			Clients.showNotification("Failed to insert user record!! " + e.getMessage(), true);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Checking either user exist or not
	 * @param email
	 * @param password
	 * @return
	 */
	public static boolean isUserPresent(String email, String password) {
		try {
		Connection con = DBConnection.getConnection();
			if (con == null) throw new SQLException();
			PreparedStatement pstm = con.prepareStatement("Select name from User where email = ? and password = ?");
			pstm.setString(1, email);
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			
			return rs.next();
		} catch(SQLException e) {
			Clients.showNotification("Failed to fetch user.. Error: " + e.getMessage(), true);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Fetching User data by email ad password
	 * @param email
	 * @param password
	 * @return
	 */
	public static User getUserByEmail(String email, String password) {
		try {
			Connection con = DBConnection.getConnection(); 
			if (con == null) throw new SQLException();
			PreparedStatement pstm = con.prepareStatement("Select * from User where email = ? and password = ?");
			pstm.setString(1, email);
			pstm.setString(2, password);
			ResultSet rs = pstm.executeQuery();
			
			User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			return user;
		} catch(SQLException e) {
			Clients.showNotification("Failed to fetch user.. Error: " + e.getMessage(), true);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Fetch all user from DB
	 * @return
	 */
	public static ResultSet getUsers() {
		try {
			Connection con = DBConnection.getConnection(); 
			if (con == null) throw new SQLException();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("Select * from User");
			
			return rs;
		} catch(SQLException e) {
			Clients.showNotification("Failed to fetch users.. Error: " + e.getMessage(), true);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Fetch user by name
	 * @return
	 */
	public static ResultSet getUsers(String searchName) {
		try {
			Connection con = DBConnection.getConnection(); 
			if (con == null) throw new SQLException();
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("Select * from User where name ='" + searchName + "'");
			
			return rs;
		} catch(SQLException e) {
			Clients.showNotification("Failed to fetch users.. Error: " + e.getMessage(), true);
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creating User table
	 * 
	 * @return True if table has created else False
	 */
	public static boolean createUser() {
		try {
			Connection con = DBConnection.getConnection();
			if (con == null) return false;
			Statement st = con.createStatement();
			String createQuery = "CREATE TABLE if Not Exists User(id int auto_increment primary key, name varchar(20) Not Null, mobileNo varchar(20) Not Null, email varchar(20), password varchar(20) Not Null)";
			st.executeUpdate(createQuery);
			
			return true;
		} catch (SQLException e) {
			Clients.showNotification("Failed to create user table.. Error: " + e.getMessage(), true);
			e.printStackTrace();
			return false;
		}
	}
}
