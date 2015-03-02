package com.project.waverr;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
//timport com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.project.waverr.SimpleGestureFilter.SimpleGestureListener;

public class DealPage extends GlobalActionBar implements OnTabChangeListener, /*tOnMapReadyCallback,*/ OnClickListener, SimpleGestureListener{

	TabHost th;
	TextView x;
	double latitude;
	double longitude;
	Button getDirections;
	ImageButton favouriteStatus;
	String restaurantPhoneNumber;
	String restaurantName;
	GlobalClass global;
	Button timer;
	String time;
	JSONObtainer obtainer;
	private SimpleGestureFilter detector;
	Button activate;
	boolean login;
	/*Integer[] imageIDs = {
			 R.drawable.chinese1,R.drawable.ic_launcher,R.drawable.splash,R.drawable.chinese1};*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.deal_page);
		
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting deals...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.show();
		Intent intent = getIntent();
		String dealString = intent.getStringExtra("deal");
		Deal deal=null;
		Gson gson = new Gson();
		if(dealString!=null)
		{
			deal = gson.fromJson(dealString, Deal.class);
			setLayout(deal);
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewPager.setAdapter(adapter);

		th=(TabHost)findViewById(R.id.tabhost1);

		global = (GlobalClass) getApplication();
		findViewById(R.id.button_call).setOnClickListener(this);
		favouriteStatus = (ImageButton) findViewById(R.id.favourite_status);
		favouriteStatus.setOnClickListener(this);

		restaurantPhoneNumber = "+918105563395";

		th.setup();
		TabSpec specs = th.newTabSpec("Deal");
		specs.setContent(R.id.dealtab1);
		specs.setIndicator("Deal");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		specs = th.newTabSpec("About");
		specs.setContent(R.id.dealtab2);
		specs.setIndicator("About");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		specs = th.newTabSpec("Images");
		specs.setContent(R.id.dealtab3);
		specs.setIndicator("Images");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		specs = th.newTabSpec("Location");
		specs.setContent(R.id.dealtab4);
		specs.setIndicator("Location");
		th.addTab(specs);
		x = (TextView) th.getTabWidget().getChildAt(3).findViewById(android.R.id.title);
		x.setTextSize(15);
		x.setTextColor(Color.parseColor("#424242"));

		for(int i=0;i<th.getTabWidget().getChildCount();i++){
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_waverraccent);
			th.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_unselected_pressed_waverraccent);
		}
		th.getTabWidget().setCurrentTab(0);
		th.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selected_waverraccent);
		th.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selected_pressed_waverraccent);

		th.setOnTabChangedListener(this);

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
		//tmapFragment.getMapAsync(this);
		latitude = 13.0092;
		longitude = 74.7937;

		getDirections = (Button) findViewById(R.id.get_directions);
		getDirections.setOnClickListener(this);

		timer = (Button) findViewById(R.id.deal_countdown_button);
		activate = (Button) findViewById(R.id.activatedeal);
		activate.setOnClickListener(this);

		final Date startDate = deal.getStartDate();
		final Date endDate = deal.getEndDate();
		final Time startTime = deal.getStartTime();
		final Time endTime = deal.getEndTime();
		Bundle b=getIntent().getExtras();
		login=b.getBoolean("login");
		if(login==false)
		{
			activate.setEnabled(false);
			activate.setBackgroundColor(Color.parseColor("#f1f1f1"));
		}
		
		JSONObtainer obtainer = new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {
				Date currentDate=null;
				Time currentTime=null;
				try {
					JSONObject object = array.getJSONObject(0);
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						currentDate = format.parse(object.getString("date"));
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					currentTime = Time.valueOf(object.getString("time"));
					progressDialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Toast.makeText(getBaseContext(), currentDate.toString()+"\n"+currentTime.toString(), Toast.LENGTH_LONG).show();
				long time=0;

				Boolean hasStarted = false;
				Boolean hasEnded = false;

				if(currentDate.before(startDate)) {
					time+=startDate.getTime()-currentDate.getTime();
					time+=startTime.getTime()-currentTime.getTime();
				}
				else if(currentDate.compareTo(startDate)==0)
					time+=startTime.getTime()-currentTime.getTime();
				else {
					hasStarted = true;
					if(currentDate.before(endDate)) {
						time+=endDate.getTime()-currentDate.getTime();
						time+=endTime.getTime()-currentTime.getTime();
					}
					else if(currentDate.compareTo(endDate)==0)
						time+=endTime.getTime()-currentTime.getTime();
					else
						hasEnded = true;
				}

				final String text;
				if(!hasStarted)
					text = "Deal starts in";
				else if(!hasEnded)
					text = "Deal ends in";
				else
					text = "Deal has ended!!";

				new CountDownTimer(time, 1000) {

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub

						long seconds = millisUntilFinished/1000;

						/*String hours = String.valueOf((int)(secondsUntil/3600));
						String days = String.valueOf(Integer.parseInt(hours)/24);
						int remainder = (int) (secondsUntil%3600);
						hours = String.valueOf(Integer.parseInt(hours)%24);
						String minutes = String.valueOf(remainder/60);
						remainder = remainder%60;
						String seconds = String.valueOf(remainder);*/

