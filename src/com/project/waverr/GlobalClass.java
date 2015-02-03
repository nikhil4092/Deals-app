package com.project.waverr;

import java.util.ArrayList;

import android.app.Application;

public class GlobalClass extends Application{
	
	private String city;
	private ArrayList<String> favouritedRestaurants;
	
	public void setCity(String data) {
		city = data;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setFavourite(String restaurant, boolean preference) {
		if(favouritedRestaurants==null) {
			favouritedRestaurants = new ArrayList<>();
			//Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT);
		}
			
		if(preference==true) {
			favouritedRestaurants.add(restaurant);
			//Toast.makeText(this, "Restaurant "+restaurant+" added to favs", Toast.LENGTH_SHORT).show();
		}
		else
			favouritedRestaurants.remove(restaurant);
	}
	
	public boolean isFavourited(String restaurant) {
		if(favouritedRestaurants==null)
			return false;
		
		/*for(String i: favouritedRestaurants)
			if(i.equalsIgnoreCase(restaurant))
				return true;
		
		return false;*/
		return favouritedRestaurants.contains(restaurant);
	}
}
