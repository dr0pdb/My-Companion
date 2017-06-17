package com.example.srv_twry.studentcompanion.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srv_twry on 18/6/17.
 * The recycler view adapter for the contest list recyclerview
 */

public class ContestRecyclerViewAdapter extends RecyclerView.Adapter<ContestRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Contest> contestArrayList;
    private ContestRecyclerViewOnClickListener contestRecyclerViewOnClickListener;

    public ContestRecyclerViewAdapter(ArrayList<Contest> contestArrayList , ContestRecyclerViewOnClickListener contestRecyclerViewOnClickListener){
        this.contestArrayList = contestArrayList;
        this.contestRecyclerViewOnClickListener = contestRecyclerViewOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.contest_list_view_holder_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return contestArrayList.size();
    }

    public interface ContestRecyclerViewOnClickListener{
        void onContestListItemClicked(Contest clickedContest);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.contest_view_holder_platform_image) ImageView contestPlatform;
        @BindView(R.id.tv_contest_platform_name) TextView contestTitle;
        @BindView(R.id.tv_contest_start_time) TextView contestStartTime;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        public void bind(int position) {
            setImageViewUsingUrl(contestArrayList.get(position).getUrl());
            contestTitle.setText(contestArrayList.get(position).getTitle());
            contestStartTime.setText(contestArrayList.get(position).getStartTime().toString());
        }

        private void setImageViewUsingUrl(String url) {
            URL urlPlatform;
            try{
                urlPlatform = new URL(url);
                //get the first part of the host.
                String platformString = urlPlatform.getHost();
                Log.v("ContestRecyclerViewAdap",platformString);

                if (platformString.equals("topcoder")){
                    contestPlatform.setImageResource(R.mipmap.topcoder_logo);
                }else if (platformString.equals("codechef")){
                    contestPlatform.setImageResource(R.mipmap.codechef_logo);
                }else if(platformString.equals("hackerrank")){
                    contestPlatform.setImageResource(R.mipmap.hackerrank_logo);
                }else if(platformString.equals("hackerearth")){
                    contestPlatform.setImageResource(R.mipmap.hackerearth_logo);
                }else if(platformString.equals("codeforces")){
                    contestPlatform.setImageResource(R.mipmap.codeforces_logo);
                }else{
                    contestPlatform.setImageResource(R.mipmap.ic_code);
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
                contestPlatform.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Contest clickedContest = contestArrayList.get(getAdapterPosition());
            contestRecyclerViewOnClickListener.onContestListItemClicked(clickedContest);
        }
    }
}
