package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.List;

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
        view = layoutInflater.inflate(R.layout.stylist_view_card,viewGroup,false);
        final  MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.stylistSkill.setText((CharSequence) mData.get(i).getServices());
        myViewHolder.stylistName.setText(mData.get(i).getFullName());
        myViewHolder.stylistImg.setImageResource(mData.get(i).getProfileImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView stylistName,stylistSkill;
        private ImageView stylistImg;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        stylistImg = (ImageView) itemView.findViewById(R.id.imgStylist);
        stylistName = (TextView)itemView.findViewById(R.id.stylistName);
        stylistSkill =(TextView)itemView.findViewById(R.id.stylistSkill);
    }
}
}