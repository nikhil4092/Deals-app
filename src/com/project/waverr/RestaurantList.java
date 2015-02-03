package com.project.waverr;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestaurantList extends Activity {

	//private ListView mListView;
	//private RestaurantArrayAdapter adapter;
	
	private LinearLayout mLinearLayout;
	private String[] restaurants = {
			"Smoke 'n' Oven",
			"Diesel Cafe",
			"Chefs Xinlai",
			"Hao Ming",
			"Cafe Mojo",
			"Trattoria"
	};
	private GlobalClass global;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_list);
		global = (GlobalClass) getApplication();
		Arrays.sort(restaurants);
		
		/*mListView = (ListView) findViewById(R.id.restaurantListView);
		adapter = new RestaurantArrayAdapter(this, restaurants);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				if(position==0){
	                  ImageView imageView = (ImageView) view.findViewById(R.id.favourite);
	                  imageView.setImageResource(R.drawable.favorite_full);
	            }
			}
		});*/
		
		ArrayList<ImageButton> buttonList = new ArrayList<>();
		ArrayList<TextView> restaurantList = new ArrayList<>();
		
		mLinearLayout = (LinearLayout) findViewById(R.id.restaurant_layout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		for(String i: restaurants) {
			LinearLayout lineLayout = new LinearLayout(this);
			lineLayout.setOrientation(LinearLayout.HORIZONTAL);
			lineLayout.setGravity(Gravity.CENTER_VERTICAL);
			final TextView textView = new TextView(this);
			textView.setText(i);
			textView.setLayoutParams(params);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			//textView.setTextSize(android.R.attr.textAppearanceSmall);
			final ImageButton button = new ImageButton(this);
			button.setBackgroundColor(Color.TRANSPARENT);
			if(global.isFavourited(i))
				button.setImageResource(R.drawable.favorite_full);
			else
				button.setImageResource(R.drawable.favorite_empty);
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String restaurant = textView.getText().toString();
					global.setFavourite(restaurant, !global.isFavourited(restaurant));
					if(global.isFavourited(restaurant))
						button.setImageResource(R.drawable.favorite_full);
					else
						button.setImageResource(R.drawable.favorite_empty);
				}
			});
			buttonList.add(button);
			restaurantList.add(textView);
			lineLayout.addView(button, 0);
			lineLayout.addView(textView, 1);
			mLinearLayout.addView(lineLayout);
		}
	}
}
