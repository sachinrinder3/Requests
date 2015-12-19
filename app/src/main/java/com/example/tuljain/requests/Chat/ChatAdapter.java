//package com.example.tuljain.requests.Chat;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import com.example.tuljain.requests.R;
//
///**
// * Created by tuljain on 12/19/2015.
// */
//public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private int INCOMING = "INCOMING".hashCode();
//    private int OUTGOING = "OUTGOING".hashCode();
//    //private ChatPresenter presenter;
//
////    public ChatAdapter (ChatPresenter presenter) {
////        this.presenter = presenter;
////    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        if (viewType == INCOMING) {
//            View root = inflater.inflate(R.layout.send_chat, parent, false);
//            return new ChatViewHolder(root);
//        } else {
//            View root = inflater.inflate(R.layout.send_chat, parent, false);
//            return new ChatViewHolder(root);
//        }
//    }
//
////    @Override
////    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////        ChatMessage message = presenter.getMessageAt(position);
////        ((ChatViewHolder) holder).showMessageText(message.getText());
////    }
////
////    @Override
////    public int getItemViewType(int position) {
////        ChatMessage message = presenter.getMessageAt(position);
////        if (message instanceof IncomingChatMessage) {
////            return INCOMING;
////        } else {
////            return OUTGOING;
////        }
////    }
////
////    @Override
////    public int getItemCount() {
////        return presenter.getMessageCount();
////    }
//}
