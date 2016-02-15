package com.example.android.requests.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.android.requests.BuildConfig;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatterBoxService extends Service {
    private final Map<String, List<ChatterBoxCallback>> listeners = new HashMap<>();
    private Pubnub pubnub;
    private boolean connected = false;
    private String emailid;

    public ChatterBoxService() {

    }

    public Map<String, List<ChatterBoxCallback>> getListeners() {

        return listeners;
    }

    @Override
    public IBinder onBind(Intent intent) {

        ChatterBoxClient chatterBoxClient = new ChatterBoxClient(this);
        return chatterBoxClient;

    }

    public boolean isConnected() {

        return connected;
    }

    public String getCurrentUserEmailID() {

        return emailid;
    }


    public void setCurrentUserEmailID(String emailid) {
        this.emailid = emailid;
        this.connected = true;
    }

    public Pubnub getPubNub() {
//        SharedPreferences sharepref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        String email = sharepref.getString(Constant.EMAIL, "");
        if ((null == pubnub)
                &&
                ( emailid!= "")
                ) {

            pubnub = new Pubnub("pub-c-0e57abe1-40bd-4357-8754-ec6d0e4a5add", "sub-c-38127c1c-cb42-11e5-a316-0619f8945a4f",
                    false);

//            pubnub.setHeartbeat(140, new Callback() {
//                @Override
//                public void successCallback(String channel, Object message) {
//                    Log.d(Constant.TAG, "heartbeat received");
//                }
//
//                @Override
//                public void errorCallback(String channel, Object message) {
//                    Log.i(Constant.TAG, "error receiving heartbeat");
//                    pubnub.disconnectAndResubscribe();
//                }
//            });


            //pubnub.setHeartbeatInterval(120);
            //pubnub.setNonSubscribeTimeout(60);
            pubnub.setResumeOnReconnect(true);
            pubnub.setMaxRetries(500);
            //pubnub.setSubscribeTimeout(20000);
            pubnub.setUUID(emailid);
            connected = true;
        }
        return pubnub;
    }


    @Override
    public void onCreate() {

    }
}
