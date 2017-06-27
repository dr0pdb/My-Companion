package com.example.srv_twry.studentcompanion;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.srv_twry.studentcompanion.Database.DatabaseContract;
import com.example.srv_twry.studentcompanion.Utilities.DatabaseUtilites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Implementation of App Widget functionality.
 */
public class CodingCalendarUpcomingContestWidget extends AppWidgetProvider {

    private static String upcomingContests;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.coding_calendar_upcoming_contest_widget);

        Cursor cursor = context.getContentResolver().query(DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS,null,null,null,null);
        generateStringForContests(cursor);
        cursor.close();

        views.setTextViewText(R.id.contest_widget_upcoming_contests,upcomingContests);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void generateStringForContests(Cursor cursor){

        //Only show the 8 upcoming contests
        int numberContests = cursor.getCount();
        if (numberContests >8 ){
            numberContests = 8;
        }

        int titleColumnIndex = cursor.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE);
        int startTimeColumnIndex = cursor.getColumnIndex(DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i=0; i< numberContests ; i++){
            cursor.moveToPosition(i);

            String title = cursor.getString(titleColumnIndex);
            String temp = cursor.getString(startTimeColumnIndex);
            String startTime = getDateFromString(temp).toString();

            stringBuilder.append(i+1).append(". ").append(title).append(" on ").append(startTime).append("\n");
        }

        upcomingContests = stringBuilder.toString();
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    //A helper method to convert the time in String to Java Date Class
    public static Date getDateFromString(String string){

        Date result;
        try {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            //TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
            //Calendar cal = Calendar.getInstance(tz);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            //sdf.setCalendar(cal);
            //cal.setTime(sdf.parse(string));
            //result = cal.getTime();
            sdf.setTimeZone(tz);
            result = sdf.parse(string);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

