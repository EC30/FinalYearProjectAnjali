package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.util.Date;

public class Common {
    public static final String KEY_REQUESTING_LOCATION_UPDATES = "LocationUpdateEnable";
    public static String LastLocation="";
    public static String latt="";
    public static String longi="";

    public static String getLocationText(Location mLocation) {
        LastLocation= mLocation == null ? "UnKnown Location" : new StringBuilder()
                .append(mLocation.getLatitude())
                .append("/")
                .append(mLocation.getLongitude())
                .toString();
        return LastLocation;
    }
    public static String getLatitude(Location mLocation){
        latt=mLocation == null ? "UnKnown Location" : new StringBuilder()
                .append(mLocation.getLatitude()).toString();
        return latt;
    }
    public static String getLongitude(Location mLocation){
        longi=mLocation == null ? "UnKnown Location" : new StringBuilder()
                .append(mLocation.getLongitude()).toString();
        return longi;
    }

    public static CharSequence getLocationTitle(MyBackgroundService myBackgroundService) {
//        CharSequence aa="yujeen";
//        return aa;

        return String.format("Location Updated: %l$s", DateFormat.getDateInstance().format(new Date()));
    }

    public static void setRequestingLocationUpdates(Context context, boolean value) {
        PreferenceManager.
                getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES,value)
                .apply();
    }

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES,false);
    }
}
