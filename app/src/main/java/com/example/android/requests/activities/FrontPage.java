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
import android.view.Gravity;
import android.view.Menu;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_toolbar);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.i(MainActivity.TAG,"Navigational item is clicked");

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

        //----------------------------do not touch this --------------------------------------------------------//

        //String s = myAdapter.getItem(0).toString();
        //getSupportActionBar().setTitle(s);

        //mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //frameLayout = (FrameLayout)findViewById(R.id.frameholder);
        //listview = (ListView) findViewById(R.id.drawer);
        //drawerLayoutAdapter = new DrawerLayoutAdapter(this);
        //listview.setAdapter(drawerLayoutAdapter);
        //listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                loadSelection(position);
//                                            }
//                                        }
//

        //----------------------------do not touch this --------------------------------------------------------//
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        Log.i(MainActivity.TAG,"Post Create of FrontPage Activity");
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(MainActivity.TAG, "onCreateOptiosnMenu  is called");
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i(MainActivity.TAG, "onOptionsItemSelected is called");
        return super.onOptionsItemSelected(item);
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
            case R.id.profile:
                Profile fragment_profile = new Profile();
                FragmentTransaction ft_profile = getSupportFragmentManager().beginTransaction();
                ft_profile.replace(R.id.frameholder, fragment_profile);
                ft_profile.commit();
            return true;
            case R.id.logout:
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
            case R.id.share:
                Share fragment_share = new Share();
                FragmentTransaction ft_share = getSupportFragmentManager().beginTransaction();
                ft_share.replace(R.id.frameholder, fragment_share);
                ft_share.commit();
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


    //public void selectItem(int position){
    //listview.setItemChecked(position, true);
    //}

//
    //
//
//    private void loadSelection(int i){
//
//
//        switch (i) {
//            case 0:
//                //mdrawerlayout.closeDrawer(listview);
//                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
//                ChatWithUs chatwithus = new ChatWithUs();
//                FragmentTransaction  ft = fragmentManager.beginTransaction();
//                ft.replace(R.id.frameholder, chatwithus);
//                ft.addToBackStack("chat_fragment");
//                ft.commit();
//                break;
//            case 1:
//                //mdrawerlayout.closeDrawer(listview);
//                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
//                int lockMode = mdrawerlayout.getDrawerLockMode(Gravity.LEFT);
//                if (lockMode == DrawerLayout.LOCK_MODE_UNLOCKED ) {
//                }
//                YourOrder yourOrder  = new YourOrder();
//                FragmentTransaction ft1 = fragmentManager.beginTransaction();
//                ft1.replace(R.id.frameholder, yourOrder);
//                ft1.addToBackStack("yourorder_fragment");
//                ft1.commit();
//                break;
//            case 2:
//                //mdrawerlayout.closeDrawer(listview);
//                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
//                Wallet wallet = new Wallet();
//                FragmentTransaction ft2 =fragmentManager.beginTransaction();
//                ft2.replace(R.id.frameholder, wallet);
//                ft2.addToBackStack("wallet_fragment");
//                ft2.commit();
//                break;
//
//            case 3:
//                //mdrawerlayout.closeDrawer(listview);
//                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
//                Profile profile  = new Profile();
//                FragmentTransaction ft3 = fragmentManager.beginTransaction();
//                ft3.replace(R.id.frameholder, profile);
//                ft3.addToBackStack("profile_fragment");
//                ft3.commit();
//                break;
//            case 4:
//                SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharepref.edit();
//                editor.putString("loginStatus", "false");
//                editor.remove("user_name");
//                editor.remove("user_email");
//                editor.remove("user_uuid");
//                editor.remove("user_phone");
//                editor.remove("user_password");
//                editor.apply();
//                Intent logout = new Intent(this, MainActivity.class);
//                String fragmnet = "Login";
//                logout.putExtra("fragment", fragmnet);
//                startActivity(logout);
//            case 5:
//                //mdrawerlayout.closeDrawer(listview);
//                //getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
//                Share share = new Share();
//                FragmentTransaction ft5 = fragmentManager.beginTransaction();
//                ft5.replace(R.id.frameholder, share);
//                ft5.addToBackStack("share_fragment");
//                ft5.commit();
//                break;
//        }
//    }
    //----------------------------do not touch this --------------------------------------------------------//


}


