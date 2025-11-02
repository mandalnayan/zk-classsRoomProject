package com.iispl.service;

import org.zkoss.zk.ui.util.Clients;

import com.iispl.dao.UserCRUD;

/**
 * Verifying & submitting user details to database
 */
public class RegistrationService {

	public static boolean submitForm(String name, String mobNo, String email, String password1, String password2) {
		
		if (name == null || mobNo == null || email == null || password1 == null) {
			Clients.showNotification("All the fields are requird..!", true);
		}
		
		else if (!password1.equals(password2)) {
			Clients.showNotification("Password doesn't match", true);
		} else {
			return UserCRUD.insertUser(name, mobNo, email, password1);
		}
		
		return false;
	}
}
