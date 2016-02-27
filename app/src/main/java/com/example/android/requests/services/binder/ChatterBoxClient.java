package com.example.android.requests.services.binder;

import android.os.Binder;
import android.util.Log;

import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.services.ChatterBoxCallback;
import com.example.android.requests.services.ChatterBoxService;
import com.example.android.requests.utils.Constant;
import com.pubnub.api.Callback;
import com.pubnub.api.PubnubError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChatterBoxClient extends Binder {


    private ChatterBoxService chatterBoxService;

    public ChatterBoxClient(ChatterBoxService service) {

        chatterBoxService = service;
    }


    ChatterBoxClient getService() {

        return ChatterBoxClient.this;
    }

    public void publish(final String channel, ChatMessage message) {
        try {
            JSONObject messageJSON = new JSONObject();
            //Log.i("TAG", "yahJHJB JH Ju");
            //messageJSON.put(ChatterBoxMessage.DEVICETAG, message.getDeviceTag());
            messageJSON.put(ChatMessage.SENDERUUID, chatterBoxService.getPubNub().getUUID()); //Set the uuid
            //messageJSON.put(ChatterBoxMessage.EMOTICON, "");
            messageJSON.put(ChatMessage.FROM, message.getFrom());
            messageJSON.put(ChatMessage.SERVICE, message.getservice());
            messageJSON.put(ChatMessage.INCOMING, message.getincoming());
            messageJSON.put(ChatMessage.OUTGOING, message.getoutgoing());
            messageJSON.put(ChatMessage.SENTON, new Date().getTime());
            messageJSON.put(ChatMessage.TYPE, message.getType());
            messageJSON.put(ChatMessage.MESSAGECONTENT, message.getMessageContent());
            messageJSON.put(ChatMessage.SENDERUUID, chatterBoxService.getCurrentUserEmailID());

            chatterBoxService.getPubNub().publish(channel, messageJSON, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    //Log.i("TAG", channel);
                    //Log.i("TAG", "channel");
                    List<ChatterBoxCallback> listeners = chatterBoxService.getListeners().get(channel);
                    //String status = "";
                    Log.i("TAG", String.valueOf(listeners));
                    String timeToken = "";
                    //String resultCode = "";
                    try {
                        JSONArray results = (JSONArray) message;
                        timeToken = results.getString(2);
                    } catch (JSONException e) {
                        Log.i(Constant.TAG, "Exception while attempting to process publish results.");
                    }

                    for (ChatterBoxCallback chatterBoxCallback : listeners) {
                        chatterBoxCallback.onMessagePublished(timeToken);
                    }
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    Log.i("TAG", "ERROR SENDING MESSAGES");
                    Log.i("TAG", String.valueOf(error));
                    List<ChatterBoxCallback> listeners = chatterBoxService.getListeners().get(channel);
                    for (ChatterBoxCallback chatterBoxCallback : listeners) {
                        chatterBoxCallback.onError(error.getErrorString());
                    }
                }

            });

        } catch (Exception e) {
            Log.i(Constant.TAG, "exception while publishing message", e);
        }
    }

    public void addRoom(final ChatterBoxCallback listener){


            int i =0;


            List<ChatterBoxCallback> l = null;

            if (!chatterBoxService.getListeners().containsKey("Food")) {
                l = new ArrayList<>();
            } else {
                l = chatterBoxService.getListeners().get("Food");
            }

            l.add(listener); //add the listener for this room.
            i = chatterBoxService.getListeners().size();
            Log.i("TAG", ""+i);
            chatterBoxService.getListeners().put("Food", l);
            l = null;
//            //Log.i("TAG", chatterBoxService.getListeners().containsKey("Food")+"");
            if (!chatterBoxService.getListeners().containsKey("Shopping")) {
                l = new ArrayList<>();
            } else {
                l = chatterBoxService.getListeners().get("Shopping");
            }

            l.add(listener); //add the listener for this room.

            chatterBoxService.getListeners().put("Shopping", l);
            l = null;
            //Log.i("TAG", chatterBoxService.getListeners().containsKey("Food")+"");
            if (!chatterBoxService.getListeners().containsKey("HomeServices")) {
                l = new ArrayList<>();
            } else {
                l = chatterBoxService.getListeners().get("HomeServices");
            }

            l.add(listener); //add the listener for this room.

            chatterBoxService.getListeners().put("HomeServices", l);
    }

    public void addRoom(final String roomName, final ChatterBoxCallback listener, String gcm_id) {

        if (chatterBoxService.isConnected()) {
            //Log.i("TAG", "CONNECTED");
            boolean bfound = false;
            String[] currentChannels = chatterBoxService.getPubNub().getSubscribedChannelsArray();
            chatterBoxService.getPubNub().enablePushNotificationsOnChannel(roomName, gcm_id, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    Log.i("TAG", message.toString());
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    Log.i("TAG", "EFERBCMC");
                }
            });
            int i =0;
            //Log.i("TAG", String.valueOf(currentChannels.length));
            for (String c : currentChannels) {
                Log.i("TAG", c);
                i=i+1;
                if (c.equals(roomName)) {
                    bfound = true;
                    break;
                }
            }
            //Log.i("TAG", String.valueOf(!bfound));
            if (!bfound) {
                try {
                    chatterBoxService.getPubNub().subscribe(roomName, new Callback() {
                        @Override

                        public void successCallback(String channel, Object message, String timetoken) {
                            try {
                                if (message instanceof JSONObject) {
                                    JSONObject jmessage = (JSONObject) message;
                                    String messageType = jmessage.getString(ChatMessage.TYPE);
                                    if (messageType.equals("chattmessage")) {
                                        Log.i("TAG", "MESSAGE HAS BEEN RECEIVED");
                                        ChatMessage msg = ChatMessage.create(jmessage, timetoken);
                                        List<ChatterBoxCallback> thisRoomListeners = chatterBoxService.getListeners().get(roomName);
                                        Log.i("TAG", String.valueOf(thisRoomListeners));
                                        for (ChatterBoxCallback l : thisRoomListeners) {
//                                            if (i<1) {
                                            Log.i("TAG", "INSIDE FOR LOOP");
                                                l.onMessage(msg);
//                                            }
//                                            i=1;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.i(Constant.TAG, "Exception while processing message", e);
                            }
                        }

                        @Override
                        public void connectCallback(String channel, Object message) {

                            //history(channel, );
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError err) {
                            Log.i(Constant.TAG, "error processing messages" + err.toString());

                        }
                    });

                    List<ChatterBoxCallback> l;
                    if (!chatterBoxService.getListeners().containsKey(roomName)) {
                        l = new ArrayList<>();
                    }
                    else {
                        l = chatterBoxService.getListeners().get(roomName);
                    }

                    l.add(listener); //add the listener for this room.
                    chatterBoxService.getListeners().put(roomName, l);
                    Log.i("TAG",chatterBoxService.getListeners().size()+"");

                    //Set up the Presence on this Room
                    //chatterBoxService.getPubNub().presence(roomName, new PresenceCallback(l, chatterBoxService.getPubNub(), chatterBoxService.getPresenceCache()));


                    //Grab the last 10 messages
//                    List<ChatMessage> historyMessages = history(roomName, -1, -1, 50);
//
//                    for (ChatMessage m : historyMessages) {
//                        for (ChatterBoxCallback c : l) {
//                            c.onMessage(m);
//                        }
//                    }


                } catch (Exception e) {
                    Log.i(Constant.TAG, "exception while adding subscription", e);

                }

            }

//            List<ChatterBoxCallback> l = null;
//
//            if (!chatterBoxService.getListeners().containsKey("Food")) {
//                l = new ArrayList<>();
//            } else {
//                l = chatterBoxService.getListeners().get("Food");
//            }
//
//            l.add(listener); //add the listener for this room.
//             i = chatterBoxService.getListeners().size();
//            Log.i("TAG", ""+i);
//            chatterBoxService.getListeners().put("Food", l);
//           l = null;
////            //Log.i("TAG", chatterBoxService.getListeners().containsKey("Food")+"");
//            if (!chatterBoxService.getListeners().containsKey("Shopping")) {
//                l = new ArrayList<>();
//            } else {
//                l = chatterBoxService.getListeners().get("Shopping");
//            }
//
//            l.add(listener); //add the listener for this room.
//
//            chatterBoxService.getListeners().put("Shopping", l);
//            l = null;
//            //Log.i("TAG", chatterBoxService.getListeners().containsKey("Food")+"");
//            if (!chatterBoxService.getListeners().containsKey("HomeServices")) {
//                l = new ArrayList<>();
//            } else {
//                l = chatterBoxService.getListeners().get("HomeServices");
//            }
//
//            l.add(listener); //add the listener for this room.
//
//            chatterBoxService.getListeners().put("HomeServices", l);

        }
    }


    public void removeRoomListener(String roomName, ChatterBoxCallback listener) {
        Map<String, List<ChatterBoxCallback>> rooms = chatterBoxService.getListeners();
        if (rooms.containsKey(roomName)) {
            List<ChatterBoxCallback> roomListeners = rooms.get(roomName);
            roomListeners.remove(listener);
        }
    }


    public void leaveRoom(String roomName) {

        Map<String, List<ChatterBoxCallback>> rooms = chatterBoxService.getListeners();
        if (rooms.containsKey(roomName)) {
            List<ChatterBoxCallback> roomListeners = rooms.get(roomName);
            roomListeners.clear();
            chatterBoxService.getPubNub().unsubscribe(roomName);
        }
    }


    public boolean isConnected() {

        return chatterBoxService.isConnected();
    }


    public boolean connect(String emailid) {

        if (!emailid.equals("")) {
            chatterBoxService.setCurrentUserEmailID(emailid);
        }

        return chatterBoxService.isConnected();
    }



}
