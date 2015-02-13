package com.project.waverr;

import org.json.JSONArray;

import android.os.AsyncTask;

public class JSONObtainer extends AsyncTask<String, Void, JSONArray>{

	@Override
	protected JSONArray doInBackground(String... url) {
		// TODO Auto-generated method stub
		return JSONFunctions.getJSONfromURL(url[0]);
	}

}
