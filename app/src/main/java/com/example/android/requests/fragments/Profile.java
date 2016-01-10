package com.example.android.requests.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.AppCompatTextView;
import com.example.android.requests.activities.FrontPage;


import com.example.android.requests.R;
import com.example.android.requests.activities.MainActivity;


public class Profile extends Fragment {

    private Button edit_button;
    private AppCompatTextView email;
    private AppCompatTextView name;
    private AppCompatTextView phone;
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
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(MainActivity.TAG, "onCreateOptionsMenu is called of fragment profile");
        super.onCreateOptionsMenu(menu, inflater);
//        if (menu != null) {
//
//            menu.findItem(R.id.your_order).setVisible(false);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        name = (AppCompatTextView)v.findViewById(R.id.user_name);
        email = (AppCompatTextView)v.findViewById(R.id.user_email);
        phone = (AppCompatTextView)v.findViewById(R.id.user_phone);
        SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        name.setText(sharepref.getString("user_name", "nhi aaya"));
        email.setText(sharepref.getString("user_email", "nhi aaya"));
        phone.setText(sharepref.getString("user_phone", "nhi aaya"));


        edit_button = (Button) v.findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               EditableProfile editableProfile = new EditableProfile();

                                               FragmentManager manager = getActivity().getSupportFragmentManager();
                                               manager.beginTransaction().replace(R.id.frameholder, editableProfile).commit();
                                               ActionBar actionBar = ((AppCompatActivity )getActivity()).getSupportActionBar();
                                               actionBar.setDisplayHomeAsUpEnabled(false);
                                               actionBar.setDisplayShowHomeEnabled(false);
                                               actionBar.setLogo(R.drawable.hamburger);
                                               actionBar.setIcon(R.drawable.hamburger);
                                               actionBar.setTitle("HEY BRO JUST FOR CHECKING");
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
