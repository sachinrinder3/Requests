package com.example.android.requests.chat;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.requests.R;


public  class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView chatmessage;

    public ChatViewHolder(View v) {
        super(v);
        // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(, feedItem.getTitle(), Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "yo I am chatting view holder");
                }
            });
        chatmessage = (TextView) v.findViewById(R.id.SingleMessage);
    }

    public void showMessageText(String text) {
        chatmessage.setText(text);
    }
}
