package com.example.android.requests.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {

    public String chatmessage;

//    public Message(String chatmessage){
//
//        this.chatmessage = chatmessage;
//    }
//
//    public String getChatmessage() {
//        return chatmessage;
//    }
//
//    public void setChatmessage(String chatmessage) {
//
//        this.chatmessage = chatmessage;
//    }
//
//
//    public static Message[] createChatMessages(int numContacts) {
//        Message[] chatmessages1 = new Message[numContacts];
//
//        for (int i = 0; i < numContacts; i++) {
//            chatmessages1[i] = new Message("Person");
//        }
//
//        return chatmessages1;
//    }

    public String getUserId() {
        return getString("userId");
    }

    public String getBody() {
        return getString("body");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public void setBody(String body) {
        put("body", body);
    }
}