package com.example.tuljain.requests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Logout extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(myAdapter.getItem(position).toString());
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        listview.setItemChecked(position, true);
        //setContentView(R.layout.activity_logout);
    }
}
