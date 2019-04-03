package com.kulzdev.bubblesproject.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Models.Appointment;
import com.kulzdev.bubblesproject.Models.AppointmentList;
import com.kulzdev.bubblesproject.Models.Services;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.Models.Stylist;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AppointmentActivity extends AppCompatActivity implements   View.OnClickListener{

    private DatePicker datePicker;
    private TimePicker timePicker;
    private Dialog myDialog;

    private boolean isOpen = false;
    private ConstraintSet layout1, layout2;
    private ConstraintLayout constraintLayout;
    private ImageView imageViewPhoto;
    private Button btnSchedule;

    private ServicesList servicesList = new ServicesList();

    private User mClientUser;
    private User mStylistUser;


    private TextView txtclose, txtStylistDisplay;

    private ImageView photo, cover;

    private Appointment mAppointment;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String mClientId;
    private String [] mIntentData ;
    private  Random mRandom ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        btnSchedule = (Button)findViewById(R.id.btnSchedule);
        txtStylistDisplay =(TextView)findViewById(R.id.StylistName_display);

        //FireBase instances
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        mClientId = mAuth.getCurrentUser().getUid();

        /* TODO: receiving data from  RecyclerViewAdapter*/

        Intent i =  getIntent();
         mIntentData = getIntent().getExtras().getStringArray("StylistData");   // = i.getExtras().getString("StylistData");

        //Log.d("TAG","" + intentData[0] +" " +   intentData[1]+ " " + intentData[2] +" "+ intentData[3]);
        mRandom= new Random(10000);
        mRandom.nextDouble();

        Query findCurrentUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("id")
                .equalTo(mAuth.getCurrentUser().getUid());

        findCurrentUser.addValueEventListener(findClientUsersByID);

        Query findStylistUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("id")
                .equalTo(mIntentData[0]);

        findStylistUser.addValueEventListener(findStylistUsersByID);


        txtStylistDisplay.setText(mIntentData[1]);

        datePicker = (DatePicker)findViewById(R.id.appointmentDatePicker);
        timePicker = (TimePicker)findViewById(R.id.appointmentTimePicker);

        photo = findViewById(R.id.photo);
        cover = findViewById(R.id.cover);

        Intent intent= getIntent();
        String stylistName = intent.getExtras().getString("stylistName");
        String stylistAddress = intent.getExtras().getString("stylistDistance");
        int image = intent.getExtras().getInt("stylistImg");


        Window v = getWindow();
        v.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        layout1=new ConstraintSet();
        layout2 = new ConstraintSet();
        imageViewPhoto = (ImageView)findViewById(R.id.photo);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this,R.layout.profile_expanded);
        layout1.clone(constraintLayout);
        myDialog = new Dialog(this);

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = timePicker.getCurrentMinute();
                int hour = timePicker.getCurrentHour();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                mAppointment = new Appointment(
                        mClientId +""+mRandom.nextInt(),
                        mClientId, mIntentData[0], //stylistId
                        mClientUser.getFullName(),
                        mStylistUser.getFullName(),
                        hour + ":"+minutes,
                        day + " " + month + " " + year
                );

                mAppointment.setStyleRequested(mIntentData[3]);

    /*            List<Appointment> appListTemp = new ArrayList<>();
                appListTemp.add(mAppointment);

                AppointmentList appList = new AppointmentList(appListTemp);

                List<AppointmentList> appointmentList = new ArrayList<>();


                appointmentList.add(appList);


                *//* TODO: GETTING ALLAPPOINTMENT ENTRIES IN THE LIST*//*

                if(mClientUser.getAppointment() != null){

                    appointmentList = mClientUser.getAppointment();




                    Toast.makeText(getApplicationContext(),"there are clients",Toast.LENGTH_LONG).show();
                }*/

            /*    if(mStylistUser.getAppointment() != null){
                    for(Appointment appointment: mStylistUser.getAppointment()){
                        appointmentList.add(appointment);
                    }

                    Toast.makeText(getApplicationContext(),"there are stylist",Toast.LENGTH_LONG).show();
                }
*/
                //final AppointmentList appointmentList = new AppointmentList(mAppointment);


                mClientUser.setAppointment(mAppointment);
                mStylistUser.setAppointment(mAppointment);


                Log.d("Client", mClientUser.toString());
                Log.d("Client", mStylistUser.toString());


                updateUserInfo(mStylistUser);
                updateUserInfo(mClientUser);

            }
        });

    }


    ValueEventListener findClientUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    mClientUser = dataSnap.getValue(User.class);
                }

            }else{

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ValueEventListener findStylistUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    mStylistUser = dataSnap.getValue(User.class);
                }

            }else{

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){


        }
    }

    private void updateUserInfo(final User user) {
        /*TODO: Create a new user - Bubbles Database*/
        // user.setId(currentUser.getUid());
        if(user.getId() != null) {
            mDatabase.child(user.getId()).child("Appointments").child(user.getAppointment().getId())
                    .setValue(user.getAppointment())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                showMessage("Appointment Booked");
                            }
                        }
                    });
        }else{

        }
    }

    private void showMessage(String message) {
        /*TODO: Generating Toast Messages*/
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
