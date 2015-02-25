package com.project.waverr;

import java.util.ArrayList;

import android.app.Application;

import com.google.android.gms.plus.model.people.Person.Image;

public class GlobalClass extends Application{
	
	private String city;
	private ArrayList<String> favouritedRestaurants;
	private String personName = "Anonymous";
	private Image personPhoto;
	private String personGooglePlusProfile;
	private String personEmail = "anon@waverr.in";
	private String loginstatus;
	private String lastitem;
	
	public void setloginstatus(String status){
		loginstatus = status;
		
		if(loginstatus.equals("none")){
			lastitem="Login";
		}
		else{
			lastitem="Logout";
		}
	}
	
	public String getlastitem(){
		return lastitem;
	}
	public String getloginstatus(){
		return loginstatus;
	}
	
	public void setCity(String data) {
		city = data;
	}
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Image getPersonPhoto() {
		return personPhoto;
	}

	public void setPersonPhoto(Image personPhoto) {
		this.personPhoto = personPhoto;
	}

	public String getPersonGooglePlusProfile() {
		return personGooglePlusProfile;
	}

	public void setPersonGooglePlusProfile(String personGooglePlusProfile) {
		this.personGooglePlusProfile = personGooglePlusProfile;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
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
