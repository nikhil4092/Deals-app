package com.project.waverr;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;

public class GlobalActionBar extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
	ActionBar bar;
	GlobalClass global;
	//private NavigationDrawerFragment mNavigationDrawerFragment;
	AlertDialog.Builder builder;
	AlertDialog locationChoose;
	String[] cities;
	LocationGiver giver;
	int flagLocation;
	
	/*protected RelativeLayout fullLayout;
	protected FrameLayout frameLayout;

	@Override
	public void setContentView(int layoutResID) {

	    fullLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.act_layout, null);
	    frameLayout = (FrameLayout) fullLayout.findViewById(R.id.drawer_frame);

	    getLayoutInflater().inflate(layoutResID, frameLayout, true);

	    super.setContentView(fullLayout);

	    //Your drawer content...

	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		global = (GlobalClass) getApplicationContext();
		bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe5335")));
		bar.setTitle(global.getCity());
		//mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		//mNavigationDrawerFragment.setUp(R.id.navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout));
		
		giver = new LocationGiver(this);
		cities = new String[]{"Mangaluru"};
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//if (!mNavigationDrawerFragment.isDrawerOpen()) {
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
		//}
		//return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
		    locationChoose.show();
			return true;
		}
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	protected void onResume () {
		super.onResume();
		//AppEventsLogger.activateApp(this);
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
					bar.setTitle(result);
					global.setCity(result);
					checkStuff();
					}
				}.execute(giver);
				flagLocation=0;
		}
		bar.setTitle(global.getCity());
	}

}
