package com.example.android.requests.fragments;


import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.android.requests.R;
import com.example.android.requests.adapters.ChatAdapter;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.services.ChatterBoxService;
import com.example.android.requests.services.DefaultChatterBoxCallback;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;
import com.example.android.requests.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class ChatterBoxMessageFragment extends Fragment {


    private ChatterBoxClient chatterBoxServiceClient;
    private String emailid;
    private String roomName;
    private String servicename;
    public RecyclerView recyclerView;
    private ScrollView mMessageScrollView;
    public ChatAdapter chatAdapter;
    private List<ChatMessage> chatList;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private void  addincomingtoadapter(ChatMessage fmsg){
        ChatMessage hey = new ChatMessage(fmsg.getMessageContent(), "N", "Y");
        chatAdapter.addItem(chatAdapter.getItemCount(), hey);
    }

    private void  addoutgoingtoadapter(ChatMessage fmsg){
        ChatMessage hey = new ChatMessage(fmsg.getMessageContent(), "Y", "N");
        chatAdapter.addItem(chatAdapter.getItemCount(), hey);
    }



    private DefaultChatterBoxCallback roomListener = new DefaultChatterBoxCallback() {

        @Override
        public void onMessage(ChatMessage message) {
            //Log.i(Constant.TAG, "received a message");
            final ChatMessage fmsg = message;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (fmsg.getservice().equals(servicename)) {
                        if (fmsg.getincoming().equals("Y") && fmsg.getoutgoing().equals("N")) {
                            addincomingtoadapter(fmsg);
                        } else if (fmsg.getincoming().equals("N") && fmsg.getoutgoing().equals("Y")) {
                            addoutgoingtoadapter(fmsg);
                        }
                    }
                    addMessageToDataBase(fmsg.getMessageContent(), fmsg.getservice(), fmsg.getoutgoing(), fmsg.getincoming());
                }
            });

        }

        @Override
        public void onError(String message) {
            Log.d(Constant.TAG, "error while listening for message");
        }
    };
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

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            chatterBoxServiceClient = (ChatterBoxClient) service;
            if (chatterBoxServiceClient.isConnected() == false) {
                chatterBoxServiceClient.connect(emailid);
            }
            chatterBoxServiceClient.addRoom(roomName, roomListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constant.TAG, "disconnecting from service");
        }
    };

    public ChatterBoxMessageFragment() {

    }

    public static ChatterBoxMessageFragment newInstance(String emailid, String servicename) {
        ChatterBoxMessageFragment fragment = new ChatterBoxMessageFragment();
        Log.i("TAG", emailid);
        fragment.setCurrentUserEmailid(emailid);
        fragment.setRoomName(emailid);
        fragment.setServiceName(servicename);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //chatAdapter = new ChatMessageListArrayAdapter(getActivity(), R.layout.chat_message_item, chatterMessageArray,
                //currentUserProfile);
        Log.i("TAG", servicename+"geetika");
        dataBaseHelper = new DataBaseHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        chatAdapter = new ChatAdapter(getActivity(), getChatListFromDataBase(servicename));



    }

    public void setCurrentUserEmailid(String emailid) {

        this.emailid = emailid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chattmessage_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.message_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = (new LinearLayoutManager(getActivity()));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Intent chatterBoxServiceIntent = new Intent(getActivity(), ChatterBoxService.class);
        getActivity().bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        chatterBoxServiceClient.removeRoomListener(this.roomName, roomListener);
        getActivity().unbindService(serviceConnection);
    }






    public void setRoomName(String roomName) {

        this.roomName = roomName;
    }
    public void setServiceName(String servicename) {

        this.servicename = servicename;
    }



    public List<ChatMessage> getChatListFromDataBase(String channelName){

        chatList =  new ArrayList<>();
        //String query = "select chat_message from CHAT_TABLE";
        String[] columns = {"_id", "chat_message", "message_service",  "outgoing", "incoming"};

        Cursor c = sqLiteDatabase.query("CHAT_TABLE",columns,"message_service=?", new String[] { channelName }, null, null,null,null);
        //Log.i("TAG",String.valueOf(c.getCount()));
        while (c.moveToNext()){
            String message = c.getString(1);
            String outgoing = c.getString(3);
            String incoming = c.getString(4);
            ChatMessage chatMessage = new ChatMessage(message, outgoing, incoming);
            chatList.add(chatMessage);
        }
        //Log.i("TAG","I AM GETTING CALLED");
        return chatList;

    }


    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
//    public void setEmptyText(CharSequence emptyText) {
//        View emptyView = mListView.getEmptyView();
//
//        if (emptyView instanceof TextView) {
//            ((TextView) emptyView).setText(emptyText);
//        }
//    }


}
