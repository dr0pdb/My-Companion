package com.example.srv_twry.studentcompanion.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by srv_twry on 20/6/17.
 * The content provider for the SQLite database.
 */

public class DatabaseContentProvider extends ContentProvider {

    private static final int CONTESTS =100;
    private static final int SUBSCRIBED_CONTESTS = 200;
    private static final int SUBSCRIBED_CONTESTS_INDIVIDUAL = 201;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.PATH_CONTESTS,CONTESTS);
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.PATH_SUBSCRIBED_CONTESTS,SUBSCRIBED_CONTESTS);
        uriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.PATH_SUBSCRIBED_CONTESTS +"/#",SUBSCRIBED_CONTESTS_INDIVIDUAL);
        return uriMatcher;
    }

    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (match){
            case CONTESTS:
                returnCursor = db.query(DatabaseContract.ContestEntry.TABLE_NAME_CONTESTS,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case SUBSCRIBED_CONTESTS:
                returnCursor = db.query(DatabaseContract.SubscribedContestEntry.TABLE_NAME_SUBSCRIBED_CONTESTS,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch (match){
            case CONTESTS:
                id = db.insert(DatabaseContract.ContestEntry.TABLE_NAME_CONTESTS,null,values);
                if (id >0){
                    returnUri = ContentUris.withAppendedId(uri,id);
                }else{
                    throw new android.database.SQLException("Failed to insert contest row into " + uri);
                }
                break;

            case SUBSCRIBED_CONTESTS:
                id = db.insert(DatabaseContract.SubscribedContestEntry.TABLE_NAME_SUBSCRIBED_CONTESTS,null,values);
                if (id>0){
                    returnUri = ContentUris.withAppendedId(uri,id);
                }else{
                    throw new android.database.SQLException("Failed to insert contest row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int contestsDeleted;

        int match = uriMatcher.match(uri);

        switch (match){
            case SUBSCRIBED_CONTESTS_INDIVIDUAL:
                String stringIds = uri.getPathSegments().get(1);
                contestsDeleted = db.delete(DatabaseContract.SubscribedContestEntry.TABLE_NAME_SUBSCRIBED_CONTESTS,"_id=?", new String[]{stringIds});
                Log.v("ContentProvider ","Deleted id="+stringIds + " from the database");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (contestsDeleted !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return contestsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
