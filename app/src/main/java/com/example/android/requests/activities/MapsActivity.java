package com.example.android.requests.activities;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.requests.R;
import com.example.android.requests.utils.CustomMarker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;;
    private HashMap markersHashMap;
    private Iterator<Map.Entry> iter;
    private CameraUpdate cu;
    private CustomMarker customMarkerOne, customMarkerTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleap) {
        googleMap = googleap;

        // Add a marker in Sydney and move the camera
        LatLng vasantVihar = new LatLng(28.5574, 77.159);
        googleap.addMarker(new MarkerOptions().position(vasantVihar).title("Marker in Vasant Vihar"));
        googleap.moveCamera(CameraUpdateFactory.newLatLng(vasantVihar));
        Log.i("TAG",String.valueOf(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        else {
        googleap.setMyLocationEnabled(true);}
    }

    @Override
    protected void onResume() {
        super.onResume();
        // initilizeMap();
    }

    void setCustomMarkerOnePosition() {
        customMarkerOne = new CustomMarker("markerOne", 40.7102747, -73.9945297);
        addMarker(customMarkerOne);
    }
    void setCustomMarkerTwoPosition() {
        customMarkerTwo = new CustomMarker("markerTwo", 43.7297251, -74.0675716);
        addMarker(customMarkerTwo);
    }
    public void startAnimation(View v) {
        animateMarker(customMarkerOne, new LatLng(40.0675716, 40.7297251));
    }
    public void zoomToMarkers(View v) {
        zoomAnimateLevelToFitMarkers(120);
    }
    public void animateBack(View v) {
        animateMarker(customMarkerOne, new LatLng(32.0675716, 27.7297251));
    }
    public void initializeUiSettings() {
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
    public void initializeMapLocationSettings() {

        //googleMap.setMyLocationEnabled(true);
        }
    public void initializeMapTraffic() {
        googleMap.setTrafficEnabled(true);
        }

            public void initializeMapType() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }


            public void initializeMapViewSettings() {
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(false);
        }


            //this is method to help us set up a Marker that stores the Markers we want to plot on the map
            public void setUpMarkersHashMap() {

        if (markersHashMap == null) {
            markersHashMap = new HashMap();
        }
        }

            //this is method to help us add a Marker into the hashmap that stores the Markers
            public void addMarkerToHashMap(CustomMarker customMarker, Marker marker) {
        setUpMarkersHashMap();
        markersHashMap.put(customMarker, marker);
        }


            //this is method to help us find a Marker that is stored into the hashmap

    public Marker findMarker(CustomMarker customMarker) {

        iter = markersHashMap.entrySet().iterator();

        while (iter.hasNext()) {

            Map.Entry mEntry = (Map.Entry) iter.next();
            CustomMarker key = (CustomMarker) mEntry.getKey();
            if (customMarker.getCustomMarkerId().equals(key.getCustomMarkerId())) {
                Marker value = (Marker) mEntry.getValue();
                return value;
                }
            }
        return null;
    }

    public void addMarker(CustomMarker customMarker) {
        MarkerOptions markerOption = new MarkerOptions().position(
        new LatLng(customMarker.getCustomMarkerLatitude(), customMarker.getCustomMarkerLongitude())).icon(
                BitmapDescriptorFactory.defaultMarker());

        Marker newMark = googleMap.addMarker(markerOption);
        addMarkerToHashMap(customMarker, newMark);
        }
            //this is method to help us remove a Marker
            public void removeMarker(CustomMarker customMarker) {

        if (markersHashMap != null) {
            if (findMarker(customMarker) != null) {

                findMarker(customMarker).remove();

                markersHashMap.remove(customMarker);

            }
        }
        }

            //this is method to help us fit the Markers into specific bounds for camera position
            public void zoomAnimateLevelToFitMarkers(int padding) {

        LatLngBounds.Builder b = new LatLngBounds.Builder();
        iter = markersHashMap.entrySet().iterator();

                while (iter.hasNext()) {
                     Map.Entry mEntry = (Map.Entry) iter.next();
                    CustomMarker key = (CustomMarker) mEntry.getKey();

            LatLng ll = new LatLng(key.getCustomMarkerLatitude(), key.getCustomMarkerLongitude());

            b.include(ll);
                 }
        LatLngBounds bounds = b.build();
        // Change the padding as per needed
        cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);
        }
            //this is method to help us move a Marker.

    public void moveMarker(CustomMarker customMarker, LatLng latlng) {
        if (findMarker(customMarker) != null) {
            findMarker(customMarker).setPosition(latlng);
            customMarker.setCustomMarkerLatitude(latlng.latitude);
            customMarker.setCustomMarkerLongitude(latlng.longitude);
            }
    }
            //this is method to animate the Marker. There are flavours for all Android versions

    public void animateMarker(CustomMarker customMarker, LatLng latlng) {
        if (findMarker(customMarker) != null) {
//            LatLngInterpolator latlonInter = new LinearFixed();
//            latlonInter.interpolate(20,
//            new LatLng(customMarker.getCustomMarkerLatitude(), customMarker.getCustomMarkerLongitude()), latlng);
//            customMarker.setCustomMarkerLatitude(latlng.latitude);
//            customMarker.setCustomMarkerLongitude(latlng.longitude);
//
//            if (android.os.Build.VERSION.SDK_INT >= 14) {
//
//                MarkerAnimation.animateMarkerToICS(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
//                        customMarker.getCustomMarkerLongitude()), latlonInter);
//            } else if (android.os.Build.VERSION.SDK_INT >= 11) {
//                MarkerAnimation.animateMarkerToHC(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
//                        customMarker.getCustomMarkerLongitude()), latlonInter);
//            } else {
//                MarkerAnimation.animateMarkerToGB(findMarker(customMarker), new LatLng(customMarker.getCustomMarkerLatitude(),
//
//                        customMarker.getCustomMarkerLongitude()), latlonInter);
//                }
//            }
        }
}





}
