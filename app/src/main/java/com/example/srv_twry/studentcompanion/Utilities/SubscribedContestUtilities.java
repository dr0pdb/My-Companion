package com.example.srv_twry.studentcompanion.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Database.DatabaseContract;
import com.example.srv_twry.studentcompanion.POJOs.Contest;

/**
 * Created by srv_twry on 21/6/17.
 */

public class SubscribedContestUtilities {

    public static int saveContestIntoSubscribedDatabase(Context context, Contest mContest) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_TITLE,mContest.getTitle());
        contentValues.put(DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_URL,mContest.getUrl());
        contentValues.put(DatabaseContract.SubscribedContestEntry.SUBSCRIBED_CONTEST_START_TIME,mContest.getStartTime().getTime());

        Uri uri = context.getContentResolver().insert(DatabaseContract.SubscribedContestEntry.CONTENT_URI_SUBSCRIBED_CONTESTS,contentValues);

        if (uri !=null){
            Log.v("Subscribe Contest","successfully added");
            Toast toast = Toast.makeText(context,"Reminder set successfully",Toast.LENGTH_SHORT);
            toast.show();
        }
        return Integer.parseInt(uri.getPathSegments().get(1));
    }

    public static int removeContestFromSubscribedDatabase(Context context,int subscribedDatabaseId) {
        String stringId = Integer.toString(subscribedDatabaseId);
        Uri uri = DatabaseContract.SubscribedContestEntry.CONTENT_URI_SUBSCRIBED_CONTESTS.buildUpon().appendPath(stringId).build();
        return context.getContentResolver().delete(uri,null,null);
    }
}
