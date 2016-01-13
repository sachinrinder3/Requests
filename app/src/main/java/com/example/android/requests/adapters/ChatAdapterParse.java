package com.example.android.requests.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.requests.R;
import com.example.android.requests.chat.ChatViewHolder;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.models.Message;

public class ChatAdapterParse extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Message[] chatList;

    public ChatAdapterParse(Message[] chatList) {

        this.chatList = chatList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.send_chat, viewGroup, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(v);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message chatMessage = chatList[position];
        ((ChatViewHolder) holder).showMessageText(chatMessage.getBody());
    }


    @Override
    public int getItemViewType(int position) {
        Message message = chatList[position];
        return position;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return chatList.length;
    }


}