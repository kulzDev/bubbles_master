package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kulzdev.bubblesproject.Activities.AppointmentActivity;
import com.kulzdev.bubblesproject.Activities.ClientHomeActivity;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public RecyclerViewAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    private Context mContext;
    private List<User> mData;

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.stylist_view_card, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.stylistSkill.setText("Hair Services");
        myViewHolder.address.setText(mData.get(i).getUserAddress());
        myViewHolder.stylistName.setText(mData.get(i).getFullName());
        myViewHolder.stylistImg.setImageResource(R.drawable.ic_person);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] list = new String[]{
                        mData.get(i).getId(),
                        mData.get(i).getFullName(),
                        mData.get(i).getUserAddress(),
                        "Hair Service"
                };




                Toast.makeText(mContext, "StylistCard Clicked" + mData.get(i).getId(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(mContext, AppointmentActivity.class);
                i.putExtra("StylistData", list);
                mContext.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView stylistName, stylistSkill, address;
        private ImageView stylistImg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stylistImg = (ImageView) itemView.findViewById(R.id.imgStylist);
            stylistName = (TextView) itemView.findViewById(R.id.stylistName);
            stylistSkill = (TextView) itemView.findViewById(R.id.stylistSkill);
            address = (TextView) itemView.findViewById(R.id.addressId);
        }
    }
}