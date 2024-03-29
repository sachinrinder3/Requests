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

    public static String userLogin(String email, String password){
        OkHttpClient client = new OkHttpClient();
        String uri = Constant.intialUrl + "login?"+Constant.EMAIL_ID+"="+email+"&"+Constant.PASSWORD+"="+password;
        Request request = new Request.Builder().url(uri).build();
        String message = Constant.USER_DOES_NOT_EXITS;
        //String message = Constant.USER_EXITS;
        Context applicationContext = MainActivity.getContextOfApplication();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            JsonElement jelement = new JsonParser().parse(response.body().string());
            JsonObject  jobject = jelement.getAsJsonObject();
            message = jobject.get(Constant.MESSAGE).getAsString();
            if (message.equals(Constant.USER_EXITS)) {
                SharedPreferences sharepref = applicationContext.getSharedPreferences("MyPref", applicationContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString(Constant.LOGINSTATUS, "true");
                editor.putString(Constant.PASSWORD, password);
                editor.putString(Constant.FIRST_NAME, jobject.get(Constant.FIRST_NAME).getAsString());
                editor.putString(Constant.EMAIL_ID, jobject.get(Constant.EMAIL_ID).getAsString());
                editor.putString(Constant.CONATCT_NUMBER, jobject.get(Constant.CONATCT_NUMBER).getAsString());
                editor.commit();

            }
            else if (message.equals(Constant.USER_DOES_NOT_EXITS)){
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
