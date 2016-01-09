package com.example.android.requests.activities;



import com.example.android.requests.utils.Login;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.requests.R;

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
        fragmentManager = getSupportFragmentManager();
        Intent i = getIntent();
        String fragmentName = i.getStringExtra("fragment");
        String forum = "Login";
        Log.e("Test1", "Test1" + fragmentName);
        if (fragmentName != null && fragmentName.equals(forum)) {
            com.example.android.requests.fragments.Login login = new com.example.android.requests.fragments.Login ();
            fragmentManager.beginTransaction().replace(R.id.frameholder1, login).commit();
        }
        else {
            Log.i("Geetika", "Else main condition");
            SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
            String email = sharepref.getString("email", "");
            String loginStatus = sharepref.getString("loginStatus", "");
            String password = sharepref.getString("password", "");
            if (!email.equals("") && !password.equals("") && loginStatus.equals("true")){
                Log.i("Geetika", "Async task is called");
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(email, password);
            }
            else{

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
            String loginStatus = sharepref.getString("loginStatus", "false");
            Log.i("Geetika",loginStatus);
            Log.i("Geetika",result);
            if (loginStatus.equals("true") && result.equals("User Exits")) {
                Toast.makeText(context, "already logged in", Toast.LENGTH_LONG).show();
                Intent intentlogin = new Intent(context, FrontPage.class);
                startActivity(intentlogin);
            }
            else {
                Startup startup = new Startup ();
                fragmentManager.beginTransaction().replace(R.id.frameholder1, startup).commit();
            }
        }

//        @Override
//        protected void onProgressUpdate(Void) {
//            super.onProgressUpdate(values);
//        }

        @Override
        protected String doInBackground(String...params) {
            String username = params[0];
            String password = params[1];
            String result = com.example.android.requests.utils.Login.userLogin(username, password);
            return result;
        }
    }

}






