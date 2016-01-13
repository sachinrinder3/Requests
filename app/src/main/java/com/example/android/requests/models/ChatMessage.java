package com.example.android.requests.models;

/**
 * Created by tuljain on 12/20/2015.
 */
public class ChatMessage {
    public String chatmessage;

    public ChatMessage(String chatmessage){

        this.chatmessage = chatmessage;
    }

    public String getChatmessage() {
        return chatmessage;
    }

    public void setChatmessage(String chatmessage) {

        this.chatmessage = chatmessage;
    }


    public static ChatMessage[] createChatMessages(int numContacts) {
        ChatMessage[] chatmessages1 = new ChatMessage[numContacts];

        for (int i = 0; i < numContacts; i++) {
            chatmessages1[i] = new ChatMessage("Person");
        }

        return chatmessages1;
    }
}
