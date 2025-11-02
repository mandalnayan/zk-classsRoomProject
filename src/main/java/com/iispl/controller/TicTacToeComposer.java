package com.iispl.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Vbox;

import com.iispl.service.GameService;

public class TicTacToeComposer extends SelectorComposer<Vbox> {

	@Wire
	private Button b1, b2, b3, b4, b5, b6, b7, b8, b9,
					resetGameBtn, pauseGameBtn, reloadGameBtn ;
	
	private boolean flag = true;
	List<Button>buttonList = null;
	@Listen("onClick = .btn")
	public void markButton(Event e) {
		buttonList = new ArrayList<>(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9));
		Button clickedBtn = (Button) e.getTarget();
		// 'O' Chance
		if (flag) {
			clickedBtn.setLabel("O");
			clickedBtn.setStyle("background-color: #aaefff; font-size: 30px;");
		} else { // 'X' Chance
			clickedBtn.setLabel("X");
			clickedBtn.setStyle("background-color: #9eec9b; font-size: 30px;");
		}
		
		
		// Change flag
		flag = !flag;
		
		// Made reset button visible once game start
		resetGameBtn.setVisible(true);
		
		// Made pause button visible once game start
		pauseGameBtn.setVisible(true);
		
		// Made resume button in-visible once game start
		reloadGameBtn.setVisible(false);
		String res = GameService.checkWinner(buttonList);
		if (res != null) {
			alert(res);
		}
	}

	/**
	 * Restart the game
	 */
	@Listen("onClick = #resetGameBtn")
	public void restartGame() {
		alert("Game is resecting..!");
		GameService.resetAllButton();
		Executions.sendRedirect("tictactoe.zul");
		Clients.showNotification("Game is resecting..!", true);
	}

	/**
	 * pause the game and save the current state of button into DB
	 */
	@Listen("onClick = #pauseGameBtn")
	public void pauseGame(){
		if(GameService.saveCurrentState(buttonList)) {
			Clients.showNotification("Game has paused. Click Reload button to relod the game..!", Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 2000);
		} else {
			Clients.showNotification("Faild to save..!", Clients.NOTIFICATION_TYPE_ERROR, null, 20, 20, 2000);
		}
	}
	
	/**
	 * Reload the game form DB 
	 */
	@Listen("onClick = #reloadGameBtn") 
	public void reloadGame() {
		buttonList = new ArrayList<>(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9));
		List<String> oldBtnsState = GameService.getBtnsState();
		if (oldBtnsState != null) {
			for (int i = 0; i < buttonList.size(); i++) {
				String value = oldBtnsState.get(i);
				if (value.isBlank()) continue;
				Button btn = buttonList.get(i);
				markButton(btn, value);
			}
			Clients.showNotification("Game has reloaded..!", Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 2000);
		} else {
			Clients.showNotification("Failed to reloaded..!", Clients.NOTIFICATION_TYPE_ERROR, null, 20, 20, 2000);
		}
	}
	
	/**
	 * Mark the button
	 * @param value
	 */
	private void markButton(Button btn, String value) {	
		btn.setLabel(value);
		// Made the button disabled
		btn.setDisabled(true);
		// Made reset button visible once game start
		resetGameBtn.setVisible(true);
		
		// Made resume button in-visible once game start
		reloadGameBtn.setVisible(false);
		// Made pause button visible once game start
		pauseGameBtn.setVisible(true);
		if (value.equals("X")) {
			btn.setStyle("background-color: #aaefff; font-size: 30px;");
		} else {
			btn.setStyle("background-color: #9eec9b; font-size: 30px;");
		}
	}
}
