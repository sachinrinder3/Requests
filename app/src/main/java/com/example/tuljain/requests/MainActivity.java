package com.example.tuljain.requests;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
//    private DrawerLayout mdrawerlayout;
//    private ListView listview;
//    private   MyAdapter myAdapter;
//    private android.support.v4.app.FragmentManager  fragmentManager;
    //private android.support.v4.app.FragmentTransaction fragmentTransaction;
//    private ActionBarDrawerToggle drawerListener;
//    private FrameLayout frameLayout;
//    private static int position;
//    private Toolbar toolbar;
    private Button loginbtn;
    private Button registerbtn;
    private Button forgotpasswordbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginbtn  = (Button) findViewById(R.id.login);
        registerbtn = (Button) findViewById(R.id.register);
        forgotpasswordbtn = (Button) findViewById(R.id.passres);

        loginbtn.setOnClickListener(new View.OnClickListener() {

                                         public void onClick(View v) {
                                             Intent intentlogin = new Intent(MainActivity.this, FrontPage.class);
                                             startActivity(intentlogin);
                                         }
                                     }

        );
        registerbtn.setOnClickListener(new View.OnClickListener() {

                                           public void onClick(View v) {
                                               Intent intentregis = new Intent(MainActivity.this, Register.class);
                                               startActivity(intentregis);
                                           }
                                       }

        );

//        toolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(myAdapter.getItem(0).toString());
//        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        frameLayout = (FrameLayout)findViewById(R.id.frameholder);
//        listview = (ListView) findViewById(R.id.drawer);
//        myAdapter = new MyAdapter(this);
//        listview.setAdapter(myAdapter);
//        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                loadSelection(position);
//                                            }
//                                        }
//        );
//        drawerListener=  new ActionBarDrawerToggle(this, mdrawerlayout,toolbar, R.string.drawer_closed, R.string.drawer_opened){
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Select an option");
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//        };
//        mdrawerlayout.setDrawerListener(drawerListener);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        fragmentManager = getSupportFragmentManager();
//        ChatWithUs myFragment0 = new ChatWithUs();
//        fragmentManager.beginTransaction().replace(R.id.frameholder, myFragment0).commit();


    }

//    private void loadSelection(int i){
//        //getSupportActionBar().setTitle(myAdapter.getItem(i).toString());
//        //Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
//        //listview.setItemChecked(i, true);
//        MainActivity.position = i;
//        getSupportActionBar().setTitle(myAdapter.getItem(i).toString());
//        mdrawerlayout.closeDrawer(listview);
//
//        switch (i) {
//            case 0:
//                ChatWithUs chatwithus = new ChatWithUs();
//                fragmentManager.beginTransaction().replace(R.id.frameholder, chatwithus).commit();
//                break;
//            case 1:
//                AboutUs aboutus  = new AboutUs();
//                fragmentManager.beginTransaction().replace(R.id.frameholder, aboutus).commit();
//                break;
//
//            case 2:
//                Profile profile  = new Profile();
//                fragmentManager.beginTransaction().replace(R.id.frameholder, profile).commit();
//                break;
//            case 3:
//               Logout logout = new Logout();
//                fragmentManager.beginTransaction().replace(R.id.frameholder, logout).commit();
//               break;
//            case 4:
//                Share share = new Share();
//                fragmentManager.beginTransaction().replace(R.id.frameholder, share).commit();
//                break;
//        }
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerListener.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public void selectItem(int position){
//        listview.setItemChecked(position, true);
//    }
//
//    @Override
//    public void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerListener.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//        drawerListener.onConfigurationChanged(newConfig);
//    }
}





