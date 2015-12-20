package com.example.tuljain.requests.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tuljain.requests.R;

/**
 * Created by tuljain on 12/20/2015.
 */
public  class ChatViewHolder extends RecyclerView.ViewHolder {
    private final TextView chatmessage;

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

    public void showMessageText(String text) {
        chatmessage.setText(text);
    }
}
