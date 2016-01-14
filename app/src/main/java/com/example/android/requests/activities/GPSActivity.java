package com.example.android.requests.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.services.FetchAddressIntentService;
import com.example.android.requests.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class GPSActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{
    public GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    public boolean mRequestLocationUpdates;
    private LocationRequest mLocationRequest;
    public static int update_interval = 10000;
    private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 100;
    public static int fastest_interval = 5000;
    public static int displacement = 10;
    boolean mAddressRequested;
    Button showdata;
    TextView mLongitudeText;
    TextView mLatitudeText;
    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

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

        createLocationRequest();
        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaylocation();
            }
        });
    }

    public void fetchAddressButtonHandler(View view) {
        // Only start the service to fetch the address if GoogleApiClient is
        // connected.
        if (mGoogleApiClient.isConnected() && mLastLocation != null) {
            startIntentService();
        }
        // If GoogleApiClient isn't connected, process the user's request by
        // setting mAddressRequested to true. Later, when GoogleApiClient connects,
        // launch the service to fetch the address. As far as the user is
        // concerned, pressing the Fetch Address button
        // immediately kicks off the process of getting the address.
        //mAddressRequested = true;
        //updateUIWidgets();
    }



    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("TAG", "GOOGLE hvkhvkCLIENT");
        displaylocation();
        if (mRequestLocationUpdates){
            startLocationUpdates();
        }

        if (mLastLocation != null) {
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, "no geocoder is there ", Toast.LENGTH_LONG).show();
                return;
            }
            if (mAddressRequested) {
                startIntentService();
            }
        }
    }





    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("TAG", "GOOGLE CLIENT failed");

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

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
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
            startLocationUpdates();
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(update_interval);
        mLocationRequest.setFastestInterval(fastest_interval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(displacement);
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
        Log.i("TAG", String.valueOf(mLastLocation != null));
        Log.i("TAG", "cdcece");
        if (mLastLocation != null) {
            Log.i("TAG", "PERMINBNOISSION");
            double lati = mLastLocation.getLatitude();
            double longi = mLastLocation.getLongitude();
            mLongitudeText = (TextView)findViewById(R.id.mLongitudeText);
            mLatitudeText = (TextView)findViewById(R.id.mLatitudeText);
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(longi+""+lati);
        }
        else {
            mLongitudeText = (TextView)findViewById(R.id.mLongitudeText);
            mLatitudeText = (TextView)findViewById(R.id.mLatitudeText);
            mLatitudeText.setText("cant get");
            mLongitudeText.setText("Cant get");
        }
    }

    private void togglePeriodLocationUpdate(){
        if(!mRequestLocationUpdates){
            mRequestLocationUpdates =true;
            startLocationUpdates();

        }
        else{
                    mRequestLocationUpdates = false;
                    stopLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSION_ACCESS_COURSE_LOCATION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displaylocation();

               } else {
                   // permission denied, boo! Disable the// functionality that depends on this permission.
               }
               return;
           }
        }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            //displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                // to something here
            }
            else {

            }

        }
    }

}
