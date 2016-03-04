package com.example.android.requests.models;

/**
 * Created by tulsijain on 04/03/16.
 */
public class YourOrders {
    private String service;
    private String price;

    public String getPrice() {

        return price;
    }


    public YourOrders(String price, String service) {
        this.price = price;
        this.service = service;
    }

    public String getService() {
        return service;
    }
}
