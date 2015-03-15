package com.project.waverr;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.model.people.Person.Image;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class GlobalClass extends Application {
	
	private String city;
	private String personName = "Not available";
	private Image personPhoto;
	private String personGooglePlusProfile;
	private String personEmail = "Please login";
	private String loginstatus;
	private boolean loggedIn;
	private String lastitem;
	private GoogleApiClient mGoogleApiClient;
	private Deal currentDeal;
	private boolean drawerOpen = false;
	
	private static GlobalClass instance = new GlobalClass();

    public void MainApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "i83LuSHlmyI7wTcI7Cmlh0n6v5glfRVXmFzpc5xb", "qWSCgY6QGgeSbAXVZfG7VVrFDxbSHZ3qQFlfbiEo");
        PushService.setDefaultPushCallback(this, Splash.class);
        PushService.subscribe(this, "", Splash.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
	
	public boolean getDrawerOpen() {
		return drawerOpen;
	}

	public void setDrawerOpen(boolean drawerOpen) {
		this.drawerOpen = drawerOpen;
	}

	public void clearUser() {
		personName = "Not available";
		personEmail = "Please login";
		personPhoto = null;
		loggedIn = false;
		loginstatus = "none";
	}
	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setDeal(Deal deal) {
		currentDeal = deal;
	}
	
	public Deal getDeal() {
		return currentDeal;
	}
	public GoogleApiClient getClient(){
		return mGoogleApiClient;
	}
	
	public void setClient(GoogleApiClient mGoogleApiClient){
		this.mGoogleApiClient=mGoogleApiClient;
	}
	
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
	
}
