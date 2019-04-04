package com.kulzdev.bubblesproject.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Adapters.ClientListRecyclerAdapter;
import com.kulzdev.bubblesproject.Adapters.StylistListRecyclerAdapter;
import com.kulzdev.bubblesproject.Models.Appointment;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;

public class ClientListActivity extends AppCompatActivity {

    private Appointment mAppointment;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mDBAppointment;
    ArrayList<Appointment> mAppointmentList;
    private User findUser;
    private Query dbUser, mQuaryAppointment;
    private FirebaseUser currentUser;
    StylistListRecyclerAdapter adapter;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_appointment);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


//        mQuaryAppointment = mDBAppointment
//                .orderByChild("id");

//        Log.d("TAG","DB Appointment reference : " + mDBAppointment.toString());

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        recyclerView = findViewById(R.id.client_appointment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAppointmentList = new ArrayList<Appointment>();

//        dbUser = mDatabase
//                .orderByChild("id")
//                .equalTo(mAuth.getCurrentUser().getUid());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDBAppointment = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Appointments");

        mQuaryAppointment = mDBAppointment
                .orderByChild("id");


        //mQuaryAppointment.addValueEventListener(findAllUsersByID);

        Log.d("TAG","DB Appointment reference : " + mDBAppointment.toString());

        mQuaryAppointment.addValueEventListener(findAppointment);

    }

    ValueEventListener findAppointment = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    Log.d("TAG2","APPOINTMENTS  " + dataSnap.getValue(Appointment.class));

                    mAppointmentList.add(dataSnap.getValue(Appointment.class));
                }

                Log.d("TAG2", " snap shot exits");
            }else{
                showMessage("User doesn't exits, please register");
                Log.d("TAG2", "No snap shot");
            }


            adapter = new StylistListRecyclerAdapter(ClientListActivity.this /*list*/, mAppointmentList);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    ValueEventListener findAllUsersByID  = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){


                    User user = dataSnapshot1.getValue(User.class);
                    //if(user.getUserType().equals("Client"))
                    Toast.makeText(getApplicationContext(),"Do something", Toast.LENGTH_SHORT).show();

                    mAppointmentList.add(mAppointment);

                    Log.d("TAG","USers  " + user);

                }


            }else {

                Log.d("TAG", "No snap shot");
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(ClientListActivity.this, "Oops... Something went wrong", Toast.LENGTH_LONG).show();

        }
    };


}
