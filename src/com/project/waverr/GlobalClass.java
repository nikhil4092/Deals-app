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
	
	public void addRestaurantToFavourites(String restaurant) {
		if(favouritedRestaurants==null)
			favouritedRestaurants = new ArrayList<>();
		favouritedRestaurants.add(restaurant);
	}
	
	public boolean isFavourited(String restaurant) {
		return favouritedRestaurants.contains(restaurant);
	}
	
	public void removeFromFavourites(String restaurant) {
		favouritedRestaurants.remove(restaurant);
	}
}
