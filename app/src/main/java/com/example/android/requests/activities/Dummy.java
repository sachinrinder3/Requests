package com.example.android.requests.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.android.requests.activities.Dummy2;

import com.example.android.requests.R;

public class Dummy extends AppCompatActivity {
    private Toolbar toolbar;
    private Button dummybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dummy Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dummybutton = (Button)findViewById(R.id.dummybutton);
        dummybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent dummy2 = new Intent(Dummy.this, Dummy2.class);
                  startActivity(dummy2);
            }
        });

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
