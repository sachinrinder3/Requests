package com.example.android.requests.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper{

    private static final String Database_name = "Chatting.db";
    private static final String Table_name = "CHAT_TABLE";
    private static final String id = "_id";
    private static final String chat_message = "chat_message";
    private static final String send_to = "send_to";
    private static int Database_version =4;

    public DataBaseHelper(Context context) {
        super(context, Database_name, null, Database_version);
        Log.i("TAG", "DATABASE CONSTRUCTER IS CALLED");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TAG", "DATABASE OF CREATE IS CALLED");
        try {
            db.execSQL("CREATE TABLE " + Table_name + " ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + chat_message + " VARCHAR(255), " + send_to + " VARCHAR(255) " + ")");
        } catch (SQLException e){
            Log.i("TAG", "no create catch");
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG", "DATABASE On update IS CALLED");
        try{
            db.execSQL("DROP TABLE IF EXIST "+Table_name);
        } catch (SQLException e){
            e.printStackTrace();
        }
        onCreate(db);

    }
}
