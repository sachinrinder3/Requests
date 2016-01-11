package com.example.android.requests.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.requests.fragments.Notification;
import com.example.android.requests.fragments.SavedAddress;
import com.example.android.requests.fragments.ChatWithUs;
import com.example.android.requests.fragments.Profile;
import com.example.android.requests.fragments.YourOrder;


import com.example.android.requests.R;
import com.example.android.requests.fragments.Wallet;


public class FrontPage extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    //private DrawerLayout mdrawerlayout;
    //private ListView listview;
    //private DrawerLayoutAdapter drawerLayoutAdapter;
    // private FrameLayout frameLayout;
    //private ActionBarDrawerToggle drawerListener;

    private android.support.v4.app.FragmentManager  fragmentManager;
    private Toolbar toolbar;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView headeremail;
    private TextView headerusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View vi = inflater.inflate(R.layout.header, null);

        setContentView(R.layout.activity_front_page);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);
        View header = navigationView.getHeaderView(0);
        headerusername = (TextView) header.findViewById(R.id.headerusername);
         headeremail = (TextView) header.findViewById(R.id.headeremail);
        SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepref.edit();
        headerusername.setText(sharepref.getString("user_name", "username"));
        headeremail.setText(sharepref.getString("user_email", "useremail"));


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_toolbar);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                drawerLayout.closeDrawers();
                return menuchange(menuItem);

            }
        });
//
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
////
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //getSupportActionBar().setIcon(R.drawable.user);
        fragmentManager = getSupportFragmentManager();
        ChatWithUs myFragment0 = new ChatWithUs();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frameholder, myFragment0);
        ft.addToBackStack("chat_fragment");
        ft.commit();
        Log.i(MainActivity.TAG, "this is create method last line");
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.i(MainActivity.TAG, "Post Create of FrontPage Activity");
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(MainActivity.TAG, "onCreateOptiosnMenu  is called");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        switch (item.getItemId()) {
            case R.id.profilemenu:
                Profile fragment_profile = new Profile();
                FragmentTransaction ft_profile = getSupportFragmentManager().beginTransaction();
                ft_profile.replace(R.id.frameholder, fragment_profile);
                ft_profile.commit();
                return true;
            case R.id.logoutmenu:
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
   {
       super.onConfigurationChanged(newConfig);
       //actionBarDrawerToggle.onConfigurationChanged(newConfig);
       Log.i(MainActivity.TAG, "onConfigurationChanged is called");
  }

    @Override
    public void onBackStackChanged() {
        Log.i(MainActivity.TAG, "Called");
        Toast.makeText(this, fragmentManager.getBackStackEntryCount(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        fragmentManager = getSupportFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();
        //Log.i(MainActivity.TAG, String.valueOf(backCount));
        if((backCount > 1)){
            //Log.i(MainActivity.TAG, String.valueOf(backCount)) ;
            //Log.i(MainActivity.TAG, "im here") ;
            fragmentManager.popBackStack();
            Log.i(MainActivity.TAG, String.valueOf(fragmentManager.getBackStackEntryCount()));
        }
        else{
            //super.onBackPressed();
            //Log.i(MainActivity.TAG, String.valueOf(fragmentManager.getBackStackEntryCount())) ;
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

    }

    public boolean menuchange(MenuItem menuItem){
        //Replacing the main content with ContentFragment Which is our Inbox View;
        switch (menuItem.getItemId()) {

            case R.id.chat_with_us:
                ChatWithUs fragment_chat_with_us = new ChatWithUs();
                FragmentTransaction ft_chat_with_us = getSupportFragmentManager().beginTransaction();
                ft_chat_with_us.replace(R.id.frameholder, fragment_chat_with_us);
                ft_chat_with_us.commit();
                return true;

            case R.id.your_order:
                YourOrder fragment_your_order = new YourOrder();
                FragmentTransaction ft_your_order = getSupportFragmentManager().beginTransaction();
                ft_your_order.replace(R.id.frameholder, fragment_your_order);
                ft_your_order.commit();
                return true;
            case R.id.wallet:
                Wallet fragment_wallet = new Wallet();
                FragmentTransaction ft_wallet = getSupportFragmentManager().beginTransaction();
                ft_wallet.replace(R.id.frameholder, fragment_wallet);
                ft_wallet.commit();
                return true;
            case R.id.saved_address:
                SavedAddress fragment_saved_address = new SavedAddress();
                FragmentTransaction ft_saved_address = getSupportFragmentManager().beginTransaction();
                ft_saved_address.replace(R.id.frameholder, fragment_saved_address);
                ft_saved_address.commit();
                //actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                return true;
            case R.id.notification:
                Notification fragment_notification = new Notification();
                FragmentTransaction ft_notification = getSupportFragmentManager().beginTransaction();
                ft_notification.replace(R.id.frameholder, fragment_notification);
                ft_notification.commit();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject test");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"Share With"));
                return true;

            case R.id.dummy:
                Intent dummy = new Intent(this, Dummy.class);
                startActivity(dummy);
                return true;
            case R.id.dummy2:
                Intent dummy2 = new Intent(this, Dummy2.class);
                startActivity(dummy2);
                return true;
            default:
            Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
            return true;
    }}

       //----------------------------do not touch this --------------------------------------------------------//

    //----------------------------do not touch this --------------------------------------------------------//


}


