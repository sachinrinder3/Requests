package com.example.android.requests.services;

import com.example.android.requests.models.ChatMessage;

/**
 * Created by tulsijain on 12/02/16.
 */
public interface ChatterBoxCallback {

    void onMessage(ChatMessage message);

    void onMessagePublished(String message);

    //void onPresence(ChatterBoxPresenceMessage pmessage);

    void onHeartBeat(boolean error);

    void onError(String e);

    //void onPrivateChatRequest(ChatterBoxPrivateChatRequest request);

    void onDisconnect();

    void onConnect();


}
