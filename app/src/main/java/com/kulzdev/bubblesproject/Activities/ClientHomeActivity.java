package com.kulzdev.bubblesproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.kulzdev.bubblesproject.Models.Services;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;
import com.kulzdev.bubblesproject.Adapters.ServicesRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage,mDisplayName;
    private FirebaseAuth mAuth;
    private User findUser;
    private FirebaseUser currentUser;



    //for displaying
    private AdapterViewFlipper adapterViewFlipper;
    private static final String[] TEXT = {"One","Two","Three","Four","Five","Six","Seven","Eight"};
    private static final int[] IMAGES = {R.drawable.fade01,R.drawable.fade02,R.drawable.fade03,R.drawable.fade04,R.drawable.fade05
            ,R.drawable.fade06,R.drawable.fade07,R.drawable.fade08};
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

        Button inButton = (Button)findViewById(R.id.button);
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ClientHomeActivity.this,ServicesActivity.class);
                startActivity(in);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mDisplayName = (TextView)findViewById(R.id.nav_displayName);
       // mTextMessage = (TextView)findViewById(R.id.txtMessage);


        //find a user by ID.

        //mTextMessage.setText("Welcome User " + mAuth.getCurrentUser().getUid());

        Query findUser = FirebaseDatabase.getInstance().getReference("users")
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

        findUser.addValueEventListener(findUsersByID);


        //Diplaying the output
        adapterViewFlipper = (AdapterViewFlipper)findViewById(R.id.viewFlip);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView) ;

        //creating adapter object
        FlipperAdapter adapter = new FlipperAdapter(this,IMAGES,TEXT);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);


        //Services recyclerView methods
        intializedata();
        initializeAdapter();


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
        if (id == R.id.action_settings) {
            return true;
        }

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

                    //mTextMessage.setText("Welcome " + findUser.getFullName());
                    //mDisplayName.setText(findUser.getFullName());

                    updateNavHeader();

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

    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navDisplayName = headerView.findViewById(R.id.nav_displayName);

        //Update the profile image here also
        ImageView nav_profile_image = findViewById(R.id.nav_user_photo);

        navDisplayName.setText(currentUser.getDisplayName());

        //currentUser.up


        //use Glide to load user image
        //Toast.makeText(getApplicationContext(),"" + currentUser.getPhotoUrl(), Toast.LENGTH_LONG).show();

        if( currentUser.getPhotoUrl() != null){
             Glide.with(this).load(currentUser.getPhotoUrl()).into(nav_profile_image);
        }


    }

//added 9:50 by Nkazi
private void intializedata(){
    services = new ArrayList<>();
    services.add(new Services("Hair Services",R.drawable.hairservices));
    services.add(new Services("Massage",R.drawable.massage));
    services.add(new Services("Make up",R.drawable.makeup));
    services.add(new Services("Nail Services",R.drawable.nailservices));





}
    private void initializeAdapter(){
        ServicesRecyclerViewAdapter servicesRecyclerViewAdapter = new ServicesRecyclerViewAdapter(services);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(servicesRecyclerViewAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ClientHomeActivity.this,ServicesActivity.class);
                startActivity(in);
            }
        });


    }
    //class for the AdapterViewFlipper
    class FlipperAdapter extends BaseAdapter {
        Context ctx;
        int[] images;
        String[] text;
        LayoutInflater inflater;

        public FlipperAdapter(Context ctx, int[] images, String[] text) {
            this.ctx = ctx;
            this.images = images;
            this.text = text;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount(){
            return text.length;
        }

        @Override
        public Object getItem(int i){
            return null;
        }
        @Override
        public long getItemId(int i){
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            view = inflater.inflate(R.layout.flipper_items, null);
            TextView txtname = (TextView)view.findViewById(R.id.idTextView);
            ImageView txtImage = (ImageView)view.findViewById(R.id.idImageview);
            txtname.setText(text[i]);
            txtImage.setImageResource(images[i]);
            return view;
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


}
