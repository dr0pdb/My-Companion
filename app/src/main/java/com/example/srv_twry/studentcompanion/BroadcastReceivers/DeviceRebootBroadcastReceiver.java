package com.example.srv_twry.studentcompanion.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by srv_twry on 22/6/17.
 * The broadcast manager that gets triggered whenever the device reboots so that it can
 * start the service that can set the reminders again.
 */

public class DeviceRebootBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // TODO: start the service here.
        }

    }
}
