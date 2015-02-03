package com.project.waverr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.waverr.SimpleGestureFilter.SimpleGestureListener;

public class DealPage extends GlobalActionBar implements OnTabChangeListener, OnMapReadyCallback, OnClickListener, SimpleGestureListener{

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
    private SimpleGestureFilter detector;
	/*Integer[] imageIDs = {
			 R.drawable.chinese1,R.drawable.ic_launcher,R.drawable.splash,R.drawable.chinese1};*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deal_page);
		/*Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		 gallery.setAdapter(new ImageAdapter(this));
		 gallery.setOnItemClickListener(new OnItemClickListener() {
		 

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
					 ImageView imageView = (ImageView) findViewById(R.id.showpic);
					 imageView.setImageResource(imageIDs[position]);
		}
		 });*/
		
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
	    mapFragment.getMapAsync(this);
	    latitude = 13.0092;
	    longitude = 74.7937;
	    
	    getDirections = (Button) findViewById(R.id.get_directions);
	    getDirections.setOnClickListener(this);
	    
	    timer = (Button) findViewById(R.id.deal_countdown_button);
	    
	    new CountDownTimer(90*60*1000, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getApplicationContext(), "Tick", Toast.LENGTH_SHORT).show();
				long secondsUntil = millisUntilFinished/1000;
				String hours = String.valueOf((int)(secondsUntil/3600));
			    int remainder = (int)(secondsUntil - Integer.parseInt(hours) * 3600);
			    String minutes = String.valueOf(remainder/60);
			    remainder = remainder - Integer.parseInt(minutes) * 60;
			    String seconds = String.valueOf(remainder);
			    
			    time = hours+":"+minutes+":"+seconds;
			    timer.setText(time.toString());
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				timer.setText("Time's up!");
			}
		}.start();
	    
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
	    		R.drawable.splash,
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

	/*public class ImageAdapter extends BaseAdapter {
		 private Context context;
		 private int itemBackground;
		 public ImageAdapter(Context c)
		 {
		 context = c;
		 // sets a grey background; wraps around the images
		 TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
		 itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
		 a.recycle();
		 }
		 // returns the number of images
		 public int getCount() {
		 return imageIDs.length;
		 }
		 // returns the ID of an item
		 public Object getItem(int position) {
		 return position;
		 }
		 // returns the ID of an item
		 public long getItemId(int position) {
		 return position;
		 }
		 // returns an ImageView view
		 public View getView(int position, View convertView, ViewGroup parent) {
		 ImageView imageView = new ImageView(context);
		 imageView.setImageResource(imageIDs[position]);
		 imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
		 imageView.setBackgroundResource(itemBackground);
		 return imageView;
		 }
		}*/

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
}
