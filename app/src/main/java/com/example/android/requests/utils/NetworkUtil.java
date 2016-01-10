package com.example.android.requests.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.v4.util.ArrayMap;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.requests.activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetworkUtil {
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
    private static final MediaType JSON = MediaType.parse(HTTP_HEADER_JSON + "; " +
            ENCODING);
    private static final MediaType URLENCODED = MediaType.parse(HTTP_HEADER_FORM_URLENCODED + "; " +
            ENCODING);
    public static final String UTF_8 = "UTF-8";
    public static final String ZERO = "0";
    public static final String MD5 = "MD5";
    protected static final String intialUrl = "http://192.168.0.7:3000/api/v0/";

    public static String userLogin(String email, String password){
        OkHttpClient client = new OkHttpClient();
        String uri = intialUrl + "login?email="+email+"&password="+password;
        Request request = new Request.Builder().url(uri).build();
        String message = "User does not Exits";
       // String message = "User Exits";
        Context applicationContext = MainActivity.getContextOfApplication();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            JsonElement jelement = new JsonParser().parse(response.body().string());
            JsonObject  jobject = jelement.getAsJsonObject();
            message = jobject.get("message").getAsString();
            //JSONObject json = new JSONObject(response.body().string());
            //message = json.getString("message");
            if (message.equals("User Exits")) {
                SharedPreferences sharepref = applicationContext.getSharedPreferences("MyPref", applicationContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString("loginStatus", "true");
                editor.putString("user_password", password);
                editor.putString("user_name", jobject.get("name").getAsString());
                editor.putString("user_email", jobject.get("email").getAsString());
                editor.putString("user_uuid", jobject.get("uuid").getAsString());
                editor.putString("user_phone", jobject.get("phone").getAsString());
                editor.commit();
                //Log.i(MainActivity.TAG,sharepref.getString("user_name", "no values") );
            }
            else if (message.equals("User does not Exits")){
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    };

//    public static <T> ServerResponse<T> doGetCall(String url, Class<T> responseClass) throws NetworkException {
//        Response response = getResponseForGetCall(url);
//
//        T responseObject = parseFromResponseToClass(response, responseClass);
//        return new ServerResponse<T>(responseObject, response.message(), response.code());
//    }
//    private static Response getResponseForGetCall(String url)  {
//
//    return getResponseForGetCall(url);
//    }
//    private static void  parseFromResponseToClass(Response response, Class<T> tClass) {
//        Gson gson = new Gson();
//        if (tClass == null) {
//            //tClass = (Class<T>) Object.class;
//        }
//
////     try {
////       return (gson.fromJson(response.body().charStream(), tClass));
//////        } catch (Exception e) {
////       //   throw wrapGsonExceptionIntoNetworkException(e);
//////        }
//    }
//
//    private static NetworkException wrapGsonExceptionIntoNetworkException(Exception e) {
//        String errorMessage = String.format("Exception occurred while parsing success " +
//                        "response from server through GSON: %s",
//                e.getMessage());
//        NetworkException.NetworkExceptionBuilder nBuilder
//                = new NetworkException.NetworkExceptionBuilder();
//        nBuilder.errorMessage(errorMessage);
//        nBuilder.httpStatusCode(HttpURLConnection.HTTP_OK);
//        nBuilder.messageFromServer(errorMessage);
//        return nBuilder.createNetworkException();
//    }
//
//    public static class NetworkException extends Exception {
//
//        public static final int HTTP_STATUS_NOT_AVAILABLE = -1;
//        private String errorMessage;
//        private int httpStatusCode;
//        private String messageFromServer;
//
//        private NetworkException(Throwable cause, int httpStatusCode, String errorMessage,
//                                 String messageFromServer) {
//            super(errorMessage, cause);
//            this.errorMessage = errorMessage;
//            this.httpStatusCode = httpStatusCode;
//            this.messageFromServer = messageFromServer;
//        }
//
//        @Override
//        public String getMessage() {
//            return errorMessage;
//        }
//
//        public String getMessageFromServer() {
//            return messageFromServer;
//        }
//
//        public int getHttpStatusCode() {
//            return httpStatusCode;
//        }
//
//        public static class NetworkExceptionBuilder {
//
//            private String errorMessage;
//            private int httpStatusCode = HTTP_STATUS_NOT_AVAILABLE;
//            private String messageFromServer;
//            private Throwable cause;
//
//            public NetworkExceptionBuilder() {
//            }
//
//            public NetworkExceptionBuilder errorMessage(String errorMessage) {
//                this.errorMessage = errorMessage;
//                return this;
//            }
//
//            public NetworkExceptionBuilder httpStatusCode(int httpStatusCode) {
//                this.httpStatusCode = httpStatusCode;
//                return this;
//            }
//
//            public NetworkExceptionBuilder messageFromServer(String messageFromServer) {
//                this.messageFromServer = messageFromServer;
//                return this;
//            }
//
//            public NetworkExceptionBuilder cause(Throwable th) {
//                this.cause = th;
//                return this;
//            }
//
//            public NetworkException createNetworkException() {
//                return new NetworkException(cause, httpStatusCode, errorMessage, messageFromServer);
//            }
//        }
//
//    }
//    public static String md5(final String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = MessageDigest.getInstance(MD5);
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuilder hexString = new StringBuilder();
//            for (byte aMessageDigest : messageDigest) {
//                String h = Integer.toHexString(0xFF & aMessageDigest);
//                while (h.length() < 2) {
//                    h = ZERO + h;
//                }
//                hexString.append(h);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    public static boolean isNetworkConnected() {
////        ConnectivityManager cm = (ConnectivityManager) MainThread.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
////        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
//        return false;
//    }


}
