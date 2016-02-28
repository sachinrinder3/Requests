package com.example.android.requests.models;

import android.graphics.drawable.Drawable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tuljain on 12/20/2015.
 */
public class ChatMessage {

    public static final String DEVICETAG = "deviceTag";
    public static final String TYPE = "type";
    public static final String FROM = "from";
    public static final String SENTON = "sentOn";
    public static final String EMOTICON = "emoticon";
    public static final String MESSAGECONTENT = "messageContent";
    public static final String MESSAGEID = "msgId";
    public static final String SENDERUUID = "uuid"; //the uuid of the sender
    public static final String MTYPE_PRIVATE_CHAT_REQUEST = "pcr";
    public static final String CHATTMESSAGE = "chattmessage";
    public static final String INCOMING = "incoming";
    public static final String OUTGOING = "outgoing";
    public static final String SERVICE = "service";



    private String type;
    private String messageContent;
    private String from;
    private Date sentOn;
    private String deviceTag;
    private String emoticon;
    private Drawable avatarImage;
    private String senderUUID;
    public String chatmessage;
    public String outgoing;
    public String incoming;
    public String service;



    private ChatMessage(){

    }

    public static ChatMessage create() {

        ChatMessage c = new ChatMessage();
        c.setType(CHATTMESSAGE);
        return c;
    }

    public static ChatMessage create(JSONObject obj, String timeToken) throws Exception {
        ChatMessage message = new ChatMessage();
        //String deviceTag = obj.getString(DEVICETAG);
        String type = obj.getString(TYPE);
        String Service = obj.getString(SERVICE);
        String incoming = obj.getString(INCOMING);
        String outgoing = obj.getString(OUTGOING);
        //String from = obj.getString(FROM);
        //String emoticon = obj.getString(EMOTICON);
        String messageContent = obj.getString(MESSAGECONTENT);
        //String uuid = obj.getString(SENDERUUID);

        //message.setDeviceTag(deviceTag);
        message.setincoming(incoming);
        message.setoutgoing(outgoing);
        message.setType(type);
        //message.setFrom(from);
        message.setSentOn(new Date());
        message.setservice(Service);
        //message.setSenderUUID(uuid);
        //message.setEmoticon(emoticon);
        message.setMessageContent(messageContent);

        return message;
    }




    public void setincoming(String inconing) {

        this.incoming = inconing;
    }
    public String getincoming() {

        return incoming;
    }
    public void setoutgoing(String outgoing) {

        this.outgoing = outgoing;
    }
    public String getoutgoing() {

        return outgoing;
    }

    public void setservice(String service) {

        this.service = service;
    }
    public String getservice() {

        return service;
    }




    public String getMessageContent() {

        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getFrom() {

        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getSentOn() {
        return sentOn;
    }

    public void setSentOn(Date sentOn) {
        this.sentOn = sentOn;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public String getType() {
        return type;
    }

    public String getSenderUUID() {
        return senderUUID;
    }

    public void setSenderUUID(String senderUUID) {
        this.senderUUID = senderUUID;
    }

    public Drawable getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(Drawable avatarImage) {
        this.avatarImage = avatarImage;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ChatMessage(String messageContent, String outgoing, String incmoing){

        this.messageContent = messageContent;
        this.incoming = incmoing;
        this.outgoing = outgoing;
    }


    public String getChatmessage() {
        return messageContent;
    }

    public void setChatmessage(String messageContent) {

        this.messageContent = messageContent;
    }
}
