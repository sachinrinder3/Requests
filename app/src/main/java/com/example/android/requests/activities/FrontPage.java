package com.example.android.requests.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.example.android.requests.fragments.Share;
import com.example.android.requests.fragments.ChatWithUs;
import com.example.android.requests.fragments.Profile;
import com.example.android.requests.fragments.YourOrder;


import com.example.android.requests.R;
import com.example.android.requests.adapters.DrawerLayoutAdapter;
import com.example.android.requests.fragments.Wallet;


public class FrontPage extends AppCompatActivity {
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
        getSupportActionBar().setTitle("Chat with us");
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
                getSupportActionBar().setTitle("Select an option");
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
        fragmentManager.beginTransaction().replace(R.id.frameholder, myFragment0).commit();


    }

    private void loadSelection(int i){


        switch (i) {
            case 0:
                mdrawerlayout.closeDrawer(listview);
                getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                ChatWithUs chatwithus = new ChatWithUs();
                fragmentManager.beginTransaction().replace(R.id.frameholder, chatwithus).commit();
                break;
            case 1:
                mdrawerlayout.closeDrawer(listview);
                getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                YourOrder yourOrder  = new YourOrder();
                fragmentManager.beginTransaction().replace(R.id.frameholder, yourOrder).commit();
                break;
            case 2:
                mdrawerlayout.closeDrawer(listview);
                getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Wallet wallet = new Wallet();
                fragmentManager.beginTransaction().replace(R.id.frameholder, wallet).commit();
                break;

            case 3:
                mdrawerlayout.closeDrawer(listview);
                getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Profile profile  = new Profile();
                fragmentManager.beginTransaction().replace(R.id.frameholder, profile).commit();
                break;
            case 4:
                Intent logout = new Intent(this, MainActivity.class);
                startActivity(logout);
            case 5:
                mdrawerlayout.closeDrawer(listview);
                getSupportActionBar().setTitle(drawerLayoutAdapter.getItem(i).toString());
                Share share = new Share();
                fragmentManager.beginTransaction().replace(R.id.frameholder, share).commit();
                break;
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
}


