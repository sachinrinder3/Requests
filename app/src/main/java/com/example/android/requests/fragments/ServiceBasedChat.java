package com.example.android.requests.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
//import com.example.android.requests.activities.ChatActivity;
import com.example.android.requests.activities.HomeServices;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;


public class ServiceBasedChat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AppCompatImageButton shopping;
    private AppCompatImageButton homeservices;
    private AppCompatImageButton foods;
    private AppCompatImageButton recharge;
    private AppCompatImageButton cabs;
    private AppCompatImageButton travel;
    private ChatterBoxClient chatterBoxServiceClient;

    public ServiceBasedChat() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_service_based_chat, container, false);
        shopping = (AppCompatImageButton)v.findViewById(R.id.shopping);
        homeservices = (AppCompatImageButton)v.findViewById(R.id.homeservices);
        foods = (AppCompatImageButton)v.findViewById(R.id.food);
        recharge = (AppCompatImageButton)v.findViewById(R.id.recharge);
        cabs = (AppCompatImageButton)v.findViewById(R.id.cabs);
        travel = (AppCompatImageButton)v.findViewById(R.id.travel);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent shoppingactivity = new Intent(getActivity(), com.example.android.requests.activities.Shopping.class);
//                startActivity(shoppingactivity);
                Intent foodactivity = new Intent(getActivity(), HomeServices.class);
                foodactivity.putExtra("Service", "Shopping");
                startActivity(foodactivity);
            }
        });
        homeservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent homeservicesactivity = new Intent(getActivity(), com.example.android.requests.activities.HomeServices.class);
//                startActivity(homeservicesactivity);
                Intent foodactivity = new Intent(getActivity(), HomeServices.class);
                foodactivity.putExtra("Service", "HomeServices");
                startActivity(foodactivity);
            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodactivity = new Intent(getActivity(), HomeServices.class);
                foodactivity.putExtra("Service", "Food");
                startActivity(foodactivity);
            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recharge = new Intent(getActivity(), HomeServices.class);
                recharge.putExtra("Service", "Recharge");
                startActivity(recharge);
            }
        });
        cabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cabs = new Intent(getActivity(), HomeServices.class);
                cabs.putExtra("Service", "Cabs");
                startActivity(cabs);
            }
        });
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent travel = new Intent(getActivity(), HomeServices.class);
                travel.putExtra("Service", "Travel");
                startActivity(travel);
            }
        });
        return v;

    }

    //@Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Constant.TAG, "connecting to service");
            chatterBoxServiceClient = (ChatterBoxClient) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constant.TAG, "disconnecting from service");
        }
    };
}
