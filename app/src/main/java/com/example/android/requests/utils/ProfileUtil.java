package com.example.android.requests.utils;

import android.content.Context;

import com.example.android.requests.activities.MainActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by tuljain on 1/9/2016.
 */
public class ProfileUtil {

    public static JsonObject profileUpdate(String email, String name){
        Context applicationContext = MainActivity.getContextOfApplication();

        String intialUrl = Constant.intialUrl;
        OkHttpClient client = new OkHttpClient();
        SharedPreferences sharepref = applicationContext.getSharedPreferences("MyPref", applicationContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepref.edit();
        String phone = sharepref.getString("user_phone", "nhi aaya");
        Log.i(MainActivity.TAG,phone);
        String uri = intialUrl + "update?email="+email+"&phone="+phone+"&name="+name;
        Request request = new Request.Builder().url(uri).build();
        JsonObject jobject = new JsonObject();

        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            JsonElement jelement = new JsonParser().parse(response.body().string());
            jobject = jelement.getAsJsonObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return jobject;
    }
}
