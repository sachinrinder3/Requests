package com.example.android.requests.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.FrontPage;
import com.example.android.requests.activities.MainActivity;
import com.example.android.requests.utils.Constant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    private AppCompatEditText email;
    private String emailString;
    private AppCompatEditText name;
    private String nameString;
    private AppCompatEditText password;
    private String passwordString;
    private AppCompatEditText phone;
    private String phoneString;
    private AppCompatButton regisbtn;
    GoogleCloudMessaging gcm;
    String regid;
    String msg;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        email = (AppCompatEditText)v.findViewById(R.id .email_register);
        password = (AppCompatEditText)v.findViewById(R.id .password_register);
        phone = (AppCompatEditText)v.findViewById(R.id .phonenumber_register);
        name = (AppCompatEditText)v.findViewById(R.id .name_register);
        regisbtn = (AppCompatButton)v.findViewById(R.id.register);

        if (checkPlayServices()) {
//            Log.i("TAG", "I AM CALLED HERE");
//            //gcm = GoogleCloudMessaging.getInstance(getActivity());
//            regid = getRegistrationId(getActivity());
//            Log.i("TAG", regid);
//            if (regid.isEmpty()) {
//                Log.i("TAG", "empty regid");
//                registerInBackground();
//            }
        } else {
            Log.i("TAG", "No valid Google Play Services APK found.");
        }
        regisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = email.getText().toString();
                passwordString = password.getText().toString();
                phoneString = phone.getText().toString();
                nameString = name.getText().toString();
                final SharedPreferences prefs = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                String registrationId = prefs.getString(Constant.PROPERTY_REG_ID, "");
                registrationId="hey";
                if (!registrationId.equals("")){
                    sendRegistrationIdToBackend();
                }
                else {
                    Log.i("TAG", "YOU ARE NOT REGISTERED SUCCESSFULLY AS Registration key is not received by the ");
                }

            }
        });
        return  v;
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

    private boolean checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        //int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        int resultCode = api.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                //GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
                GoogleApiAvailability.getInstance().getErrorString(resultCode);
            } else {
                Log.i(MainActivity.TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }

//    private String getRegistrationId(Context context) {
//        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(Constant.PROPERTY_REG_ID, "");
//        editor.commit();
//        String registrationId = prefs.getString(Constant.PROPERTY_REG_ID, "");
//        Log.i("TAG", "REQID"+registrationId);
//        if (registrationId.isEmpty()) {
//            Log.i("TAG", "REQID is emtty"+registrationId);
//            Log.i(MainActivity.TAG, "Registration not found.");
//            return "";
//        }
//        int registeredVersion = prefs.getInt(Constant.PROPERTY_APP_VERSION, Integer.MIN_VALUE);
//        int currentVersion = getAppVersion(context);
//        if (registeredVersion != currentVersion) {
//            Log.i("TAG", "REQID is emxsxsxsdxtty"+registrationId);
//            Log.i(MainActivity.TAG, "App version changed.");
//            return "";
//        }
//        return registrationId;
//    }
    private void storeUserDetails(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        //int appVersion = getAppVersion(context);
        //Log.i(MainActivity.TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.EMAIL_ID, email.getText().toString());
        editor.putString(Constant.FIRST_NAME, name.getText().toString());
        editor.putString(Constant.CONATCT_NUMBER, phone.getText().toString());
        editor.putString(Constant.PASSWORD, password.getText().toString());
        editor.commit();
    }


    private class SendGcmToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            String uri = Constant.intialUrl+"?"+Constant.FIRST_NAME+"="+nameString+"&"+Constant.EMAIL_ID+"="+emailString+"&"+Constant.CONATCT_NUMBER+"="+phoneString+"&"+Constant.PASSWORD+"="+passwordString;
            Log.i("TAG", uri);
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
                if (result.equals("Success")) {
                    storeUserDetails(getActivity());
                    Toast.makeText(getActivity(), "You have been successfully registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(), FrontPage.class));
                } else {
                    Toast.makeText(getActivity(), "Trytrytry Again" + result, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Check net connection it has some problem", Toast.LENGTH_LONG).show();
            }
        }
    }


//    private void registerInBackground() {
//        new AsyncTask() {
//            @Override
//            protected String doInBackground(Object[] params) {
////                try {
////                    if (gcm == null) {
////                        gcm = GoogleCloudMessaging.getInstance(getActivity());
////                    }
////                    regid = gcm.register(Constant.SENDER_ID);
////                    Log.i("TAG", "YO MAN"+ regid);
////                    msg = "Device registered, registration ID=" + regid;
////                    storeRegistrationId(getActivity(), regid);
////                    SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
////                    Log.i(MainActivity.TAG, sharepref.getString(Constant.PROPERTY_REG_ID, "no values"));
////                    Log.i(MainActivity.TAG, regid);
////                } catch (IOException ex) {
////                    msg = "Error :" + ex.getMessage();
////                }
//                Log.i("TAG", msg);
//                return msg;
//            }
//        }.execute();
    //}

//    private void storeRegistrationId(Context context, String regId) {
//        final SharedPreferences prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        int appVersion = getAppVersion(context);
//        Log.i(MainActivity.TAG, "Saving regId on app version " + appVersion);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(Constant.PROPERTY_REG_ID, regId);
//        editor.putInt(Constant.PROPERTY_APP_VERSION, appVersion);
//        editor.commit();
//
//    }

    private void sendRegistrationIdToBackend() {
        new
                SendGcmToServer().execute();
    }

//    private static int getAppVersion(Context context) {
//        try {
//            PackageInfo packageInfo = context.getPackageManager()
//                    .getPackageInfo(context.getPackageName(), 0);
//            return packageInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            throw new RuntimeException("Could not get package name: " + e);
//        }
//    }

}
