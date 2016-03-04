package com.example.android.requests.models;

import com.example.android.requests.R;

public enum CustomPageEnum {

    RED(R.string.your_order, R.layout.receive_message_card),
    BLUE(R.string.wallet, R.layout.send_message_card),
    ORANGE(R.string.chat_message_hint, R.layout.receive_message_card);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPageEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}