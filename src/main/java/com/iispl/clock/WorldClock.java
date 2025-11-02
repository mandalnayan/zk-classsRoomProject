package com.iispl.clock;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

public class WorldClock extends SelectorComposer<Window> {

	@Wire
	private Button set;

	@Wire
	private Textbox tIndia, tUsa, tNewYork, tTokyo;

	@Wire
	private Timebox time;

	private List<String> alarmList = new ArrayList<>();

	@Listen("onClick = #set")
	public void setClock() {
		String alarmTime = time.getText().trim();
		alarmList.add(alarmTime.toLowerCase());
		tIndia.setValue(alarmTime);
	}

	public void matchAlarm(String currentTime) {
		if (alarmList.stream().anyMatch(t -> t.replace("\u00A0", " ").equalsIgnoreCase(currentTime))) {
			alarmList.remove(currentTime);
			alert("Ring.. Ring.. Wake-Up.........!");
		}
	}

	@Listen("onTimer = #timer")
	public void currentTime() {
		ZonedDateTime indiaZone = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		ZonedDateTime londonZone = ZonedDateTime.now(ZoneId.of("Europe/London"));
		ZonedDateTime newYorkZone = ZonedDateTime.now(ZoneId.of("America/New_York"));
		LocalTime tokyoZone = LocalTime.now(ZoneId.of("Asia/Tokyo"));

		DateTimeFormatter formate = DateTimeFormatter.ofPattern("hh:mm:ss a");

		String indiaTime = indiaZone.format(formate).trim();
		String londonTime = londonZone.format(formate).trim();
		String newYorkTime = newYorkZone.format(formate);
		String tokyoTime = tokyoZone.format(formate);

		tIndia.setValue(indiaTime);
		tUsa.setValue(londonTime);
		tNewYork.setValue(tokyoTime);
		tTokyo.setValue(tokyoTime);

	}
}
