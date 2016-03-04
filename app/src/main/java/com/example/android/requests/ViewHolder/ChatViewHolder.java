package com.example.android.requests.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.requests.R;


public  class ChatViewHolder extends RecyclerView.ViewHolder {
    private TextView chatmessage;

    public ChatViewHolder(View v) {
        super(v);
        chatmessage = (TextView) v.findViewById(R.id.SingleMessage);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("TAG", "yo I am chatting view holder");
                }
            });
    }

    public void showMessageText(String text) {

        chatmessage.setText(text);
    }
}
