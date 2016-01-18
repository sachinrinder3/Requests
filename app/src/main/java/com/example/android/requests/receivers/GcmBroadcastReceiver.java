package com.example.android.requests.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.android.requests.services.GcmIntentService;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    public GcmBroadcastReceiver() {
        Log.i("TAG", "someone called me 2");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.i("TAG", "someone called me 3");

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        Log.i("TAG", "someone called me 4");
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
        Log.i("TAG", "someone called me 5");

    }

}
