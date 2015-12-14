package com.example.tuljain.requests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class AboutUs extends MainActivity {

    RelativeLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(myAdapter.getItem(position).toString());
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
//        r = (RelativeLayout)findViewById(R.id.about);
//        getLayoutInflater().inflate(r);
        listview.setItemChecked(position, true);
//        setContentView(R.layout.activity_about_us);

    }
}
