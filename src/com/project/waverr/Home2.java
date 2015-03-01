package com.project.waverr;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.waverr.SimpleGestureFilter.SimpleGestureListener;

public class Home2 extends ActionBarActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks, OnClickListener ,OnTabChangeListener, SimpleGestureListener{

	int LAC;
	ImageButton ib1,ib2;
	TabHost th;
	TextView tv;
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
	private Boolean firstTimeNearbyClicked;

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
		th=(TabHost)findViewById(R.id.tabhost);
		b=(Button)findViewById(R.id.slidemenu);
		locationSelect = (Button)findViewById(R.id.cityselect);
		/*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);*/
		giver = new LocationGiver(this);
		cities = new String[]{"Mangaluru"};

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

		//getDealsByDistance();

		/*GetDistance sample = new GetDistance();
		float distance = sample.getDistance(13.013758, 74.798322, 13.004930, 74.792860);
		Toast.makeText(this, Float.toString(distance), Toast.LENGTH_LONG).show();*/
	}

	private void checkStuff() {
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

			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
		case R.id.cuisine2:c2= new Intent("com.project.waverr.INDIANCUISINE");
		startActivity(c2);
		break;
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(global.getCity().equalsIgnoreCase("Location Off") && flagLocation==1) {
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
		bar.setTitle(global.getCity());
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
		if(current==1 && firstTimeNearbyClicked) {
			firstTimeNearbyClicked = false;
			getDealsByDistance();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		int currentTab = th.getCurrentTab();
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT:
			th.setCurrentTab(currentTab-1);
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			th.setCurrentTab(currentTab+1);
			break;
		}
		//Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDoubleTap() {
		//Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

	private void getDealsByDistance() {
		final ArrayList<Deal> deals = new ArrayList<Deal>();
		final ArrayList<ImageButton> buttons = new ArrayList<>();
		final ArrayList<TextView> texts = new ArrayList<>();

		//LocationManager locationManager;
		/*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);*/
		final Location location = getLastKnownLocation();
		/*if(provider!=null) {
			Toast.makeText(this, "We have something!", Toast.LENGTH_SHORT).show();
			location = locationManager.getLastKnownLocation(provider);
		}*/
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
					"http://waverr.in/getdealparameters.php",
					"",
					""
			};

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

					final LinearLayout mLayout = (LinearLayout) findViewById(R.id.tab2);

					try {

						for(int i=0; i<array.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							Deal newDeal = new Deal();
							newDeal.setID(object.getInt(things[0]));
							newDeal.setRestaurantID(object.getString(things[1]));
							newDeal.setRestaurantName(object.getString(things[2]));
							newDeal.setPercentageDiscount(object.getInt(things[3]));
							newDeal.setAmountDiscount(object.getInt(things[4]));
							newDeal.setFreebie(object.getString(things[5]));
							newDeal.setCanvasText(object.getString(things[6]));
							newDeal.setMinimumAmount(object.getInt(things[7]));
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							try {
								newDeal.setStartDate(format.parse(object.getString(things[8])));
								newDeal.setEndDate(format.parse(object.getString(things[9])));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							newDeal.setStartTime(Time.valueOf(object.getString(things[10])));
							newDeal.setEndTime(Time.valueOf(object.getString(things[11])));
							newDeal.setCuisine(object.getString(things[12]));

							final Location restaurantLocation = new Location("database");
							String[] url = {
									"http://waverr.in/getrestaurantlocation.php",
									"restaurantname",
									newDeal.getRestaurantName()
							};

							JSONObtainer locationObtainer = new JSONObtainer() {
								@Override
								protected void onPostExecute(JSONArray array) {
									Toast.makeText(getBaseContext(), "Getting location", Toast.LENGTH_SHORT).show();
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

									Toast.makeText(getBaseContext(), coords[0]+","+coords[1], Toast.LENGTH_SHORT).show();
								}
							};
							locationObtainer.execute(url);

							newDeal.setDistanceFromUser(GetDistance.getValue(location.getLatitude(),
									location.getLongitude(),
									restaurantLocation.getLatitude(),
									restaurantLocation.getLongitude()));

							deals.add(newDeal);
							Toast.makeText(getBaseContext(), "Got the deal!", Toast.LENGTH_SHORT).show();	
						}

						Collections.sort(deals,new Comparator<Deal>() {
							@Override
							public int compare(Deal lhs, Deal rhs) {
								// TODO Auto-generated method stub
								return (int) (lhs.getDistanceFromUser() - rhs.getDistanceFromUser());
							}
						});

						for(Deal newDeal : deals) {
							LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
							Gson gson = new Gson();

							ImageButton button = new ImageButton(getBaseContext());
							button.setLayoutParams(params);
							button.setImageResource(R.drawable.soup5);
							button.setScaleType(ScaleType.FIT_XY);
							button.setBackgroundColor(Color.TRANSPARENT);
							button.setAdjustViewBounds(true);
							buttons.add(button);

							final String deal = gson.toJson(newDeal);

							button.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(getBaseContext(), com.project.waverr.DealPage.class);
									Toast.makeText(getBaseContext(), deal, Toast.LENGTH_SHORT).show();
									intent.putExtra("deal", deal);
									startActivity(intent);
								}
							});

							TextView text = new TextView(getBaseContext());
							//text.setText(newDeal.getDetails());
							text.setText("This is just some text for testing");
							text.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_normal));
							text.setLayoutParams(params);
							texts.add(text);

							LinearLayout smallLayout = new LinearLayout(getBaseContext());
							smallLayout.setOrientation(LinearLayout.VERTICAL);
							smallLayout.setLayoutParams(params);
							smallLayout.setPadding(4, 0, 4, 0);
							smallLayout.addView(button);
							smallLayout.addView(text);

							mLayout.addView(smallLayout);
						}
						progressDialog.dismiss();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			obtainer.execute(url);
		}
		
		else
			Toast.makeText(this, "Nope!", Toast.LENGTH_SHORT).show();
	}
	
	private Location getLastKnownLocation() {
	    LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
	    List<String> providers = mLocationManager.getProviders(true);
	    Location bestLocation = null;
	    for (String provider : providers) {
	        Location l = mLocationManager.getLastKnownLocation(provider);
	        if (l == null) {
	            continue;
	        }
	        if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
	            // Found best last known location: %s", l);
	            bestLocation = l;
	        }
	    }
	    return bestLocation;
	}
}

