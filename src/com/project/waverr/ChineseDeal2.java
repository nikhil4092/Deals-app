package com.project.waverr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ChineseDeal2 extends GlobalActionBar {

	private RecyclerView mRecyclerView;
	private DealAdapter mAdapter;
	private ArrayList<Deal> deals;
	private String s;
	private TextView text,type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chinese_deal2);
		
		Bundle b=getIntent().getExtras();
        s=b.getString("cuisine");

		mRecyclerView = (RecyclerView)findViewById(R.id.dealRecycleView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		text = (TextView) findViewById(R.id.noDeals);
		type = (TextView) findViewById(R.id.DealType);
		type.setTextSize(25);
		type.setTextColor(Color.parseColor("#fe5335"));
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
		type.setTypeface(typeface);
		type.setText(s);
		getDeals();

		mAdapter = new DealAdapter(deals, R.layout.row_deal, this);
		mRecyclerView.setAdapter(mAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.chinese_deal, menu);
			
			restoreActionBar();
			return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.refresh_deal) {
			finish();
			Intent intent = getIntent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			return true;
		}
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getDeals() {
		String[] url = {
				"http://waverr.in/getdealparameters.php",
				"cuisine", s
		};
		deals = new ArrayList<>();
		final ProgressDialog dialog = new  ProgressDialog(this);
		dialog.setMessage("Getting deals...");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
		
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
							type.setText("No "+s+" Deals Currently. Please reload or check back later.");
						}
						
					}
					if(array!=null){
						for(int i=0; i<array.length(); i++) {
							JSONObject object = array.getJSONObject(i);

							//Gson gson = new Gson();
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
							newDeal.setImageURL(object.getString(things[13]));
							deals.add(newDeal);
						}
					}
					//pullToRefreshView.onRefreshComplete();
					mAdapter.notifyDataSetChanged();
					dialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		obtainer.execute(url);
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
