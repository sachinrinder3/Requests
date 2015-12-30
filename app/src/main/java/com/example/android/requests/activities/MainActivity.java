package com.example.android.requests.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.example.android.requests.R;
import com.facebook.FacebookSdk;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.security.auth.callback.Callback;


public class MainActivity extends AppCompatActivity {

    public String parsedString = "";
    OkHttpClient client = new OkHttpClient();
    NotificationManager nm;
    boolean isnotification;
    int id = 33;

    private Button loginbtn;
    private Button registerbtn;
    private Button forgotpasswordbtn;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);
        loginbtn = (Button) findViewById(R.id.login);
        email = (TextView) findViewById(R.id.email);
        registerbtn = (Button) findViewById(R.id.register);
        forgotpasswordbtn = (Button) findViewById(R.id.passres);

        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                           Intent intentlogin = new Intent(MainActivity.this, FrontPage.class);
                                           startActivity(intentlogin);
                                            //user();
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
    }
    public void showNotification (View v){
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this).setContentTitle("hey whats up").setContentText("hey").setTicker("heybidshcb").setSmallIcon(R.drawable.hamburger);


    };
    public void stopNotification (View v){

    };


    public void user (){


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Network Unavailable", Toast.LENGTH_SHORT).show();
        }


        String uri = "http://192.168.0.2:3000/users/login?email=jaintulsi";
        Request request = new Request.Builder().url(uri).build();
        try {
            Toast.makeText(MainActivity.this, "all1", Toast.LENGTH_SHORT).show();

            Call call = client.newCall(request);

            Toast.makeText(MainActivity.this, "all2", Toast.LENGTH_SHORT).show();

            Response response = call.execute();

            Toast.makeText(MainActivity.this, "all3", Toast.LENGTH_SHORT).show();
            String hey = response.body().string();
            JSONObject json = new JSONObject(hey);
            final String owner = json.getString("message");
            email.setText(hey);
            System.out.println(hey);
            Toast.makeText(MainActivity.this, hey, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}






