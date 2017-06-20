package com.example.srv_twry.studentcompanion.Adapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.example.srv_twry.studentcompanion.Network.FetchContestsVolley;

/**
 * Created by srv_twry on 20/6/17.
 * The sync adapter for the coding calendar.
 */

public class CodingCalendarSyncAdapter extends AbstractThreadedSyncAdapter implements FetchContestsVolley.onLoadingFinishedListener{

    private final String TAG = CodingCalendarSyncAdapter.class.getSimpleName();

    private Context mContext;

    public CodingCalendarSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext=context;
    }

    public CodingCalendarSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        //FetchContestsVolley fetchContestsVolley = new FetchContestsVolley(mContext,this);
        //fetchContestsVolley.fetchContest();
        Log.v(TAG,"Syncing started");
    }

    /*The interface method in the FetchContestsVolley class which sets the recycler view with the new data
        Here it will not do anything useful other than logging.
    */
    @Override
    public void onLoadingFinished() {
        Log.v(TAG,"synced the data");
    }
}
