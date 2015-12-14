package com.example.tuljain.requests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by geetika on 14/12/15.
 */
class MyAdapter extends BaseAdapter {
    private Context context;
    String[] galaxy;
    int[] images = {R.drawable.hamburger, R.drawable.hamburger, R.drawable.user, R.drawable.exit, R.drawable.ic_action_name2};

    public MyAdapter(Context context) {
        this.context = context;
        galaxy = context.getResources().getStringArray(R.array.planet);
    }

    @Override
    public int getCount() {
        return galaxy.length;
    }

    @Override
    public Object getItem(int position) {
        return galaxy[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.customlayout, parent, false);
        } else {
            row = convertView;
        }
        TextView titleTextView = (TextView) row.findViewById(R.id.t1);
        ImageView titleImageView = (ImageView) row.findViewById(R.id.im1);
        titleTextView.setText(galaxy[position]);
        //titleTextView.setHeight(5);
        titleImageView.setImageResource(images[position]);
        //titleTextView.setHeight(5);
        row.setMinimumHeight(100);
        return row;
    }

}