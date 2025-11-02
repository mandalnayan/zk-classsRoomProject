package com.iispl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;

public class TicTacToeCRUD {

	/**
	 * Save each button current state to DB
	 * @param btnList
	 * @return
	 */
	public static boolean saveCurrentBtnState(List<Button> btnList) {
		try {
			Connection con = DBConnection.getConnection();
			if (con == null) throw new SQLException();
			PreparedStatement pstm = con.prepareStatement("UPDATE TicTacToe SET value = ? WHERE id = ?");
			
			for (Button button : btnList) {
				String value = button.getLabel();
			//	if (value.isBlank()) continue;
				String id = button.getId();
				pstm.setString(1, value);
				pstm.setString(2, id);
				pstm.addBatch();
			}
			int rs[] = pstm.executeBatch();
			return rs.length > 1;
		} catch (SQLException e) {
			Clients.showNotification("Error" + e.getMessage(), true);
			return false;
		}
	}
	
	/**
	 * Fetch previous state of all button
	 * @return
	 */
	public static ResultSet getCurrentBtnState() {
		try {
			Connection con = DBConnection.getConnection();
			if (con == null) throw new SQLException();
			Statement stmt = con.createStatement();
			String getValueQ = "Select * from TicTacToe";			
		
			ResultSet rs = stmt.executeQuery(getValueQ);

			return rs;
		} catch (SQLException e) {
			Clients.showNotification("CRUD class Error " + e.getMessage(), true);
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deleteCurrentBtnState() {
		try {
			Connection con = DBConnection.getConnection();
			if (con == null) throw new SQLException();
			Statement stmt = con.createStatement();
			String resetValueQ = "UPDATE TicTacToe set value = ''";			
		
			int rs = stmt.executeUpdate(resetValueQ);
			return rs == 9;
		} catch (SQLException e) {
			Clients.showNotification("Error" + e.getMessage(), true);
			return false;
		}
	}
	/**
	 * Creating TicTacToe Table
	 * @return
	 */
	private static boolean createTicTacToeTable() {
		try {
			Connection con = DBConnection.getConnection();
			if (con == null) throw new SQLException();
			Statement stmt = con.createStatement();
			String tableQ = "CREATE TABLE IF NOT EXISTS TicTacToe " +
							"(id varchar(10) Not Null, value varchar(10) Not Null)";
			
			int rs = stmt.executeUpdate(tableQ);
			if (rs >= 0) {
				Clients.showNotification("Table created successfully", Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 1000);				
			}
			return true;
			
		} catch(SQLException e) {
			Clients.showNotification("Error" + e.getMessage(), true);

			return false;
		}
	}
}
