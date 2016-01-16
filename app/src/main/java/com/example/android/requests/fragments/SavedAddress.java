package com.example.android.requests.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.example.android.requests.R;
import com.example.android.requests.activities.EditProfile;
import com.example.android.requests.adapters.AddressAdapter;
import com.example.android.requests.models.Address;

import java.util.ArrayList;
import java.util.List;


public class SavedAddress extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private AddressAdapter addressAdapter;
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SavedAddress() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_saved_address, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.saved_address_list);
        recyclerView.setHasFixedSize(true);
        addressAdapter = new AddressAdapter(getActivity(), getdata());
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        android.support.v7.widget.AppCompatTextView newAddressText = (android.support.v7.widget.AppCompatTextView)v.findViewById(R.id.add_address_text);
        ImageView newAddressImage = (ImageView)v.findViewById(R.id.add_address_icon);

        newAddressText.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
//                                                  NewAddress newAddress = new NewAddress();
//                                                  FragmentManager manager = getActivity().getSupportFragmentManager();
//                                                  manager.beginTransaction().replace(R.id.frameholder, newAddress).commit();
                                                  Intent newAddress = new Intent(getActivity(), com.example.android.requests.activities.NewAddress.class);
                                                  startActivity(newAddress);

                                              }
                                          }

        );

        return v;
    }
    public static List<Address> getdata (){
        List<Address> data = new ArrayList<>();
        String[] flat_no = {"309", "3434","444"};
        String[] location = {"ram nanag", "prem nagar","rajput nagar"};
        String[] adress = {"tohana", "haryana","delhi"};
        int [] ids = {1,2,3,4};
        int i;
        for (i=0; i < 3 ; i++ ){
                Address address = new Address();
                address.flatno = flat_no[i];
                address.location = location[i];
                address.nearby = adress[i];
            address.id = ids[i];
              data.add(address);

        }
        return data;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
