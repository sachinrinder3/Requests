package com.example.android.requests.fragments;

import android.content.Context;
import com.facebook.CallbackManager;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.FrontPage;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;


public class Login extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CallbackManager callbackManager;
    OkHttpClient client = new OkHttpClient();
    TextView email;
    com.facebook.login.widget.LoginButton loginButton;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
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
        Button loginbtn = (Button)v.findViewById(R.id.login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    user();
                                                }
                                            }).start();
                                            Intent intentlogin = new Intent(getActivity(), FrontPage.class);
                                            startActivity(intentlogin);

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
    public void user (){


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

        } else {

        }
        String uri = "http://10.1.22.240:3000/users/login?email=geet.com&password=geet";
        Request request = new Request.Builder().url(uri).build();
        try {
            Log.i("Geetika","1");

            Call call = client.newCall(request);

            Log.i("Geetika", "2");

            Response response = call.execute();

            Log.i("Geetika", "3");
            String hey = response.body().string();
            JSONObject json = new JSONObject(hey);
            final String owner = json.getString("message");
            System.out.println(hey);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
