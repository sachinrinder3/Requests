
package com.example.android.requests.activities;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.android.requests.fragments.ChatWithUs;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.utils.Constant;
import com.example.android.requests.R;
import com.example.android.requests.utils.DataBaseHelper;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import java.util.ArrayList;
import java.util.List;


public class HomeServices extends AppCompatActivity {
    private Toolbar toolbar;
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
    //private View stickersFrame;


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
        getSupportActionBar().setTitle("Home Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sharepref =getSharedPreferences("MyPref", MODE_PRIVATE);
        final String email = sharepref.getString(Constant.EMAIL, "");

        try {
            ChatWithUs.pubnub.subscribe(email+Constant.HOME_SERVICES, new Callback() {
                        @Override
                        public void connectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                            Log.i("TAG", "11");
                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : DISCONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                            Log.i("TAG", "12");
                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        public void reconnectCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : RECONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                            Log.i("TAG", "13");
                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                        }

                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("SUBSCRIBE : " + channel + " : "
                                    + message.getClass() + " : " + message.toString());
                            Log.i("TAG", "14");
                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + message.getClass() + " : "
                                    + message.toString());
                            String received_message = message.toString();
                            ChatMessage hey1 = new ChatMessage(received_message, "N", "Y");
                            addMessageToDataBase(received_message, Constant.HOME_SERVICES, "N", "Y");
                            homeservices_chatAdapter.addItem(homeservices_chatAdapter.getItemCount(), hey1);
                        }
                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("SUBSCRIBE : ERROR on channel " + channel
                                    + " : " + error.toString());
                            Log.i("TAG", "15");
                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
                                    + " : " + error.getClass() + " : "
                                    + error.toString());
                        }
                    }
            );
        } catch (PubnubException e) {
        System.out.println(e.toString());
        }

        homeservice_recList.setHasFixedSize(true);
        homeservices_chatAdapter = new ChatAdapter(this, getChatListFromDataBase());
        homeservice_recList.setAdapter(homeservices_chatAdapter);
        homeservice_recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager homeservice_llm = (new LinearLayoutManager(HomeServices.this));
        homeservice_llm.setOrientation(LinearLayoutManager.VERTICAL);
        homeservice_recList.setLayoutManager(homeservice_llm);
        homeservices_sendButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String newmessage = homeservices_sendText.getText().toString();
                                              homeservices_sendText.setText("");
                                              if (!newmessage.equals("")) ;
                                              {

                                                  ChatMessage hey = new ChatMessage(newmessage, "Y", "N");
                                                  homeservices_chatAdapter.addItem(homeservices_chatAdapter.getItemCount(), hey);
                                                  Callback callback = new Callback() {
                                                      public void successCallback(String channel, Object response) {
                                                          Log.i("TAG", "SUCCESSFULL SENT");
                                                          System.out.println(response.toString());
                                                      }

                                                      public void errorCallback(String channel, PubnubError error) {
                                                          System.out.println(error.toString());
                                                          Log.i("TAG", "ERROR IN SENDIND");
                                                      }
                                                  };
                                                  ChatWithUs.pubnub.publish(Constant.HOME_SERVICES+email, newmessage, callback);
                                                  addMessageToDataBase(newmessage, Constant.HOME_SERVICES, "Y", "N");
//                                                  SendMessage runner = new SendMessage();
//                                                  runner.execute("");
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
        Cursor c = sqLiteDatabase.query("CHAT_TABLE",columns,"message_service=?", new String[] { Constant.HOME_SERVICES }, null, null,null,null);
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
}
