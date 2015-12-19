package com.example.tuljain.requests.Chat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.tuljain.requests.R;

/**
 * Created by tuljain on 12/19/2015.
 */
public class ChatViewHolder extends RecyclerView.ViewHolder  {


    @InjectView(R.id.SingleMessage)
    TextView messageText;

    public ChatViewHolder(View view) {
        super(view);
        ButterKnife.inject(this, view);
    }

    public void showMessageText(String text) {
        messageText.setText(text);
    }
}
