package com.iispl.controller;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 * when home page is loaded then save the current session
 */
public class HomeController extends SelectorComposer<Window>{

	@Wire 
	Hbox afterLogin, beforeLogin;
	
	@Wire
	Vbox userData;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		if (Sessions.getCurrent().getAttribute("user") != null) {
			revert(true);
		} else {
			revert(false);
		}
	}
	
	@Listen("onClick = #afterLogin #logout")
	public void logout() {
		Sessions.getCurrent().setAttribute("user", null);
		revert(false);
	
	}
	
	private void revert(boolean isLogined) {
		afterLogin.setVisible(isLogined);
		beforeLogin.setVisible(!isLogined);
		userData.setVisible(isLogined);
	}
}
