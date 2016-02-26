package com.example.android.requests.activities;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//import com.livechatinc.inappchat.ChatWindowActivity;
import com.example.android.requests.fragments.ChatterBoxMessageFragment;
import com.example.android.requests.fragments.Notification;
import com.example.android.requests.fragments.SavedAddress;
import com.example.android.requests.fragments.ChatWithUs;
import com.example.android.requests.fragments.Profile;
import com.example.android.requests.fragments.ServiceBasedChat;
import com.example.android.requests.fragments.YourOrder;
import com.example.android.requests.R;
import com.example.android.requests.fragments.Wallet;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.services.ChatterBoxService;
import com.example.android.requests.services.DefaultChatterBoxCallback;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;
import com.example.android.requests.utils.DataBaseHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;


public class FrontPage extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    //private DrawerLayout mdrawerlayout;
    //private ListView listview;
    //private DrawerLayoutAdapter drawerLayoutAdapter;
    // private FrameLayout frameLayout;
    //private ActionBarDrawerToggle drawerListene

    private android.support.v4.app.FragmentManager fragmentManager;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView headeremail;
    private TextView headerusername;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View vi = inflater.inflate(R.layout.header, null);

        setContentView(R.layout.activity_front_page);
       
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        dataBaseHelper = new DataBaseHelper(FrontPage.this);
        setSupportActionBar(toolbar);
        View header = navigationView.getHeaderView(0);
        headerusername = (TextView) header.findViewById(R.id.headerusername);
        headeremail = (TextView) header.findViewById(R.id.headeremail);
        SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepref.edit();
        headerusername.setText(sharepref.getString(Constant.NAME, "username"));
        headeremail.setText(sharepref.getString(Constant.EMAIL, "useremail"));


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
        ServiceBasedChat myFragment0 = new ServiceBasedChat();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frameholder, myFragment0);
        ft.addToBackStack("chat_fragment");
        ft.commit();
        //Log.i(MainActivity.TAG, "this is create method last line");
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        //Log.i(MainActivity.TAG, "Post Create of FrontPage Activity");
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
                //SharedPreferences.Editor editor = sharepref.edit();
                String emaillogout = sharepref.getString(Constant.EMAIL,"");
                String reqidlogout = sharepref.getString(Constant.PROPERTY_REG_ID,"");
                reqidlogout="hey";
                Log.i("TAG", emaillogout);
                Log.i("TAG", reqidlogout);
                if(!emaillogout.equals("")) {
                    AsyncTaskLogout runner = new AsyncTaskLogout();
                    runner.execute(emaillogout, reqidlogout);
                }
                chatterBoxServiceClient.leaveRoom("jaintulsi");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostResume() {

        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FrontPage.this.unbindService(serviceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
         Intent chatterBoxServiceIntent = new Intent(FrontPage.this, ChatterBoxService.class);
        FrontPage.this.bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }
    public  ChatterBoxClient chatterBoxServiceClient;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            chatterBoxServiceClient = (ChatterBoxClient) service;
            if (chatterBoxServiceClient.isConnected() == false) {
                chatterBoxServiceClient.connect("jaintulsi");
            }
            chatterBoxServiceClient.addRoom("jaintulsi", roomList);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constant.TAG, "disconnecting from service");
        }
    };
    private DefaultChatterBoxCallback roomList = new DefaultChatterBoxCallback() {

        @Override
        public void onMessage(ChatMessage message) {
            //Log.i(Constant.TAG, "received a message");
            final ChatMessage fmsg = message;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //ChatterBoxMessageFragment chatterBoxMessageFragment = (ChatterBoxMessageFragment) getSupportFragmentManager().findFragmentByTag("message");
                    //ChatterBoxMessageFragment chatterBoxMessageFragment = new ChatterBoxMessageFragment();
                    String TITLE = getSupportActionBar().getTitle().toString();
                    Log.i("TAG", TITLE);
                    ActivityManager am = (ActivityManager) FrontPage.this .getSystemService(ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
                    ComponentName componentInfo = taskInfo.get(0).topActivity;
                    if (taskInfo.get(0).topActivity.getClassName().equals("com.example.android.requests.activities.HomeServices")){
                        //chatterBoxMessageFragment.IamSeeing(fmsg);
                    }
                    else {
                        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(FrontPage.this);
                        mbuilder.setSmallIcon(R.drawable.hamburger);
                        mbuilder.setContentText(fmsg.getMessageContent());
                        mbuilder.setContentTitle(fmsg.getservice());
                        mbuilder.setAutoCancel(true);
                        Intent intent = new Intent(FrontPage.this, HomeServices.class);
                        intent.putExtra("Service", fmsg.getservice());
                        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(FrontPage.this);
                        taskStackBuilder.addParentStack(HomeServices.class);
                        taskStackBuilder.addNextIntent(intent);
                        PendingIntent pendingIntent =  taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        mbuilder.setContentIntent(pendingIntent);
                        NotificationManager NM = (NotificationManager) FrontPage.this.getSystemService(Context.NOTIFICATION_SERVICE);
                        NM.notify(0, mbuilder.build());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("chat_message", fmsg.getMessageContent());
                        contentValues.put("message_service", fmsg.getservice());
                        contentValues.put("outgoing", fmsg.getoutgoing());
                        contentValues.put("incoming", fmsg.getincoming());
                        sqLiteDatabase.insert("CHAT_TABLE", null, contentValues);
                        Log.i("TAG", "VALUE IS INSERTED INTO THE DATABASE");
                    }

                }
            });

        }
    };


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

