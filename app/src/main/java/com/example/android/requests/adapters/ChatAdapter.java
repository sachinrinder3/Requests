package com.example.android.requests.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
import com.example.android.requests.chat.ChatViewHolder;
import com.example.android.requests.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  ChatMessage[] chatList;

    public ChatAdapter(ChatMessage[] chatList) {
        this.chatList = chatList;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.send_chat, viewGroup, false);

        return new ChatViewHolder(v);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return chatList.length;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //((ChatViewHolder) holder).ch
        ((ChatViewHolder) holder).chatmessage.setText(chatList[position].getChatmessage());

    }


}
