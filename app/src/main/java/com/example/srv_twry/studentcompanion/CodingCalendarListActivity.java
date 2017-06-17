package com.example.srv_twry.studentcompanion;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.srv_twry.studentcompanion.Fragments.CodingCalendarListFragment;

/*
* This activity contains the coding contest lists for phones and The two pane layout for the tablets.
* */
public class CodingCalendarListActivity extends AppCompatActivity implements CodingCalendarListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_calendar_list);
        setTitle(getResources().getString(R.string.coding_calendar));

        CodingCalendarListFragment codingCalendarListFragment = new CodingCalendarListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_layout_coding_calendar_list,codingCalendarListFragment).commit();


        // TODO: For a later case, check for phone or tablet. If tablet then add the details Fragment.


    }

    // In case of phones it will start the ContestDetailActivity while in case of tablets it will contact ContestDetailFragment for details.
    @Override
    public void onListFragmentInteraction(Uri uri) {

    }
}
