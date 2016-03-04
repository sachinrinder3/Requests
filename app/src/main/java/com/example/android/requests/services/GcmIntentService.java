package com.example.android.requests.services;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.android.requests.R;
import com.example.android.requests.activities.ChatActivity;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.receivers.GcmBroadcastReceiver;
import com.example.android.requests.utils.DataBaseHelper;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

public class GcmIntentService extends IntentService {

    private static final String ACTION_FOO = "com.example.android.requests.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.android.requests.services.action.BAZ";
    private static final String EXTRA_PARAM1 = "com.example.android.requests.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.android.requests.services.extra.PARAM2";

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;


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
                ChatMessage chatMessageerror = new ChatMessage("Send error: " + extras.toString(),"N", "Y");
                sendNotification(chatMessageerror);
            }
            else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                ChatMessage chatMessagedeleted = new ChatMessage("Deleted messages on server: " + extras.toString(),"N", "Y");
                sendNotification(chatMessagedeleted);
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                if (intent.getStringExtra(ChatMessage.TYPE).equals(ChatMessage.CHATTMESSAGE)) {

                    if (!isAppIsInBackground(this)
                            //&& currentService.equals(messageService) && taskInfo.get(0).topActivity.getClassName().equals("com.example.android.requests.activities.ChatActivity")
                            ) {
//                                Log.i("TAG", "someone called me 12");
//                                Intent sendIntent = new Intent("message_recieved");
//                                sendIntent.putExtra("message", messageContent);
//                                Log.i("TAG", "someone called me 13");
//                                LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
//                                Log.i("TAG", "someone called me 14");
                    }
                    else {
                        Log.i("TAG", "someone called me 11");
                        String messageContent=intent.getStringExtra(ChatMessage.MESSAGECONTENT);
                        Log.i("TAG", messageContent);
                        ChatMessage chatMessage = new ChatMessage(messageContent,"N", "Y");
                        Log.i("TAG", chatMessage.getMessageContent());
                        chatMessage.setservice(intent.getStringExtra(ChatMessage.SERVICE));
                        chatMessage.setType(intent.getStringExtra("type"));
                        dataBaseHelper = new DataBaseHelper(this);
                        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("chat_message", chatMessage.getMessageContent());
                        contentValues.put("message_service", chatMessage.getservice());
                        contentValues.put("outgoing", chatMessage.getoutgoing());
                        contentValues.put("incoming", chatMessage.getincoming());
                        sqLiteDatabase.insert("CHAT_TABLE", null, contentValues);
    //                    SharedPreferences shareprefe = getSharedPreferences("MyPref", MODE_PRIVATE);
    //                    String currentService = shareprefe.getString("CurrentService", "");
    //                    String messageService = chatMessage.getservice();
    //                    ActivityManager am = (ActivityManager) this .getSystemService(ACTIVITY_SERVICE);
    //                    List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
    //                    ComponentName componentInfo = taskInfo.get(0).topActivity;
                            //Log.i("TAG", taskInfo.get(0).topActivity.getClassName());
                            sendNotification(chatMessage);
                    }
                }
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    private void sendNotification(ChatMessage msg) {
//        Log.i("TAG", "someone called me 10");
//        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.hamburger)
//                        .setContentTitle("Alternative Notification")
//                        .setStyle(new NotificationCompat.BigTextStyle()
//                                .bigText(msg.getMessageContent()))
//                        .setContentText(msg.getMessageContent());
//        mBuilder.setContentIntent(contentIntent);
//        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());



        android.support.v4.app.NotificationCompat.Builder mbuilder = new android.support.v4.app.NotificationCompat.Builder(this);
        mbuilder.setSmallIcon(R.drawable.user);
        mbuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(msg.getservice()));
        mbuilder.setContentText(msg.getMessageContent());
        mbuilder.setContentTitle(msg.getservice());
        mbuilder.setAutoCancel(true);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("Service", msg.getservice());
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(ChatActivity.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =  taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(pendingIntent);
        NotificationManager NM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0, mbuilder.build());

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
