package com.iispl.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.iispl.service.LoginService;

/**
 * Performing all validation (Login)
 */
public class ValidationComposer extends SelectorComposer<Window> {

	@Wire
	Textbox tUserName, tPassword;

	@Listen("onClick = #loginBtn")
	public void doLogin() {
		String userName = tUserName.getText();
		String password = tPassword.getText();

		if (LoginService.authenticate(userName, password)) {
			Clients.showNotification("Login Successfull", Clients.NOTIFICATION_TYPE_INFO, tUserName, 20, 20, 2000);
			Sessions.getCurrent().setAttribute("user", userName);
			Executions.sendRedirect("index.zul");
		} else {
			Clients.showNotification("Unauthorized credentials", Clients.NOTIFICATION_TYPE_ERROR, tUserName, 20, 100,
					4000);			
		}
	}

	/**
	 * Clear the username and password input box
	 */
	private void resetForm() {
		tUserName.setText("");
	//	tPassword.setValue("");
		tPassword.setText("");
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		Clients.showNotification("Enter UserName and Password for login", Clients.NOTIFICATION_TYPE_WARNING , tUserName, 20,
				20, 2000);
		
	}
}
