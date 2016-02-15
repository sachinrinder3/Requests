
package com.example.android.requests.activities;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.android.requests.adapters.ChatAdapter;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.services.ChatterBoxService;
import com.example.android.requests.services.DefaultChatterBoxCallback;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;
import com.example.android.requests.R;
import com.example.android.requests.utils.DataBaseHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeServices extends AppCompatActivity {
    private String emailid;
    private Toolbar toolbar;
    private String roomName;
    private ChatAdapter homeservices_chatAdapter;
    private AppCompatEditText homeservices_sendText;
    private AppCompatButton homeservices_sendButton;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    //private BroadcastReceiver recieve_chat;
    //private LocalBroadcastManager localBroadcastManager;
    //private LocalBroadcastManager mLocalBroadcastManager;
    private List<ChatMessage> homeservices_chatList;
    private RecyclerView homeservice_recList;
    private AppCompatImageButton stickerButton;
    private boolean isStickersFrameVisible;
    private ChatterBoxClient chatterBoxServiceClient;
    boolean mServiceBound = false;
    String channelName;
    String roomChannel;
    //private View stickersFrame;

    private DefaultChatterBoxCallback roomListener = new DefaultChatterBoxCallback() {
        @Override
        public void onMessagePublished(String timeToken) {
            Log.i(Constant.TAG, "inside: onMessagePublished");
            HomeServices.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("TAG", "I am publishing messages");
                    homeservices_sendText.setEnabled(true);
                    homeservices_sendButton.setEnabled(true);
                }
            });

        }

        @Override
        public void onMessage(ChatMessage message) {
            super.onMessage(message);
            Log.i("TAG", channelName);
            Log.i("TAG", message.getservice());
            if (message.getservice().equals(channelName)) {
                ChatMessage hey = new ChatMessage(message.getMessageContent(), "N", "Y");
                homeservices_chatAdapter.addItem(homeservices_chatAdapter.getItemCount(), hey);
            }
            addMessageToDataBase(message.getMessageContent(), message.getservice(), "N", "Y");
            //Log.i(Constant.TAG, "Message received callback");
        }
    };
    private void setRoomChannel(String roomChannel){

        this.roomChannel = roomChannel;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Constant.TAG, "connecting to service");
            mServiceBound = true;
            chatterBoxServiceClient = (ChatterBoxClient) service;
            if(chatterBoxServiceClient.isConnected() == false){
                chatterBoxServiceClient.connect(emailid);
                Log.i("TAG", "CONDCED");
                Log.i("TAG", String.valueOf(chatterBoxServiceClient.connect(emailid)));
                Log.i("TAG", "CONDCED");
            }
            chatterBoxServiceClient.addRoom(roomChannel,roomListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constant.TAG, "disconnecting from service");

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_services);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        homeservices_sendButton =(AppCompatButton)findViewById(R.id.homeservices_send_msg);
        homeservices_sendText  =(AppCompatEditText)findViewById(R.id.homeservices_chat_msg);
        homeservice_recList = (RecyclerView)findViewById(R.id.homeservices_message_list);
        dataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sharepref =getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email = sharepref.getString(Constant.EMAIL, "");
        Intent chatterBoxServiceIntent = new Intent(HomeServices.this, ChatterBoxService.class);
        HomeServices.this.bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent i = getIntent();


        String Service = i.getStringExtra("Service");

         channelName = Service;
        if (Service.equals("Food")){
            getSupportActionBar().setTitle("Food");
        }else if (Service.equals("HomeServices")){
            getSupportActionBar().setTitle("HomeServices");
        }else if (Service.equals("AnyThingElse")){
            getSupportActionBar().setTitle("AnyThingElse");
        }else if (Service.equals("Travel")){
            getSupportActionBar().setTitle("Travel");
        }else if (Service.equals("Cabs")){
            getSupportActionBar().setTitle("Cabs");
        }else if (Service.equals("Recharge")){
            getSupportActionBar().setTitle("Recharge");
        } else if (Service.equals("Shopping")){
            getSupportActionBar().setTitle("Shopping");
        }
        //boolean bfound = false;
