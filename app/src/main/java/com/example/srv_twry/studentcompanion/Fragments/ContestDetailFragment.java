package com.example.srv_twry.studentcompanion.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srv_twry.studentcompanion.CodingCalendarContestDetailActivity;
import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContestDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContestDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContestDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTEST_TAG = "contest";
    private static final String IS_REMINDED = "isReminded";

    private Contest mContest;
    private OnFragmentInteractionListener mListener;
    private Boolean isSetForReminder;

    public ContestDetailFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.contest_cover) ImageView coverImage;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_contest_details_title) TextView contestDetailTitleView;
    @BindView(R.id.tv_contest_details_start_time) TextView contestDetailStartTimeText;
    @BindView(R.id.tv_contest_detail_duration) TextView contestDetailDurationText;
    @BindView(R.id.tv_contest_detail_description)TextView contestDetailDescriptionText;
    @BindView(R.id.button_contest_detail_register) Button contestDetailRegistrationButton;
    @BindView(R.id.contest_detail_share_fab) FloatingActionButton shareFloatingActionButton;
    @BindView(R.id.contest_detail_set_reminder) FloatingActionButton setReminderFloatingActionButton;

    //For a new instance of the Fragment.
    public static ContestDetailFragment newInstance(Contest contest) {
        ContestDetailFragment fragment = new ContestDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONTEST_TAG,contest);
        fragment.setArguments(args);
        return fragment;
    }

    public CodingCalendarContestDetailActivity getActivityCast(){
        return (CodingCalendarContestDetailActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContest = getArguments().getParcelable(CONTEST_TAG);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest_detail, container, false);
        if (savedInstanceState != null){
            isSetForReminder = savedInstanceState.getBoolean(IS_REMINDED);
        }

        // Initialize and set the data.
        ButterKnife.bind(this,view);
        getActivityCast().setSupportActionBar(toolbar);

        //Responding to back button. NOTE:// ONLY FOR PHONES
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ActionBar actionBar = getActivityCast().getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle("");

        //setting the fab buttons
        shareFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: set the share intent here.
            }
        });

        if (isSetForReminder){
            setReminderFloatingActionButton.setImageResource(R.drawable.ic_remove_reminder);
        }else{
            setReminderFloatingActionButton.setImageResource(R.drawable.ic_set_reminder);
        }

        setReminderFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSetForReminder) {
                    removeContestReminder();
                    isSetForReminder = false;
                    setReminderFloatingActionButton.setImageResource(R.drawable.ic_set_reminder);
                } else {
                    setContestReminder();
                    isSetForReminder = true;
                    setReminderFloatingActionButton.setImageResource(R.drawable.ic_remove_reminder);
                }
            }
        });

        //Setting the data to the views.
        setDataToViews();
        return view;
    }

    // A helper method to set the reminder for the contest
    private void setContestReminder() {
        //TODO: Set the reminder
    }

    // A helper method to remove the reminder set for the contest
    private void removeContestReminder() {
        // TODO: Remove the reminder
    }

    // A helper method to set the data into the views.
    private void setDataToViews() {
        if (mContest == null){
            return;
        }
        contestDetailTitleView.setText(mContest.getTitle());
        getCoverImage(mContest.getUrl());


    }
    //Helper method to get the Cover image of the Contest
    private void getCoverImage(String url) {
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private long getContestDuration(Date start, Date end){
        long startTime = start.getTime();
        long endTime = end.getTime();

        long difference = endTime- startTime;
        difference = difference / (60 * 60 * 1000) % 24;        // returning the difference in hours.
        return difference;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_REMINDED,isSetForReminder);
    }
}
