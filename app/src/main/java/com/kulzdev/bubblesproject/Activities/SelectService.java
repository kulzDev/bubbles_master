package com.kulzdev.bubblesproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.kulzdev.bubblesproject.Adapters.HairServicesAdapter;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;

public class SelectService extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ServicesList> lists;
    private SeekBar seekBar;
    private TextView selectedDistance;
    Button btnFilterSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
        btnFilterSearch = (Button)findViewById(R.id.btnSearchFilter);

        btnFilterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FilterResultActivity.class);
                startActivity(intent);
            }
        });


        servicesOffer();
        seekBarr();

    }

    public void seekBarr() {

        seekBar = (SeekBar)findViewById(R.id.distance_seekbar);
        selectedDistance = (TextView)findViewById(R.id.distanceSelected);
        selectedDistance.setText(seekBar.getProgress() + " " + seekBar.getMax());
        //seekBar.setMax(40);


           seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

               int progressValue;
               @Override
              public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                  progressValue = progress;
                  selectedDistance.setText(progress + " km ");
                  //Toast.makeText(getApplicationContext(),"Distance selected: " + seekBar.getMax(),Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onStartTrackingTouch(SeekBar seekBar) {
                  //Toast.makeText(getApplicationContext(),"Started ",Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onStopTrackingTouch(SeekBar seekBar) {
                  selectedDistance.setText( progressValue +" km");
                  //Toast.makeText(getApplicationContext(),"Stopped " + seekBar.getMax() ,Toast.LENGTH_SHORT).show();
              }
          });
    }

    private void  servicesOffer() {
        lists = new ArrayList<>();
    /*    lists.add(new ServicesList("Cut"));
        lists.add(new ServicesList("Coloring"));
        lists.add(new ServicesList("Mohawk"));
        lists.add(new ServicesList("Brush"));
        lists.add(new ServicesList("Hair wash"));
        lists.add(new ServicesList("Bob cut"));
        lists.add(new ServicesList("Shave"));
        lists.add(new ServicesList("CUT"));
        lists.add(new ServicesList("CUT"));
        lists.add(new ServicesList("Cut"));
        lists.add(new ServicesList("Coloring"));
        lists.add(new ServicesList("Mohawk"));
        lists.add(new ServicesList("Brush"));
        lists.add(new ServicesList("Hair wash"));
        lists.add(new ServicesList("Bob cut"));
        lists.add(new ServicesList("Shave"));
        lists.add(new ServicesList("CUT"));
        lists.add(new ServicesList("CUT"));
        lists.add(new ServicesList("Cut"));
        lists.add(new ServicesList("Coloring"));
        lists.add(new ServicesList("Mohawk"));
        lists.add(new ServicesList("Brush"));
        lists.add(new ServicesList("Hair wash"));
        lists.add(new ServicesList("Bob cut"));
        lists.add(new ServicesList("Shave"));
        lists.add(new ServicesList("CUT"));
        lists.add(new ServicesList("CUT"));*/

        iniBit();
    }

    private void iniBit(){

        recyclerView = findViewById(R.id.recycler_view_ServicesList);
        HairServicesAdapter adapter = new HairServicesAdapter(lists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
}
