package com.example.android.requests.utils;

import com.example.android.requests.models.Room;

import java.util.Map;

public interface RoomHost {
    void connectedToRoom(String roomTitle, String roomChannelForHereNow);
    void disconnectingFromRoom(String roomTitle);
    Map<String, Room> getCurrentRooms();

}