package com.example.srv_twry.studentcompanion.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
 * The class which uses the volley library to fetch Contests from the Hackerrank's API and return the arrayList of Contests.
 */

public class FetchContestsVolley {
    private static RequestQueue requestQueue;
    private static final String HACKERRANK_API_URL = "https://www.hackerrank.com/calendar/feed.json";
    private Context context;
    private ArrayList<Contest> contestArrayList;
    private onLoadingFinishedListener onLoadingFinishedListener;

    public FetchContestsVolley(Context context,onLoadingFinishedListener onLoadingFinishedListener){
        this.context=context;
        this.onLoadingFinishedListener= onLoadingFinishedListener;
        requestQueue = Volley.newRequestQueue(context);
    }

    public  void fetchContest(){
        contestArrayList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HACKERRANK_API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fetchArrayList(response);
                onLoadingFinishedListener.onLoadingFinished(contestArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Check your internet connection !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    // Parse the JSON Response to ArrayList
    private void fetchArrayList(JSONObject response) {
        try{
            JSONArray models = response.getJSONArray("models");
            for(int i=0; i<models.length(); i++){
                JSONObject obj = models.getJSONObject(i);
                String title = obj.getString("title");
                String description = obj.getString("description");
                String start = obj.getString("start");
                String end = obj.getString("end");
                String url = obj.getString("url");

                Date startTime = getDateFromString(start);
                Date endTime = getDateFromString(end);

                contestArrayList.add(new Contest(title,description,url,startTime,endTime));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //A helper method to convert the time in String to Java Date Class
    public Date getDateFromString(String string){

        Date result;
        try {
            TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
            Calendar cal = Calendar.getInstance(tz);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setCalendar(cal);
            cal.setTime(sdf.parse(string));
            result = cal.getTime();
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public interface onLoadingFinishedListener{
        void onLoadingFinished(ArrayList<Contest> contestArrayList);
    }
}
