package com.kulzdev.bubblesproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kulzdev.bubblesproject.Adapters.ClientListRecyclerAdapter;
import com.kulzdev.bubblesproject.Adapters.ServiceRegistrationRecyclerViewAdapter;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.Models.User;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;

public class ServicesRegistration extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ServicesList> list;
    ServiceRegistrationRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_registration);


        recyclerView = findViewById(R.id.servicesRegistrationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<ServicesList>();
        list.add(new ServicesList(R.drawable.makeup,"Nail Services"));
        list.add(new ServicesList(R.drawable.hairservices,"Hair Services"));
        list.add(new ServicesList(R.drawable.massageservice,"Massage Services"));
        list.add(new ServicesList(R.drawable.makeup,"Make-up Services"));
        list.add(new ServicesList(R.drawable.massageservice,"Massage Services"));
        list.add(new ServicesList(R.drawable.makeup,"Make-up Services"));

        adapter = new ServiceRegistrationRecyclerViewAdapter(list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(adapter);

    }
}
