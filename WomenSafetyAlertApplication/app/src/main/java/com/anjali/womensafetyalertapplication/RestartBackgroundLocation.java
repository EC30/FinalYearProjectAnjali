package com.anjali.womensafetyalertapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RestartBackgroundLocation extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(RestartBackgroundLocation.class.getSimpleName(), "Service Stopped, but this is a never ending service.");
        context.startService(new Intent(context, GetBackgroundLocation.class));;
    }
}
