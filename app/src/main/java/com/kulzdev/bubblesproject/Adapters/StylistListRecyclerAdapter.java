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
import com.kulzdev.bubblesproject.Models.Appointment;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StylistListRecyclerAdapter extends RecyclerView.Adapter<StylistListRecyclerAdapter.StylistListView>{

    private Context mContext;
    private ArrayList<Appointment> mAppointments;
    private FirebaseAuth mAuth;
    private ArrayList<User> mUsers;


//    public StylistListRecyclerAdapter(Context mContext,/*ArrayList<Appointment> appointments,*/ ArrayList<User> mUsers) {
//        this.mContext = mContext;
//      //  this.mAppointments = appointments;
//        this.mUsers = mUsers;
//        mAuth = FirebaseAuth.getInstance();
//    }

  /*  public StylistListRecyclerAdapter(Context mContext, ArrayList<User> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        mAuth = FirebaseAuth.getInstance();
    }*/

    public StylistListRecyclerAdapter(Context mContext, ArrayList<Appointment> mAppoiment) {
        this.mContext = mContext;
        this.mAppointments = mAppoiment;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public StylistListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_list_item, viewGroup, false);


        return new StylistListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StylistListView clientListView, int position) {

        clientListView.clientFullName.setText(mAppointments.get(position).getClientName());
        clientListView.serviceRequested.setText(mAppointments.get(position).getStyleRequested());
       clientListView.date.setText(mAppointments.get(position).getDate());
        clientListView.time.setText(mAppointments.get(position).getTime());
        //clientListView.clientFullName.setText(mUsers.get(position).getFullName());
        //clientListView.clientFullName.setText(mUsers.get(position).getFullName());



        //clientListView.eMail.setText(users.get(position).getEmail());
        //  Picasso.get().load(users.get(position).getProfilePics()).into(clientListView.imageView);


    }

    @Override
    public int getItemCount() {
        //return mAppointments.size();
        return mAppointments.size();


    }

    public class StylistListView extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView clientFullName;
        TextView clientNumber;
        TextView  serviceRequested;
        TextView date;
        TextView time;
        Button confirm_btn;
        Button cancel_btn;
        ImageView btnBack_arrow;

        public StylistListView(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.appointment_user_profileImage);
            clientFullName = itemView.findViewById(R.id.booking_client_name);
            clientNumber = itemView.findViewById(R.id.booking_client_phonenumber);
            time = itemView.findViewById(R.id.client_appointmentTime);
            date = itemView.findViewById(R.id.client_appointmentDate);
            serviceRequested = itemView.findViewById(R.id.client_serviceRequired);

            confirm_btn = itemView.findViewById(R.id.btnStylist_confirm_client);
            cancel_btn = itemView.findViewById(R.id.btnStylist_cancel_client);
            btnBack_arrow = itemView.findViewById(R.id.appointment_back_arrow);
            //   eMail = itemView.findViewById(R.id.Email);

        }
    }

}
