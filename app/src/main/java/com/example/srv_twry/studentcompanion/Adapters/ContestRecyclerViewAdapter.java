package com.example.srv_twry.studentcompanion.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srv_twry on 18/6/17.
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
        void onContestListItemClicked();
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
            
        }

        @Override
        public void onClick(View v) {

        }
    }
}
