package com.example.tuljain.requests.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.example.tuljain.requests.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    public String parsedString = "";

    private Button loginbtn;
    private Button registerbtn;
    private Button forgotpasswordbtn;
    private TextView email;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
                                            Toast.makeText(MainActivity.this, "Working launde", Toast.LENGTH_SHORT).show();
                                            Intent intentlogin = new Intent(MainActivity.this, FrontPage.class);
                                            startActivity(intentlogin);
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
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tuljain.requests/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tuljain.requests/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}






