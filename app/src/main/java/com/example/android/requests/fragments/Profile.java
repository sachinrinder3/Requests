package com.example.android.requests.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.requests.R;


public class Profile extends Fragment {

    private Button edit_button;
    private android.support.v4.app.FragmentManager  fragmentManager;

    public Profile() {
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        edit_button = (Button) v.findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               EditableProfile editableProfile = new EditableProfile();
                                               FragmentManager manager = getActivity().getSupportFragmentManager();
                                               manager.beginTransaction().replace(R.id.frameholder, editableProfile).commit();
                                           }
                                       }

        );
        return v;


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
