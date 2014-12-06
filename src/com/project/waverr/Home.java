package com.project.waverr;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Home extends ActionBarActivity implements OnClickListener{

	int LAC;
	ImageButton ib1,ib2;
	TabHost th;
	TextView tv;
	TextView x;
	String cityName;
	LocationManager locationManager;
	Criteria criteria;
	String provider;
	Location location;
	Button b;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		    
		
		setContentView(R.layout.home);
		
		ib1=(ImageButton)findViewById(R.id.cuisine1);
		ib2=(ImageButton)findViewById(R.id.cuisine2);
		th=(TabHost)findViewById(R.id.tabhost);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		b=(Button)findViewById(R.id.slidemenu);
		th.setup();
		TabSpec specs = th.newTabSpec("Search");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Search");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
	    x.setTextSize(15);
	    x.setTextColor(Color.parseColor("#424242"));
		
	    specs = th.newTabSpec("Nearby");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Nearby");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
	    x.setTextSize(15);
	    x.setTextColor(Color.parseColor("#424242"));
		
	    specs = th.newTabSpec("New");
		specs.setContent(R.id.tab3);
		specs.setIndicator("New");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
	    x.setTextSize(15);
	    x.setTextColor(Color.parseColor("#424242"));
		
	    specs = th.newTabSpec("BestDeals");
		specs.setContent(R.id.tab4);
		specs.setIndicator("Best Deals");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
	    x.setTextSize(15);
	    x.setTextColor(Color.parseColor("#424242"));
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		b.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent c1,c2;
		switch(arg0.getId())
		{
		case R.id.slidemenu:
			c1 = new Intent("com.project.waverr.HOMETWO");
			startActivity(c1);break;
		case R.id.cuisine1:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		startActivity(c1);
			break;
		case R.id.cuisine2:c2= new Intent("come.project.waverr.INDIANCUISINE");
		startActivity(c2);
			break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String city=null;
		if(provider!=null) {
			location = locationManager.getLastKnownLocation(provider);
			city = getLocationName(location.getLatitude(), location.getLongitude());
		}
		tv = (TextView) findViewById(R.id.cityname);
		tv.setText(city);
	}
	private String getLocationName(double latitude, double longitude) {
		String result = "Unavailable";
		
		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
	    try {

	        List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);

	        for (Address adrs : addresses) {
	            if (adrs != null) {

	                String city = adrs.getLocality();
	                if (city != null && !city.equals("")) {
	                    result = city;
	                } else {

	                }
	                // // you should also try with addresses.get(0).toSring();

	            }

	        }
	    } catch (IOException e) {
	        
	    	e.printStackTrace();
	    	result="Unavailable";
	        
	    }
	    
	    {
	    return result;
	    }
	}
	
}