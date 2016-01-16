package com.example.android.requests.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.android.requests.R;

public class NewAddress extends AppCompatActivity {
    private  Toolbar toolbar;
    private AppCompatEditText address_flatno;
    private AppCompatEditText address_type;
    private AppCompatEditText address_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        address_flatno = (AppCompatEditText)findViewById(R.id.address_flatno);
        address_type  = (AppCompatEditText)findViewById(R.id.address_type);
        address_location = (AppCompatEditText)findViewById(R.id.address_location);

        Intent i = getIntent();
        int id = i.getIntExtra("address_id",0);
       if (id != 0) {
           getSupportActionBar().setTitle("Edit Address");
              address_flatno.setText("hey man whats up");
       }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
