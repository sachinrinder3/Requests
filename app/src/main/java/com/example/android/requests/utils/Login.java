package com.example.android.requests.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.requests.activities.MainActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.json.JSONObject;


public class Login {

    public static String userLogin(String email, String password){
        OkHttpClient client = new OkHttpClient();
        String uri = "http://192.168.0.4:3000/api/v0/login?email="+email+"&password="+password;
            Request request = new Request.Builder().url(uri).build();
            String message = "Some Value";
            Context applicationContext = MainActivity.getContextOfApplication();
            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                JsonElement jelement = new JsonParser().parse(response.body().string());
                JsonObject  jobject = jelement.getAsJsonObject();
                message = jobject.get("message").getAsString();
                //JSONObject json = new JSONObject(response.body().string());
                //message = json.getString("message");

                if (message.equals("User Exits")) {
                    SharedPreferences sharepref = applicationContext.getSharedPreferences("MyPref", applicationContext.MODE_PRIVATE);
                    Editor editor = sharepref.edit();
                    editor.putString("try1", "yeah working");

//                    Log.i("Geetika", "you bro whats up");
//                    Log.i("Geetika", sharepref.getString("try1", "no values"));
//                    Log.i("Geetika", sharepref.getString("loginStatus", "no values"));
                    editor.putString("loginStatus", "true");
//                    editor.putString("user_password", password);
//                    editor.putString("user_name", jobject.get("name").getAsString());
//                    editor.putString("user_phone", jobject.get("phone").getAsString());
//                    editor.putString("user_email", jobject.get("email").getAsString());
//                    editor.putString("user_uuid", jobject.get("uuid").getAsString());
                    editor.putString("try1", "lets see what's happens");
                    editor.apply();
                    Log.i("Geetika",sharepref.getString("try1", "no values") );
                }
//                else if (message.equals("User does not Exits")){
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return message;
        };
}
