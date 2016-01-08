package com.example.android.requests.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.android.requests.R;
import com.example.android.requests.fragments.Login;
import com.example.android.requests.fragments.Startup;
import com.facebook.FacebookSdk;
public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        String loginStatus = sharepref.getString("loginStatus", "nhi hai");
        Log.d("hey", loginStatus);
        Log.i("Geetika", loginStatus);
        if (loginStatus.equals("Login")){
            Intent intentlogin = new Intent(this, FrontPage.class);
            startActivity(intentlogin);
        }
        else {

            fragmentManager = getSupportFragmentManager();

            Intent i = getIntent();
            String fragmentName = i.getStringExtra("fragment");
            String forum = "Login";
            Log.e("Test1", "Test1" + fragmentName);
            if (fragmentName != null && fragmentName.equals(forum)) {

                Login login = new Login ();
                fragmentManager.beginTransaction().replace(R.id.frameholder1, login).commit();
            }
            else {
                Startup startup = new Startup ();
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
    public static Context getContextOfApplication(){
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
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}






