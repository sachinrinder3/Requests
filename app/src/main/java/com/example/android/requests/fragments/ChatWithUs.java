package com.example.android.requests.fragments;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
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

import com.example.android.requests.R;
import com.example.android.requests.adapters.ChatAdapter;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class ChatWithUs extends Fragment {
    private ChatAdapter chatAdapter;
    private AppCompatEditText sendtext;
    AppCompatButton sendbutton;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    BroadcastReceiver recieve_chat;
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
        Log.i("TAG", "yo babydvdvfdvf");
        chatAdapter = new ChatAdapter(getActivity(), getChatListFromDataBase());
        Log.i("TAG","I AM GETTING CALLEDALKVDSVKLDSV");
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
            llm.setReverseLayout(true);
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
                                              ChatMessage hey =  new ChatMessage(newmessage);
                                              addMessageToDataBase(newmessage, "I m a don HAHAHA !");
                                              Log.i("TAG", String.valueOf(chatAdapter.getItemCount()));
                                              Log.i("TAG","I AM GETTING CALLED jlknkln nblnlk");
                                              chatAdapter.addItem(chatAdapter.getItemCount(), hey);
                                          }
                                      }
        );
//
//      recieve_chat=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String message = intent.getStringExtra("message");
//                Log.d("pavan", "in local braod " + message);
//                showChat("recieve", message);
//            }
//        };
//
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(recieve_chat, new IntentFilter("message_recieved"));

        return rootView;
    }
    public List<ChatMessage> getChatListFromDataBase(){

        chat_list =  new ArrayList<>();
        String query = "select chat_message from CHAT_TABLE";
        String[] columns = {"_id", "chat_message", "send_to"};
        Cursor c = sqLiteDatabase.query("CHAT_TABLE",columns,null, null, null,null,null);
        //Log.i("TAG",String.valueOf(c.getCount()));
        while (c.moveToNext()){
           // Log.i("TAG",String.valueOf(c.getCount()));
            String message = c.getString(1);
            //Log.i("TAG",String.valueOf(c.getCount()));
            ChatMessage chatMessage = new ChatMessage(message);
            //Log.i("TAG",String.valueOf(c.getCount()));
            chat_list.add(chatMessage);
            //Log.i("TAG",String.valueOf(c.getCount()));
        }
        //Log.i("TAG","I AM GETTING CALLED");
        return chat_list;

    }

    public void addMessageToDataBase (String message, String send_to){
        dataBaseHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chat_message", message);
        contentValues.put("send_to", send_to);
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

    private void showChat(String type, String message){
        if(chat_list==null || chat_list.size()==0){
            chat_list= new ArrayList<ChatMessage>();
        }
        chat_list.add(new ChatMessage(message));
//        chatAdapter=new ChatAdapter(getActivity(),R.layout.chat_view,chat_list);
//        recList.setAdapter(chatAdapter);
        //chatAdabter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    private class SendMessage extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            String url = Uti.send_chat_url+"?email_id=";
//            //+editText_mail_id.getText().toString()+"&message="+editText_chat_message.getText().toString();
//            Log.i("pavan", "url" + url);
//            OkHttpClient client_for_getMyFriends = new OkHttpClient();;
//            String response = null;
//            try {
//                url = url.replace(" ", "%20");
//                response = callOkHttpRequest(new URL(url),
//                        client_for_getMyFriends);
//                for (String subString : response.split("<script", 2)) {
//                    response = subString;
//                    break;
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//        }
//    }
//    String callOkHttpRequest(URL url, OkHttpClient tempClient)
//            throws IOException {
//        HttpURLConnection connection = tempClient.open(url);
//        connection.setConnectTimeout(40000);
//        InputStream in = null;
//        try {
//            in = connection.getInputStream();
//            byte[] response = readFully(in);
//            return new String(response, "UTF-8");
//        } finally {
//            if (in != null)
//                in.close();
//        }
//    }
//    byte[] readFully(InputStream in) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        for (int count; (count = in.read(buffer)) != -1;) {
//            out.write(buffer, 0, count);
//        }
        //return out.toByteArray();
}