						/*int days = (int) (secondsUntil/(24*60*60));
						secondsUntil = secondsUntil % (24*60*60);
						int hours = (int) (secondsUntil/(60*60));
						secondsUntil = secondsUntil % (60*60);
						int minutes = (int) (secondsUntil/60);
						secondsUntil = secondsUntil % 60;*/

						int days = (int)TimeUnit.SECONDS.toDays(seconds);        
						long hours = TimeUnit.SECONDS.toHours(seconds) - (days *24);
						long minutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
						long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);

						String time = days+" d "+hours+" h "+minutes+" m "+second+" s";
						timer.setText(/*text+"\n"+*/time);
						
						
						//timer.setTextSize(android.R.attr.textAppearanceSmall);
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						timer.setText("Time's up!");
						activate.setEnabled(false);
						activate.setBackgroundColor(Color.parseColor("#f1f1f1"));
					}
					
				}.start();
				
			}
			
		};
		String[] url = {
				"http://waverr.in/getcurrenttime.php",
				"",
				""
		};
		progressDialog.dismiss();
		obtainer.execute(url);

		restaurantName = "Hao Ming";
		if(global.isFavourited(restaurantName)) {
			//Toast.makeText(this, "It's a favourite!!", Toast.LENGTH_SHORT).show();
			favouriteStatus.setImageResource(R.drawable.favorite_full);
		}
		else {
			//Toast.makeText(this, "Nope!!", Toast.LENGTH_SHORT).show();
			favouriteStatus.setImageResource(R.drawable.favorite_empty);
		}

		detector = new SimpleGestureFilter(this, this);
		
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private int[] mImages = new int[] {
				R.drawable.chinese,
				R.drawable.ic_launcher,
				R.drawable.soup3,
				R.drawable.chinese1
		};

		@Override
		public int getCount() {
			return mImages.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = DealPage.this;
			ImageView imageView = new ImageView(context);
			int padding = context.getResources().getDimensionPixelSize(
					R.dimen.padding_medium);
			imageView.setPadding(padding, padding, padding, padding);
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageView.setImageResource(mImages[position]);
			((ViewPager) container).addView(imageView, 0);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
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

	}

	/*@Override
	tpublic void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude) , 15.0f) );
		map.addMarker(new MarkerOptions()
		.position(new LatLng(latitude, longitude))
		.title("Location"));
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.get_directions:
			Uri gmmIntentUri = Uri.parse("google.navigation:q="+Double.toString(latitude)+","+Double.toString(longitude));
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			mapIntent.setPackage("com.google.android.apps.maps");
			startActivity(mapIntent);
			break;

		case R.id.button_call:
			Intent dialIntent = new Intent(Intent.ACTION_DIAL);
			dialIntent.setData(Uri.parse("tel:" + restaurantPhoneNumber));
			startActivity(dialIntent);
			break;

		case R.id.favourite_status:
			global.setFavourite(restaurantName, !global.isFavourited(restaurantName));
			if(global.isFavourited(restaurantName))
				favouriteStatus.setImageResource(R.drawable.favorite_full);
			else
				favouriteStatus.setImageResource(R.drawable.favorite_empty);
			break;

		case R.id.activatedeal:
			// TODO Auto-generated method stub
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			intent.putExtra("PROMPT_MESSAGE", "");
			startActivityForResult(intent, 0);
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

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");
				if(contents.compareTo("Tritoria")==0)
				{
					Intent i=new Intent("com.project.waverr.SUCCESS");
					i.putExtra("Working",true);
					startActivity(i);
				}
				else
				{
					Intent i=new Intent("com.project.waverr.SUCCESS");
					i.putExtra("Working",false);
					startActivity(i);
				}
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");	   	     	
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Toast.makeText(getApplicationContext(),"not working", Toast.LENGTH_LONG).show();

			}
		}
	}

	private void setLayout(Deal deal) {
		TextView theThing = (TextView) findViewById(R.id.theDeal);
		String dealThing = "Flat "+deal.getPercentageDiscount()+"% off on all food items!";
		theThing.setText(dealThing);

		TextView details = (TextView) findViewById(R.id.conditions);
		details.setText("Minimum amount is Rs "+deal.getMinimumAmount()+"\n"+deal.getDetails());

		TextView duration = (TextView) findViewById(R.id.timeLimit);
		duration.setText("The deal is valid from "+deal.getStartDate().toString()+"to "+deal.getEndDate().toString());
	}
}
