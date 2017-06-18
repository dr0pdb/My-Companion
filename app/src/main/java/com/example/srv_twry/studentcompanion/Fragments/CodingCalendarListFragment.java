package com.example.srv_twry.studentcompanion.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Adapters.ContestRecyclerViewAdapter;
import com.example.srv_twry.studentcompanion.Network.FetchContestsVolley;
import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.util.ArrayList;

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
public class CodingCalendarListFragment extends Fragment implements ContestRecyclerViewAdapter.ContestRecyclerViewOnClickListener, FetchContestsVolley.onLoadingFinishedListener {

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

        //If the device is online then start the network operation otherwise show an error.
        if (isOnline()){
            FetchContestsVolley fetchContestsVolley = new FetchContestsVolley(getContext(),this);
            fetchContestsVolley.fetchContest();
        }else{
            Toast toast = Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT);
            toast.show();
        }

        ContestRecyclerViewAdapter contestRecyclerViewAdapter = new ContestRecyclerViewAdapter(contestArrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getBaseContext(),getResources().getInteger(R.integer.number_columns_grid_view_features));
        contestRecyclerView.setAdapter(contestRecyclerViewAdapter);
        contestRecyclerView.setLayoutManager(gridLayoutManager);
        return view;
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

    @Override
    public void onLoadingFinished(ArrayList<Contest> contestArrayListReceived) {
        contestArrayList = contestArrayListReceived;
        ContestRecyclerViewAdapter contestRecyclerViewAdapter = new ContestRecyclerViewAdapter(contestArrayList,this);
        contestRecyclerView.setAdapter(contestRecyclerViewAdapter);
        contestRecyclerView.invalidate();
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
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}
