package com.iispl.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;

import com.iispl.dao.TicTacToeCRUD;

public class GameService {

	/**
	 * Checking winner
	 * 
	 * @param btnList
	 * @return
	 */
	public static String checkWinner(List<Button> btnList) {

		int dirs[][] = { { 0, 1, 2 }, { 2, 5, 8 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 3, 4, 5 }, { 0, 4, 8 },
				{ 2, 4, 6 } };

		for (int dir[] : dirs) {
			Button b1 = btnList.get(dir[0]), b2 = btnList.get(dir[1]), b3 = btnList.get(dir[2]);

			if (!b1.getLabel().isBlank() && b1.getLabel().equals(b2.getLabel())
					&& b2.getLabel().equals(b3.getLabel())) {
				disableAllButton(btnList);
				return b1.getLabel() + " Won the game..!";
			}
		}
		return null;
	}

	/**
	 * Once the game finished disable all the remaining button
	 */
	private static void disableAllButton(List<Button> btnList) {
		btnList.forEach((button) -> {
			if (button != null)
				button.setDisabled(true);
		});
	}

	/**
	 * Save Current state of button
	 * 
	 * @param btnList
	 * @return
	 */
	public static boolean saveCurrentState(List<Button> btnList) {
		return TicTacToeCRUD.saveCurrentBtnState(btnList);
	}

	/**
	 * Fetch Current state of Button *
	 */
	public static List<String> getBtnsState() {
		ResultSet rs = TicTacToeCRUD.getCurrentBtnState();
		
		List<String> btnValues = new ArrayList<>();
		try {
			if (rs == null) return null;	

			while (rs.next()) {
				btnValues.add(rs.getString(2));
			//	Clients.showNotification(rs.getString(1), true);
			}
			return btnValues;
		} catch (SQLException e) {
			e.printStackTrace();
			Clients.showNotification("Service class Error " + e.getMessage(), true);
			return null;
		}
	}
	
	public static void resetAllButton() {
		TicTacToeCRUD.deleteCurrentBtnState();
	}

}
