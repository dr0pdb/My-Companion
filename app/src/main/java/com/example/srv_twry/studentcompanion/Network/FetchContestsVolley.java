package com.example.srv_twry.studentcompanion.Network;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.srv_twry.studentcompanion.Database.DatabaseContract;
import com.example.srv_twry.studentcompanion.POJOs.Contest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by srv_twry on 19/6/17.
 * The class which uses the volley library to fetch Contests from the Hackerrank's API
 */

public class FetchContestsVolley {
    private static RequestQueue requestQueue;
    private static final String HACKERRANK_API_URL = "https://www.hackerrank.com/calendar/feed.json";
    private Context context;
    private onLoadingFinishedListener onLoadingFinishedListener;

    public FetchContestsVolley(Context context,onLoadingFinishedListener onLoadingFinishedListener){
        this.context=context;
        this.onLoadingFinishedListener= onLoadingFinishedListener;
        requestQueue = Volley.newRequestQueue(context);
    }

    public  void fetchContest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HACKERRANK_API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                addToDatabase(response);
                onLoadingFinishedListener.onLoadingFinished();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Check your internet connection !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    // Parse the JSON Response and add to database
    private void addToDatabase(JSONObject response) {
        try{
            JSONArray models = response.getJSONArray("models");
            //The hackerrank API sorts contests in reverse order so reversing the array
            for(int i=models.length()-1; i>=0; i--){
                JSONObject obj = models.getJSONObject(i);
                String title = obj.getString("title");
                String description = obj.getString("description");
                String start = obj.getString("start");
                String end = obj.getString("end");
                String url = obj.getString("url");

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_TITLE,title);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_DESCRIPTION,description);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_URL,url);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_START_TIME,start);
                contentValues.put(DatabaseContract.ContestEntry.CONTEST_COLUMN_END_TIME,end);

                //Adding the data to the database.
                context.getContentResolver().insert(DatabaseContract.ContestEntry.CONTENT_URI_CONTESTS,contentValues);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface onLoadingFinishedListener{
        void onLoadingFinished();
    }
}
