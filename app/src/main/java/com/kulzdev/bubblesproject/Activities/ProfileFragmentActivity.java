package com.kulzdev.bubblesproject.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kulzdev.bubblesproject.Adapters.RecyclerViewAdapter;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragmentActivity extends Fragment {



    private View v;
    private RecyclerView myRecyclerview;
    private List<User> lStylist;

    public ProfileFragmentActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lStylist = new ArrayList<>();
        ArrayList <String> servlist = new ArrayList<>();

        lStylist.add(new User("bob lowe",R.drawable.sliderimage1,null));
        lStylist.add(new User("bruce mo",R.drawable.sliderimage1,null));
        lStylist.add(new User("nia fey",R.drawable.sliderimage1,null));
        lStylist.add(new User("xan be",R.drawable.sliderimage1,null));
        lStylist.add(new User("billy",R.drawable.sliderimage1,null));
        lStylist.add(new User("daniel",R.drawable.sliderimage1,null));
        lStylist.add(new User("sun",R.drawable.sliderimage1,null));
        lStylist.add(new User("tina",R.drawable.sliderimage1,null));
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


}
