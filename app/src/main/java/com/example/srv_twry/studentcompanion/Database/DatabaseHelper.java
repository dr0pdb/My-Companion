package com.example.srv_twry.studentcompanion.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srv_twry on 19/6/17.
 * The SQlite database helper class for the database operations.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "StudentCompanion.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_CONTEST_TABLE = "CREATE TABLE " + DatabaseContract.ContestEntry.TABLE_NAME_CONTESTS + " (" +
                DatabaseContract.ContestEntry._ID  + " INTEGER PRIMARY KEY, " +
                DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE + " TEXT NOT NULL, " +
                DatabaseContract.ContestEntry.CONTEST_COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                DatabaseContract.ContestEntry.CONTEST_COLUMN_URL + " TEXT NOT NULL, " +
                DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME + " TEXT NOT NULL, " +
                DatabaseContract.ContestEntry.CONTEST_COLUMN_END_TIME + " TEXT NOT NULL);";

        db.execSQL(CREATE_CONTEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ContestEntry.TABLE_NAME_CONTESTS);
        onCreate(db);
    }
}
