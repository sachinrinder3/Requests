package com.example.android.requests.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import com.example.android.requests.activities.MainActivity;

import com.example.android.requests.R;
import com.example.android.requests.utils.ProfileUtil;
import com.google.gson.JsonObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditableProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditableProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditableProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button save_button;
    private  AppCompatEditText email;
    private AppCompatEditText name;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EditableProfile() {
        // Required empty public constructor
    }

    public static EditableProfile newInstance(String param1, String param2) {
        EditableProfile fragment = new EditableProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editable_profile, container, false);
        save_button = (Button)v.findViewById(R.id.save_button);
        name = (AppCompatEditText)v.findViewById(R.id.user_name);
        email = (AppCompatEditText)v.findViewById(R.id.user_email);
        SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        name.setText(sharepref.getString("user_name", "nhi aaya"));
        email.setText(sharepref.getString("user_email", "nhi aaya"));
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (AppCompatEditText)getView().findViewById(R.id.user_email);
                name = (AppCompatEditText)getView().findViewById(R.id.user_name);
                String emailString =  email.getText().toString();
                String namestring = name.getText().toString();
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(emailString,namestring);

            }
        });
        return v;

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, JsonObject> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getActivity(), "Async is called", Toast.LENGTH_LONG).show();
        }
        public AsyncTaskRunner() {

        }
        Context context = MainActivity.getContextOfApplication();

        @Override
        protected void onPostExecute(JsonObject result) {
            String message = result.get("message").getAsString();
            if (message.equals("Successfully updated")) {
                String name = result.get("name").getAsString();
                String email = result.get("email").getAsString();
                SharedPreferences sharepref = getActivity().getSharedPreferences("MyPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString("user_name", name);
                editor.putString("user_email", email);
                editor.commit();
                Profile profile = new Profile();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.frameholder, profile).commit();
            }
            else{
                Toast.makeText(getActivity(), "Please enter both email and password", Toast.LENGTH_LONG).show();
            }
        }

//        @Override
//        protected void onProgressUpdate(Void) {
//            super.onProgressUpdate(values);
//        }
        @Override
        protected JsonObject doInBackground(String...params) {
            return ProfileUtil.profileUpdate(params[0],params[1]);
        }
    }


}
