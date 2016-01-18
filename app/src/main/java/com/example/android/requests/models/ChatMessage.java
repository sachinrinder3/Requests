package com.example.android.requests.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuljain on 12/20/2015.
 */
public class ChatMessage {
    public String chatmessage;
    public String outgoing;
    public String incmoing;

    public ChatMessage(String chatmessage, String outgoing, String incmoing){

        this.chatmessage = chatmessage;
        this.incmoing = incmoing;
        this.outgoing = outgoing;
    }

    public String getChatmessage() {
        return chatmessage;
    }

    public void setChatmessage(String chatmessage) {

        this.chatmessage = chatmessage;
    }


//    public static List<ChatMessage> createChatMessages(int numContacts) {
//        List<ChatMessage> data = new ArrayList<>();
//
//        for (int i = 0; i < numContacts; i++) {
//            ChatMessage address = new ChatMessage("rahul whats up bcodnbckwdsxsxdwx kpckmdcmd dcmdwklmckldwc");
//            data.add(address);
//        }
//
//        return data;
//    }
}
