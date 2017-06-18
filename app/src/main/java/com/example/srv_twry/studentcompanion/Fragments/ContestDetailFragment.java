package com.example.srv_twry.studentcompanion.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srv_twry.studentcompanion.CodingCalendarContestDetailActivity;
import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContestDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContestDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTEST_TAG = "contest";
    private static final String IS_REMINDED = "isReminded";

    private Contest mContest;
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
        }else{
            isSetForReminder= false;
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
        getStartTimeText(mContest.getStartTime());
        String duration = "Approximately "+getContestDuration(mContest.getStartTime(),mContest.getEndTime())+" hours";
        contestDetailDurationText.setText(duration);
        if (mContest.getDescription().equals("")){
            contestDetailDescriptionText.setText("Coding Contest for Programming enthusiasts");
        }else{
            contestDetailDescriptionText.setText(mContest.getDescription());
        }
        contestDetailRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mContest.getUrl()));
                startActivity(intent);
            }
        });
    }

    // Helper method to set the start time and date of the contest in intended order
    private void getStartTimeText(Date startTime) {
        SpannableString originalFormatTime = new SpannableString(startTime.toString());
        String finalStartTime;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(originalFormatTime,0,3);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(3f),0,3,0);
        spannableStringBuilder.append("\n");
        spannableStringBuilder.append(originalFormatTime,4,10);
        spannableStringBuilder.setSpan(new RelativeSizeSpan(1.5f),4,10,0);
        spannableStringBuilder.append("\n");
        spannableStringBuilder.append(originalFormatTime,11,16);
        finalStartTime = spannableStringBuilder.toString();

        contestDetailStartTimeText.setText(finalStartTime);
    }

    //Helper method to get the Cover image of the Contest
    private void getCoverImage(String url) {
        URL urlPlatform;
        try{
            urlPlatform = new URL(url);
            String platformString = urlPlatform.getHost();

            switch (platformString){
                case "www.topcoder.com":
                    coverImage.setImageResource(R.mipmap.topcoder_cover);
                    break;
                case "www.codechef.com":
                    coverImage.setImageResource(R.mipmap.codechef_cover);
                    break;
                case "www.hackerrank.com":
                    coverImage.setImageResource(R.mipmap.hackerrank_cover);
                    break;
                case "www.hackerearth.com":
                    coverImage.setImageResource(R.mipmap.hackerearth_cover);
                    break;
                case "codeforces.com":
                    coverImage.setImageResource(R.mipmap.codeforces_cover);
                    break;
                default:
                    coverImage.setImageResource(R.mipmap.ic_code);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
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
