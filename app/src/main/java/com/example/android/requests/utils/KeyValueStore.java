package com.example.android.requests.utils;

/**
 * Created by tuljain on 1/9/2016.
 */
public interface KeyValueStore  {

        boolean put(String key, String value);

        boolean put(String key, boolean value);

        boolean put(String key, Long value);

        String get(String key);

        boolean getBoolean(String key);

        Long getLong(String key);

        long getLong(String key, long defaultValue);

        boolean remove(String key);

        boolean isUserLoggedIn();

}
