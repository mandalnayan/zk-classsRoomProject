package com.iispl.service;

import com.iispl.dao.UserCRUD;

/**
 * For login process
 */
public class LoginService {

	public static boolean authenticate(String userName, String password) {
		if (userName == null || password == null) return false;
		
		return UserCRUD.isUserPresent(userName, password);
	}
}
