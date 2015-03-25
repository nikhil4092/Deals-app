package com.project.waverr;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private List<Deal> deals;
    private int rowLayout;
    private Context mContext;

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
        viewHolder.dealText.setText(deal.getCanvasText());
        String start = deal.getStartDateTime().getDateTime().split(" ")[0];
        String end = deal.getEndDateTime().getDateTime().split(" ")[0];
        if(start.equals(end))
        	viewHolder.date.setText("\n\n"+start);
        else
        	viewHolder.date.setText(start+"\n\nto\n\n"+end);
        
        viewHolder.time.setText(deal.getStartDateTime().getDateTime().split(" ")[1]+"\n\nto\n\n"
        						+ deal.getEndDateTime().getDateTime().split(" ")[1]);
        viewHolder.restaurantName.setText(deal.getRestaurantName());
        Picasso.with(mContext)
        .load(deal.getImageURL())
        .placeholder(R.drawable.placeholder_fetching)
        .error(R.drawable.placeholderimage)
        .into(viewHolder.dealImage);
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
        

        public ViewHolder(View itemView) {
            super(itemView);
            dealText = (TextView) itemView.findViewById(R.id.dealText);
            dealImage = (ImageView)itemView.findViewById(R.id.dealImage);
            date = (TextView) itemView.findViewById(R.id.dealDate);
            time = (TextView) itemView.findViewById(R.id.dealTime);
            restaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
        }

    }
}