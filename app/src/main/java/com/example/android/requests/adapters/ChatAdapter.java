package com.example.android.requests.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.requests.R;
import com.example.android.requests.chat.ChatViewHolder;
import com.example.android.requests.models.Address;
import com.example.android.requests.models.ChatMessage;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater layoutInflater;

    List<ChatMessage> chatList = Collections.emptyList();
    Context c1;

    public ChatAdapter(Context c,List<ChatMessage>chatList) {
        this.chatList = chatList;
        layoutInflater = layoutInflater.from(c);
        this.c1=c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.send_chat, viewGroup, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(v);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatList.get(position);
        ((ChatViewHolder)holder).chatmessage.setText(chatMessage.chatmessage);
        ((ChatViewHolder) holder).showMessageText(chatMessage.getChatmessage());
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatList.get(position);
        return position;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return chatList.size();
    }


}