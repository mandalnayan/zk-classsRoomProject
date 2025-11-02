package com.iispl.controller;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Textbox;

import com.iispl.service.RegistrationService;

public class RegistrationFormComposer extends SelectorComposer<Hbox>{

	@Wire
	Textbox tName, tEmail, tPass, tPass2, tMobNo;
	
	
	@Listen("onClick = #submitRegForm")
	public void sumbitForm() {
		
		String name = tName.getValue();
		String email = tEmail.getValue();
		String mobileNo = tMobNo.getValue();
		String password = tPass.getValue();
		String password2 = tPass2.getValue();
		
	//	Clients.showNotification("Registration successfully..!", Clients.NOTIFICATION_TYPE_ERROR);
		//Clients.
		if (RegistrationService.submitForm(name, mobileNo, email, password, password2)) {
			Clients.showNotification("Registraton successfully..!", Clients.NOTIFICATION_TYPE_INFO, tName, 25, 30, 2000);
			Executions.sendRedirect("login.zul");
		}
		else 
			Clients.showNotification("Failed to register", Clients.NOTIFICATION_TYPE_ERROR, tEmail, 25, 55, 2000);
	}
}
