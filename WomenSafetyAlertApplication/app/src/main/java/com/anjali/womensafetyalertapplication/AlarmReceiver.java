package com.anjali.womensafetyalertapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;

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
               // smsManager.sendTextMessage(splitted[i], null, Common.LastLocation, pi, null);
                String SMS_TEXT="https://www.google.com/maps/search/"+Common.LastLocation.split("/")[0]+","+Common.LastLocation.split("/")[1];

                smsManager.sendTextMessage(splitted[i],null,"My current location is at, "+SMS_TEXT+" .Please click on the link to view in browser.",pi,null);
                //smsManager.sendTextMessage(splitted[i],null,"My current location is at, Longitude: "+Common.LastLocation.split("/")[1]+" Latitude: "+Common.LastLocation.split("/")[0],pi,null);
               // this.registerReceiver(this.batterryLevelReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            }
        }

        if (intent.getStringExtra("action_by").equals("WSAA_UPDATE_LOC_TO_DB")) {

            //Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
            if(!Common.LastLocation.contains("UnKnown")) {
                String[] splitted = Common.LastLocation.split("/");
                Log.d("last_loc",Common.LastLocation);
                VolleyHandlerFrLoc vhfl = new VolleyHandlerFrLoc();
                vhfl.update_loc(context, intent.getStringExtra("user_phone"), splitted[0], splitted[1]);
            }

        }
    }
}
