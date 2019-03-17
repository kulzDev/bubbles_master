package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kulzdev.bubblesproject.Activities.ServicesActivity;
import com.kulzdev.bubblesproject.Models.Services;
import com.kulzdev.bubblesproject.R;

import java.util.List;

public class ServicesRecyclerViewAdapter extends RecyclerView.Adapter<ServicesRecyclerViewAdapter.ServicesViewHolder> {

    List<Services> services;
    private Context mContext;

    public ServicesRecyclerViewAdapter(List<Services> services, Context mContext) {
        this.services = services;
        this.mContext = mContext;
    }

    public ServicesRecyclerViewAdapter(List<Services> services) {
        this.services = services;
    }

    public ServicesRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public static class ServicesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView serviceType;
        ImageView serviceId;

        public ServicesViewHolder( View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.cardview_id);
            serviceId = (ImageView)itemView.findViewById(R.id.servicesImageView);
            serviceType = (TextView)itemView.findViewById(R.id.servicesName);
        }
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_services,viewGroup,false);
        ServicesViewHolder servicesViewHolder = new ServicesViewHolder(view);
        return servicesViewHolder;
    }

    @Override
    public void onBindViewHolder( ServicesViewHolder servicesViewHolder,final int i) {
        servicesViewHolder.serviceId.setImageResource(services.get(i).getmServiceId());
        servicesViewHolder.serviceType.setText(services.get(i).getmServices());

        servicesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(",","Error - "+ i);
                Intent inr = new Intent(mContext, ServicesActivity.class);
                mContext.startActivity(inr);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public ServicesRecyclerViewAdapter activity;

}
