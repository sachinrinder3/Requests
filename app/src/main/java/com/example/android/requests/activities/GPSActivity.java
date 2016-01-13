package com.example.android.requests.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.requests.R;
import com.example.android.requests.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.text.DateFormat;
import java.util.Date;

public class GPSActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
    public GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    public Location mCurrentLocation;
    public boolean mRequestLocationUpdates;
    private LocationRequest mLocationRequest;
    public static int update_interval = 10000;
   private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 2;
    public static int fastest_interval = 5000;
    public static int displacement = 5000;
    Button showdata;
    TextView mLongitudeText;
    TextView mLatitudeText;
    //private AddressResultReceiver mResultReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        showdata = (Button)findViewById(R.id.button);

        if (mGoogleApiClient == null) {
            Log.i("TAG", "GOOGLE CLIENT");
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API)
                    .build();
        }
        Log.i("TAG", "GOOGLE CLIENT2");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        createLocationRequest();
        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaylocation();
            }
        });
    }

//    public void fetchAddressButtonHandler(View view) {
//        // Only start the service to fetch the address if GoogleApiClient is
//        // connected.
//        if (mGoogleApiClient.isConnected() && mLastLocation != null) {
//            startIntentService();
//        }
//        // If GoogleApiClient isn't connected, process the user's request by
//        // setting mAddressRequested to true. Later, when GoogleApiClient connects,
//        // launch the service to fetch the address. As far as the user is
//        // concerned, pressing the Fetch Address button
//        // immediately kicks off the process of getting the address.
//        //mAddressRequested = true;
//        //updateUIWidgets();
//    }



//    protected void startIntentService() {
//        Intent intent = new Intent(this, FetchAddressIntentService.class);
//        intent.putExtra(Constants.RECEIVER, mResultReceiver);
//        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
//        startService(intent);
//    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("TAG", "GOOGLE hvkhvkCLIENT");
        displaylocation();
        if (mRequestLocationUpdates){
            startLocationUpdates();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("TAG", "GOOGLE CLIENT4");

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        displaylocation();
        //updateUI();
    }

//    private void updateUI() {
//        mLatitudeText.setText(String.valueOf(mCurrentLocation.getLatitude()));
//        mLongitudeText.setText(String.valueOf(mCurrentLocation.getLongitude()));
//        //mLastUpdateTimeTextView.setText(mLastUpdateTime);
//    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //&& checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "PERMISSION");

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,  mLocationRequest,this);
    }





    @Override
    protected void onStart() {
        if (mGoogleApiClient != null) {
            Log.i("TAG", "NOT NULL");
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mGoogleApiClient.isConnected()&&mRequestLocationUpdates){
            stopLocationUpdates();
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private void displaylocation(){
        Log.i("TAG",String.valueOf(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));

        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                //&& checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "PERMISSION");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
            Log.i("TAG", String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i("TAG",String.valueOf(mLastLocation != null));
        Log.i("TAG","cdcece");
        if (mLastLocation != null) {
            Log.i("TAG", "PERMINBNOISSION");
            mLongitudeText = (TextView)findViewById(R.id.mLongitudeText);
            mLatitudeText = (TextView)findViewById(R.id.mLatitudeText);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       // switch (requestCode) {
//            case MY_PERMISSION_ACCESS_COURSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
        displaylocation();
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displaylocation();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            //}

            // other 'case' lines to check for other
            // permissions this app might request
        }

}
