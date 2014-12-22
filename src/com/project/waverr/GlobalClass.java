package com.project.waverr;

import android.app.Application;

public class GlobalClass extends Application{
	
	private String city;
	
	public void setCity(String data) {
		city = data;
	}
	
	public String getCity() {
		return city;
	}
}
