package com.project.waverr;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class JSONfunctions {

    public static JSONArray getJSONfromURL(String[] url) {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;
      //the year data to send
      ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      
        

        // Download JSON data from URL
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url[0]);
            nameValuePairs.add(new BasicNameValuePair(url[1], url[2]));
            nameValuePairs.add(new BasicNameValuePair("pass", "Waverr2015"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
    }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
    }
    //convert response to string
    try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
            }
            is.close();
     
            result=sb.toString();
    }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
    }
     
    //parse json data
    try{
            jArray = new JSONArray(result);
           /* for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Log.i("log_tag","id: "+json_data.getInt("id")+
                            ", name: "+json_data.getString("name")+
                            ", sex: "+json_data.getInt("sex")+
                            ", birthyear: "+json_data.getInt("birthyear")
                    );
            }*/
    
    }catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
    }

        return jArray;
    }
}