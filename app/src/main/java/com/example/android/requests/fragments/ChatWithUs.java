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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;


public class ChatWithUs extends Fragment {
    private ChatAdapter chatAdapter;
    private AppCompatEditText sendtext;
    AppCompatButton sendbutton;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    BroadcastReceiver recieve_chat;
    LocalBroadcastManager localBroadcastManager;
    private LocalBroadcastManager mLocalBroadcastManager;
    List<ChatMessage> chat_list;
    RecyclerView recList;

    public ChatWithUs() {}

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


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
                                              ChatMessage hey =  new ChatMessage(newmessage, "Y", "N");
                                              addMessageToDataBase(newmessage, "I m a don HAHAHA !", "Y", "N");
                                              chatAdapter.addItem(chatAdapter.getItemCount(), hey);
                                              SendMessage runner = new SendMessage();
                                              runner.execute("");
                                          }
                                      }
        );

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
        String query = "select chat_message from CHAT_TABLE";
        String[] columns = {"_id", "chat_message", "send_to",  "outgoing", "incoming"};
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

    public void addMessageToDataBase (String message, String send_to, String outgoing, String incoming ){
        dataBaseHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_message", message);
        contentValues.put("send_to", send_to);
        contentValues.put("incoming", incoming);
        contentValues.put("outgoing", outgoing);
        long id = sqLiteDatabase.insert("CHAT_TABLE", null, contentValues);
        Log.i("TAG", String.valueOf(id));
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

    private class SendMessage extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            String intialUrl = Constant.intialUrl;
            OkHttpClient client = new OkHttpClient();


            String uri = intialUrl + "sendmessage?message=fvfvfvf";
            Log.i("TAG", uri);
            Request request = new Request.Builder().url(uri).build();
            JsonObject jobject = new JsonObject();
            String status = "failure";


            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                JsonElement jelement = new JsonParser().parse(response.body().string());
                jobject = jelement.getAsJsonObject();
                status = jobject.get("message").getAsString();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return status;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("Success")){
                Log.i("TAG", result);
                Log.i("TAG", "I am getting called");
                Toast.makeText(getActivity(), "Message Sent !", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
