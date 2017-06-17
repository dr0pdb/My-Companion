package com.example.srv_twry.studentcompanion.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srv_twry.studentcompanion.Adapters.ContestRecyclerViewAdapter;
import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass which contains the list of Active and upcoming contests.
 * Activities that contain this fragment must implement the
 * {@link CodingCalendarListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CodingCalendarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CodingCalendarListFragment extends Fragment implements ContestRecyclerViewAdapter.ContestRecyclerViewOnClickListener {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Contest> contestArrayList = new ArrayList<>();
    @BindView(R.id.rv_contest_list)
     RecyclerView contestRecyclerView;


    public CodingCalendarListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * No parameters are required for this fragment.
     * @return A new instance of fragment CodingCalendarListFragment.
     */
    public static CodingCalendarListFragment newInstance() {
        return new CodingCalendarListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coding_calendar_list, container, false);
        ButterKnife.bind(this,view);

        contestArrayList = fillWithFakeData();
        ContestRecyclerViewAdapter contestRecyclerViewAdapter = new ContestRecyclerViewAdapter(contestArrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getBaseContext(),getResources().getInteger(R.integer.number_columns_grid_view_features));
        contestRecyclerView.setAdapter(contestRecyclerViewAdapter);
        contestRecyclerView.setLayoutManager(gridLayoutManager);
        return view;
    }

    private ArrayList<Contest> fillWithFakeData() {
        ArrayList<Contest> contestArrayList = new ArrayList<>();
        Date startTime = getDateFromString("2017-07-23T16:00:00.000Z");
        Date endTime = getDateFromString("2017-07-23T18:30:00.000Z");
        contestArrayList.add(new Contest("Codechef - July Lunchtime 2017","","https://www.codechef.com/LTIME50" ,startTime,endTime));
        contestArrayList.add(new Contest("Codechef - July Lunchtime 2017","","https://www.codechef.com/LTIME50" ,startTime,endTime));
        contestArrayList.add(new Contest("Codechef - July Lunchtime 2017","","https://www.codechef.com/LTIME50" ,startTime,endTime));
        contestArrayList.add(new Contest("Codechef - July Lunchtime 2017","","https://www.codechef.com/LTIME50" ,startTime,endTime));
        return contestArrayList;
    }

    // To pass the activity with the Contest clicked.
    public void passContestToActivity(Contest clickedContest) {
        if (mListener != null) {
            mListener.onListFragmentInteraction(clickedContest);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onContestListItemClicked(Contest clickedContest) {
        passContestToActivity(clickedContest);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(Contest clickedContest);
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
}
