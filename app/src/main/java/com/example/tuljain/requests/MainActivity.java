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
import android.widget.FrameLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    protected DrawerLayout mdrawerlayout;
    protected ListView listview;
    protected   MyAdapter myAdapter;
    private android.support.v4.app.FragmentManager  fragmentManager;
    //private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private ActionBarDrawerToggle drawerListener;
    protected FrameLayout frameLayout;
    protected static int position;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** setTheme(R.style.MyTheme);
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat with us");
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        frameLayout = (FrameLayout)findViewById(R.id.frameholder);
        //  planets= getResources().getStringArray(R.array.planets);
        listview = (ListView) findViewById(R.id.drawer);
        //  listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        myAdapter = new MyAdapter(this);
        listview.setAdapter(myAdapter);
        //listview.setOnItemClickListener(this);
        //listview.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, planets));
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
//                Toast.makeText(MainActivity.this,  "is opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle("Reach out to us");
//                Toast.makeText(MainActivity.this,  "is closed", Toast.LENGTH_SHORT).show();
            }
        };
        mdrawerlayout.setDrawerListener(drawerListener);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fragmentManager = this.getSupportFragmentManager();
        MyFragment1 myFragment0 = new MyFragment1();
        //Bundle args = new Bundle();
        //args.putInt(MyFragment1.ARG_PLANET_NUMBER, position);
        //myFragment0.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.frameholder, myFragment0).commit();


    }

    private void loadSelection(int i){
        //getSupportActionBar().setTitle(myAdapter.getItem(i).toString());
        //Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
        //listview.setItemChecked(i, true);
        MainActivity.position = i;
        mdrawerlayout.closeDrawer(listview);

        switch (i) {
            case 0:
                //Intent chatwithus = new Intent("com.example.tuljain.requests.ChatWithUs");
                //startActivity(chatwithus);
               MyFragment1 myFragment0 = new MyFragment1();
                Bundle args = new Bundle();
                //args.putInt(MyFragment1.ARG_PLANET_NUMBER, position);
                myFragment0.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.frameholder, myFragment0).commit();
                break;
            case 1:
                //Intent aboutus = new Intent("com.example.tuljain.requests.AboutUs");
                //startActivity(aboutus);
            case 2:
                //Intent profile = new Intent("com.example.tuljain.requests.Profile");
                //startActivity(profile);
//                MyFragment3 myFragment2 = new MyFragment3();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameholder, myFragment2);
//                fragmentTransaction.commit();
//                break;
            case 3:
                //Intent logout = new Intent("com.example.tuljain.requests.Logout");
                //startActivity(logout);
//                MyFragment4 myFragment3 = new MyFragment4();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameholder, myFragment3);
//                fragmentTransaction.commit();
//                break;
            case 4:
                //Intent share = new Intent("com.example.tuljain.requests.Share");
                //startActivity(share);
//                MyFragment4 myFragment4 = new MyFragment4();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameholder, myFragment4);
//                fragmentTransaction.commit();
//                break;

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

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }
}





