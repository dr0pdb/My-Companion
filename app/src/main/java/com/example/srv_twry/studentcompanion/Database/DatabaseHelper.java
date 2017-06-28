package com.example.srv_twry.studentcompanion.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by srv_twry on 19/6/17.
 * The SQlite database helper class for the database operations.
 */

class DatabaseHelper extends SQLiteOpenHelper {

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

        final String CREATE_SUBSCRIBED_CONTEST_TABLE = "CREATE TABLE " + DatabaseContract.SubscribedContestEntry.TABLE_NAME_SUBSCRIBED_CONTESTS + " (" +
                DatabaseContract.SubscribedContestEntry._ID + " INTEGER PRIMARY KEY, " +
                DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_TITLE + " TEXT NOT NULL, " +
                DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_URL + " TEXT NOT NULL, " +
                DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_START_TIME + " INTEGER);";

        final String CREATE_FLASH_CARDS_TOPICS_TABLE = "CREATE TABLE "  + DatabaseContract.FlashCardsTopicsEntry.TABLE_NAME_FLASH_CARDS_TOPICS + " (" +
                DatabaseContract.FlashCardsTopicsEntry._ID                + " INTEGER PRIMARY KEY, " +
                DatabaseContract.FlashCardsTopicsEntry.FLASH_CARDS_TOPIC_NAME + " TEXT NOT NULL, " +
                DatabaseContract.FlashCardsTopicsEntry.FLASH_CARDS_TOPIC_PRIORITY    + " INTEGER NOT NULL);";

        final String CREATE_FLASH_CARDS_TABLE = "CREATE TABLE "  + DatabaseContract.FlashCardsEntry.TABLE_NAME_FLASH_CARDS + " (" +
                DatabaseContract.FlashCardsEntry._ID                + " INTEGER PRIMARY KEY, " +
                DatabaseContract.FlashCardsEntry.FLASH_CARD_TOPIC_NAME + " TEXT NOT NULL, " +
                DatabaseContract.FlashCardsEntry.FLASH_CARD_QUESTION + " TEXT NOT NULL, " +
                DatabaseContract.FlashCardsEntry.FLASH_CARD_ANSWER    + " TEXT NOT NULL);";

        db.execSQL(CREATE_CONTEST_TABLE);
        db.execSQL(CREATE_SUBSCRIBED_CONTEST_TABLE);
        db.execSQL(CREATE_FLASH_CARDS_TOPICS_TABLE);
        db.execSQL(CREATE_FLASH_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ContestEntry.TABLE_NAME_CONTESTS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.SubscribedContestEntry.TABLE_NAME_SUBSCRIBED_CONTESTS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FlashCardsTopicsEntry.TABLE_NAME_FLASH_CARDS_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FlashCardsEntry.TABLE_NAME_FLASH_CARDS);
        onCreate(db);
    }
}
