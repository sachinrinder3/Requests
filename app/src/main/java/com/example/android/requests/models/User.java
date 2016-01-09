package com.example.android.requests.models;

/**
 * Created by tuljain on 1/9/2016.
 */
public class User {
    private String name;
    private String email;
    private String phone;
    private String uuid;

    public User(String email, String name, String phone, String uuid) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
