package com.example.srv_twry.studentcompanion;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.srv_twry.studentcompanion.Adapters.FlashCardsTopicsRecyclerViewCursorAdapter;
import com.example.srv_twry.studentcompanion.Database.DatabaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.srv_twry.studentcompanion.Database.DatabaseContract.FlashCardsTopicsEntry.FLASH_CARDS_TOPIC_PRIORITY;

/*
* The home activity of the flash cards module. It will contain the list of flash card categories/topics added by the user
* and ability to add more categories.
* */
//TODO: Set up the swipe to delete functionality, alert dialog and the actual deletion methods

public class FlashCardsHomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> ,
        FlashCardsTopicsRecyclerViewCursorAdapter.FlashCardsTopicRecyclerViewOnClickListener{

    private static final String TAG = FlashCardsHomeActivity.class.getSimpleName();
    private static final int TOPICS_LOADER_ID = 300;

    @BindView(R.id.fab_add_flash_cards_topics) FloatingActionButton addFlashCardsFab;
    @BindView(R.id.rv_flash_cards_topics) RecyclerView flashCardsRecyclerView;
    @BindView(R.id.pb_loading_flash_cards_topics) ProgressBar loadingFlashCardsTopics;

    private FlashCardsTopicsRecyclerViewCursorAdapter flashCardsTopicsRecyclerViewCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.flash_cards));
        setContentView(R.layout.activity_flash_cards_home);

        // Handle up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //Instantiate the recycler view
        flashCardsRecyclerView.setLayoutManager(new GridLayoutManager(this,getResources().getInteger(R.integer.number_colums_grid_view_flash_topic)));
        flashCardsTopicsRecyclerViewCursorAdapter = new FlashCardsTopicsRecyclerViewCursorAdapter(FlashCardsHomeActivity.this,this);
        flashCardsRecyclerView.setAdapter(flashCardsTopicsRecyclerViewCursorAdapter);

        //setUp fab button to add topics here.
        addFlashCardsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashCardsHomeActivity.this,AddFlashCardTopicActivity.class);
                startActivity(intent);
            }
        });

        //start the loader
        getSupportLoaderManager().initLoader(TOPICS_LOADER_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        loadingFlashCardsTopics.setVisibility(View.VISIBLE);
        addFlashCardsFab.setVisibility(View.GONE);
        flashCardsRecyclerView.setVisibility(View.GONE);
        getSupportLoaderManager().restartLoader(TOPICS_LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            private Cursor mCursor = null;

            @Override
            protected void onStartLoading() {
                if (mCursor !=null){
                    deliverResult(mCursor);
                }else{
                    forceLoad();
                }
            }
            @Override
            public void deliverResult(Cursor cursor){
                mCursor = cursor;
                super.deliverResult(cursor);
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getContentResolver().query(DatabaseContract.FlashCardsTopicsEntry.CONTENT_URI_FLASH_CARDS_TOPICS,
                            null,null,null,FLASH_CARDS_TOPIC_PRIORITY);
                }catch(Exception e){
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(TAG,"Loading completed");
        flashCardsTopicsRecyclerViewCursorAdapter.swapCursor(data);
        loadingFlashCardsTopics.setVisibility(View.GONE);
        addFlashCardsFab.setVisibility(View.VISIBLE);
        flashCardsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        flashCardsTopicsRecyclerViewCursorAdapter.swapCursor(null);
    }

    //Method to open all the flash cards with the topicName
    @Override
    public void onFlashCardTopicClicked(String topicName) {
        Log.v(TAG,"Clicked topic with Name: "+topicName);
    }
}
