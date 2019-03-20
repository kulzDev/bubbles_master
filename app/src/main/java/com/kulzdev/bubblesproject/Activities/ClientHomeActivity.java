package com.kulzdev.bubblesproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Adapters.FlipperAdapter;
import com.kulzdev.bubblesproject.Models.Services;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;
import com.kulzdev.bubblesproject.Adapters.ServicesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView mTextMessage,mDisplayName;
    private FirebaseAuth mAuth;
    private User findUser;
    private FirebaseUser currentUser;
    private CircleImageView mUserPhoto;
    private Query dbUser;
    private CardView mCardViewService;



    //for displaying
    private AdapterViewFlipper adapterViewFlipper;

    private static final int[] IMAGES = {
            R.drawable.sliderimage1,
            R.drawable.sliderimage2,
            R.drawable.sliderimage3,
            R.drawable.sliderimage4,
            R.drawable.sliderimage5
    };

    private int mPosition = -1;
    Context context;
    private List<Services> services;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        dbUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("id")
                .equalTo(mAuth.getCurrentUser().getUid());



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        dbUser.addValueEventListener(findUsersByID);

        //Diplaying the output
        adapterViewFlipper = (AdapterViewFlipper)findViewById(R.id.viewFlipper);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView) ;

        //creating adapter object
        FlipperAdapter adapter = new FlipperAdapter(this,IMAGES);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);

        findViewById(R.id.cvHairServices).setOnClickListener(this);
        findViewById(R.id.cvNailServices).setOnClickListener(this);
        findViewById(R.id.cvMassageServices).setOnClickListener(this);
        findViewById(R.id.cvMakeupServices).setOnClickListener(this);
        findViewById(R.id.cvTatooServices).setOnClickListener(this);
        findViewById(R.id.cvPiercingServices).setOnClickListener(this);


        //Services recyclerView methods
        //intializedata();
        //initializeAdapter();

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onRestart() {

        dbUser.addValueEventListener(findUsersByID);

        super.onRestart();
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
        getMenuInflater().inflate(R.menu.client_home, menu);
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
            case R.id.nav_profile:
                Intent i = new Intent(this, ClientProfileActivity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                signOut();
                break;
            case R.id.nav_appointment:
                Intent ie = new Intent(this, ClientListActivity.class);
                startActivity(ie);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
        TextView navDisplayName = headerView.findViewById(R.id.nav_displayName);

        //Update the profile image here also
        ImageView nav_profile_image = findViewById(R.id.nav_user_photo);

        navDisplayName.setText(user.getFullName());

        //currentUser.up

        nav_profile_image.setOnClickListener(this);


        //use Glide to load user image

        if( currentUser.getPhotoUrl() != null){
             Glide.with(this).load(currentUser.getPhotoUrl()).into(nav_profile_image);
        }


    }



    @Override
    public void onClick(View v) {
        int id = v.getId();


        switch (id){
            case R.id.nav_user_photo:
                Intent i = new Intent(this, ClientProfileActivity.class);
                startActivity(i);
                break;
            case R.id.cvHairServices:
                Toast.makeText(getApplicationContext(), "Hair Services Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cvNailServices:
                Toast.makeText(getApplicationContext(), "Nail Services Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cvMakeupServices:
                Toast.makeText(getApplicationContext(), "Makeup Services Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cvMassageServices:
                Toast.makeText(getApplicationContext(), "Massage Services Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cvTatooServices:
                Toast.makeText(getApplicationContext(), "Tatoo Services Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cvPiercingServices:
                Toast.makeText(getApplicationContext(), "Piercing Services Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {

        }
    }

    private void signOut() {
        mAuth.signOut();

        //log out
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;


            }
            return false;
        }
    };


}
