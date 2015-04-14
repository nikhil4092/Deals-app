package com.project.waverr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AnnouncementActivity extends GlobalActionBar {

	private ProgressDialog progressDialog;
	private RecyclerView mRecyclerView;
	private AnnouncementAdapter mAdapter;
	private ArrayList<Restaurant> restaurants;
	TextView announcementhead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement);
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
		announcementhead = (TextView)findViewById(R.id.Announcement);
		announcementhead.setTextColor(Color.parseColor("#fe5335"));
		announcementhead.setTypeface(typeface);
		announcementhead.setTextSize(25);
		announcementhead.setText("Announcements");
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Checking for announcements...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

		mRecyclerView = (RecyclerView) findViewById(R.id.announcementRecycleView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		restaurants = new ArrayList<>();

		mAdapter = new AnnouncementAdapter(restaurants, R.layout.row_announcement, this);

		mRecyclerView.setAdapter(mAdapter);

		String[] url = {
				"http://waverr.in/getannouncements.php"
		};

		new JSONObtainer() {

			@Override
			protected void onPostExecute(JSONArray array) {
				try {
					String[] things = {
							"Restaurant ID",
							"Restaurant Name",
							"announcements",
							"url"
					};

					for(int i=0; i<array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						String restName = object.getString(things[1]);
						String[] announcements = object.getString(things[2]).split("~");
						String announcement = announcements[announcements.length-1];
						String url = object.getString(things[3]);
						if(!announcement.isEmpty()) {
							Restaurant restaurant = new Restaurant();
							restaurant.setName(restName);
							restaurant.setAnnouncements(announcement);
							restaurant.seturl(url);
							restaurants.add(restaurant);
						}
					}
					mAdapter.notifyDataSetChanged();
					if(progressDialog!=null && progressDialog.isShowing())
						progressDialog.dismiss();
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.announcement, menu);
		return true;
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

	@Override
	protected void onPause() {
		super.onPause();
		if(progressDialog!=null && progressDialog.isShowing())
			progressDialog.dismiss();
	}
}
