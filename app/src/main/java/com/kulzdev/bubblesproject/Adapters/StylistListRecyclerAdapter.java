package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StylistListRecyclerAdapter extends RecyclerView.Adapter<StylistListRecyclerAdapter.StylistListView>{

    private Context mContext;
    private ArrayList<User> mUsers;
    private FirebaseAuth mAuth;

    public StylistListRecyclerAdapter(Context mContext,ArrayList<User> users) {
        this.mContext = mContext;
        this.mUsers = users;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public StylistListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_user_list, viewGroup, false);


        return new StylistListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistListView clientListView, int position) {

        clientListView.userName.setText(mUsers.get(position).getFullName());
        //clientListView.eMail.setText(users.get(position).getEmail());
        //  Picasso.get().load(users.get(position).getProfilePics()).into(clientListView.imageView);


    }

    @Override
    public int getItemCount() {
        return mUsers.size();

    }

    public class StylistListView extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView userName;
        Button confirm_btn;
        Button cancel_btn;
        ImageView btnBack_arrow;

        public StylistListView(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.appointment_user_profileImage);
            userName = itemView.findViewById(R.id.profile_displayName);
            confirm_btn = itemView.findViewById(R.id.btnStylist_confirm_client);
            cancel_btn = itemView.findViewById(R.id.btnStylist_cancel_client);
            btnBack_arrow = itemView.findViewById(R.id.appointment_back_arrow);
            //   eMail = itemView.findViewById(R.id.Email);

        }
    }

}
