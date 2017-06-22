package com.example.srv_twry.studentcompanion;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.srv_twry.studentcompanion.Fragments.CodingCalendarListFragment;
import com.example.srv_twry.studentcompanion.POJOs.Contest;
import com.example.srv_twry.studentcompanion.Services.CodingCalendarAuthenticatorService;
import com.example.srv_twry.studentcompanion.Services.CodingCalendarSyncService;

/*
* This activity contains the coding contest lists for phones and The two pane layout for the tablets.
* */
public class CodingCalendarListActivity extends AppCompatActivity implements CodingCalendarListFragment.OnFragmentInteractionListener{

    private static final String TAG = CodingCalendarListActivity.class.getSimpleName();
    public static final String INTENT_EXTRA_TAG = "Contest";

    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.example.srv_twry.studentcompanion";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "example.com";
    // The account name
    public static final String ACCOUNT = "dummyaccount";

    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 60L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coding_calendar_list);
        setTitle(getResources().getString(R.string.coding_calendar));

        //Only create a fragment when their isn't one.
        if (savedInstanceState == null){
            CodingCalendarListFragment codingCalendarListFragment = CodingCalendarListFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_layout_coding_calendar_list,codingCalendarListFragment).commit();
            Log.v("CalendarListActivity","CREATING THE FRAGMENT");
        }

        //Setting the syncAccount
        mAccount = CreateSyncAccount(CodingCalendarListActivity.this);
        //Setting the periodic sync per hour.
        ContentResolver.addPeriodicSync(mAccount,AUTHORITY,Bundle.EMPTY,SYNC_INTERVAL);

        // TODO: For a later case, check for phone or tablet. If tablet then add the details Fragment.

    }

    // In case of phones it will start the ContestDetailActivity while in case of tablets it will contact ContestDetailFragment for details.
    @Override
    public void onListFragmentInteraction(Contest clickedContest) {
        //For phones
        Intent intent = new Intent(CodingCalendarListActivity.this,CodingCalendarContestDetailActivity.class);
        intent.putExtra(INTENT_EXTRA_TAG,clickedContest);
        startActivity(intent);
    }

    // Creating the sync account for the Coding calendar activity
    private Account CreateSyncAccount(Context context) {

        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            Log.v(TAG,"Created the account");

            // Requesting the first sync after creating the account.
            ContentResolver.requestSync(newAccount,AUTHORITY,Bundle.EMPTY);
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
            Log.e(TAG,"Cannot create the account as it may exist already!");
        }

        return newAccount;
    }

}
