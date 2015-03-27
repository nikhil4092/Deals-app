package com.project.waverr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.project.waverr.SimpleGestureFilter.SimpleGestureListener;

public class Home2 extends ActionBarActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks, OnClickListener ,OnTabChangeListener, SimpleGestureListener{

	static boolean activedealtest;
	int LAC;
	String temp;
	ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7;
	TabHost th;
	DateTime start,end;
	TextView tv,type;
	TextView x;
	//String cityName;
	Button b;
	Button locationSelect;
	LocationGiver giver;
	android.support.v7.app.ActionBar bar;
	String[] cities;
	AlertDialog.Builder builder;
	AlertDialog locationChoose;
	int flagLocation=0;
	GlobalClass global;
	private SimpleGestureFilter detector;
	private boolean firstTimeNearbyClicked;
	private boolean firstTimeRestClicked;
	private RecyclerView bestRecyclerView;
	private DealAdapter bestAdapter;
	private RecyclerView restaurantRecyclerView;
	private RestaurantAdapter restaurantAdapter;
	private ArrayList<Restaurant> restaurants;
	boolean login=true;
	private TextView displayRestaurant;

	private ArrayList<Deal> bestDeals;
	
	/*private ArrayList<Deal> locationDeals;
	private boolean firstTimeLocationClicked;
	private RecyclerView locationRecyclerView;
	private DealAdapter locationAdapter;*/
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home2);
		global = (GlobalClass) getApplicationContext();
		bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe5335")));
		global.setCity(getString(R.string.location_na));
		bar.setTitle(getString(R.string.location_na));
		ib1=(ImageButton)findViewById(R.id.cuisine1);
		ib2=(ImageButton)findViewById(R.id.cuisine2);
		ib3=(ImageButton)findViewById(R.id.cuisine3);
		ib4=(ImageButton)findViewById(R.id.cuisine4);
		ib5=(ImageButton)findViewById(R.id.cuisine5);
		ib6=(ImageButton)findViewById(R.id.cuisine6);
		ib7=(ImageButton)findViewById(R.id.cuisine7);
		findViewById(R.id.cuisine8).setOnClickListener(this);
		findViewById(R.id.cuisine9).setOnClickListener(this);
		findViewById(R.id.cuisine10).setOnClickListener(this);

		th=(TabHost)findViewById(R.id.tabhost);
		b=(Button)findViewById(R.id.slidemenu);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);
		ib6.setOnClickListener(this);
		ib7.setOnClickListener(this);
		b.setOnClickListener(this);
		Bundle b=getIntent().getExtras();
		if(b!=null)
			login=b.getBoolean("login");
		locationSelect = (Button)findViewById(R.id.cityselect);
		/*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);*/
		giver = new LocationGiver(this);
		cities = new String[]{"Mangaluru"};

		th.setup();
		TabSpec specs = th.newTabSpec("Cuisine");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Cuisine");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));


		specs = th.newTabSpec("Restaurants");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Restaurants");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		specs = th.newTabSpec("Top Deals");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Top Deals");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		/*specs = th.newTabSpec("Nearby");
		specs.setContent(R.id.tab4);
		specs.setIndicator("Nearby");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));*/

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		for(int i=0;i<th.getTabWidget().getChildCount();i++){
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_pressed_waverraccent);
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_waverraccent);			
		}
		th.getTabWidget().setCurrentTab(0);
		th.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selected_waverraccent);
		th.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selected_pressed_waverraccent);


		th.setOnTabChangedListener(this);

		builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(cities, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/*if(which==0) {
					new LocationObtainer() {
						protected void onProgressUpdate(Void...voids) {
							bar.setTitle("Updating...");
						}
						protected void onPostExecute(String result) {
							bar.setTitle(result);
							global.setCity(result);
							checkStuff();
						}
					}.execute(giver);
					//cityName = giver.getLocation();
					//bar.setTitle(cityName);
					dialog.dismiss();
				}*/
				//else {
				bar.setTitle(cities[which]);
				global.setCity(cities[which]);
				//}
				dialog.dismiss();
			}
		});
		builder.setTitle("Choose your location");
		locationChoose = builder.create();
		//if(cityName==null)
		locationChoose.show();

		detector = new SimpleGestureFilter(this, this);
		firstTimeNearbyClicked = true;
		firstTimeRestClicked = true;
		//firstTimeLocationClicked = true;
		
		bestDeals = new ArrayList<>();
		restaurants = new ArrayList<>();
		//locationDeals = new ArrayList<>();
		//getDealsByDistance();

		/*GetDistance sample = new GetDistance();
		float distance = sample.getDistance(13.013758, 74.798322, 13.004930, 74.792860);
		Toast.makeText(this, Float.toString(distance), Toast.LENGTH_LONG).show();*/
	}



	/*private void checkStuff() {
		if(((String) global.getCity()).compareTo("Location Off")==0) {
			AlertDialog.Builder promptBuilder = new AlertDialog.Builder(this);
			promptBuilder.setMessage("Location is disabled.\nEnable location?\n\nNote: You can also choose your preferred location by clicking on the location icon at the top.");
			promptBuilder.setCancelable(false);
			promptBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { 
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});
			promptBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { 
					// do nothing
				}
			});
			AlertDialog locationPrompt = promptBuilder.create();
			locationPrompt.show();
			flagLocation=1;
		}
	}*/



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

	@SuppressWarnings("deprecation")
	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		/*cityName = giver.getLocation();*/
		actionBar.setTitle(global.getCity());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.home2, menu);
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
			locationChoose.show();
			return true;
		}
		if(id == R.id.refresh_home) {
			if(th.getCurrentTab()==1)
				getAllRestaurants();
			else if(th.getCurrentTab()==2)
				getBestDeals();
			/*else if(th.getCurrentTab()==2)
				getDealsByDistance();*/

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
		Intent c1=new Intent();
		switch(arg0.getId())
		{

		case R.id.cuisine1:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Fast Food");
		break;
		case R.id.cuisine2:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Italian");
		break;
		case R.id.cuisine3:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Desserts");

		break;
		case R.id.cuisine4:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Drinks");

		break;
		case R.id.cuisine5:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Biriyani");

		break;
		case R.id.cuisine6:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "North Indian");

		break;
		case R.id.cuisine7:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Chinese");

		break;
		case R.id.cuisine8:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "South Indian");

		break;
		case R.id.cuisine9:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Kebabs");

		break;
		case R.id.cuisine10:c1 = new Intent("com.project.waverr.CHINESECUISINE");
		c1.putExtra("cuisine", "Sea food");

		break;
		}
		c1.putExtra("login", login);
		startActivity(c1);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		try{
			super.onResume();
		} catch(java.lang.NullPointerException e ){
			Intent intent = new Intent(getBaseContext(), com.project.waverr.Splash.class);
			startActivity(intent);
		}
		/*if(global.getCity().equalsIgnoreCase("Location Off") && flagLocation==1) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new LocationObtainer() {
				protected void onProgressUpdate(Void...voids) {
					bar.setTitle("Updating...");
				}
				protected void onPostExecute(String result) {
					global.setCity(result);
					checkStuff();
				}
			}.execute(giver);
			flagLocation=0;
		}
		bar.setTitle(global.getCity());*/
	}



	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		for(int i=0;i<th.getTabWidget().getChildCount();i++){
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_waverraccent);
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_pressed_waverraccent);
		}
		th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundResource(R.drawable.tab_indicator_ab_waverraccent);
		th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundResource(R.drawable.tab_selected_waverraccent);
		th.getTabWidget().getChildAt(th.getCurrentTab()).setBackgroundResource(R.drawable.tab_selected_pressed_waverraccent);

		int current = th.getCurrentTab();
		if(current==1 && firstTimeRestClicked) {
			firstTimeRestClicked = false;
			getAllRestaurants();
		}
		if(current==2 && firstTimeNearbyClicked) {
			firstTimeNearbyClicked = false;
			getBestDeals();
		}
		/*if(current==3 && firstTimeLocationClicked) {
			firstTimeLocationClicked = false;
			getDealsByDistance();
		}*/
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		if(global.getDrawerOpen()==false) {
			int currentTab = th.getCurrentTab();
			switch (direction) {
			case SimpleGestureFilter.SWIPE_RIGHT:
				th.setCurrentTab(currentTab-1);
				break;
			case SimpleGestureFilter.SWIPE_LEFT:
				th.setCurrentTab(currentTab+1);
				break;
			}
		}
		//Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDoubleTap() {
		//Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

	/*private void getDealsByDistance() {

		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);
		final Location location;
		//final Location location = getLastKnownLocation();
		if(provider!=null) {
			Toast.makeText(this, "We have a provider!", Toast.LENGTH_SHORT).show();
			location = locationManager.getLastKnownLocation(provider);
		}
		else
			location =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		if(location == null) {
			Toast.makeText(this, "No provider", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder promptBuilder = new AlertDialog.Builder(this);
			promptBuilder.setMessage("Location is disabled.\nEnable location?\n\nNote: You can also choose your preferred location by clicking on the location icon at the top.");
			promptBuilder.setCancelable(false);
			promptBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { 
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			});

			promptBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { 
					// do nothing
				}
			});
			AlertDialog locationPrompt = promptBuilder.create();
			locationPrompt.show();
		}

		if(location!=null) {
			Toast.makeText(this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_LONG).show();
			String[] url = {
					"http://waverr.in/getdealparameters.php"
			};

			locationRecyclerView = (RecyclerView)findViewById(R.id.NearbyDealRecycleView);
			locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
			locationRecyclerView.setItemAnimator(new DefaultItemAnimator());
			type = (TextView) findViewById(R.id.NearbyDealType);
			type.setTextSize(25);
			type.setTextColor(Color.parseColor("#fe5335"));
			Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
			type.setTypeface(typeface);
			type.setText("Nearby Deals");

			locationDeals.clear();

			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Getting deals...");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.show();

			JSONObtainer obtainer = new JSONObtainer() {
				@Override
				protected void onPostExecute(JSONArray array) {

					Toast.makeText(getBaseContext(), "Getting deals", Toast.LENGTH_SHORT).show();

					String things[] = {
							"ID",
							"Restaurant ID",
							"Restaurant Name",
							"Percentage Discount",
							"Amount Discount",
							"Freebie",
							"CanvasText",
							"Min Purchase Amount",
							"Deal Start Date",
							"Deal End Date",
							"Start Time",
							"End Time",
							"Cuisine",
					};

					//final LinearLayout mLayout = (LinearLayout) findViewById(R.id.nearby);

					try {

						if(array==null){
							boolean network=isNetworkAvailable();
							if(network==false)
							{
								type.setText("Please check your internet connection and try again.");
							}
							//text.setText(newDeal.getDetails());
							else
							{
								type.setText("No Deals Currently. Please reload or check back later.");
							}
						}
						else {
							for(int i=0; i<array.length(); i++) {
								JSONObject object = array.getJSONObject(i);
								Deal newDeal = new Deal();
								newDeal.setID(object.getString(things[0]));
								newDeal.setRestaurantID(object.getString(things[1]));
								newDeal.setRestaurantName(object.getString(things[2]));
								newDeal.setPercentageDiscount(object.getInt(things[3]));
								newDeal.setAmountDiscount(object.getInt(things[4]));
								newDeal.setFreebie(object.getString(things[5]));
								newDeal.setCanvasText(object.getString(things[6]));
								newDeal.setMinimumAmount(object.getInt(things[7]));
								DateTime start = new DateTime();
								start.setDate(object.getString(things[8]));
								start.setTime(object.getString(things[10]));
								DateTime end = new DateTime();
								end.setDate(object.getString(things[9]));
								end.setTime(object.getString(things[11]));
								newDeal.setStartDateTime(start);
								newDeal.setEndDateTime(end);
								newDeal.setCuisine(object.getString(things[12]));

								final Location restaurantLocation = new Location("database");
								String[] url = {
										"http://waverr.in/getrestaurantlocation.php",
										"restaurantname", newDeal.getRestaurantName()
								};

								JSONObtainer locationObtainer = new JSONObtainer() {
									@Override
									protected void onPostExecute(JSONArray array) {
										//Toast.makeText(getBaseContext(), "Getting location", Toast.LENGTH_SHORT).show();
										JSONObject object;
										try {
											object = array.getJSONObject(0);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											object = null;
										}

										String[] coords = null;
										try {
											coords = object.getString("Coordinates").split(",");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										restaurantLocation.setLatitude(Float.parseFloat(coords[0]));
										restaurantLocation.setLongitude(Float.parseFloat(coords[1]));

										//Toast.makeText(getBaseContext(), coords[0]+","+coords[1], Toast.LENGTH_SHORT).show();
									}
								};
								locationObtainer.execute(url);

								newDeal.setDistanceFromUser(GetDistance.getValue(location.getLatitude(),
										location.getLongitude(),
										restaurantLocation.getLatitude(),
										restaurantLocation.getLongitude()));

								locationDeals.add(newDeal);
								//Toast.makeText(getBaseContext(), "Got the deal!", Toast.LENGTH_SHORT).show();	
							}

							Collections.sort(locationDeals, new Comparator<Deal>() {
								@Override
								public int compare(Deal lhs, Deal rhs) {
									// TODO Auto-generated method stub
									return (int) (lhs.getDistanceFromUser() - rhs.getDistanceFromUser());
								}
							});
						}
						locationAdapter.notifyDataSetChanged();
						progressDialog.dismiss();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			obtainer.execute(url);
			locationAdapter = new DealAdapter(locationDeals, R.layout.row_deal, this);
			locationRecyclerView.setAdapter(locationAdapter);
		}
		else
			Toast.makeText(this, "Nope!", Toast.LENGTH_SHORT).show();
	}*/

	private void getAllRestaurants() {
		String[] url = {
				"http://waverr.in/restaurantpartners.php",
		};

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting Restaurant List...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.show();

		restaurantRecyclerView = (RecyclerView) findViewById(R.id.RestaurantRecycleView);
		restaurantRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		restaurantRecyclerView.setItemAnimator(new DefaultItemAnimator());

		displayRestaurant = (TextView) findViewById(R.id.resUnavailable);
		displayRestaurant.setTextSize(25);
		displayRestaurant.setTextColor(Color.parseColor("#fe5335"));
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
		displayRestaurant.setTypeface(typeface);
		displayRestaurant.setText("Deals by Restaurants");
		
		JSONObtainer obtainer = new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {

				final String things[] = {
						"Restaurant Name",
						"Restaurant ID"
						//"url",
				};

				try {
					if(array==null){
						boolean network=isNetworkAvailable();
						if(network==false)
						{
							displayRestaurant.setText("Please check your internet connection and try again.");
						}
						//text.setText(newDeal.getDetails());
						else
						{
							displayRestaurant.setText("Congratulations... You are selected for Hogwarts School of Witchcraft and Wizardry! Just kidding. :)");
						}
					}
					if(array!=null){
						for(int i=0; i<array.length(); i++) {
							final JSONObject object = array.getJSONObject(i);
							Restaurant newRestaurant;
							String name = (object.getString(things[0]));
							//String url = (object.getString(things[1]));
							String id = (object.getString(things[1]));
							newRestaurant = new Restaurant(name, id, null);
							restaurants.add(newRestaurant);
						}
					}
					restaurantAdapter.notifyDataSetChanged();
					progressDialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		obtainer.execute(url);
		restaurantAdapter = new RestaurantAdapter(restaurants, R.layout.restaurant_list, this);
		restaurantRecyclerView.setAdapter(restaurantAdapter);
	}
	public void startTime(){


		final long startMillis = start.getTimeInMillis();
		final long endMillis = end.getTimeInMillis();

		new JSONObtainer() {
			DateTime current = new DateTime();

			@Override
			protected void onProgressUpdate(Void... voids) {

			}

			@Override
			protected void onPostExecute(JSONArray array) {
				if(array!=null) {
					try {
						JSONObject object = array.getJSONObject(0);
						current.setDate(object.getString("date"));
						current.setTime(object.getString("time"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					long currentMillis = current.getTimeInMillis();

					final long timeUntilStart, timeUntilEnd;

					if(startMillis > currentMillis)
						timeUntilStart = startMillis - currentMillis;
					else
						timeUntilStart = 0;
					if(endMillis > currentMillis)
						timeUntilEnd = endMillis - currentMillis;
					else
						timeUntilEnd = 0;


					new CountDownTimer(timeUntilStart, 1000) {
						final DateTime actual = new DateTime();

						@Override
						public void onTick(long millisUntilFinished) {
							// TODO Auto-generated method stub
							actual.setDateTimeByMillis(millisUntilFinished);


						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							activedealtest=true;
							new CountDownTimer(timeUntilEnd, 1000) {

								@Override
								public void onTick(long millisUntilFinished) {
									// TODO Auto-generated method stub
									actual.setDateTimeByMillis(millisUntilFinished);


								}

								@Override
								public void onFinish() {
									// TODO Auto-generated method stub

								}
							}.start();
						}
					}.start();
				}
			}
		}.execute(new String[] {"http://waverr.in/getcurrenttime.php"});
	}
	public static boolean hasstarted(){

		return activedealtest;

	}

	private void getBestDeals() {

		bestRecyclerView = (RecyclerView)findViewById(R.id.BestDealRecycleView);
		bestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		bestRecyclerView.setItemAnimator(new DefaultItemAnimator());
		type = (TextView) findViewById(R.id.DealType);
		type.setTextSize(25);
		type.setTextColor(Color.parseColor("#fe5335"));
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
		type.setTypeface(typeface);
		type.setText("Top Deals");

		String[] url = {
				"http://waverr.in/getbest.php",
		};

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting deals...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.show();

		bestDeals.clear();

		JSONObtainer obtainer = new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {

				String things[] = {
						"dealID",
						"Restaurant ID",
						"Restaurant Name",
						"Percentage Discount",
						"Amount Discount",
						"Freebie",
						"CanvasText",
						"Min Purchase Amount",
						"Deal Start Date",
						"Deal End Date",
						"Start Time",
						"End Time",
						"Cuisine",
						"url"
				};

				try {
					if(array==null){
						boolean network=isNetworkAvailable();
						if(network==false)
						{
							type.setText("Please check your internet connection and try again.");
						}
						//text.setText(newDeal.getDetails());
						else
						{
							type.setText("No Deals Currently. Please reload or check back later.");
						}
					}
					if(array!=null){
						for(int i=0; i<array.length(); i++) {
							JSONObject object = array.getJSONObject(i);

							Deal newDeal = new Deal();
							newDeal.setID(object.getString(things[0]));
							newDeal.setRestaurantID(object.getString(things[1]));
							newDeal.setRestaurantName(object.getString(things[2]));
							newDeal.setPercentageDiscount(object.getInt(things[3]));
							newDeal.setAmountDiscount(object.getInt(things[4]));
							newDeal.setFreebie(object.getString(things[5]));
							newDeal.setCanvasText(object.getString(things[6]));
							newDeal.setMinimumAmount(object.getInt(things[7]));
							start = new DateTime();
							start.setDate(object.getString(things[8]));
							start.setTime(object.getString(things[10]));
							end = new DateTime();
							end.setDate(object.getString(things[9]));
							end.setTime(object.getString(things[11]));
							newDeal.setStartDateTime(start);
							newDeal.setEndDateTime(end);
							newDeal.setCuisine(object.getString(things[12]));
							newDeal.setImageURL(object.getString(things[13]));
							bestDeals.add(newDeal);
						}
					}
					bestAdapter.notifyDataSetChanged();
					progressDialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		obtainer.execute(url);
		bestAdapter = new DealAdapter(bestDeals, R.layout.row_deal, this);
		bestRecyclerView.setAdapter(bestAdapter);
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		try{
			super.onRestart();
		} catch(java.lang.NullPointerException e ){
			Intent intent = new Intent(getBaseContext(), com.project.waverr.Splash.class);
			startActivity(intent);
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		try{
			super.onStart();
		} catch(java.lang.NullPointerException e ){
			Intent intent = new Intent(getBaseContext(), com.project.waverr.Splash.class);
			startActivity(intent);
		}
	}
}

