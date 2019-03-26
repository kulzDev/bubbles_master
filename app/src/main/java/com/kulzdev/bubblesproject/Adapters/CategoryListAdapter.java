package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kulzdev.bubblesproject.Models.ServicesList;
import com.kulzdev.bubblesproject.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryHolder> {

    private List<ServicesList> listC = new ArrayList<>();
    private Context dContext;



    public CategoryListAdapter(List<ServicesList> listC, Context dContext){
        this.dContext = dContext;
        this.listC = listC;
    }


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_items,viewGroup,false);
        CategoryHolder categoryListAdapter= new CategoryHolder(view);
        return categoryListAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder categoryHolder, int i) {
        //categoryHolder.serviceListTxt.setText(listC.get(i).getName());
        categoryHolder.serviceListCheckBox.setText(listC.get(i).getServiceCheckboxType());
    }

    @Override
    public int getItemCount() {
        return listC.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        CheckBox serviceListCheckBox;
        TextView  serviceListTxt;
        CardView listCard;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            listCard = itemView.findViewById(R.id.listCard);
            serviceListCheckBox = itemView.findViewById(R.id.listSelectionCheckBox);
           // serviceListTxt = itemView.findViewById(R.id.txtListSelection);
        }
    }
}
