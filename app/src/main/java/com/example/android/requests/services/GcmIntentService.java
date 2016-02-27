package com.example.android.requests.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.android.requests.R;
import com.example.android.requests.activities.HomeServices;
import com.example.android.requests.activities.MainActivity;
import com.example.android.requests.receivers.GcmBroadcastReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmIntentService extends IntentService {

    private static final String ACTION_FOO = "com.example.android.requests.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.android.requests.services.action.BAZ";
    private static final String EXTRA_PARAM1 = "com.example.android.requests.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.requests.services.extra.PARAM2";




    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("TAG", "someone called me 12");
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) { // has effect of unparcelling Bundle

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            }
            else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.i("TAG", "someone called me 11");
                String recieved_message=intent.getStringExtra("collapse_key");
                sendNotification("Message recieved :" +recieved_message);
                Log.i("TAG", "someone called me 12");
                Intent sendIntent =new Intent("message_recieved");
                sendIntent.putExtra("message",recieved_message);
                Log.i("TAG", "someone called me 13");
                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
                Log.i("TAG", "someone called me 14");
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        Log.i("TAG", "someone called me 10");
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.hamburger)
                        .setContentTitle("Alternative Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());



//        android.support.v4.app.NotificationCompat.Builder mbuilder = new android.support.v4.app.NotificationCompat.Builder(this);
//        mbuilder.setSmallIcon(R.drawable.hamburger);
//        mbuilder.setStyle(new NotificationCompat.BigTextStyle()
//                .bigText(msg));
//        mbuilder.setContentText(fmsg.getMessageContent());
//        mbuilder.setContentTitle(fmsg.getservice());
//        mbuilder.setAutoCancel(true);
//        Intent intent = new Intent(this, HomeServices.class);
//        intent.putExtra("Service", fmsg.getservice());
//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
//        taskStackBuilder.addParentStack(HomeServices.class);
//        taskStackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent =  taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mbuilder.setContentIntent(pendingIntent);
//        NotificationManager NM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        NM.notify(0, mbuilder.build());





    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GcmIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GcmIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
