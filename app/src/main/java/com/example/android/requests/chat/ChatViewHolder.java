package com.example.android.requests.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.requests.R;


public  class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView chatmessage;

    public ChatViewHolder(View v) {
        super(v);
        // Define click listener for the ViewHolder's View.
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
        chatmessage = (TextView) v.findViewById(R.id.SingleMessage);
    }
}
