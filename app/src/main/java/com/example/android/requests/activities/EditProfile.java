package com.example.android.requests.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.fragments.Profile;
import com.example.android.requests.utils.ProfileUtil;
import com.google.gson.JsonObject;

public class EditProfile extends AppCompatActivity {
    private AppCompatButton save_button;
    private AppCompatEditText name;
    private AppCompatEditText email;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        save_button = (AppCompatButton)findViewById(R.id.save_button);
        name = (AppCompatEditText)findViewById(R.id.user_name);
        email = (AppCompatEditText)findViewById(R.id.user_email);
        SharedPreferences sharepref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        name.setText(sharepref.getString("user_name", "nhi aaya"));
        email.setText(sharepref.getString("user_email", "nhi aaya"));
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (AppCompatEditText)findViewById(R.id.user_email);
                name = (AppCompatEditText)findViewById(R.id.user_name);
                String emailString = email.getText().toString();
                String namestring = name.getText().toString();
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(emailString, namestring);

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
                SharedPreferences sharepref = getSharedPreferences("MyPref", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharepref.edit();
                editor.putString("user_name", name);
                editor.putString("user_email", email);
                editor.commit();
                Profile profile = new Profile();
//                FragmentManager manager = .getSupportFragmentManager();
//                manager.beginTransaction().replace(R.id.frameholder, profile).commit();
            }
            else{
                //Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_LONG).show();
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
