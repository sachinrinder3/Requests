package com.example.android.requests.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
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

    private RecyclerView recyclerView;
    private ScrollView mMessageScrollView;

    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatList;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;



    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */


    private DefaultChatterBoxCallback roomListener = new DefaultChatterBoxCallback() {

        @Override
        public void onMessage(ChatMessage message) {
            Log.i(Constant.TAG, "received a message");
            final ChatMessage fmsg = message;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Just add another message to the adapter.
                    ChatMessage hey = new ChatMessage(fmsg.getMessageContent(), "N", "Y");
                    chatAdapter.addItem(chatAdapter.getItemCount(),hey);
                }
            });

        }

        @Override
        public void onError(String message) {
            Log.d(Constant.TAG, "error while listening for message");
        }
    };

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


    public static ChatterBoxMessageFragment newInstance(String emailid, String roomName) {
        ChatterBoxMessageFragment fragment = new ChatterBoxMessageFragment();
        fragment.setCurrentUserProfile(emailid);
        fragment.setRoomName(roomName);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
        //chatAdapter = new ChatMessageListArrayAdapter(getActivity(), R.layout.chat_message_item, chatterMessageArray,
                //currentUserProfile);

        chatAdapter = new ChatAdapter(getActivity(), getChatListFromDataBase("HomeServices"));


    }
    public void setCurrentUserProfile(String profile) {

        this.emailid = profile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chattmessage_list, container, false);

        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.message_list);
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
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
