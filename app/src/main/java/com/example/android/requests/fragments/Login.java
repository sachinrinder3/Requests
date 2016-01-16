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
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.FrontPage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    private OnFragmentInteractionListener mListener;

    @Override
    public String toString() {
        return super.toString();
    }

    public Login() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        AppCompatButton loginbtn = (AppCompatButton)v.findViewById(R.id.login);

        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            email = (AppCompatEditText)getView().findViewById(R.id.email_login);
                                            password = (AppCompatEditText)getView().findViewById(R.id.password_login);
                                            emailstring =  email.getText().toString();
                                            passwordstring = password.getText().toString();
                                            if (!emailstring.equals("") && !passwordstring.equals("") ){
                                                AsyncTaskRunner runner = new AsyncTaskRunner();
                                                runner.execute(emailstring,passwordstring);
                                            }
                                            else {
                                                Toast.makeText(getActivity(), "Please enter both email and password", Toast.LENGTH_LONG).show();
                                            }
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

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
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
            //super.onPostExecute();
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
// should never happen
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

    // private RequestQueue mRequestQueue;
    private void sendRegistrationIdToBackend() {
// Your implementation here.
        new SendGcmToServer().execute();
// Access the RequestQueue through your singleton class.
// AppController.getInstance().addToRequestQueue(jsObjRequest, "jsonRequest");
    }

    private class SendGcmToServer extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = Uti.register_url + "?name=" ;
                    //+
                    //editText_user_name.getText().toString() + "&email=" + editText_email.getText().toString() + "&regId=" + regid;
            Log.i("pavan", "url" + url);
            OkHttpClient client_for_getMyFriends = new OkHttpClient();
            ;
            String response = null;
//            try {
//                url = url.replace(" ", "%20");
//                response = callOkHttpRequest(new URL(url),
//                        client_for_getMyFriends);
//                for (String subString : response.split("<script", 2)) {
//                    response = subString;
//                    break;
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (result.equals("success")) {
                    storeUserDetails(getActivity());
                    startActivity(new Intent(getActivity(), ChatActivity.class));
                    //finish();
                } else {
                    Toast.makeText(getActivity(), "Try Again" + result, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Check net connection ", Toast.LENGTH_LONG).show();
            }
        }
//        String callOkHttpRequest(URL url, OkHttpClient tempClient)
//                throws IOException {
//            HttpURLConnection connection = tempClient.open(url);
//            connection.setConnectTimeout(40000);
//            InputStream in = null;
//            try {
//// Read the response.
//                in = connection.getInputStream();
//                byte[] response = readFully(in);
//                return new String(response, "UTF-8");
//            } finally {
//                if (in != null)
//                    in.close();
//            }

        }
        byte[] readFully(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int count; (count = in.read(buffer)) != -1;) {
                out.write(buffer, 0, count);
            }
            return out.toByteArray();
        }

 
}