//            case R.id.chat_with_us:
//                ChatWithUs fragment_chat_with_us = new ChatWithUs();
//                FragmentTransaction ft_chat_with_us = getSupportFragmentManager().beginTransaction();
//                ft_chat_with_us.replace(R.id.frameholder, fragment_chat_with_us);
//                ft_chat_with_us.commit();
//                return true;

            case R.id.service_based_chat:
                ServiceBasedChat fragment_service_based_chat = new ServiceBasedChat();
                FragmentTransaction ft_service_based_chat = getSupportFragmentManager().beginTransaction();
                ft_service_based_chat.replace(R.id.frameholder, fragment_service_based_chat);
                ft_service_based_chat.commit();
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
                Intent location = new Intent(this, CurrentLocation.class);
                startActivity(location);
                return true;
            case R.id.dummy2:
                Intent dummy2 = new Intent(this, ImageUploading.class);
                startActivity(dummy2);
//                Intent intent = new Intent(this, com.livechatinc.inappchat.ChatWindowActivity.class);
//                intent.putExtra(com.livechatinc.inappchat.ChatWindowActivity.KEY_GROUP_ID, "your_group_id");
//                intent.putExtra(com.livechatinc.inappchat.ChatWindowActivity.KEY_LICENSE_NUMBER, "your_license_number");
//                startActivity(intent);
                return true;
            default:
            Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
            return true;
    }}


    private class AsyncTaskLogout extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Async is called", Toast.LENGTH_LONG).show();
        }

        public AsyncTaskLogout() {

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(FrontPage.this, result, Toast.LENGTH_LONG).show();
            if (result.equals("Success")){
                Toast.makeText(FrontPage.this, "Successfully logged-out", Toast.LENGTH_LONG).show();
                SharedPreferences sharepref = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString(Constant.LOGINSTATUS, "false");
                editor.remove(Constant.NAME);
                editor.remove(Constant.EMAIL);
                editor.remove(Constant.UUID);
                editor.remove(Constant.PHONE);
                editor.remove(Constant.PASSWORD);
                editor.remove(Constant.PROPERTY_REG_ID);
                editor.apply();
                Log.i("TAG", "VCFV");
                sqLiteDatabase = dataBaseHelper.getWritableDatabase();
				sqLiteDatabase.execSQL("DELETE FROM " + DataBaseHelper.Table_name);
//                Log.i("TAG", sharepref.getString(Constant.NAME, ""));
//                Log.i("TAG",sharepref.getString(Constant.PHONE,""));
//                Log.i("TAG",sharepref.getString(Constant.PASSWORD,""));
//                Log.i("TAG",sharepref.getString(Constant.UUID,""));
//                Log.i("TAG",sharepref.getString(Constant.EMAIL,""));
                //Log.i("TAG",sharepref.getString(Constant.PROPERTY_REG_ID,""));
                Intent logout = new Intent(FrontPage.this, MainActivity.class);
                String fragmnet = "Login";
                logout.putExtra("fragment", fragmnet);
                startActivity(logout);
            }
            else if (result.equals("User does not Exits")){
                Toast.makeText(FrontPage.this, "Error", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(FrontPage.this, "You can not logged-in hold on", Toast.LENGTH_LONG).show();
            }
        }

//        @Override
//        protected void onProgressUpdate(Void) {
//            super.onProgressUpdate(values);
//        }

        @Override
        protected String doInBackground(String...params) {
            String emaillog = params[0];
            String reqidlog = params[1];
            Log.i("TAG", "LOGOUT");
            Log.i("TAG", reqidlog);
            Log.i("TAG", "LOGOUT");
            OkHttpClient client = new OkHttpClient();
            String uri = Constant.intialUrl + "logout?"+Constant.EMAIL+"="+emaillog+"&"+Constant.PROPERTY_REG_ID+"="+reqidlog;
            Log.i("TAG", "cdsdscds");
            //Log.i("TAG", uri);
            Log.i("TAG", "FVFDVFD");
            Request request = new Request.Builder().url(uri).build();
            String message = "Unsuccess";
            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                JsonElement jelement = new JsonParser().parse(response.body().string());
                JsonObject jobject = jelement.getAsJsonObject();
                message = jobject.get("message").getAsString();
            }catch (Exception e){
                e.printStackTrace();
            }
            return message;
        }
    }


}


