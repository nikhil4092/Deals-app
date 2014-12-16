package com.project.waverr;

import android.os.AsyncTask;

public class LocationObtainer extends AsyncTask<LocationGiver, Void, String>{
	
	@Override
	protected String doInBackground(LocationGiver... giver) {
		// TODO Auto-generated method stub
		publishProgress();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return giver[0].getLocation();
	}
}