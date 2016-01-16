package com.example.android.requests.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.adapters.ChatAdapter;
import com.example.android.requests.models.ChatMessage;



public class ChatWithUs extends Fragment {
    private ChatAdapter chatAdapter;
    private ChatMessage chatMessage;
    private AppCompatEditText sendtext;
    String sendmessage;
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


        ChatMessage[] galaxy = {new ChatMessage("hey, lets meet tonight if possible"), new ChatMessage("hello, whats up lets plan a thing after some time")};


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat_with_us, container, false);
        RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.message_list);
        recList.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(getActivity(), ChatMessage.createChatMessages(10));
        recList.setAdapter(chatAdapter);
        recList.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager llm = (new LinearLayoutManager(getActivity()));
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        int lastCompletelyVisibleItemPosition = llm.findLastCompletelyVisibleItemPosition();
        Log.i("TAG",String.valueOf(lastCompletelyVisibleItemPosition));
        int i = recList.getScrollState();
        Log.i("TAG",String.valueOf(i));
        if(lastCompletelyVisibleItemPosition < chatAdapter.getItemCount()){
            Log.i("TAG",String.valueOf(lastCompletelyVisibleItemPosition));
            llm.setReverseLayout(true);
        }
        else{
            llm.setReverseLayout(false);
        }
        recList.setLayoutManager(llm);


        Button sendbutton =(Button)rootView.findViewById(R.id.send_chat_msg_button);
        sendtext =(AppCompatEditText)rootView.findViewById(R.id.chat_msg);
        //chatMessage = new ChatMessage(sendmessage);

        sendbutton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );

        return rootView;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
