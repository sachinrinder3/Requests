package com.example.android.requests.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.requests.R;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.services.ChatterBoxService;
import com.example.android.requests.services.DefaultChatterBoxCallback;
import com.example.android.requests.services.binder.ChatterBoxClient;
import com.example.android.requests.utils.Constant;

import java.util.Date;


public class ChatterBoxMessageSendFragment extends Fragment {
    private String emailid;
    private ChatterBoxClient chatterBoxServiceClient;
    private AppCompatEditText mMessageEditText;
    private AppCompatButton mBtnSend;
    private String roomName;

    private DefaultChatterBoxCallback roomListener = new DefaultChatterBoxCallback() {
        @Override
        public void onMessagePublished(String timeToken) {
            Log.i(Constant.TAG, "inside: onMessagePublished for Send fragment");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageEditText.setEnabled(true);
                    mBtnSend.setEnabled(true);
                    mMessageEditText.setText("");
                }
            });

        }
    };


    public void setRoomName(String roomName) {

        this.roomName = roomName;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(Constant.TAG, "connecting to service");
            chatterBoxServiceClient = (ChatterBoxClient) service;
            if(chatterBoxServiceClient.isConnected() == false){
                chatterBoxServiceClient.connect(emailid);
            }

            chatterBoxServiceClient.addRoom(roomName,roomListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(Constant.TAG, "disconnecting from service");
        }
    };

    public ChatterBoxMessageSendFragment() {

    }

    public static ChatterBoxMessageSendFragment newInstance(String emailid, String roomName) {
        ChatterBoxMessageSendFragment fragment = new ChatterBoxMessageSendFragment();
        fragment.setRoomName(roomName);
        fragment.setCurrentUserProfile(emailid);
        return fragment;
    }

    public void setCurrentUserProfile(String emailid) {
        this.emailid= emailid;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View messageControlsView = inflater.inflate(R.layout.fragment_chatter_box_message_send, container, false);
        mBtnSend = (AppCompatButton) messageControlsView.findViewById(R.id.send_msg_btn);
        mMessageEditText  = (AppCompatEditText) messageControlsView.findViewById(R.id.chat_msg);


        final String roomNameF = this.roomName;
        final AppCompatEditText txtMsg = mMessageEditText;
        final AppCompatButton btn = mBtnSend;

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CharSequence content = txtMsg.getText();
                if ((content.length() == 0) || (content.equals(""))) {
                    return;
                }

                ChatMessage message = ChatMessage.create();
                message.setDeviceTag("android");
                //message.setSenderUUID(currentUserProfile.getId());
                message.setType(ChatMessage.CHATTMESSAGE);
                message.setMessageContent(txtMsg.getText().toString());
                //message.setFrom(currentUserProfile.getEmail());
                message.setSentOn(new Date());

                txtMsg.setEnabled(false);
                btn.setEnabled(false);

                if (chatterBoxServiceClient.isConnected()) {
                    chatterBoxServiceClient.publish(roomNameF, message);
                    txtMsg.setText("");
                }

            }
        });



        return messageControlsView;
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
        getActivity().unbindService(serviceConnection);
    }
}
