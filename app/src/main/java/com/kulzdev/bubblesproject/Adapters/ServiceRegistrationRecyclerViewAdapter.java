package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.R;

import java.util.List;

public class ServiceRegistrationRecyclerViewAdapter
        extends RecyclerView.Adapter<ServiceRegistrationRecyclerViewAdapter.ViewHolder> {

    List<ServicesList> services;
    private Context mContext;

    public ServiceRegistrationRecyclerViewAdapter(List<ServicesList> services) {
        this.services = services;
    }

    public ServiceRegistrationRecyclerViewAdapter(List<ServicesList> services, Context mContext) {
        this.services = services;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.registration_services_view,viewGroup,false);
        ServiceRegistrationRecyclerViewAdapter.ViewHolder servicesViewHolder = new ServiceRegistrationRecyclerViewAdapter.ViewHolder(view);
        return servicesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageServiceCategory.setImageResource(services.get(i).getImageId());
        viewHolder.checkBoxServiceCategory.setText(services.get(i).getServiceCheckboxId());
        viewHolder.TextServiceCategory.setText(services.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxServiceCategory;
        TextView TextServiceCategory;
        ImageView imageServiceCategory;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxServiceCategory = itemView.findViewById(R.id.checkboxServiceCategory);
            TextServiceCategory = itemView.findViewById(R.id.txtServiceCategory);
            imageServiceCategory = itemView.findViewById(R.id.imageServiceCategory);

        }
    }
}
