//package com.example.android.requests.activities;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.android.requests.R;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.location.places.ui.PlacePicker;
//
//public class PlaceActivity extends AppCompatActivity {
//
//    private GoogleApiClient mGoogleApiClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_place);
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
//                //.addConnectionCallbacks(this)
//                //.addOnConnectionFailedListener(this)
//                .build();
//        int PLACE_PICKER_REQUEST = 1;
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//        //startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        mGoogleApiClient.disconnect();
//        super.onStop();
//    }
//
//}
