package com.example.android.requests.activities;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
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







}






