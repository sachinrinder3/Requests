package com.example.android.requests.fragments;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
import com.example.android.requests.adapters.ChatHistoryAdapter;
import com.example.android.requests.adapters.YourOrderAdapter;
import com.example.android.requests.models.*;
import com.example.android.requests.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourOrder extends Fragment {
    private RecyclerView recyclerView;
    private YourOrderAdapter yourOrderAdapter;


    public YourOrder() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_your_order, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        yourOrderAdapter= new YourOrderAdapter(getActivity(), getOrderHistory());
        recyclerView.setAdapter(yourOrderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    public List<com.example.android.requests.models.YourOrders> getOrderHistory (){
        List<com.example.android.requests.models.YourOrders> data = new ArrayList<>();
        String[] serviceName = {"Food", "Recharge"};
        String [] price= {"100", "200"};

        for (int i=0; i < 2 ; i++ ){
            YourOrders yourOrders = new YourOrders(price[i], serviceName[i]);
            data.add(yourOrders);

        }
        return data;
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
