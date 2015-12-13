package com.example.tuljain.requests;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private ListView listview;
    private  MyAdapter myAdapter;
    private android.support.v4.app.FragmentManager  fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private String r;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /** setTheme(R.style.MyTheme);
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.hey);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reach out to us");
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        //planets= getResources().getStringArray(R.array.planets);
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
                                                mdrawerlayout.closeDrawer(listview);

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
        fragmentManager = getSupportFragmentManager();

    }

    private void loadSelection(int i){
        getSupportActionBar().setTitle(myAdapter.getItem(i).toString());
        Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
        //listview.setItemChecked(i, true);
        switch (i) {
            case 0:
                MyFragment1 myFragment0 = new MyFragment1();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameholder,myFragment0);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction = fragmentManager.beginTransaction();
                MyFragment2 myFragment1 = new MyFragment2();
                fragmentTransaction.replace(R.id.frameholder, myFragment1);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
                break;
            case 2:
                MyFragment3 myFragment2 = new MyFragment3();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameholder, myFragment2);
                fragmentTransaction.commit();
                break;
            case 3:
                MyFragment4 myFragment3 = new MyFragment4();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameholder, myFragment3);
                fragmentTransaction.commit();
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
        //setTitle(planets[position]);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    public void setTitle (String title){

        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        Toast.makeText(MainActivity.this, "was selected", Toast.LENGTH_SHORT).show();
//        switch (position){
//            case 0:
//                break;
//
//            case 1:
//
//                break;
//        }
//        mdrawerlayout.closeDrawer(listview);
//    }
}




class MyAdapter extends BaseAdapter{
    private  Context context;
    String[] galaxy;
    int[] images= {R.drawable.hamburger, R.drawable.user, R.drawable.exit, R.drawable.ic_action_name2};
    public MyAdapter(Context context){
        this.context = context;
        galaxy = context.getResources().getStringArray(R.array.planet);
    }
    @Override
    public int getCount() {
        return galaxy.length;
    }

    @Override
    public Object getItem(int position) {
        return galaxy[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.customlayout,parent,false);
        }
        else{
            row = convertView;
        }
        TextView titleTextView = (TextView)row.findViewById(R.id.t1);
        ImageView titleImageView = (ImageView)row.findViewById(R.id.im1);
        titleTextView.setText(galaxy[position]);
        //titleTextView.setHeight(5);
        titleImageView.setImageResource(images[position]);
        //titleTextView.setHeight(5);
        row.setMinimumHeight(100);
        return row;
    }
}

