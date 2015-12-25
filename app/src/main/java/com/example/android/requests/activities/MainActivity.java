package com.example.android.requests.activities;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.example.android.requests.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    public String parsedString = "";

    private Button loginbtn;
    private Button registerbtn;
    private Button forgotpasswordbtn;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginbtn = (Button) findViewById(R.id.login);
        email = (TextView) findViewById(R.id.email);
        registerbtn = (Button) findViewById(R.id.register);
        forgotpasswordbtn = (Button) findViewById(R.id.passres);

        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Toast.makeText(MainActivity.this, "all", Toast.LENGTH_SHORT).show();
//                                            Intent intentlogin = new Intent(MainActivity.this, FrontPage.class);
//                                            startActivity(intentlogin);
//                                            BufferedReader reader = null;
//                                            String uri = "http://192.168.0.2:3000/users/login?email=jaintulsi&password=tulsi";
//                                            try {
//                                                URL url = new URL (uri);
                                               OkHttpClient client = new OkHttpClient();
//                                                HttpURLConnection con =  client.open(url);
//                                                StringBuilder sb = new StringBuilder();
//                                                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                                                String line;
//                                                while ((line = reader.readLine()) != null) {
//
//                                                    sb.append(line + "\n");
//                                                }
//
//                                            }catch(Exception e){
//                                                e.printStackTrace();
//                                            }
                                            Request request = new Request.Builder()
                                                    .url("http://192.168.0.2:3000/users/login?email=jaintulsi&password=tulsi")
                                                    .build();
                                            try {
                                                Call call = client.newCall(request);

                                                Response response = call.execute();
                                                String hey = response.body().toString();
                                                //System.println
                                                Toast.makeText(MainActivity.this, hey, Toast.LENGTH_SHORT).show();
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
//                                            int len = 500;
//                                            email.setText("set the e-mail");
//                                            try {
//                                                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                                                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//                                                if (networkInfo != null && networkInfo.isConnected()) {
//                                                    Toast.makeText(MainActivity.this, "Network Available", Toast.LENGTH_SHORT).show();
//                                                } else {
//                                                    Toast.makeText(MainActivity.this, "Network Unavailable", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                                URL url = new URL("http://www.flickr.com/services/feeds/photos_public.gne?tags=soccer&format=json");
//                                                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//                                                httpConn.setReadTimeout(100000 /* milliseconds */);
//                                                httpConn.setConnectTimeout(150000 /* milliseconds */);
//                                                httpConn.setRequestMethod("GET");
//                                                httpConn.connect();
//                                                email.setText("connected yes");
//                                                int response = httpConn.getResponseCode();
//                                                //Log.d(DEBUG_TAG, "The response is: " + response);
//                                                InputStream in = httpConn.getInputStream();
//                                                BufferedReader r = new BufferedReader(new InputStreamReader(in));
//                                                StringBuilder total = new StringBuilder();
//                                                String line;
//                                                while ((line = r.readLine()) != null) {
//                                                    total.append(line);
//                                                }
//                                                parsedString = total.toString();
//
//
//                                                System.out.println(parsedString);
//                                                Toast.makeText(MainActivity.this, parsedString, Toast.LENGTH_SHORT).show();
//                                            }   catch (Exception e) {
//                                                e.printStackTrace();
//                                            }

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
}






