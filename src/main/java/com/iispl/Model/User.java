package com.iispl.Model;

public class User {
	private String id;
	private String userName;
	private String mobileNo;
	private String email;
	private String password;
	
	public User(String id, String userName, String mobileNo, String emailId) {
		this.id = id;
		this.userName = userName;
		this.mobileNo = mobileNo;
		this.email = emailId;
	}

	public String getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmailId(String emailId) {
		this.email = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
