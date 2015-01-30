package com.project.waverr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	View rowView;
	ImageView imageView;
 
	public RestaurantArrayAdapter(Context context, String[] values) {
		super(context, R.layout.restaurant_list, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		rowView = inflater.inflate(R.layout.restaurant_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.restaurantName);
		imageView = (ImageView) rowView.findViewById(R.id.favourite);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
 
		System.out.println(s);
 
		GlobalClass global = (GlobalClass) context.getApplicationContext();
		
		if (global.isFavourited(s))
			imageView.setImageResource(R.drawable.favorite_full);
		else
			imageView.setImageResource(R.drawable.favorite_empty);
 
		return rowView;
	}

	public void updateIcon(int position) {
		imageView.setImageResource(R.drawable.favorite_full);
	}
}
