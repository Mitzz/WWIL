package com.enercon.global.utility;

public class StopWatch {

	private long start;
	
	public StopWatch(){
		start = System.currentTimeMillis();
	}
	
	public long getElapsedTime(){
		return (System.currentTimeMillis() - start);
	}
	
	public long getElapsedTimeInSeconds(){
		return (System.currentTimeMillis() - start) / 1000;
	}
	
	public long getElapsedTimeInMinutes(){
		return getElapsedTimeInSeconds() / 60;
	}
	
	public long getElapsedTimeInHours(){
		return getElapsedTimeInMinutes() / 60;
	}
	
	public long getElapsedTimeInDays(){
		return getElapsedTimeInHours() / 24;
	}
}
