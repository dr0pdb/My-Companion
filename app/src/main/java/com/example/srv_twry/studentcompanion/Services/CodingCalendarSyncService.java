package com.example.srv_twry.studentcompanion.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.srv_twry.studentcompanion.Adapters.CodingCalendarSyncAdapter;

/**
 * Created by srv_twry on 20/6/17.
 * Define a Service that returns an IBinder for the
 * sync adapter class, allowing the sync adapter framework to call
 * onPerformSync().
 */

public class CodingCalendarSyncService extends Service {

    private CodingCalendarSyncAdapter mCodingCalendarSyncAdapter = null;
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (mCodingCalendarSyncAdapter == null) {
                mCodingCalendarSyncAdapter = new CodingCalendarSyncAdapter(getApplicationContext(), true);
                Log.v("CodingCalendarSyncAdap","Started sync adapter.");
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mCodingCalendarSyncAdapter.getSyncAdapterBinder();
    }
}
