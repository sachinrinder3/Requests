package com.example.android.requests.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
import com.example.android.requests.activities.ChatActivity;
import com.example.android.requests.models.ChatHistory;
import com.example.android.requests.utils.Constant;

import java.util.Collections;
import java.util.List;

/**
 * Created by tulsijain on 04/03/16.
 */
public class ChatHistoryAdapter extends  RecyclerView.Adapter<ChatHistoryAdapter.ChatHistoryViewHolder> {

    private LayoutInflater layoutInflater;
    List<ChatHistory> data = Collections.emptyList();
    Context c1;

    public ChatHistoryAdapter (Context c, List<ChatHistory> data){
        layoutInflater = layoutInflater.from(c);
        this.c1 =c;
        this.data = data;
    }



    @Override
    public ChatHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_chat_history, parent, false);
        ChatHistoryViewHolder chatHistoryViewHolder = new ChatHistoryViewHolder(view);
        return chatHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatHistoryViewHolder holder, int position) {
        final ChatHistory chatHistory = data.get(position);
        holder.serviceName.setText(chatHistory.getService());
        holder.serviceNameIcon.setImageResource(chatHistory.getServiceicon());
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public static class ChatHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        android.support.v7.widget.AppCompatImageView serviceNameIcon;
        android.support.v7.widget.AppCompatTextView serviceName;
        Context c;

        public ChatHistoryViewHolder(View itemView) {
            super(itemView);
            this.c = itemView.getContext();
            serviceNameIcon = (android.support.v7.widget.AppCompatImageView) itemView.findViewById(R.id.chat_history_icon);
            serviceName = (android.support.v7.widget.AppCompatTextView) itemView.findViewById(R.id.chat_history_servicename);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(c, ChatActivity.class);
            intent.putExtra(Constant.SERVICE, serviceName.getText());
            intent.putExtra(Constant.ISCHATHISTORYTOBELOADED, Constant.YES);
            c.startActivity(intent);
        }
    }

}
