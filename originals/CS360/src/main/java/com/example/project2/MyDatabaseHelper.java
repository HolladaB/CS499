package com.example.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
//used to create a database with uses inputted weight information
class MyDatabaseHelper extends SQLiteOpenHelper {

    //sets up the database variables
    private Context context;
    public static final String DATABASE_NAME = "WeightLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "_date";
    private static final String COLUMN_WEIGHT = "_weight";
    private static final String COLUMN_GOAL = "_goal";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    //creates a database with the columns needed to store inputs
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_DATE +  " TEXT, " +
                        COLUMN_WEIGHT + " INTEGER, " +
                        COLUMN_GOAL + " INTEGER);";
        db.execSQL(query);
    }

    //drops table if one with the same name exists
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    // adds weight to database on click of add button
    void addWeight(String date, int weight, int goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_GOAL, goal);
        long result = db.insert(TABLE_NAME, null, cv);

        // loops to determine if addition was successful
        if (result == -1) {
            Toast.makeText(context, "Add Failed!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Successful!", Toast.LENGTH_SHORT).show();
        }
    }
    // used to loop through database
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // used to update data on update button click
    void updateData(String row_id, String date, String weight, String goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_GOAL, goal);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successful Update!", Toast.LENGTH_SHORT).show();
        }
    }

    // used to deleted database row if user clicks button and accepts dialog
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to Delete!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successful Delete!", Toast.LENGTH_SHORT).show();
        }
    }
}
