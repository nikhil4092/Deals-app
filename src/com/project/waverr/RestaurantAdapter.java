package com.project.waverr;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

	private List<Restaurant> restaurants;
	private int rowLayout;
	private Context mContext;

	public RestaurantAdapter(List<Restaurant> restaurants, int rowLayout, Context context) {
		this.restaurants = restaurants;
		this.rowLayout = rowLayout;
		this.mContext = context;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return restaurants==null ? 0 : restaurants.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		// TODO Auto-generated method stub
		final Restaurant restaurant = restaurants.get(i);
		viewHolder.name.setTextSize(20);
		Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Oswald-Light.ttf");
		viewHolder.name.setTypeface(typeface);
		viewHolder.name.setText(restaurant.getName());
		String url = restaurant.getUrl().isEmpty() ? null : restaurant.getUrl();
		Picasso.with(mContext)
		.load(url)
		.placeholder(R.drawable.placeholder_fetching)
		.error(R.drawable.placeholderimage)
		.into(viewHolder.image);
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.project.waverr.CHINESECUISINE");
				intent.putExtra("restaurant", restaurant.getName());
				mContext.startActivity(intent);
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
		return new ViewHolder(v);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView name;
		public ImageView image;


		public ViewHolder(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.restaurantNametext);
			image = (ImageView) itemView.findViewById(R.id.RestImage);
		}
	}

}
