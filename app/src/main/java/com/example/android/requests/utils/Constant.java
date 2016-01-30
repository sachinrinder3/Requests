package com.example.android.requests.utils;

import com.squareup.okhttp.MediaType;

public class Constant {
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";

    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String UUID = "uuid";
    public static final String LOGINSTATUS = "loginstatus";
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public final static String SENDER_ID = "711149879456";
    public static final String intialUrl = "http://192.168.100.2:3000/api/v0/";
    public static final String HEADER_APP_NAME = "app_name";
    public static final String HEADER_APP_VERSION = "app_version";
    public static final String HEADER_LOGIN_AUTH_TOKEN = "login_auth_token";
    public static final String CONTENT_TYPE_TAG = "Content-Type";
    public static final String HTTP_HEADER_JSON = "application/json";
    public static final String HTTP_HEADER_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String ENCODING = "charset=utf-8";
    public static final long CONNECTION_TIMEOUT_GET_VALUE = 15;
    public static final long READ_TIMEOUT_GET_VALUE = 15;
    public static final long CONNECTION_TIMEOUT_POST_VALUE = 20;
    public static final long READ_TIMEOUT_POST_VALUE = 20;
    private static final MediaType JSON = MediaType.parse(HTTP_HEADER_JSON + "; " + ENCODING);
    private static final MediaType URLENCODED = MediaType.parse(HTTP_HEADER_FORM_URLENCODED + "; " + ENCODING);
    public static final String UTF_8 = "UTF-8";
    public static final String ZERO = "0";
    public static final String MD5 = "MD5";
}
