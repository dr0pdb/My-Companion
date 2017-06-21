package com.example.srv_twry.studentcompanion.Utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.BroadcastReceivers.ShowSubscribedContestNotificationReceiver;
import com.example.srv_twry.studentcompanion.Database.DatabaseContract;
import com.example.srv_twry.studentcompanion.POJOs.Contest;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by srv_twry on 21/6/17.
 * The class containing some useful functions to handle subscribing to contests.
 */

public class SubscribedContestUtilities {

    public static final String CONTEST_TITLE = "contestTitle";
    public static final String CONTEST_URL = "contestUrl";
    public static final String CONTEST_START_TIME = "contestStartTime";

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

    public static int removeContestFromSubscribedDatabase(Context context, int subscribedDatabaseId) {
        String stringId = Integer.toString(subscribedDatabaseId);
        Uri uri = DatabaseContract.SubscribedContestEntry.CONTENT_URI_SUBSCRIBED_CONTESTS.buildUpon().appendPath(stringId).build();
        return context.getContentResolver().delete(uri,null,null);
    }


    public static void setReminderUsingAlarmManager(Context context, String title, String url, long time) {
        Intent intent = new Intent(context, ShowSubscribedContestNotificationReceiver.class);
        intent.putExtra(CONTEST_TITLE,title);
        intent.putExtra(CONTEST_URL,url);

        long currentTime = System.currentTimeMillis();
        if (time<currentTime){
            return;
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}
