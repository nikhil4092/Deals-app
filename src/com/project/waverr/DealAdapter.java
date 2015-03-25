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
        viewHolder.dealName.setText(deal.getCanvasText());
        Picasso.with(mContext)
        .load(deal.getImageURL())
        .placeholder(R.drawable.placeholder_fetching)
        .error(R.drawable.placeholderimage)
        .into(viewHolder.dealImage);
        //viewHolder.DealImage.setImageDrawable(mContext.getDrawable(deal.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return deals == null ? 0 : deals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dealName;
        public ImageView dealImage;

        public ViewHolder(View itemView) {
            super(itemView);
            dealName = (TextView) itemView.findViewById(R.id.dealName);
            dealImage = (ImageView)itemView.findViewById(R.id.dealImage);
        }

    }
}