
package com.iispl.clock;

import org.zkoss.zk.ui.select.SelectorComposer;

import org.zkoss.zk.ui.select.annotation.Listen;

import org.zkoss.zk.ui.select.annotation.Wire;

import org.zkoss.zul.Textbox;

import org.zkoss.zul.Timer;

import org.zkoss.zul.Window;

public class StopWatchComposer extends SelectorComposer<Window> {

	@Wire
	private Textbox timeBox;

	@Wire
	private Timer timer;

	private long hr = 0, mm = 0, ss = 0;
	@Listen("onClick = #startBtn")

	public void startWatch() {
		timer.setRunning(true);
		timer.start();

	}

	@Listen("onTimer = timer")

	public void updateTimer() {

		hr = (hr + (mm / 60)) % 24;

		mm = (mm + (ss / 60)) % 60;

		ss = ++ss % 60;

		String time = String.format("%d : %d : %d", hr, mm, ss);

		timeBox.setValue(time);

	}

	@Listen("onClick = #pauseBtn")

	public void pauseTimer() {

		timer.stop();

		timeBox.setStyle("background-color: green");

	}

	@Listen("onClick = #resumeBtn")

	public void resumeTimer() {

		timer.start();

		timeBox.setStyle("background-color: #ffffff");

	}

	@Listen("onClick = #stopBtn")

	public void stopTimer() {

		hr = 0;

		mm = 0;

		ss = 0;

		timer.setRunning(false);

		timeBox.setValue("");

	}

}