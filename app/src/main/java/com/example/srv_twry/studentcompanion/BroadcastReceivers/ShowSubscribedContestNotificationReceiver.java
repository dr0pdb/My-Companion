package com.example.srv_twry.studentcompanion.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Utilities.SubscribedContestUtilities;

/**
 * Created by srv_twry on 21/6/17.
 * The broadcast receiver that will be triggered when the contest is about to start.
 */

public class ShowSubscribedContestNotificationReceiver extends BroadcastReceiver {

    private String contestTitle;
    private String contestUrl;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Use Notification builder to show notifications here.

        contestTitle = intent.getStringExtra(SubscribedContestUtilities.CONTEST_TITLE);
        contestUrl = intent.getStringExtra(SubscribedContestUtilities.CONTEST_URL);

        Log.v("Contest receiver","CONTEST IS HERE "+ contestTitle);
        Toast.makeText(context, "Contest is here !!! ", Toast.LENGTH_SHORT).show();
    }
}
