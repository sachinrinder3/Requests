//package com.example.android.requests.fragments;
//
//import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.android.requests.R;
//import com.example.android.requests.services.ChatterBoxService;
//import com.example.android.requests.services.DefaultChatterBoxCallback;
//import com.example.android.requests.services.binder.ChatterBoxClient;
//import com.example.android.requests.utils.Constant;
//import com.example.android.requests.utils.RoomHost;
//
//
//public class ChatterBoxRoomFragment extends Fragment {
//    private RoomHost mListener;
//    private String emailid;
//    private ChatterBoxClient chatterBoxServiceClient;
//    private ChatterBoxMessageSendFragment chatterBoxMessageSendFragment;
//    private ChatterBoxMessageFragment chatterBoxMessageListFragment;
//    private String roomTitle;
//    private String roomChannel;
//
//
//    private DefaultChatterBoxCallback roomListener = new DefaultChatterBoxCallback(){
////        @Override
////        public void onPresence(ChatterBoxPresenceMessage pmessage) {
////
////        }
//
//        @Override
//        public void onHeartBeat(boolean error) {
//
//        }
//
//        @Override
//        public void onError(String e) {
//
//        }
//
////        @Override
////        public void onPrivateChatRequest(ChatterBoxPrivateChatRequest request) {
////
////        }
//
//        @Override
//        public void onDisconnect() {
//
//        }
//
//        @Override
//        public void onConnect() {
//
//        }
//
//    };
//
//
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.i(Constant.TAG, "connecting to service");
//            chatterBoxServiceClient = (ChatterBoxClient) service;
//            if(chatterBoxServiceClient.isConnected() == false){
//                Log.i("TAG", emailid);
//                chatterBoxServiceClient.connect(emailid);
//            }
//            chatterBoxServiceClient.addRoom(roomChannel, roomListener);
//           // mListener.connectedToRoom(roomTitle,roomChannel);
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.i(Constant.TAG, "disconnecting from service");
//        }
//    };
//
//    public void setCurrentUserEmailid(String emailid) {
//        this.emailid = emailid;
//    }
//
//
//    private void setChatterBoxMessageSendFragment(ChatterBoxMessageSendFragment
//                                                          sendFragment){
//        this.chatterBoxMessageSendFragment = sendFragment;
//    }
//
//    private void setChatterBoxMessageFragment(ChatterBoxMessageFragment fragment){
//        this.chatterBoxMessageListFragment = fragment;
//    }
//
////    private void setRoomTitle(String roomTitle){
////
////        this.roomTitle = roomTitle;
////    }
//
//    private void setRoomChannel(String roomChannel){
//
//        this.roomChannel = roomChannel;
//    }
//
//
//    private void configureRoom() {
//        //Load up the Message View
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.message_display_fragment_container, chatterBoxMessageListFragment);
//        fragmentTransaction.replace(R.id.message_input_fragment_container, chatterBoxMessageSendFragment);
//        fragmentTransaction.commit();
//    }
//
//
////    private void configureWhoIsOnLine() {
////        mListener.connectedToRoom(this.roomTitle,this.roomChannel);
////    }
//
//
//    public static ChatterBoxRoomFragment newInstance(String emailid, String roomChannel) {
//        ChatterBoxRoomFragment fragment = new ChatterBoxRoomFragment();
//        fragment.setCurrentUserEmailid(emailid);
//        fragment.setRoomChannel(emailid);
//        String servicename = roomChannel;
//        fragment.setChatterBoxMessageFragment(ChatterBoxMessageFragment.newInstance(emailid, servicename));
//        fragment.setChatterBoxMessageSendFragment(ChatterBoxMessageSendFragment.newInstance(emailid, roomChannel));
//        return fragment;
//    }
//
//    public ChatterBoxRoomFragment() {
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_chatter_box_room, container, false);
//    }
//
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            //mListener = (RoomHost) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement RoomHost");
//        }
//
//        Intent chatterBoxServiceIntent = new Intent(getActivity(), ChatterBoxService.class);
//        getActivity().bindService(chatterBoxServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
//        configureRoom();
//        //configureWhoIsOnLine();
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        //chatterBoxServiceClient.removeRoomListener(roomChannel, roomListener);
////        mListener.disconnectingFromRoom(roomChannel);
//        getActivity().unbindService(serviceConnection);
//        mListener = null;
//
//    }
//}
