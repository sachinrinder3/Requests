package com.example.android.requests.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.location.Address;
import java.util.List;
import android.location.Geocoder;
import android.widget.Toast;
import java.util.Locale;
import com.example.android.requests.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class NewAddress extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private  Toolbar toolbar;
    private AppCompatEditText address_flatno;
    private AppCompatEditText address_type;
    private AppCompatEditText address_location;
    private AppCompatImageButton currentlocation;
    public GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private String TAG = "TAG";
    public static int UPDATE_INTERVAL = 10000;
    private int MY_PERMISSION_ACCESS_COURSE_LOCATION = 2;
    public static int FASTEST_INTERVAL = 5000;
    public static int DISPLACEMENT = 5000;
    protected Location mLastLocation;
    double lat =0;
    double lon =0;

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

                LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewAddress.this);
                    builder.setTitle("Location Services Not Active");
                    builder.setMessage("Please enable Location Services and GPS");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Show location settings when the user acknowledges the alert dialog
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 200);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }
                else {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                    }
                    displayLocation();
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(NewAddress.this, Locale.getDefault());
                    try {
                        if (lat != 0 && lon != 0) {
                            Log.i("TAG", "NH");
                        addresses = geocoder.getFromLocation(lat, lon, 1);
                        if (addresses != null && addresses.size() > 0) {
                            Log.i("TAG", "NH");
                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            String city = addresses.get(0).getLocality();
                            //String state = addresses.get(0).getAdminArea();
                            //String country = addresses.get(0).getCountryName();
                            address_location.setText(address + ", " + city);
                        }}
                    } catch (Exception e)

                    {
                        e.printStackTrace();
                    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (checkPlayServices()) {
                Log.i(TAG, "if play services");
                buildGoogleApiClient();
                createLocationRequest();
            }
//          displayLocation();


                if (lat != 0 && lon != 0) {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(NewAddress.this, Locale.getDefault());
                            addresses = geocoder.getFromLocation(lat, lon, 1);
                                if (addresses != null && addresses.size() > 0) {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String city = addresses.get(0).getLocality();
                                    //String state = addresses.get(0).getAdminArea();
                                    //String country = addresses.get(0).getCountryName();
                                    address_location.setText(address + ", " + city);
                                }}
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }
                    };

            }


        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    private void displayLocation(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!= null){
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
            Log.i("TAG", String.valueOf(lat));
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
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

    private boolean checkPlayServices(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(this);
        if(resultCode!= ConnectionResult.SUCCESS){
            if(api.isUserResolvableError(resultCode)) {
                GoogleApiAvailability.getInstance().getErrorString(resultCode);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "This device is not supported", Toast.LENGTH_LONG);
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(NewAddress.this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }




    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    protected void createLocationRequest(){

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        displayLocation();
    }
}
