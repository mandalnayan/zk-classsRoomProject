package com.iispl.controller;

import java.util.List;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import com.iispl.Model.User;
import com.iispl.service.UserService;

public class UserComposer extends SelectorComposer<Vbox> {

	@Wire
	private Listbox userLsb;

	@Wire
	Textbox tSearch;

	@Override
	public void doAfterCompose(Vbox comp) throws Exception {
		super.doAfterCompose(comp);
		
		List<User> users = UserService.getUsers();
		mapDatatoView(users);
		
	}

	@Listen("onClick = #searchBtn")
	public void loadDataBySearch() {
		String searchName = tSearch.getText();
		List<User> users = UserService.getUsersByName(searchName);
		alert("Fetching:L= " + users.size());
		mapDatatoView(users);
	}

	private void mapDatatoView(List<?> data) {
		List<User> users = UserService.getUsers();
		if (users.isEmpty()) {
			Clients.showNotification("No Users found", Clients.NOTIFICATION_TYPE_WARNING, userLsb, 100, 100, 2000);
		} else {
			ListModelList<User> lsa = new ListModelList(users);
			userLsb.setModel(lsa);
		}
	}
}
