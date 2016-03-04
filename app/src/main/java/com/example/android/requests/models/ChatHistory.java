package com.example.android.requests.models;


public class ChatHistory {

    private String service;
    private int serviceicon;


    public ChatHistory(String service, int serviceicon) {
        this.service = service;
        this.serviceicon = serviceicon;
    }

    public String getService() {

        return service;
    }

    public int getServiceicon() {

        return serviceicon;
    }

}
