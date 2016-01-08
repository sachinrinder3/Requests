package com.example.android.requests.utils;

/**
 * Created by tuljain on 1/8/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
public class SharedPreferencesUtil implements KeyValueStore {

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public Long getLong(String key) {
        return null;
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return 0;
    }

    @Override
    public boolean isUserLoggedIn() {
        return false;
    }

    @Override
    public boolean put(String key, boolean value) {
        return false;
    }

    @Override
    public boolean put(String key, Long value) {
        return false;
    }

    @Override
    public boolean put(String key, String value) {
        return false;
    }

    @Override
    public boolean remove(String key) {
        return false;
    }
}
