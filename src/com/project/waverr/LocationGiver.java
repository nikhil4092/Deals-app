package com.project.waverr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


public class LocationGiver {
	
	String provider;
	Location location;
	String city = "Location Unavailable";
	ArrayList<String> placeList;
	Context context;
	
	public LocationGiver(Context obtained) {
		context = obtained;
		
		placeList = new ArrayList<>();
		// Update the list of valid places
		placeList.add("Kudla");
		//done
	}
	
	public String getLocation(LocationManager locationManager, Criteria criteria) {
<<<<<<< HEAD
		city = "Location Unavailable";
		if(locationManager!=null) {
		provider = locationManager.getBestProvider(criteria, true);
		if(provider!=null) {
			location = locationManager.getLastKnownLocation(provider);
			if(location!=null) {
			city = getLocationName(location.getLatitude(), location.getLongitude());
			if(!city.equals("Location Unavailable") && !placeList.contains(city))
				city="Invalid City";
			}
=======
		provider = locationManager.getBestProvider(criteria, true);
		if(provider!=null) {
			location = locationManager.getLastKnownLocation(provider);
			city = getLocationName(location.getLatitude(), location.getLongitude());
			if(!city.equals("Location Unavailable") && !placeList.contains(city))
				city="Invalid City";
>>>>>>> 707862d6681b8cefe188f2a3ebd9ecc21c3f82e6
		}
		else {
			//TODO: Give a Prompt to enable Location
		}
<<<<<<< HEAD
		}
=======
>>>>>>> 707862d6681b8cefe188f2a3ebd9ecc21c3f82e6
		return city;
	}
	private String getLocationName(double latitude, double longitude) {
		String result = "Location Unavailable";
		
		Geocoder gcd = new Geocoder(context, Locale.getDefault());
	    try {
	        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
	        for (Address adrs : addresses) {
	            if (adrs != null) {
	                String city = adrs.getLocality();
	                if (city != null && !city.equals(""))
	                    result = city;
	            }
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
}