package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Home2 extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, OnClickListener {

	int LAC;
	ImageButton ib1,ib2;
	TabHost th;
	TextView tv;
	TextView x;
	String cityName;
	LocationManager locationManager;
	Criteria criteria;
	/*String provider;
	Location location;*/
	Button b;
	LocationGiver giver;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home2);
		android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe5335")));
		ib1=(ImageButton)findViewById(R.id.cuisine1);
		ib2=(ImageButton)findViewById(R.id.cuisine2);
		th=(TabHost)findViewById(R.id.tabhost);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		criteria = new Criteria();
		/*provider = locationManager.getBestProvider(criteria, true);*/
		giver = new LocationGiver(getBaseContext());
		
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

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			getString(R.string.title_section1);
			break;
		case 2:
			getString(R.string.title_section2);
			break;
		case 3:
			getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		cityName = giver.getLocation(locationManager, criteria);
		actionBar.setTitle(cityName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.home2, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home2,
					container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Home2) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent c1,c2;
		switch(arg0.getId())
		{
		
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
		
	}
}