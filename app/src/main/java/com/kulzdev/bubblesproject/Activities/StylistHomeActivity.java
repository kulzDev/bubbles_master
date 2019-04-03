package com.kulzdev.bubblesproject.Activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class StylistHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {

    private TextView mTextMessage,mDisplayName;
    private FirebaseAuth mAuth;
    private User findUser, mUser;
    private Appointment mAppointment;
    private FirebaseUser currentUser;
    private CircleImageView mUserPhoto;
    private Query dbUser, mQuaryAppointment;
    private CardView mCardViewService;
    private ImageView nav_profile_image;
    private DatabaseReference mDatabase, mDBAppointment;
    RecyclerView recyclerView;
    ArrayList<Appointment> list;
    StylistListRecyclerAdapter adapter; //this works here for now


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.stylist_appointment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<Appointment>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        //mDBAppointment = FirebaseDatabase.getInstance().getReference().child("users").child("services");


        dbUser = mDatabase
                .orderByChild("id")
                .equalTo(mAuth.getCurrentUser().getUid());

       mQuaryAppointment = mDatabase
                .orderByChild("Appointments");




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        dbUser.addValueEventListener(findUsersByID);
        mQuaryAppointment.addValueEventListener(findAllUsersByID);


    }

    @Override
    protected void onRestart() {

        dbUser.addValueEventListener(findUsersByID);

        super.onRestart();
    }



    ValueEventListener findAllUsersByID  = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()){
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){



                   // mAppointment = dataSnapshot1.getValue(Appointment.class);
                    mUser = dataSnapshot1.getValue(User.class);

                    Log.d("TAG","Found A booking appointment  " + mUser);
                    Log.d("TAG","USers  " + mUser.getServices());

                    //Toast.makeText(getApplicationContext(), ""+mUser, Toast.LENGTH_LONG).show();

             /*   if(user.getAppointment().getmtylistId().equals(mAuth.getCurrentUser().getUid())){

                    Toast.makeText(getApplicationContext(), "Found A booking appointment" + user.getAppointment().getmtylistId(), Toast.LENGTH_LONG).show();

                }*/
                    //list.add(user.getAppointment());
                }


            }else {

                Log.d("TAG", "No snap shot");
            }
            //adapter = new StylistListRecyclerAdapter(StylistHomeActivity.this, list);
            //recyclerView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(StylistHomeActivity.this, "Oops... Something went wrong", Toast.LENGTH_LONG).show();

        }
    };

    ValueEventListener findUsersByID = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    findUser = dataSnap.getValue(User.class);
                    updateNavHeader(findUser);
                }
            }else{
                showMessage("User doesn't exits, please register");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void updateNavHeader(User user){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navDisplayName = headerView.findViewById(R.id.nav_stylist_displayName);

        //Update the profile image here also
        nav_profile_image = headerView.findViewById(R.id.nav_user_style_photo);

        navDisplayName.setText(user.getFullName());

        //currentUser.up

        nav_profile_image.setOnClickListener(this);


        //use Glide to load user image

        if( currentUser.getPhotoUrl() != null){
            Glide.with(this).load(currentUser.getPhotoUrl()).into(nav_profile_image);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stylist_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id){
            case R.id.nav_style_profile:
                // showMessage("Profile Clicked");
                Intent i = new Intent(this, StylistProfileActivity.class);
                startActivity(i);
                break;
            case R.id.nav_style_logout:
                signOut();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mAuth.signOut();

        //log out
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();


        switch (id){

        }
    }
}
