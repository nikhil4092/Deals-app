package com.project.waverr;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	long inMillis;
	String date;
	String time;
	public int days;
	public int hours;
	public int minutes;
	public int seconds;
	
	public void setDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.date = date;
		Date temp;
		try {
			temp = format.parse(date);
			inMillis += temp.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		convertTime();
	}
	
	public void setTime(String time) {
		this.time = time;
		Time temp = Time.valueOf(time);
		inMillis += temp.getTime();
		convertTime();
	}
	
	public long getTimeInMillis() {
		return inMillis;
	}
	
	private void convertTime() {
		long temp = inMillis / 1000;
		seconds = (int) (temp % 60);
		temp /= 60;
		minutes = (int) (temp % 60);
		temp /= 60;
		hours = (int) (temp % 60);
		temp /= 24;
		days = (int) temp;
	}
	
	public String getDateTime() {
		return date+" "+time;
	}
	
	public void setDateTimeByMillis(long millis) {
		inMillis = millis;
		convertTime();
	}
}
