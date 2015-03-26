package com.project.waverr;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.project.waverr.SimpleGestureFilter.SimpleGestureListener;
import com.squareup.picasso.Picasso;

public class DealPage extends GlobalActionBar implements OnTabChangeListener, OnMapReadyCallback, OnClickListener, SimpleGestureListener{

	TabHost th;
	TextView x, restname, dealtext, duration, instructions, finePrint;
	TextView placeDetails, restaurantInfo;
	double latitude;
	double longitude;
	Button getDirections;
	GlobalClass global;
	Button timerText;
	String time;
	JSONObtainer obtainer;
	private SimpleGestureFilter detector;
	Button activate;
	String dtext;
	boolean login = false;
	Deal deal;
	static boolean dealExpired = false;
	boolean dealStarted = false;
	String dealString;
	ImageView main;
	ImageView about;
	Typeface typeface;


	DateTime start;
	DateTime end;
	CountDownTimer timer;
	ProgressDialog dialog;

	String restaurantPhoneNumber;
	String restaurantName;
	String restaurantID;
	int numOfImages = 15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deal_page);
		global = (GlobalClass) getApplication();

		Intent intent = getIntent();
		dealString = intent.getStringExtra("deal");
		dealExpired = false;
		Gson gson = new Gson();
		deal = gson.fromJson(dealString, Deal.class);
		start = deal.getStartDateTime();
		end = deal.getEndDateTime();

		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading deal...");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();

		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewPager.setAdapter(adapter);
		typeface = Typeface.createFromAsset(getAssets(),"fonts/Oswald-Light.ttf");
		th=(TabHost)findViewById(R.id.tabhost1);
		restname=(TextView)findViewById(R.id.placeName);
		restname.setTextSize(20);
		restname.setTypeface(typeface);
		dealtext=(TextView)findViewById(R.id.theDeal);
		dealtext.setTextSize(16);
		dealtext.setTypeface(typeface);
		duration = (TextView) findViewById(R.id.timeLimit);
		instructions = (TextView) findViewById(R.id.instructions);
		finePrint = (TextView) findViewById(R.id.fine_print);
		placeDetails = (TextView) findViewById(R.id.namefulladdress);
		placeDetails.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				th.setCurrentTab(3);
			}
		});
		restaurantInfo = (TextView) findViewById(R.id.RestoIntro);
		findViewById(R.id.get_directions).setOnClickListener(this);
		findViewById(R.id.button_call).setOnClickListener(this);
		findViewById(R.id.share).setOnClickListener(this);
		timerText = (Button) findViewById(R.id.deal_countdown_button);
		activate = (Button) findViewById(R.id.activatedeal);
		activate.setOnClickListener(this);
		main = (ImageView) findViewById(R.id.dealImageMain);
		about = (ImageView) findViewById(R.id.dealImageAbout);

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

		final int percentageDeal = deal.getPercentageDiscount();
		final int minamount = deal.getMinimumAmount();
		final int amountdiscount = deal.getAmountDiscount();
		final String canvastext	= deal.getCanvasText();
		final String freebie = deal.getFreebie();
		dtext="";
		if(canvastext.compareTo("")!=0&&canvastext!=null)
		{
			dtext=canvastext;
		}
		if(freebie.compareTo("")!=0&&freebie!=null)
		{
			dtext="Get "+freebie+" free on purchase of "+minamount;
		}
		if(amountdiscount!=0)
		{
			dtext="Get Rs."+amountdiscount+" off on a Minimum purchase of Rs."+minamount;
		}
		if(percentageDeal!=0)
		{
			dtext="Get "+percentageDeal+"% off on a Minimum purchase of Rs."+minamount;
		}
		getRestaurantDetails();

		login = global.getLoggedIn();
		if(login==false)
		{
			//activate.setEnabled(false);
			activate.setBackgroundColor(Color.parseColor("#f1f1f1"));
		}

		detector = new SimpleGestureFilter(this, this);
	}

	private class ImagePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			//return mImages.length;
			return numOfImages;
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
			Picasso.with(context)
			.load("http://waverr.in/restaurantmenuimages/"+deal.getRestaurantID()+"/"+(position+1)+".jpg")
			.placeholder(R.drawable.placeholder_fetching)
			.error(R.drawable.placeholderimage)
			.fit()
			.centerCrop()
			.into(imageView);
			System.gc();
			//imageView.setImageResource(mImages[position]);
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

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude) , 15.0f) );
		map.addMarker(new MarkerOptions()
		.position(new LatLng(latitude, longitude))
		.title("Location"));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.get_directions:
			Uri gmmIntentUri = Uri.parse("google.navigation:q="+deal.getRestaurantCoordinates());
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			mapIntent.setPackage("com.google.android.apps.maps");
			startActivity(mapIntent);
			break;

		case R.id.button_call:
			Intent dialIntent = new Intent(Intent.ACTION_DIAL);
			dialIntent.setData(Uri.parse("tel:" + restaurantPhoneNumber));
			startActivity(dialIntent);
			break;

		case R.id.activatedeal:
			// TODO Auto-generated method stub
			if(dealExpired == true) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Deal expired");
				builder.setMessage("Sorry... The deal has expired. Please have a look at other deals.");
				builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
			else if(dealStarted==false) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Deal not yet started");
				builder.setMessage("The deal has not yet started. Please check back later.");
				builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
			else if(login==false) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("You need to be logged in to activate deals. Go to login page?");
				builder.setTitle("Not logged in");
				builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent("com.project.waverr.LOGINPAGE");
						intent.putExtra("returnActivity", "com.project.waverr.DEALPAGE");
						intent.putExtra("deal", dealString);
						finish();
						//global.setDeal(deal);
						startActivity(intent);
					}
				});
				builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
			else {
				activate.setText("Activating...");
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				intent.putExtra("PROMPT_MESSAGE", "");

				List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
				if (!resInfo.isEmpty()){
					for (ResolveInfo info : resInfo) {
						if (info.activityInfo.packageName.equalsIgnoreCase("com.project.waverr") || 
								info.activityInfo.name.equalsIgnoreCase("Waverr") ) {
							intent.setPackage(info.activityInfo.packageName);
							break;
						}
					}
				}
				global.setDeal(deal);
				startActivityForResult(intent, 0);
			}
			break;
		case R.id.share:
			final String RestaurantName = deal.getRestaurantName();
			Intent i=new Intent(android.content.Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Waverr");
			i.putExtra(android.content.Intent.EXTRA_TEXT, dtext+" at "+RestaurantName+".\nAvail this deal and more, only on Waverr - India's First Live Deal Engine. Get the app from the Play Store - waverr.in/getwaverr");
			startActivity(Intent.createChooser(i,"Share via"));
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		activate.setText("Activate Deal");
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		//int currentTab = th.getCurrentTab();
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT:
			//th.setCurrentTab(currentTab-1);
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			//th.setCurrentTab(currentTab+1);
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
				if(contents.compareTo(deal.getRestaurantID())==0)
				{
					Intent i=new Intent("com.project.waverr.SUCCESS");
					i.putExtra("Working",true);
					i.putExtra("Share", dtext+" at "+deal.getRestaurantName());
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
				Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();

			}
		}
	}

	public void startTimer() {
		final long startMillis = start.getTimeInMillis();
		final long endMillis = end.getTimeInMillis();

		new JSONObtainer() {
			DateTime current = new DateTime();

			@Override
			protected void onProgressUpdate(Void... voids) {
				timerText.setText("Calculating time left...");
				activate.setBackgroundColor(Color.parseColor("#777777"));
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
							String text = "Deal starts in\n"
									+ actual.days + "d "
									+ actual.hours + "h "
									+ actual.minutes + "m "
									+ actual.seconds + "s";
							timerText.setText(text);
							activate.setBackgroundColor(Color.parseColor("#777777"));
						}

						@Override
						public void onFinish() {
							// TODO Auto-generated method stub
							dealStarted = true;
							activate.setBackgroundColor(Color.parseColor("#00c15b"));
							new CountDownTimer(timeUntilEnd, 1000) {

								@Override
								public void onTick(long millisUntilFinished) {
									// TODO Auto-generated method stub
									actual.setDateTimeByMillis(millisUntilFinished);
							//dealactivetest=true;

									//dealactivetest=true;

									String text = "Deal ends in\n"
											+ actual.days + "d "
											+ actual.hours + "h "
											+ actual.minutes + "m "
											+ actual.seconds + "s";
									timerText.setText(text);
								}

								@Override
								public void onFinish() {
									// TODO Auto-generated method stub
									String text = "Deal\nExpired!";
									timerText.setText(text);
									dealExpired = true;
									//activate.setEnabled(false);
									activate.setBackgroundColor(Color.parseColor("#777777"));
								}
							}.start();
						}
					}.start();
				}
			}
		}.execute(new String[] {"http://waverr.in/getcurrenttime.php"});
	}

	
	private void getRestaurantDetails() {
		String[] restaurantUrl = {
				"http://waverr.in/getrestaurantlocation.php",
				"restaurantname", deal.getRestaurantName()
		};

		Picasso.with(this)
		.load(deal.getImageURL())
		.placeholder(R.drawable.placeholder_fetching)
		.error(R.drawable.placeholderimage)
		.fit()
		.centerCrop()
		.into(main);
		System.gc();

		Picasso.with(this)
		.load(deal.getImageURL())
		.placeholder(R.drawable.placeholder_fetching)
		.error(R.drawable.placeholderimage)
		.fit()
		.centerCrop()
		.into(about);
		System.gc();
		startTimer();

		new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {
				if(array==null) {
					AlertDialog.Builder builder = new AlertDialog.Builder(DealPage.this);
					builder.setMessage("Failed to load the deal. Please check your internet connection and try again.");
					builder.setTitle("No internet");
					builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							closeActivity();
						}
					});
					builder.create().show();
				}
				else {
					JSONObject object = null;
					try {
						object = array.getJSONObject(0);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String[] things = {
							"Restaurant ID",
							"Restaurant Name",
							"Contact Number",
							"Street Address",
							"City",
							"Pincode",
							"Coordinates",
							"FinePrint",
							"Details"
					};
					try {

						deal.setRestaurantID(object.getString(things[0]));
						deal.setRestaurantName(object.getString(things[1]));
						deal.setRestaurantNumber(object.getString(things[2]));
						deal.setRestaurantAddress(object.getString(things[3])+", "+object.getString(things[4])+" - "+object.getString(things[5]));
						deal.setRestaurantCoordinates(object.getString(things[6]));
						deal.setRestaurantFinePrint(object.getString(things[7]));
						deal.setRestaurantDetails(object.getString(things[8]));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					goAhead();
				}
			}
		}.execute(restaurantUrl);
	}

	private void goAhead() {
		String[] url = {
				"http://waverr.in/getinstructions.php"
		};

		new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {
				JSONObject object = null;
				try {
					object= array.getJSONObject(0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					instructions.setText("How to:\n"+object.getString("Instructions"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goStillAhead();
			}
		}.execute(url);
	}
	void goStillAhead() {
		restname.setText(deal.getRestaurantName());
		restname.setTypeface(null, Typeface.BOLD);
		dealtext.setText(dtext);
		duration.setText("The deal is valid from "+start.getDateTime()+" to "+end.getDateTime());
		finePrint.setText("Fine Print:\n"+deal.getRestaurantFinePrint());
		placeDetails.setText("Address:\n"+deal.getRestaurantAddress());
		restaurantPhoneNumber = deal.getRestaurantNumber();
		restaurantInfo.setText("About "+deal.getRestaurantName()+":\n"+deal.getRestaurantDetails());

		dialog.dismiss();
		
		String[] latlng = deal.getRestaurantCoordinates().split(",");
		latitude = Double.parseDouble(latlng[0]);
		longitude = Double.parseDouble(latlng[1]);

		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.the_map);
		mapFragment.getMapAsync(this);
	}

	private void closeActivity() {
		finish();
	}
}