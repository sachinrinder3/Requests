package com.example.android.requests.utils;
import com.squareup.okhttp.MediaType;

public class Constant {
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String LOGINSTATUS = "loginstatus";
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public final static String SENDER_ID = "32412174253";
    public static final String intialUrl = "http://192.168.0.5:3000/api/v0/";
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
    public static final String TAG = "TAG";



    public static final String EMAIL_ID = "email_id";
    public static final String CONATCT_NUMBER = "contact_number";
    public static final String PASSWORD = "password";
    public static final String MESSAGE = "message";
    public static final String FIRST_NAME = "first_name";
    public static final String USER_DOES_NOT_EXITS = "User does not Exits";
    public static final String USER_EXITS = "User Exits";

    public static final String TRUE= "true";
    public static final String FALSE = "false";

    public static final String SERVICE = "Service";
    public static final String HOME_SERVICES = "HomeServices";
    public static final String SHOPPING = "Shopping";
    public static final String CABS = "Cabs";
    public static final String TRAVEL = "Travel";
    public static final String RECHARGE = "Recharge";
    public static final String FOOD = "Food";

    public static final String ISCHATHISTORYTOBELOADED = "IsChatHistoryToBeLoaded";
    public static final String YES = "Yes";
    public static final String NO = "No";

}
