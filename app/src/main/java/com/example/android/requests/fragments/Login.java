package com.example.android.requests.fragments;

import android.content.Context;
import com.facebook.CallbackManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.activities.FrontPage;

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
}
