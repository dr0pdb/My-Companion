package com.example.srv_twry.studentcompanion.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srv_twry.studentcompanion.R;

/**
 * A simple {@link Fragment} subclass which contains the list of Active and upcoming contests.
 * Activities that contain this fragment must implement the
 * {@link CodingCalendarListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CodingCalendarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CodingCalendarListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

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
        return inflater.inflate(R.layout.fragment_coding_calendar_list, container, false);
    }

    // To pass the activity with the Contest clicked.
    public void passContestToActivity(int position) {
        if (mListener != null) {
            mListener.onListFragmentInteraction(position);
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
        void onListFragmentInteraction(Uri uri);
    }
}
