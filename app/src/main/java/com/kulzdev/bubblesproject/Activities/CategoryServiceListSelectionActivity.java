package com.kulzdev.bubblesproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kulzdev.bubblesproject.Adapters.CategoryListAdapter;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceListSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerViewList;
    private List<ServicesList> servicesLists;
    private CategoryListAdapter adapter;
    private Button btnFinish;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_service_list_selection);

        recyclerViewList = (RecyclerView) findViewById(R.id.serviceSelectionRecycleview);
        btnFinish = (Button)findViewById(R.id.btnFinish);

        servicesLists = new ArrayList<>();
        servicesLists.add(new ServicesList("Swidish cut"));
        servicesLists.add(new ServicesList("German cut"));
        servicesLists.add(new ServicesList("Bob cut"));
        servicesLists.add(new ServicesList("Rebecca cut"));
        servicesLists.add(new ServicesList("Mohawk "));
        servicesLists.add(new ServicesList("Graffit styling"));
        servicesLists.add(new ServicesList("Swidish cut"));
        servicesLists.add(new ServicesList("German cut"));
        servicesLists.add(new ServicesList("Bob cut"));
        servicesLists.add(new ServicesList("Rebecca cut"));
        servicesLists.add(new ServicesList("Mohawk "));
        servicesLists.add(new ServicesList("Graffit styling"));

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(context);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CategoryListAdapter(servicesLists,getApplicationContext());
        recyclerViewList.setAdapter(adapter);


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StylistHomeActivity.class);
                startActivity(intent);
            }
        });

    }
}