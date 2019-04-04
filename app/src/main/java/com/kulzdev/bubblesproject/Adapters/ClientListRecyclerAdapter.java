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
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientListRecyclerAdapter extends RecyclerView.Adapter<ClientListRecyclerAdapter.ClientListView>{

    private Context mContext;
    private ArrayList<Appointment> mAppointments;
    private FirebaseAuth mAuth;

    public ClientListRecyclerAdapter(Context mContext, ArrayList<Appointment> mAppointments) {
        this.mContext = mContext;
        this.mAppointments = mAppointments;
        mAuth  = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ClientListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_user_list, viewGroup, false);
        return new ClientListView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListView clientListView, int i) {
        clientListView.clientFullName.setText(mAppointments.get(i).getClientName());
        clientListView.serviceRequested.setText(mAppointments.get(i).getStyleRequested());
//        clientListView.date.setText(mAppointments.get(i).getDate());
//        clientListView.time.setText(mAppointments.get(i).getTime());


    }

    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

    public class ClientListView extends RecyclerView.ViewHolder{


        CircleImageView imageView;
        TextView clientFullName;
        TextView clientNumber;
        TextView  serviceRequested;
        TextView date;
        TextView time;
        Button confirm_btn;
        Button cancel_btn;
        ImageView btnBack_arrow;

        public ClientListView(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.appointment_user_profileImage);
            clientFullName = itemView.findViewById(R.id.booking_client_name);
            //clientNumber = itemView.findViewById(R.id.booking_stylist_phoneNumber);
            time = itemView.findViewById(R.id.stylist_appointmentTime);
            date = itemView.findViewById(R.id.stylist_appointmentDate);
            serviceRequested = itemView.findViewById(R.id.stylist_serviceRequired);

            confirm_btn = itemView.findViewById(R.id.btnStylist_confirm_client);
            cancel_btn = itemView.findViewById(R.id.btnStylist_cancel_client);
            btnBack_arrow = itemView.findViewById(R.id.appointment_back_arrow);

        }
    }
}
