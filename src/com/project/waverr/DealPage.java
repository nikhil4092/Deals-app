package com.project.waverr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class DealPage extends GlobalActionBar implements OnTabChangeListener{

	TabHost th;
	TextView x;
	Integer[] imageIDs = {
			 R.drawable.chinese1,R.drawable.ic_launcher,R.drawable.splash,R.drawable.chinese1};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.deal_page);
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		 gallery.setAdapter(new ImageAdapter(this));
		 gallery.setOnItemClickListener(new OnItemClickListener() {
		 

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
					 ImageView imageView = (ImageView) findViewById(R.id.showpic);
					 imageView.setImageResource(imageIDs[position]);
		}
		 });
		
		th=(TabHost)findViewById(R.id.tabhost1);
		
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
	}

	public class ImageAdapter extends BaseAdapter {
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
}
