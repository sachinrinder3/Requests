package com.example.android.requests.fragments;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;
import com.pubnub.api.PubnubError;


import com.example.android.requests.R;
import com.example.android.requests.activities.MainActivity;
import com.example.android.requests.adapters.ChatAdapter;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.utils.Constant;
import com.example.android.requests.utils.DataBaseHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChatWithUs extends Fragment {
    private ChatAdapter chatAdapter;
    private AppCompatEditText sendtext;
    private AppCompatButton sendbutton;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private BroadcastReceiver recieve_chat;
    private LocalBroadcastManager localBroadcastManager;
    private LocalBroadcastManager mLocalBroadcastManager;
    private List<ChatMessage> chat_list;
    private RecyclerView recList;
    private AppCompatImageButton stickerButton;
    private boolean isStickersFrameVisible;
    private View stickersFrame;
    //public static final Pubnub pubnub = new Pubnub("pub-c-0e57abe1-40bd-4357-8754-ec6d0e4a5add", "sub-c-38127c1c-cb42-11e5-a316-0619f8945a4f");


    public ChatWithUs() {

    }

   public boolean sendChatMessage(){

   //chatAdapter.add(new ChatMessage(sendtext.getText().toString()));
       chatAdapter.getItemCount();
       return false;

   }


//    @Override
//    public void onClick(View v) {
//        sendmessage = sendtext.getText().toString();
//        Toast.makeText(getActivity(), sendmessage, Toast.LENGTH_SHORT).show();
//        //sendChatMessage();
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pubnub.subscribe("vdvd",fvrv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        try {
//            pubnub.subscribe(Constant.HOME_SERVICES, new Callback() {
//                        @Override
//                        public void connectCallback(String channel, Object message) {
//
//                            Log.i("TAG", "11");
//                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                    + " : " + message.getClass() + " : "
//                                    + message.toString());
//                        }
//
//                        @Override
//                        public void disconnectCallback(String channel, Object message) {
//
//                            Log.i("TAG", "12");
//                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                    + " : " + message.getClass() + " : "
//                                    + message.toString());
//                        }
//
//                        public void reconnectCallback(String channel, Object message) {
//                            Log.i("TAG", "13");
//                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                    + " : " + message.getClass() + " : "
//                                    + message.toString());
//                        }
//
//                        @Override
//                        public void successCallback(String channel, Object message) {
//                            final Object ob = message;
//
//                            new Thread() {
//                                public void run() {
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            Toast.makeText(getActivity(), "Hello", Toast.LENGTH_LONG).show();
//                                            String received_message = ob.toString();
//                                            ChatMessage hey1 = new ChatMessage(received_message, "N", "Y");
//                                            addMessageToDataBase(received_message, "hey", "N", "Y");
//                                            chatAdapter.addItem(chatAdapter.getItemCount(), hey1);
//                                        }
//                                    });
//                                }
//                            }.start();
//
//
//                            Log.i("TAG", "14");
//                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                    + " : " + message.getClass() + " : "
//                                    + message.toString());
//
//                            //ChatMessage hey2 =  new ChatMessage("vfvdf", "N", "Y");
//                            //chatAdapter.addItem(chatAdapter.getItemCount(),hey2);
//
//
//                        }
//
//                        @Override
//                        public void errorCallback(String channel, PubnubError error) {
//                            Log.i("TAG", "15");
//                            Log.i("TAG", "SUBSCRIBE : CONNECT on channel:" + channel
//                                    + " : " + error.getClass() + " : "
//                                    + error.toString());
//                        }
//                    }
//            );
//        } catch (PubnubException e) {
//            System.out.println(e.toString());
//        }
        View rootView = inflater.inflate(R.layout.fragment_chat_with_us, container, false);
        dataBaseHelper = new DataBaseHelper(getActivity());
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        recList = (RecyclerView) rootView.findViewById(R.id.message_list);
        recList.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(getActivity(), getChatListFromDataBase());
        recList.setAdapter(chatAdapter);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = (new LinearLayoutManager(getActivity()));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
       // int lastCompletelyVisibleItemPosition = llm.findLastCompletelyVisibleItemPosition();
//        //Log.i("TAG",String.valueOf(lastCompletelyVisibleItemPosition));
//        int i = recList.getScrollState();
//        //Log.i("TAG",String.valueOf(i));
//        if(lastCompletelyVisibleItemPosition <chatAdapter.getItemCount()){
//            Log.i("TAG",String.valueOf(lastCompletelyVisibleItemPosition));
            llm.setReverseLayout(false);
        //}
        //else{
            //llm.setReverseLayout(false);
        //}
        recList.setLayoutManager(llm);


        sendbutton =(AppCompatButton)rootView.findViewById(R.id.send_chat_msg_button);
        sendtext =(AppCompatEditText)rootView.findViewById(R.id.chat_msg);
        //chatMessage = new ChatMessage(sendmessage);

        sendbutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String newmessage = sendtext.getText().toString();
                                              sendtext.setText("");
                                              if (!newmessage.equals(""));
                                              {

                                                  //SendMessage runner = new SendMessage();
                                                  //runner.execute(newmessage);

//                                                  pubnub.publish(Constant.HOME_SERVICES, newmessage, new Callback() {
//                                                      public void successCallback(String channel, Object response) {
//                                                          Log.i("TAG", "SUCCESSFULL SENT");
//                                                          System.out.println(response.toString());
//                                                      }
//                                                      public void errorCallback(String channel, PubnubError error) {
//                                                          System.out.println(error.toString());
//                                                          Log.i("TAG", "ERROR IN SENDIND");
//                                                      }
//                                                  });
//                                                  addMessageToDataBase(newmessage, "I m a don HAHAHA !", "Y", "N");
//                                                  //SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
//                                                  //String email = sharepref.getString(Constant.EMAIL, "");
//                                                  ChatMessage hey = new ChatMessage(newmessage, "Y", "N");
//                                                  chatAdapter.addItem(chatAdapter.getItemCount(), hey);
//                                                  SendMessage runner = new SendMessage();
//                                                  runner.execute("");
                                              }
                                          }
                                      }
        );

        stickersFrame = rootView.findViewById(R.id.frame);
        stickerButton = (AppCompatImageButton)rootView.findViewById(R.id.stickers_button);

        stickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStickersFrameVisible) {
                    //showKeyboard();
                    stickerButton.setImageResource(R.drawable.ic_action_insert_emoticon);
                } else {
                    //if (keyboardHandleLayout.isKeyboardVisible()) {
//                        keyboardHandleLayout.hideKeyboard(getActivity(), new KeyboardHandleRelativeLayout.OnKeyboardHideCallback() {
//                            @Override
//                            public void onKeyboardHide() {
//                                stickerButton.setImageResource(R.drawable.ic_action_keyboard);
//                                setStickersFrameVisible(true);
//                            }
//                        });
//                    } else {
//                        stickerButton.setImageResource(R.drawable.ic_action_keyboard);
//                        setStickersFrameVisible(true);
//                    }
                }
            }
        });

        recieve_chat=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String received_message = intent.getStringExtra("message");
                ChatMessage hey1 =  new ChatMessage(received_message, "N", "Y");
                addMessageToDataBase(received_message, "i m doing my job", "N", "Y");
                chatAdapter.addItem(chatAdapter.getItemCount(),hey1);

            }
        };

        localBroadcastManager.getInstance(getActivity()).registerReceiver(recieve_chat, new IntentFilter("message_recieved"));

        return rootView;
    }
    public List<ChatMessage> getChatListFromDataBase(){

        chat_list =  new ArrayList<>();
        //String query = "select chat_message from CHAT_TABLE";
        String[] columns = {"_id", "chat_message", "message_service",  "outgoing", "incoming"};
        Cursor c = sqLiteDatabase.query("CHAT_TABLE",columns,null, null, null,null,null);
        //Log.i("TAG",String.valueOf(c.getCount()));
        while (c.moveToNext()){
            String message = c.getString(1);
            String outgoing = c.getString(3);
            String incoming = c.getString(4);
            ChatMessage chatMessage = new ChatMessage(message, outgoing, incoming);
            chat_list.add(chatMessage);
        }
        //Log.i("TAG","I AM GETTING CALLED");
        return chat_list;

    }

    public void addMessageToDataBase (String message, String message_service, String outgoing, String incoming ){
        dataBaseHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_message", message);
        contentValues.put("message_service", message_service);
        contentValues.put("incoming", incoming);
        contentValues.put("outgoing", outgoing);
        long id = sqLiteDatabase.insert("CHAT_TABLE", null, contentValues);
        //Log.i("TAG", String.valueOf(id));
        Log.i("TAG", "VALUE IS INSERTED INTO THE DATABASE");

    }

    public boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //localBroadcastManager.unregisterReceiver(recieve_chat);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }


