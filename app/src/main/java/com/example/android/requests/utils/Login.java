package com.example.android.requests.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.AppCompatEditText;

import com.example.android.requests.R;
import com.example.android.requests.activities.MainActivity;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;


public class Login {

    public static String userLogin(String email, String password){
        OkHttpClient client = new OkHttpClient();
        String uri = "http://192.168.0.5:3000/api/v0/login?email="+email+"&password="+password;
            Request request = new Request.Builder().url(uri).build();
            String checking = "Some Value";
            Context applicationContext = MainActivity.getContextOfApplication();
            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                JSONObject json = new JSONObject(response.body().string());
                String message = json.getString("message");

                checking = message;
                //if (checking.equals("User Exist")) {
                    SharedPreferences sharepref = applicationContext.getSharedPreferences("MyPref", applicationContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharepref.edit();
                    editor.putString("loginStatus", "Login");
//                    editor.putString("password", password);
//                    editor.putString("name", json.getString("name"));
//                    editor.putString("phone", json.getString("phone"));
//                    editor.putString("email", json.getString("email"));
//                    editor.putString("uuid", json.getString("uuid"));
                    editor.putString("try1", "geetika jain");
                    editor.commit();
                    //Log.i("Geetika", message);
                    //if (message.equals("User Exist")) {
//                SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//                String loginStatus = "hry";
//                Editor editor = sharepref.edit();
//                editor.putString("loginStatus", "Login");
                    //editor.putString("name", json.getString("name"));
//                editor.putString("name", "kjbk");
//                editor.putString("try", "kjbvrfvrk");
//                editor.putString("phone", json.getString("phone"));
//                editor.putString("email", json.getString("email"));
//                editor.putString("uuid", json.getString("uuid"));
                    //editor.commit();
//                loginStatus = sharepref.getString("loginStatus", "");
//                System.out.println(loginStatus);
                    //Toast.makeText(getActivity(), loginStatus, Toast.LENGTH_LONG).show();
                    //Log.i("Geetika", loginStatus);
//                }
//                else if (message.equals("User does not Exits")){
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
            checking="User Exits";
            return checking;
        };
}