//        String[] currentChannels = ChatWithUs.pubnub.getSubscribedChannelsArray();
//        for (String c : currentChannels) {
//            if (c.equals(email+Constant.HOME_SERVICES)) {
//                bfound = true;
//                break;
//            }
//        }
//        if (!bfound) {
//            Log.i("TAG", "SUBSCRIBE");
//
//            try {
//                ChatWithUs.pubnub.subscribe(email + Constant.HOME_SERVICES, new Callback() {
//                            @Override
//                            public void connectCallback(String channel, Object message) {
//                                System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                                Log.i("TAG", "11");
//                                Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                            }
//
//                            @Override
//                            public void disconnectCallback(String channel, Object message) {
//                                System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                                Log.i("TAG", "12");
//                                Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                            }
//
//                            public void reconnectCallback(String channel, Object message) {
//                                System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                                Log.i("TAG", "13");
//                                Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                            }
//
//                            @Override
//                            public void successCallback(String channel, Object message) {
//                                System.out.println("SUBSCRIBE : " + channel + " : "
//                                        + message.getClass() + " : " + message.toString());
//                                Log.i("TAG", "14");
//                                Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + message.getClass() + " : "
//                                        + message.toString());
//                                String received_message = message.toString();
//                                ChatMessage hey1 = new ChatMessage(received_message, "N", "Y");
//                                addMessageToDataBase(received_message, Constant.HOME_SERVICES, "N", "Y");
//                                homeservices_chatAdapter.addItem(homeservices_chatAdapter.getItemCount(), hey1);
//                            }
//
//                            @Override
//                            public void errorCallback(String channel, PubnubError error) {
//                                System.out.println("SUBSCRIBE : ERROR on channel " + channel
//                                        + " : " + error.toString());
//                                Log.i("TAG", "15");
//                                Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                        + " : " + error.getClass() + " : "
//                                        + error.toString());
//                            }
//                        }
//                );
//            } catch (PubnubException e) {
//                System.out.println(e.toString());
//            }
//        }
        final String  channelname = channelName;
        homeservice_recList.setHasFixedSize(true);
        homeservices_chatAdapter = new ChatAdapter(this, getChatListFromDataBase());
        homeservice_recList.setAdapter(homeservices_chatAdapter);
        homeservice_recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager homeservice_llm = (new LinearLayoutManager(HomeServices.this));
        homeservice_llm.setOrientation(LinearLayoutManager.VERTICAL);
        homeservice_recList.setLayoutManager(homeservice_llm);
        final AppCompatButton btn = homeservices_sendButton;
        final AppCompatEditText txtedit = homeservices_sendText;
        homeservices_sendButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String newmessage = homeservices_sendText.getText().toString();
                                              homeservices_sendText.setText("");
                                              if (!newmessage.equals("") ) ;
                                              {
                                                  ChatMessage message = ChatMessage.create();
                                                  message.setDeviceTag("Android");
                                                  message.setSenderUUID(email);
                                                  message.setincoming("N");
                                                  message.setoutgoing("Y");
                                                  message.setSenderUUID(email);
                                                  message.setType(ChatMessage.CHATTMESSAGE);
                                                  message.setMessageContent(newmessage);
                                                  message.setFrom(email);
                                                  message.setSentOn(new Date());
                                                  txtedit.setEnabled(false);
                                                  btn.setEnabled(false);

                                                  if (chatterBoxServiceClient.isConnected()) {
                                                      chatterBoxServiceClient.publish(channelname, message);
                                                      ChatMessage hey = new ChatMessage(newmessage, "Y", "N");
                                                      homeservices_chatAdapter.addItem(homeservices_chatAdapter.getItemCount(), hey);
                                                      addMessageToDataBase(newmessage, channelName, "Y", "N");
                                                      txtedit.setText("");
                                                  }
                                              }
                                          }
                                      }
        );
    }

    public List<ChatMessage> getChatListFromDataBase(){

        homeservices_chatList =  new ArrayList<>();
        //String query = "select chat_message from CHAT_TABLE";
        String[] columns = {"_id", "chat_message", "message_service",  "outgoing", "incoming"};
        String whereClause = "message_service=Home_Services";
        Cursor c = sqLiteDatabase.query("CHAT_TABLE",columns,"message_service=?", new String[] { channelName }, null, null,null,null);
        //Log.i("TAG",String.valueOf(c.getCount()));
        while (c.moveToNext()){
            String message = c.getString(1);
            String outgoing = c.getString(3);
            String incoming = c.getString(4);
            ChatMessage chatMessage = new ChatMessage(message, outgoing, incoming);
            homeservices_chatList.add(chatMessage);
        }
        //Log.i("TAG","I AM GETTING CALLED");
        return homeservices_chatList;

    }



    public void addMessageToDataBase (String message, String message_service, String outgoing, String incoming ){

        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_message", message);
        contentValues.put("message_service", message_service);
        contentValues.put("outgoing", outgoing);
        contentValues.put("incoming", incoming);
        long id = sqLiteDatabase.insert("CHAT_TABLE", null, contentValues);
        Log.i("TAG", String.valueOf(id));
        Log.i("TAG", "VALUE IS INSERTED INTO THE DATABASE");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharepref =getSharedPreferences("MyPref", MODE_PRIVATE);
        emailid = sharepref.getString(Constant.EMAIL, "jaintulsi");
        //Log.i("TAG", emailid);
        emailid="jaintulsi";
        setRoomChannel(emailid);
        Intent chatterBoxServiceIntent = new Intent(HomeServices.this, ChatterBoxService.class);
        HomeServices.this.bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatterBoxServiceClient.removeRoomListener(this.roomName,roomListener);
        if (mServiceBound) {
            HomeServices.this.unbindService(serviceConnection);
        }
        mServiceBound = false;
    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent chatterBoxServiceIntent = new Intent(HomeServices.this, ChatterBoxService.class);
        HomeServices.this.bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
