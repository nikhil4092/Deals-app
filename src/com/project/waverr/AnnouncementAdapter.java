package com.project.waverr;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

	private ArrayList<Restaurant> restaurants;
	private int rowLayout;
	private Context mContext;
	
	public AnnouncementAdapter(ArrayList<Restaurant> restaurants, int rowLayout, Context context) {
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
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurants.get(arg1);
		arg0.restaurantName.setText(restaurant.getName());
		arg0.announcement.setText(restaurant.getAnnouncements());
		
		Picasso.with(mContext)
		.load(restaurant.getUrl())
		.placeholder(R.drawable.placeholder_fetching)
		.error(R.drawable.placeholderimage)
		.into(arg0.restimage);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
		return new ViewHolder(v);
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView restaurantName;
		public TextView announcement;
		public ImageView restimage;
		public ViewHolder(View itemView) {
			super(itemView);
			restaurantName = (TextView) itemView.findViewById(R.id.announce_rest_name);
			announcement = (TextView) itemView.findViewById(R.id.announce_actual);
			restimage = (ImageView)itemView.findViewById(R.id.announcement_image);
		}
	}

}
