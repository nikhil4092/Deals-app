package com.project.waverr;

import android.location.Location;


public class GetDistance {
	public static float getValue(double la1, double lo1, double la2, double lo2){
		Location locationA = new Location("point A");

		locationA.setLatitude(la1);
		locationA.setLongitude(lo1);

		Location locationB = new Location("point B");

		locationB.setLatitude(la2);
		locationB.setLongitude(lo2);

		float distance = locationA.distanceTo(locationB);
		/*String sdistance=Float.toString(distance);
		Toast.makeText(getBaseContext(), sdistance,Toast.LENGTH_SHORT ).show();*/
		return distance;
	}
}