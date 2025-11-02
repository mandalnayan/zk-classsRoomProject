package com.iispl.controller;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

import com.iispl.service.GameService;

public class EventQueueTicTacToeController extends SelectorComposer<Vbox> implements EventListener<Event> {

    @Wire
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, startGameBtn, resetGameBtn, pauseGameBtn, reloadGameBtn;

    @Wire
    private Label turnMessage;

    private List<Button> buttonList;

    // ---- Global shared state ----
    private static String currentTurn = "O";     // whose turn it is
    private static boolean playerOJoined = false;
    private static boolean playerXJoined = false;

    // ---- Local to each browser ----
    private String currentPlayer = "";

    @Override
    public void doAfterCompose(Vbox comp) throws Exception {
        super.doAfterCompose(comp);      

        buttonList = new ArrayList<>(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9));

        // Assign this session a unique player identity
        if (!playerOJoined) {
            currentPlayer = "O";
            playerOJoined = true;         
        } else if (!playerXJoined) {
            currentPlayer = "X";
            playerXJoined = true;
        } else {
            currentPlayer = "Spectator"; // For any extra windows
            Clients.showNotification("Two players already joined. You are a spectator.",
                    Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 3000);
        }

        EventQueue<Event> EQ = EventQueues.lookup("gameServer", EventQueues.APPLICATION, true);
        EQ.subscribe(this);

        turnMessage.setValue(currentTurn.equals("O") ? "O's Turn" : "X's Turn");

        // Enable/disable buttons properly
        toggleButtonsForPlayer(currentTurn);
    }

    /**
     * Player clicks on a cell (Publisher)
     */
    @Listen("onClick = .btn")
    public void handleClick(Event e) {
        Button clickedBtn = (Button) e.getTarget();

        if (!currentPlayer.equals(currentTurn)) {
            Clients.showNotification("Wait for your turn!", Clients.NOTIFICATION_TYPE_WARNING, null, 20, 20, 1500);
            return;
        }

        String nextTurn = currentTurn.equals("O") ? "X" : "O";
        List<String> payload = List.of(clickedBtn.getId(), currentPlayer, nextTurn);

        EventQueue<Event> EQ = EventQueues.lookup("gameServer", EventQueues.APPLICATION, true);
        EQ.publish(new Event("markBtn", null, payload));
    }

    /**
     * Receive updates from EventQueue (Subscriber)
     */
    @Override
    public void onEvent(Event e) throws Exception {
        Object data = e.getData();
        startGameBtn.setVisible(false);
        resetGameBtn.setVisible(true);
        if (data instanceof String) {
        		String id = data.toString();
        		if (id.equals("startGameBtn") || id.equals("resetGameBtn")) {
        			
        			resetAllButton();
        		}
        }
        else if (data instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> payload = (List<String>) data;
            String clickedBtnId = payload.get(0);
            String moveBy = payload.get(1);
            String nextTurn = payload.get(2);

            Button clickedBtn = buttonList.stream()
                    .filter(btn -> btn.getId().equals(clickedBtnId))
                    .findFirst()
                    .orElse(null);

            if (clickedBtn != null && clickedBtn.getLabel().isBlank()) {
                markButton(clickedBtn, moveBy);

                String res = GameService.checkWinner(buttonList);
                if (res != null) {
                    alert(res);
                    resetAllButton();
                }

                currentTurn = nextTurn;
                turnMessage.setValue(currentTurn.equals("O") ? "O's Turn" : "X's Turn");
                toggleButtonsForPlayer(currentTurn);
            }
        }
    }

    /**
     * Restart the game
     */
    @Listen("onClick = #resetGameBtn")
    public void restartGame(Event e) {
       
    }
    
    /**
     * Restart the game     
     */
    @Listen("onClick=#startGameBtn, #resetGameBtn")    
    public void start(Event e) {
    	String btnId = e.getTarget().getId();
    	   EventQueue<Event> EQ = EventQueues.lookup("gameServer", EventQueues.APPLICATION, true);
    	   Event event = new Event("resetEvent", null, btnId);
    	   EQ.publish(event);
       
    }

    /**
     * Pause and reload
     */
    @Listen("onClick = #pauseGameBtn")
    public void pauseGame() {
        if (GameService.saveCurrentState(buttonList)) {
            Clients.showNotification("Game paused. Click Reload to resume.",
                    Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 2000);
        } else {
            Clients.showNotification("Failed to save state!",
                    Clients.NOTIFICATION_TYPE_ERROR, null, 20, 20, 2000);
        }
    }

    @Listen("onClick = #reloadGameBtn")
    public void reloadGame() {
        List<String> oldBtnsState = GameService.getBtnsState();
        if (oldBtnsState != null) {
            for (int i = 0; i < buttonList.size(); i++) {
                String value = oldBtnsState.get(i);
                if (!value.isBlank()) {
                    markButton(buttonList.get(i), value);
                }
            }
            Clients.showNotification("Game reloaded successfully!",
                    Clients.NOTIFICATION_TYPE_INFO, null, 20, 20, 2000);
        } else {
            Clients.showNotification("Failed to reload game!",
                    Clients.NOTIFICATION_TYPE_ERROR, null, 20, 20, 2000);
        }
    }

    /**
     * Marks the cell with X or O
     */
    private void markButton(Button btn, String value) {
        btn.setLabel(value);
        btn.setDisabled(true);
        btn.setStyle(value.equals("X")
                ? "background-color: #aaefff; font-size: 30px;"
                : "background-color: #9eec9b; font-size: 30px;");    
    }
    
/**Reset all button
 * 
 */
    public void resetAllButton() {
    	  buttonList = new ArrayList<>(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9));
    	  buttonList.forEach((btn)->{
    		  btn.setLabel("");
    		  btn.setStyle("background-color: #ec5353;");
    		  btn.setDisabled(false);
    	  });
    	  startGameBtn.setVisible(true);
      resetGameBtn.setVisible(false);      
      playerOJoined = false;
	  playerXJoined = false;
	  currentPlayer = currentTurn;
    }
    
    /**
     * Enable buttons only for the active player
     */
    private void toggleButtonsForPlayer(String activePlayer) {
        boolean isMyTurn = currentPlayer.equals(activePlayer);
        for (Button btn : buttonList) {
            if (btn.getLabel().isBlank()) {
                btn.setDisabled(!isMyTurn);
            }
        }
    }
}
