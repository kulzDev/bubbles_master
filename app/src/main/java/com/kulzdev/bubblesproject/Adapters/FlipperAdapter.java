package com.kulzdev.bubblesproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kulzdev.bubblesproject.R;

public class FlipperAdapter extends BaseAdapter {

    private Context mContext;
    private int[] mImages;
    private LayoutInflater mInflater;

    public FlipperAdapter(Context context, int[] images) {
        mContext = context;
        mImages= images;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return mImages.length;
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
        view = mInflater.inflate(R.layout.flipper_items, null);
        ImageView txtImage = (ImageView)view.findViewById(R.id.flipperImage);
        txtImage.setImageResource(mImages[i]);
        return view;
    }
}
