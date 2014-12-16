package com.project.waverr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


public class LocationGiver {
	
	String provider;
	Location location;
	String city;// = getString(R.string.location_na);
	ArrayList<String> placeList;
	Context context;
	LocationManager locationManager;
	Criteria criteria;
	String na;
	
	public LocationGiver(Context obtained) {
		context = obtained;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();
		
		placeList = new ArrayList<>();
		// Update the list of valid places
		placeList.add("Mangaluru");
		//done
	}
	
	public String getLocation() {
		na = context.getString(R.string.location_na);
		city = na;
		//if(locationManager!=null) {
		provider = locationManager.getBestProvider(criteria, true);
		if(provider!=null) {
			location = locationManager.getLastKnownLocation(provider);
			if(location!=null) {
			city = getLocationName(location.getLatitude(), location.getLongitude());
			if(!city.equals("Location Unavailable") && !placeList.contains(city))
				city="Invalid City";
			}
		}
		else {
			city = "Location Off";
		}
		return city;
	}
	private String getLocationName(double latitude, double longitude) {
		String result = na;
		
		Geocoder gcd = new Geocoder(context);
	    try {
	        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 5);
	        for (Address adrs : addresses) {
	            if (adrs != null) {
	                String city = adrs.getLocality();
	                if (city != null) {
	                    result = city;
	                    break;
	                }
	            }
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}
}