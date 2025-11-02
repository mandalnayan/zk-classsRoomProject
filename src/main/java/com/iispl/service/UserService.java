package com.iispl.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.util.Clients;

import com.iispl.Model.User;
import com.iispl.dao.UserCRUD;

public class UserService {

	public static List<User> getUsers() {
		List<User> users = new ArrayList<>();
		ResultSet rs = UserCRUD.getUsers();
		
			try {
				while(rs.next()) {
					String id = "IISPL0"+ rs.getInt(1);
					String name = rs.getString(2);
					String email = rs.getString(3);
					String mobNo = rs.getString(4);
					
					users.add(new User(id, name, email, mobNo));
				}
			} catch (SQLException e) {
				Clients.showNotification("Service class Error: " + e.getMessage(), true);
				e.printStackTrace();
			}
			return users;
	}
	
	public static List<User> getUsersByName(String searchName) {
		ResultSet rs = UserCRUD.getUsers(searchName);
		return resultSetToList(rs);
	}
	
	private static List<User> resultSetToList(ResultSet rs) {
		List<User> users = new ArrayList<>();
		try {
			while(rs.next()) {
				String id = "IISPL0"+ rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String mobNo = rs.getString(4);
				
				users.add(new User(id, name, email, mobNo));
			}
		} catch (SQLException e) {
			Clients.showNotification("Service class Error: " + e.getMessage(), true);
			e.printStackTrace();
		}
		return users;
	}
}
