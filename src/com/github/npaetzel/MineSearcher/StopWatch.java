package com.github.npaetzel.MineSearcher;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.text.Text;

public class StopWatch {
	private long startTime;
	private long endTime;
	private long stoppedTime;
	private Timer timer;
	private Text timerText;
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public void startTime() {
		setStartTime(System.nanoTime());
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				refreshTime();
				if(timerText != null) {
					if((long)(getStoppedTime()/1e9) > 9) {
						timerText.setText("0" + String.valueOf((long)(getStoppedTime()/1e9)));
					} else {
						timerText.setText("00" + String.valueOf((long)(getStoppedTime()/1e9)));
					}
				}
			}
		}, 0, 50);
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getStoppedTime() {
		return stoppedTime;
	}
	public void setStoppedTime() {
		this.stoppedTime = getEndTime()-getStartTime();
	}
	
	public long refreshTime() {
		setEndTime(System.nanoTime());
		setStoppedTime();
		return (long) (getStoppedTime()/1e9);
	}
	
	public long stopTime() {
		timer.cancel();
		return refreshTime();
	}	
	
	public Text getTimerText() {
		return timerText;
	}
	
	public void setTimerText(Text text) {
		this.timerText = text;
	}
}
