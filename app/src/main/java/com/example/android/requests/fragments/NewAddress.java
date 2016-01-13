package com.example.android.requests.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.MainActivity;
import com.example.android.requests.services.GPSTracker;
import com.facebook.share.model.ShareLinkContent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class NewAddress extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public NewAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_new_address, container, false);
        //LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//
//            criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
//            criteria.setAccuracy(Criteria.ACCURACY_FINE);
//            criteria.setAltitudeRequired(true);
//            criteria.setBearingRequired(true);
//            criteria.setSpeedRequired(true);
//
//        }
//        String provider = locationManager.getBestProvider(criteria, true);
//        //Location location = locationManager.getLastKnownLocation(provider);
//        LocationListener locationListener = new LocationListener() {
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onLocationChanged(Location location) {
//                // TODO Auto-generated method stub
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                double speed = location.getSpeed(); //spedd in meter/minute
//                speed = (speed * 3600) / 1000;      // speed in km/minute
//                Toast.makeText(getActivity(), "Current speed:" + location.getSpeed(), Toast.LENGTH_SHORT).show();
//            }
//        };

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        GPSTracker  gps = new GPSTracker(getActivity());

        // check if GPS enabled
        //Log.i(MainActivity.TAG, String.valueOf(gps.canGetLocation()));
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        }else{
            //Log.i(MainActivity.TAG, String.valueOf(gps.canGetLocation()));
            gps.showSettingsAlert();
        }

    return rootview;
}



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
