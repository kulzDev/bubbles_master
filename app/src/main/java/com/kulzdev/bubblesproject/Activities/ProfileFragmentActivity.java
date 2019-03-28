package com.kulzdev.bubblesproject.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kulzdev.bubblesproject.Adapters.RecyclerViewAdapter;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragmentActivity extends Fragment {



    private View v;
    private RecyclerView myRecyclerview;
    private List<User> lStylist;
    private User mUser;

    public ProfileFragmentActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lStylist = new ArrayList<>();
        ArrayList <String> servlist = new ArrayList<>();

        Query findUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userType")
                .equalTo("Stylist");

        findUser.addValueEventListener(findByUserType);

        Log.d("Services", "here");

        /*lStylist.add(new User("bob lowe",R.drawable.sliderimage1,null));
        lStylist.add(new User("bruce mo",R.drawable.sliderimage1,null));
        lStylist.add(new User("nia fey",R.drawable.sliderimage1,null));
        lStylist.add(new User("xan be",R.drawable.sliderimage1,null));
        lStylist.add(new User("billy grim",R.drawable.sliderimage1,null));
        lStylist.add(new User("daniel judo",R.drawable.sliderimage1,null));
        lStylist.add(new User("sun moon",R.drawable.sliderimage1,null));
        lStylist.add(new User("tina lost",R.drawable.sliderimage1,null));*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile, container, false);
        myRecyclerview = (RecyclerView)v.findViewById(R.id.recyclerViewResults);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(),lStylist);
        myRecyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));
        myRecyclerview.setAdapter(myAdapter);
        return v;
    }


    ValueEventListener findByUserType = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

                for(DataSnapshot dataSnap : dataSnapshot.getChildren()){
                    mUser = dataSnap.getValue(User.class);
                    lStylist.add(mUser);
                }


            }else{


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



}
