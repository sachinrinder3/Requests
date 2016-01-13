package com.example.android.requests.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.requests.R;
import com.example.android.requests.adapters.ChatAdapterParse;
import com.example.android.requests.models.Message;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = ChatActivity.class.getName();
    private static String sUserId;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private ListView lvChat;
    private ArrayList<Message> mMessages;
    private ChatAdapterParse mAdapter;

    private EditText etMessage;
    private Button btSend;
    public static final String USER_ID_KEY = "userId";

    // Keep track of initial load to scroll to the bottom of the ListView
    private boolean mFirstLoad;
    public static final String YOUR_APPLICATION_ID = "G7DXbBMyOiMCOryNwHvQL3mqw0q37B6xhc9PlZFm";
    public static final String YOUR_CLIENT_KEY = "usTr4KFvBEeyP25Xg6wFAoS8COAZYsbmNPsS6WAW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


             //Register your parse models here
            ParseObject.registerSubclass(com.example.android.requests.models.Message.class);
             //Existing initialization happens after all classes are registered
            Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);



        // User login
        if (ParseUser.getCurrentUser() != null) { // start with existing user
            Log.i("TAG", "user is there");
            startWithCurrentUser();
        } else { // If not logged in, login as a new anonymous user
            Log.i("TAG", "no user is present user is there");
            login();
        }
    }

    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        sUserId = ParseUser.getCurrentUser().getObjectId();
    }

     //Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                Log.i(TAG, "Anonymous login failed: " + e.toString());
                if (e != null) {
                    Log.i(TAG, "Anonymous login failed: " + e.toString());
                } else {
                    startWithCurrentUser();
                }
            }
        });


    }


    private void setupMessagePosting() {
        // Find the text field and button
         etMessage = (EditText) findViewById(R.id.chat_msg);
          btSend = (Button) findViewById(R.id.send_chat_msg_button);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY, sUserId);
                message.put("body", data);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(ChatActivity.this, "Successfully created message on Parse", Toast.LENGTH_SHORT).show();
                    }
                });
                etMessage.setText("");
            }
        });
    }


       private void receiveMessage() {
        // Construct query to execute
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) { mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if(mFirstLoad) {
                        lvChat.setSelection(mAdapter.getItemCount() - 1);
                        mFirstLoad = false;
                    }
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });

    }
}