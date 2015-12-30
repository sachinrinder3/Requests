package com.example.android.requests.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.requests.EditableProfile;
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
        return inflater.inflate(R.layout.profile, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onClick(final View v){
        edit_button = (Button) v.findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            EditableProfile editableProfile = new EditableProfile();
                                            fragmentManager.beginTransaction().replace(R.id.frameholder, editableProfile).commit();
                                        }
                                    }

        );
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
