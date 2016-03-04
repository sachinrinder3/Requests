package com.example.android.requests.models;

/**
 * Created by tuljain on 1/15/2016.
 */
public class Address {
    private int id;
    private   String flatno;
    private   String nearby;
    private   String location;

    public Address(String flatno, int id, String location, String nearby) {
        this.flatno = flatno;
        this.id = id;
        this.location = location;
        this.nearby = nearby;
    }

    public String getFlatno() {
        return flatno;
    }


    public int getId() {
        return id;
    }


    public String getLocation() {
        return location;
    }


    public String getNearby() {
        return nearby;
    }

}
