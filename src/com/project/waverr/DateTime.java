package com.project.waverr;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	long inMillis;
	String dateString;
	String timeString;
	Date date;
	Time time;
	public int days;
	public int hours;
	public int minutes;
	public int seconds;
	
	public String getDateString() {
		return dateString;
	}
	public String getDateProper() {
		String[] dateSplit = dateString.split("-");
		return dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
	}
	public String getTimeString() {
		return timeString;
	}
	public void setDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.dateString = date;
		Date temp;
		try {
			temp = format.parse(date);
			this.date = temp;
			inMillis += temp.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		convertTime();
	}
	
	public void setTime(String time) {
		this.timeString = time;
		Time temp = Time.valueOf(time);
		this.time = temp;
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
		hours = (int) (temp % 24);
		temp /= 24;
		days = (int) temp;
	}
	
	public String getDateTime() {
		String[] dateSplit = dateString.split("-");
		return dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0]+" "+time;
	}
	
	public void setDateTimeByMillis(long millis) {
		inMillis = millis;
		convertTime();
	}
	
	public Date getDate() {
		return date;
	}
	
	public Time getTime() {
		return time;
	}
}
