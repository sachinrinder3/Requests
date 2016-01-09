package com.example.android.requests.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.requests.fragments.Share;
import com.example.android.requests.fragments.ChatWithUs;
import com.example.android.requests.fragments.Profile;
import com.example.android.requests.fragments.YourOrder;


import com.example.android.requests.R;
import com.example.android.requests.adapters.DrawerLayoutAdapter;
import com.example.android.requests.fragments.Wallet;


public class FrontPage extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private DrawerLayout mdrawerlayout;
    private ListView listview;
    private DrawerLayoutAdapter drawerLayoutAdapter;
    private android.support.v4.app.FragmentManager  fragmentManager;
    private ActionBarDrawerToggle drawerListener;
    private FrameLayout frameLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        //String s = myAdapter.getItem(0).toString();
        //getSupportActionBar().setTitle(s);
        getSupportActionBar().setTitle("Alternative");
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        frameLayout = (FrameLayout)findViewById(R.id.frameholder);
        listview = (ListView) findViewById(R.id.drawer);
        drawerLayoutAdapter = new DrawerLayoutAdapter(this);
        listview.setAdapter(drawerLayoutAdapter);
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                loadSelection(position);
                                            }
                                        }
        );
        drawerListener=  new ActionBarDrawerToggle(this, mdrawerlayout,toolbar, R.string.drawer_closed, R.string.drawer_opened){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Select an option");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mdrawerlayout.setDrawerListener(drawerListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.user);
        getSupportActionBar().setLogo(R.drawable.user);
        fragmentManager = getSupportFragmentManager();
        ChatWithUs myFragment0 = new ChatWithUs();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frameholder, myFragment0);
        ft.addToBackStack("chat_fragment");
        //ft.addToBackStack("try");
        ft.commit();

    }

    private void loadSelection(int i){


        switch (i) {
            case 0:
                mdrawerlayout.closeDrawer(listview);
                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                ChatWithUs chatwithus = new ChatWithUs();
                FragmentTransaction  ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frameholder, chatwithus);
                ft.addToBackStack("chat_fragment");
                ft.commit();
                break;
            case 1:
                mdrawerlayout.closeDrawer(listview);
                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                YourOrder yourOrder  = new YourOrder();
                FragmentTransaction ft1 = fragmentManager.beginTransaction();
                ft1.replace(R.id.frameholder, yourOrder);
                ft1.addToBackStack("yourorder_fragment");
                ft1.commit();
                break;
            case 2:
                mdrawerlayout.closeDrawer(listview);
                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Wallet wallet = new Wallet();
                FragmentTransaction ft2 =fragmentManager.beginTransaction();
                ft2.replace(R.id.frameholder, wallet);
                ft2.addToBackStack("wallet_fragment");
                ft2.commit();
                break;

            case 3:
                mdrawerlayout.closeDrawer(listview);
                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Profile profile  = new Profile();
                FragmentTransaction ft3 = fragmentManager.beginTransaction();
                ft3.replace(R.id.frameholder, profile);
                ft3.addToBackStack("profile_fragment");
                ft3.commit();
                break;
            case 4:
                SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString("loginStatus", "false");
                editor.remove("user_name");
                editor.remove("user_email");
                editor.remove("user_uuid");
                editor.remove("user_phone");
                editor.remove("user_password");
                editor.apply();
                Intent logout = new Intent(this, MainActivity.class);
                String fragmnet = "Login";
                logout.putExtra("fragment", fragmnet);
                startActivity(logout);
            case 5:
                mdrawerlayout.closeDrawer(listview);
                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Share share = new Share();
                FragmentTransaction ft5 = fragmentManager.beginTransaction();
                ft5.replace(R.id.frameholder, share);
                ft5.addToBackStack("share_fragment");
                ft5.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        fragmentManager = getSupportFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();
        Log.i("Geetika", String.valueOf(backCount));
        if((backCount > 1)){
            Log.i("Geetika", String.valueOf(backCount)) ;
            Log.i("Geetika", "im here") ;
            fragmentManager.popBackStack();
            Log.i("Geetika", String.valueOf(fragmentManager.getBackStackEntryCount()));
        }
        else{
            //super.onBackPressed();
            Log.i("Geetika", String.valueOf(fragmentManager.getBackStackEntryCount())) ;
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItem(int position){
        listview.setItemChecked(position, true);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }
    //
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackStackChanged() {
        Log.i("Geetika", "Called");
        Toast.makeText(this, fragmentManager.getBackStackEntryCount(), Toast.LENGTH_LONG).show();

    }
}


