package com.example.android.requests.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.example.android.requests.R;
import com.example.android.requests.fragments.Startup;
import com.example.android.requests.utils.Constant;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    public String Parse_Application_Key;
    GoogleCloudMessaging gcm;
    String regid;
    String email;
    String password;

    FragmentManager fragmentManager;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Parse.initialize(this, "G7DXbBMyOiMCOryNwHvQL3mqw0q37B6xhc9PlZFm", "usTr4KFvBEeyP25Xg6wFAoS8COAZYsbmNPsS6WAW");
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();
        fragmentManager = getSupportFragmentManager();
        Intent i = getIntent();
        String fragmentName = i.getStringExtra("fragment");
        String forum = "Login";
        if (fragmentName != null && fragmentName.equals(forum)) {
            com.example.android.requests.fragments.Login login = new com.example.android.requests.fragments.Login();
            fragmentManager.beginTransaction().replace(R.id.frameholder1, login).commit();
        } else {
            SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
            email  = sharepref.getString(Constant.EMAIL_ID, "");
            String loginStatus = sharepref.getString(Constant.LOGINSTATUS, "");
            password = sharepref.getString(Constant.PASSWORD, "");

            if (loginStatus.equals("true") && !email.equals("") && !password.equals("")) {

                if (checkPlayServices()) {


                    gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    regid = getRegistrationId(MainActivity.this);

                    if (regid.isEmpty()) {
                        Log.i("TAG", "empty regid");
                        Startup startup1 = new Startup();
                        fragmentManager.beginTransaction().replace(R.id.frameholder1, startup1).commit();
                        registerInBackground();
                    }
                } else {
                    Log.i("TAG", "No valid Google Play Services APK found.");
                }

            } else {
                Startup startup = new Startup();
                fragmentManager.beginTransaction().replace(R.id.frameholder1, startup).commit();
            }
        }

//        callbackManager = CallbackManager.Factory.create();
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("user_friends");
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AccessToken accessToken = loginResult.getAccessToken();
//                Profile fragment_profile = Profile.getCurrentProfile();
//                if (fragment_profile != null){
//                    TextView tv = (TextView) findViewById(R.id.email);
//                    tv.setText(fragment_profile.getName());
//                }
//                else {
//                    TextView tv = (TextView) findViewById(R.id.email);
//                    tv.setText("did not get the name");
//
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Async is called", Toast.LENGTH_LONG).show();
        }

        public AsyncTaskRunner() {

        }

        Context context = MainActivity.getContextOfApplication();

        @Override
        protected void onPostExecute(String result) {
            SharedPreferences sharepref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            String loginStatus = sharepref.getString(Constant.LOGINSTATUS, Constant.FALSE);
            Log.i("TAG", "HEY BRO");
            if (loginStatus.equals(Constant.TRUE) && result.equals(Constant.USER_EXITS)) {
                Toast.makeText(context, "already logged in", Toast.LENGTH_LONG).show();
                Intent intentlogin = new Intent(context, FrontPage.class);
                startActivity(intentlogin);
            } else {
                Startup startup = new Startup();
                fragmentManager.beginTransaction().replace(R.id.frameholder1, startup).commit();
            }
        }

        //        @Override
//        protected void onProgressUpdate(Void) {
//            super.onProgressUpdate(values);
//        }
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String result = com.example.android.requests.utils.NetworkUtil.userLogin(username, password );
            return result;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        //int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        int resultCode = api.isGooglePlayServicesAvailable(MainActivity.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                //GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                GoogleApiAvailability.getInstance().getErrorString(resultCode);
            } else {
                Log.i(MainActivity.TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.PROPERTY_REG_ID, "");
        editor.commit();
        String registrationId = prefs.getString(Constant.PROPERTY_REG_ID, "");

        if (registrationId.isEmpty()) {
            Log.i(MainActivity.TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(Constant.PROPERTY_APP_VERSION, Integer.MIN_VALUE);

        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(MainActivity.TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void registerInBackground() {

        new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(email, password);
            }

            @Override
            protected String doInBackground(Object[] params) {
                String msg;
                try {

                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
                    }
                    regid = gcm.register(Constant.SENDER_ID);
                      msg = "Device registered, registration ID=" + regid;
                    storeRegistrationId(MainActivity.this, regid);
                } catch (IOException ex) {
                     msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
        }.execute();

    }
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(MainActivity.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.PROPERTY_REG_ID, regId);
        editor.putInt(Constant.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }



}







