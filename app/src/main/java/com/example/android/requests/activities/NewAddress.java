package com.example.android.requests.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.location.Address;
import java.util.List;
import android.location.Geocoder;
import java.util.Locale;

import com.example.android.requests.R;

public class NewAddress extends AppCompatActivity {
    private  Toolbar toolbar;
    private AppCompatEditText address_flatno;
    private AppCompatEditText address_type;
    private AppCompatEditText address_location;
    private AppCompatImageButton currentlocation;

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
        currentlocation =(AppCompatImageButton)findViewById(R.id.current_location);

        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(NewAddress.this, Locale.getDefault());
                try {

                    addresses = geocoder.getFromLocation(28.5321579, 77.1506255, 1);
                    if (addresses != null && addresses.size() > 0) {
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        address_flatno.setText(address + city+state+country);
                    }
                    // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                }catch(Exception e)

                {
                    e.printStackTrace();
                }

            }
        });

        Intent i = getIntent();
        int id = i.getIntExtra("address_id",0);
       if (id != 0) {
           getSupportActionBar().setTitle("Edit Address");
              address_flatno.setText("fvfv");
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
