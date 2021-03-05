package com.anjali.womensafetyalertapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getStringExtra("action_by").equals("Follow_Me_WSAA")) {
            //Log.d("Location",Common.LastLocation);
            //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
            SmsManager smsManager = SmsManager.getDefault();
            String sent = "SMS_SENT";
            //String aa=intent.getStringExtra("contact_list");
            String[] splitted = intent.getStringExtra("contact_list").split("@@@@");

            MyBackgroundService.isSMSsent = true;

            PendingIntent pi = PendingIntent.getBroadcast(context, MY_PERMISSIONS_REQUEST_SEND_SMS, new Intent(sent), 0);
            for (int i = 0; i < splitted.length; i++) {
                smsManager.sendTextMessage(splitted[i], null, Common.LastLocation, pi, null);
            }
        }
    }
}
