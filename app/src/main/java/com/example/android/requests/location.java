package com.example.android.requests;

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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class location extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "TAG";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    protected Location mLastLocation;
    public GoogleApiClient mGoogleApiClient;
    public Location mCurrentLocation;
    public boolean mRequestLocationUpdates = false;
    private LocationRequest mLocationRequest;
    public static int UPDATE_INTERVAL = 10000;
    private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 2;
    public static int FASTEST_INTERVAL = 5000;
    public static int DISPLACEMENT = 5000;

    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Log.i(TAG, "oncreate");

        lblLocation = (TextView)findViewById(R.id.lbllocation);
        btnShowLocation = (Button)findViewById(R.id.buttonShowLocation);
        btnStartLocationUpdates = (Button)findViewById(R.id.buttonLocationUpdates);

        if(checkPlayServices()){
            Log.i(TAG, "if play services");
            buildGoogleApiClient();
            createLocationRequest();
        }
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLocation();
            }
        });

        btnStartLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePeriodLocationUpdates();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        checkPlayServices();
        Log.i("TAG", "ON RESUME");
        if(mGoogleApiClient.isConnected() && mRequestLocationUpdates){
            Log.i("TAG", "on resume is connected");
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopLocationUpdates();
    }

    private void displayLocation(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "PERMISSION");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
            Log.i("TAG", String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!= null){
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            lblLocation.setText(latitude + "," + longitude);
        }
        else{
            lblLocation.setText("could not get location");
        }
    }

//    private void displayLocation(){
//        Log.i("TAG",String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
//
//        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            //&& checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.i("TAG", "PERMISSION");
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
//            Log.i("TAG", String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
//
//        }
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        Log.i("TAG", String.valueOf(mLastLocation != null));
//        Log.i("TAG", "cdcece");
//        if (mLastLocation != null) {
//            Log.i("TAG", "PERMINBNOISSION");
//            double latitude = mLastLocation.getLatitude();
//            double longitude = mLastLocation.getLongitude();
//            lblLocation.setText(latitude + "," + longitude);
////            mLongitudeText = (TextView)findViewById(R.id.mLongitudeText);
////            mLatitudeText = (TextView)findViewById(R.id.mLatitudeText);
////            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
////            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        }
//        else{
//            lblLocation.setText("could not get location");
//        }
//    }

    private void togglePeriodLocationUpdates(){
        if(!mRequestLocationUpdates){
            btnStartLocationUpdates.setText("stop");
            mRequestLocationUpdates = true;
            startLocationUpdates();
        }
        else{
            btnStartLocationUpdates.setText("start");
            mRequestLocationUpdates = true;
            stopLocationUpdates();
        }

    }

    protected synchronized void buildGoogleApiClient(){
        Log.i(TAG, "google api client start");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        Log.i(TAG, "google api client end");
    }

    protected void createLocationRequest(){

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        Log.i(TAG, "create location request end");
    }

    private boolean checkPlayServices(){
        Log.i(TAG, "play services1");
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        Log.i(TAG, "play services2");
        int resultCode = api.isGooglePlayServicesAvailable(this);
        Log.i(TAG, String.valueOf(resultCode));
        Log.i(TAG, String.valueOf(ConnectionResult.SUCCESS));
        if(resultCode!= ConnectionResult.SUCCESS){
            Log.i(TAG, "play services4");
            if(api.isUserResolvableError(resultCode)) {
                Log.i(TAG, "play services5");
                GoogleApiAvailability.getInstance().getErrorString(resultCode);
            }
            else
            {
                Log.i(TAG, "play services6");
                Toast.makeText(getApplicationContext(), "this device is not supported", Toast.LENGTH_LONG);
                finish();
            }
            return false;
        }
        return true;
    }

    protected void startLocationUpdates(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //&& checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "PERMISSION");

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("TAG", "GOOGLE hvkhvkCLIENT");
        displayLocation();
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
        Log.i("TAG", "connection failed");

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        displayLocation();
        //updateUI();
    }

}
