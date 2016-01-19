package com.example.android.requests.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.requests.R;
import com.example.android.requests.chat.ChatViewHolder;
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
        View v;
        if (viewType ==1 ){
             v= layoutInflater.from(viewGroup.getContext()).inflate(R.layout.send_message_card, viewGroup, false);
        }
        else {
             v = layoutInflater.from(viewGroup.getContext()).inflate(R.layout.receive_message_card, viewGroup, false);
        }
        ChatViewHolder chatViewHolder = new ChatViewHolder(v );

        return chatViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ChatMessage chatMessage = chatList.get(position);
        ((ChatViewHolder)holder).showMessageText(chatMessage.chatmessage);
//        ((ChatViewHolder) holder).chatmessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                removeItem(position);
//                ChatMessage hey = new ChatMessage("jhhj");
//
//                addItem(getItemCount(), hey);
//            }
//        });
        ((ChatViewHolder) holder).showMessageText(chatMessage.getChatmessage());
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatList.get(position);
        String incoming = message.incmoing;
        String outgoing = message.outgoing;
        if(incoming.equals("N") && outgoing.equals("Y")){
            return 1;
        }
        else {
            return 2;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return chatList.size();
    }

    public void removeItem(int position) {
        chatList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, ChatMessage viewModel) {
        chatList.add(position, viewModel);
        Log.i("TAG", "ADD ITEM");
        notifyItemInserted(position);
    }

    public void updateData(List<ChatMessage> viewModels) {
        chatList.clear();
        chatList.addAll(viewModels);
        notifyDataSetChanged();
    }


}