//    private class SendMessage extends AsyncTask<String, Void, JsonObject> {
////        @Override
////        protected void onPreExecute() {
////           super.onPreExecute();
////
////       }
//
//        @Override
//        protected JsonObject doInBackground(String... params) {
//            //final JSONObject json = new JSONObject();
//             final JsonObject jso = new JsonObject();
//             jso.addProperty("message", params[0]);
//             //pubnub.publish(Constant.HOME_SERVICES, params[0], new Callback() {
//                 public void successCallback(String channel, Object response) {
//                     Log.i("TAG", "SUCCESSFULL SENT");
//                     //jso.addProperty("status", "Sent");
//                     Log.i("TAG", "SUCCESSFULL SENT");
//                     System.out.println(response.toString());
//                     Log.i("TAG", "SUCCESSFULL SENT");
//                 }
//
//                 public void errorCallback(String channel, PubnubError error) {
//                     System.out.println(error.toString());
//                    // jso.addProperty("status", "Error");
//                     Log.i("TAG", "ERROR IN SENDIND");
//                 }
//             });
//            //Log.i("TAG",jso.get("status").getAsString());
//            //return jso;
//        }
//
//
//
//        @Override
//        protected void onPostExecute(JsonObject json) {
//            super.onPostExecute(json);
//
//                Log.i("TAG",json.get("status").getAsString());
//                //Log.i("TAG",json.get("message").getAsString());
//                if (json.get("status").getAsString().equals("Sent")) {
//                    addMessageToDataBase(json.get("message").getAsString(), "I m a don HAHAHA !", "Y", "N");
//                    //SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
//                    //String email = sharepref.getString(Constant.EMAIL, "");
//                    ChatMessage hey = new ChatMessage(json.get("message").getAsString(), "Y", "N");
//                    chatAdapter.addItem(chatAdapter.getItemCount(), hey);
//                }
//                if (json.get("status").getAsString().equals("Error")){
//                    Toast.makeText(getActivity(), "You are not connected to internet ", Toast.LENGTH_SHORT).show();
//                }
//
//
//        }
//    }

//    private class SendMessage extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            String intialUrl = Constant.intialUrl;
//            OkHttpClient client = new OkHttpClient();
//            String status = "failure";
//            SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
//            String email = sharepref.getString(Constant.EMAIL, "");
//            if(!email.equals("")) {
//                String uri = intialUrl + "sendmessage?" + Constant.EMAIL + "="+email;
//                Log.i("TAG", uri);
//                Request request = new Request.Builder().url(uri).build();
//                JsonObject jobject = new JsonObject();
//
//                try {
//                Call call = client.newCall(request);
//                Response response = call.execute();
//                JsonElement jelement = new JsonParser().parse(response.body().string());
//                jobject = jelement.getAsJsonObject();
//                status = jobject.get("message").getAsString();
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }}
//            return status;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if(result.equals("Success")){
//                Log.i("TAG", result);
//                Log.i("TAG", "I am getting called");
//                Toast.makeText(getActivity(), "Message Sent !", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

//    private void showKeyboard() {
//        //((InputMethodManager) messageEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(messageEditText, InputMethodManager.SHOW_IMPLICIT);
//    }
}
