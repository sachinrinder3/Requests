package com.example.android.requests.fragments;

import android.content.Context;

import com.example.android.requests.activities.ChatActivity;
import com.example.android.requests.activities.MainActivity;
import com.example.android.requests.utils.Uti;
import com.facebook.CallbackManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.FrontPage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Login extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CallbackManager callbackManager;
    com.facebook.login.widget.LoginButton loginButton;

    private String mParam1;
    private String mParam2;
    private AppCompatEditText email;
    private AppCompatEditText password;
    private String emailstring;
    private String passwordstring;
    GoogleCloudMessaging gcm;
    String regid;
    String msg;

    private OnFragmentInteractionListener mListener;

    @Override
    public String toString() {
        return super.toString();
    }

    public Login() {
    }
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        AppCompatButton loginbtn = (AppCompatButton)v.findViewById(R.id.login);
        Log.i("TAG", String.valueOf(checkPlayServices()));

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getActivity());
            regid = getRegistrationId(getActivity());
            if (regid.isEmpty()) {
                Log.i("TAG", "empty regid");
                registerInBackground();
            }
        } else {
            Log.i("pavan", "No valid Google Play Services APK found.");
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            email = (AppCompatEditText)getView().findViewById(R.id.email_login);
                                            password = (AppCompatEditText)getView().findViewById(R.id.password_login);
                                            emailstring =  email.getText().toString();
                                            passwordstring = password.getText().toString();
                                            sendRegistrationIdToBackend();
//                                            if (!emailstring.equals("") && !passwordstring.equals("") ){
//                                                AsyncTaskRunner runner = new AsyncTaskRunner();
//                                                runner.execute(emailstring,passwordstring);
//                                            }
//                                            else {
//                                                Toast.makeText(getActivity(), "Please enter both email and password", Toast.LENGTH_LONG).show();
//                                            }


//                                            Runnable r = new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    user();
//                                                }
//                                            };
//                                            Thread loginThread = new Thread(r);
//                                            loginThread.start();
//                                            new Thread(new Runnable() {
//                                                @Override
//                                                public void run() {
//
//                                                    user();
//                                                }
//                                            }).start();
//                                            Intent intentlogin = new Intent(getActivity(), FrontPage.class);
//                                            startActivity(intentlogin);

                                        }
                                    }
        );
        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Async is called", Toast.LENGTH_LONG).show();
        }

        public AsyncTaskRunner() {

        }
        Context context = getActivity();

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            if (result.equals("User Exits")){
                SharedPreferences sharepref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                Toast.makeText(getActivity(), "You are successfully logged-in", Toast.LENGTH_LONG).show();
                Intent intentlogin = new Intent(getActivity(), FrontPage.class);
                startActivity(intentlogin);
            }
            else if (result.equals("User does not Exits")){
                Toast.makeText(getActivity(), "Please Enter Valid Credentials", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity(), "You can not logged-in hold on", Toast.LENGTH_LONG).show();
            }
        }

//        @Override
//        protected void onProgressUpdate(Void) {
//            super.onProgressUpdate(values);
//        }

        @Override
        protected String doInBackground(String...params) {
            String username = params[0];
            String password = params[1];
            String result = com.example.android.requests.utils.NetworkUtil.userLogin(username, password);
            return result;
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        //int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        int resultCode = api.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                //GooglePlayServicesUtil.getErrorDialog(resultCode, this, Uti.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                GoogleApiAvailability.getInstance().getErrorString(resultCode);
            } else {
                Log.i(MainActivity.TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String registrationId = prefs.getString(Uti.PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(MainActivity.TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(Uti.PROPERTY_APP_VERSION, Integer.MIN_VALUE);

        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(MainActivity.TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private boolean isUserRegistered(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String User_name = prefs.getString(Uti.USER_NAME, "");
        if (User_name.isEmpty()) {
            Log.i(MainActivity.TAG, "Registration not found.");
            return false;
        }
        return true;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(MainActivity.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Uti.PROPERTY_REG_ID, regId);
        editor.putInt(Uti.PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private void storeUserDetails(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(MainActivity.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Uti.EMAIL, email.getText().toString());
        //editor.putString(Uti.USER_NAME, editText_user_na.getText().toString());
        editor.commit();
    }

    private void sendRegistrationIdToBackend() {
        new SendGcmToServer().execute();
    }

    private class SendGcmToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            String uri = "http://192.168.0.5:3000/api/v0/register?register_id=" + regid + "&email=" + emailstring;
            Request request = new Request.Builder().url(uri).build();
            String message = "";
            try {
                Call call = client.newCall(request);
                Response response = call.execute();
                JsonElement jelement = new JsonParser().parse(response.body().string());
                JsonObject jobject = jelement.getAsJsonObject();
                message = jobject.get("message").getAsString();
                Log.i("TAG", message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (result.equals("success")) {
                    storeUserDetails(getActivity());
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), FrontPage.class));
                } else {
                    Toast.makeText(getActivity(), "Try Again" + result, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Check net connection ", Toast.LENGTH_LONG).show();
            }
        }

        byte[] readFully(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1; ) {
                out.write(buffer, 0, count);
            }
            return out.toByteArray();
        }
    }


    private void registerInBackground() {
        new AsyncTask() {
            @Override
            protected String doInBackground(Object[] params) {
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getActivity());
                    }
                    regid = gcm.register(Uti.SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    storeRegistrationId(getActivity(), regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                Log.i("TAG", msg);
               return msg;
            }
        }.execute();
    }
}
