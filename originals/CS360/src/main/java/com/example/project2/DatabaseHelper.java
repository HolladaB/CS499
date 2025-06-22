package com.example.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //used to create a database of username and passwords
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, passWord TEXT)");
    }
    // replaces old database on updates
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
    // used to read in the username and password into the database
    public Boolean insertData(String userName, String passWord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues info = new ContentValues();
        info.put("userName", userName);
        info.put("passWord", passWord);
        long result = db.insert("users", null, info);
        if(result == -1 ){ // verifies the data input correctly
            return false;
        }
        else{
            return true;
        }
    }
    // used to search database for a username
    public Boolean checkUserName(String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE userName = ?", new String[] {userName});
        if(cursor.getCount() > 0){ // if username exists it returns true
            return true;
        }
        else {
            return false;
        }
    }
    // verifies that username and password matches
    public Boolean checkUserPassWord(String userName, String passWord){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE userName = ? AND passWord = ?", new String[] {userName,passWord});
        if(cursor.getCount() > 0){ // if the combination exits this returns true
            return true;
        }
        else {
            return false;
        }
    }
}
