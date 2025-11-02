package com.iispl.clock;

	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;

	import java.util.ArrayList;
	import java.util.List;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
	import org.zkoss.zk.ui.select.annotation.Listen;
	import org.zkoss.zk.ui.select.annotation.Wire;
	import org.zkoss.zul.Button;
	import org.zkoss.zul.Listbox;
	import org.zkoss.zul.Listcell;
	import org.zkoss.zul.Listitem;
	import org.zkoss.zul.Timebox;
	import org.zkoss.zul.Timer;
	import org.zkoss.zul.Window;

	public class AlarmClock extends SelectorComposer<Window> {

		private List<String> alarms = new ArrayList<>();

		@Wire
		Timebox timeBox;

		@Wire
		Listbox alarmList;

		@Wire
		Timer timer;

		@Listen("onClick = #addAlarm")
		public void addAlarm() {
			alarms.add(timeBox.getText().trim());
			Listitem alarm = new Listitem();		
			Button removeBtn = new Button("Remove");
			alarm.appendChild(new Listcell(timeBox.getText()));
			Listcell btnCell = new Listcell();
			btnCell.appendChild(removeBtn);
			
			alarm.appendChild(btnCell);
			
			removeBtn.addEventListener(Events.ON_CLICK, event -> alarmList.removeChild(alarm));

			if (!alarms.isEmpty()) {
				timer.setRunning(true);
				timer.start();
			}

			alarmList.appendChild(alarm);
			alert("Alarm has added..!");
		}

		@Listen("onTimer = #timer")
		public void matchAlarm() {

			LocalDateTime curTime = LocalDateTime.now();

			DateTimeFormatter timeFormate = DateTimeFormatter.ofPattern("hh:mm:ss a");

			String curTimeStr = curTime.format(timeFormate).trim();

			if (alarms.contains(curTimeStr)) {

				alert("Ring.. Ring....!");
				alarms.remove(curTimeStr);
			}

		}
}
