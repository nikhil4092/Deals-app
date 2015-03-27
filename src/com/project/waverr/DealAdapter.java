package com.project.waverr;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

	private List<Deal> deals;
	private int rowLayout;
	private Context mContext;
	DateTime start;
	DateTime end;
	DateTime current;
	DateTime properStart;
	DateTime properEnd;

	public DealAdapter(List<Deal> deals, int rowLayout, Context context) {
		this.deals = deals;
		this.rowLayout = rowLayout;
		this.mContext = context;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
		return new ViewHolder(v);

	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		Deal deal = deals.get(i);
		start = deal.getStartDateTime();
		end = deal.getEndDateTime();
		viewHolder.dealText.setTextSize(16);
		Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Oswald-Regular.ttf");
		viewHolder.dealText.setTypeface(typeface);
		viewHolder.dealText.setText(deal.getCanvasText()+".");
		Gson gson = new Gson();
		final String dealString = gson.toJson(deal);
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("com.project.waverr.DEALPAGE");
				intent.putExtra("deal", dealString);
				mContext.startActivity(intent);
			}
		});
		typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/Oswald-Light.ttf");
		String start = deal.getStartDateTime().getDateTime().split(" ")[0];
		String[] startar = start.split("-");
		String end = deal.getEndDateTime().getDateTime().split(" ")[0];
		String[] endar = end.split("-");

		if(start.equals(end))
		{

			viewHolder.date.setTextSize(20);
			viewHolder.date.setGravity(Gravity.CENTER_HORIZONTAL);
			viewHolder.date.setTypeface(typeface);
			if(startar[1].equals("01"))
				startar[1]="JAN";
			else if(startar[1].equals("02"))
				startar[1]="FEB";
			else if(startar[1].equals("03"))
				startar[1]="MAR";
			else if(startar[1].equals("04"))
				startar[1]="APR";
			else if(startar[1].equals("05"))
				startar[1]="MAY";
			else if(startar[1].equals("06"))
				startar[1]="JUN";
			else if(startar[1].equals("07"))
				startar[1]="JUL";
			else if(startar[1].equals("08"))
				startar[1]="AUG";
			else if(startar[1].equals("09"))
				startar[1]="SEP";
			else if(startar[1].equals("10"))
				startar[1]="OCT";
			else if(startar[1].equals("11"))
				startar[1]="NOV";
			else if(startar[1].equals("12"))
				startar[1]="DEC";
			viewHolder.date.setText(startar[0]+" "+startar[1]);
		}
		else
		{
			//	viewHolder.date.setGravity(Gravity.TOP);
			viewHolder.date.setTextSize(20);
			viewHolder.date.setGravity(Gravity.CENTER_HORIZONTAL);
			viewHolder.date.setTypeface(typeface);
			if(endar[1].equals("01"))
				endar[1]="JAN";
			else if(endar[1].equals("02"))
				endar[1]="FEB";
			else if(endar[1].equals("03"))
				endar[1]="MAR";
			else if(endar[1].equals("04"))
				endar[1]="APR";
			else if(endar[1].equals("05"))
				endar[1]="MAY";
			else if(endar[1].equals("06"))
				endar[1]="JUN";
			else if(endar[1].equals("07"))
				endar[1]="JUL";
			else if(endar[1].equals("08"))
				endar[1]="AUG";
			else if(endar[1].equals("09"))
				endar[1]="SEP";
			else if(endar[1].equals("10"))
				endar[1]="OCT";
			else if(endar[1].equals("11"))
				endar[1]="NOV";
			else if(endar[1].equals("12"))
				endar[1]="DEC";
			if(startar[1].equals("01"))
				startar[1]="JAN";
			else if(startar[1].equals("02"))
				startar[1]="FEB";
			else if(startar[1].equals("03"))
				startar[1]="MAR";
			else if(startar[1].equals("04"))
				startar[1]="APR";
			else if(startar[1].equals("05"))
				startar[1]="MAY";
			else if(startar[1].equals("06"))
				startar[1]="JUN";
			else if(startar[1].equals("07"))
				startar[1]="JUL";
			else if(startar[1].equals("08"))
				startar[1]="AUG";
			else if(startar[1].equals("09"))
				startar[1]="SEP";
			else if(startar[1].equals("10"))
				startar[1]="OCT";
			else if(startar[1].equals("11"))
				startar[1]="NOV";
			else if(startar[1].equals("12"))
				startar[1]="DEC";
			viewHolder.date.setText(startar[0]+" "+startar[1]+"\nto\n"+endar[0]+" "+endar[1]);
		}
		String stime=deal.getStartDateTime().getDateTime().split(" ")[1];
		String etime=deal.getEndDateTime().getDateTime().split(" ")[1];
		String[] stimear = stime.split(":");
		String[] etimear = etime.split(":");
		int st1= Integer.parseInt(stimear[0]);
		int st2= Integer.parseInt(etimear[0]);
		if(st1<=12)
			stimear[2]="AM";
		else {
			stimear[0]=""+(st1-12);
			stimear[2]="PM";
		}
		if(st2<=12)
			etimear[2]="AM";
		else {
			etimear[0]=""+(st2-12);
			etimear[2]="PM";
		}
		viewHolder.time.setGravity(Gravity.CENTER_HORIZONTAL);
		viewHolder.time.setTextSize(20);
		viewHolder.time.setTypeface(typeface);
		viewHolder.time.setText(stimear[0]+":"+stimear[1]+" "+stimear[2]+"\nto\n"+etimear[0]+":"+etimear[1]+" "+
				etimear[2]);
		viewHolder.restaurantName.setTextSize(20);     
		viewHolder.restaurantName.setTypeface(typeface);
		viewHolder.restaurantName.setText(deal.getRestaurantName());
		Picasso.with(mContext)
		.load(deal.getImageURL())
		.placeholder(R.drawable.placeholder_fetching)
		.error(R.drawable.placeholderimage)
		.into(viewHolder.dealImage);
		startTimer(viewHolder);
	}

	@Override
	public int getItemCount() {
		return deals == null ? 0 : deals.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView dealText;
		public ImageView dealImage;
		public TextView date;
		public TextView time;
		public TextView restaurantName;
		public ImageView active;


		public ViewHolder(View itemView) {
			super(itemView);
			dealText = (TextView) itemView.findViewById(R.id.dealText);
			dealImage = (ImageView)itemView.findViewById(R.id.dealImage);
			date = (TextView) itemView.findViewById(R.id.dealDate);
			time = (TextView) itemView.findViewById(R.id.dealTime);
			restaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
			active= (ImageView)itemView.findViewById(R.id.ActiveDeal);

		}
	}
	public void startTimer(final ViewHolder viewHolder) {
		final long startMillis = start.getTimeInMillis();
		final long endMillis = end.getTimeInMillis();

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		long currentMillis = System.currentTimeMillis();

		if(startMillis > currentMillis)
			viewHolder.active.setImageResource(R.drawable.redglow);
		else if(hour >= start.hours && hour <= end.hours)
			viewHolder.active.setImageResource(R.drawable.greenglow);
		else
			viewHolder.active.setImageResource(R.drawable.redglow);
		
		if(endMillis < currentMillis)
			viewHolder.active.setImageResource(R.drawable.redglow);

	}
}