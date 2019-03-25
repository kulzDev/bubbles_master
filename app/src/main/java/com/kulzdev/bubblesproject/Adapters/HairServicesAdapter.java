package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static com.kulzdev.bubblesproject.Adapters.HairServicesAdapter.*;

public class HairServicesAdapter extends RecyclerView.Adapter<HairServicesAdapter.ServicesViewHolder>{

    private Context sContext;
    private ArrayList<ServicesList> servicesLists;

    public HairServicesAdapter(ArrayList<ServicesList> servicesLists) {
        this.sContext = sContext;
        this.servicesLists = servicesLists;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_selection, viewGroup, false);
        ServicesViewHolder servicesViewHolder = new ServicesViewHolder(view);

        return servicesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder servicesViewHolder, int i) {
servicesViewHolder.checkBox.setText(servicesLists.get(i).getServiceType());

    }

    @Override
    public int getItemCount() {
        return servicesLists.size();

    }

  public class ServicesViewHolder extends RecyclerView.ViewHolder {

        Button search;
        SeekBar seekBar;
        CheckBox checkBox;



      public ServicesViewHolder(@NonNull View itemView) {
          super(itemView);

       //   search = itemView.findViewById(R.id.btnSearch);
       //   seekBar = itemView.findViewById(R.id.distance_seekbar);
          checkBox = itemView.findViewById(R.id.servicesId);

      }
  }
    }



