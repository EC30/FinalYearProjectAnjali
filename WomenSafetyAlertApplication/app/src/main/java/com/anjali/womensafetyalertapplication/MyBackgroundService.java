package com.anjali.womensafetyalertapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;

public class MyBackgroundService extends Service {

    private static final String CHANNEL_ID="my_channel";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = "com.anjali.womensafetyalertapplication"+
            ".started_from_notification";
    private  final IBinder mBinder=new LocalBinder();

    private static final long UPDATE_INTERVAL_IN_MIL=10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MIL=UPDATE_INTERVAL_IN_MIL/2;
    private static final int NOTI_ID=12223;
    private boolean mChangingConfiguration=false;
    private NotificationManager mNotificationManager;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Handler mServiceHandler;
    private Location mLocation;
    static boolean isSMSsent;

    public MyBackgroundService(){

    }

    @Override
    public void onCreate() {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        locationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };

        isSMSsent=false;

        createLocationRequest();
        getLastLocation();

       // Toast.makeText(this, "Background Service Created", Toast.LENGTH_SHORT).show();
        HandlerThread handlerThread=new HandlerThread("Women Safety Alert App");
        handlerThread.start();
        mServiceHandler=new Handler(handlerThread.getLooper());
        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel=new NotificationChannel(CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        final BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration=true;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean startedFromNotification=intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,false);
        if(startedFromNotification){
            removeLocationUpdates();
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    public void removeLocationUpdates() {
        try{
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Common.setRequestingLocationUpdates(this,false);
            stopSelf();
        }catch (SecurityException ex){
            Common.setRequestingLocationUpdates(this,true);
            Log.e("Women Safety Alert App","Lost Location Permission. Couldn't Remove Updates. "+ex);
        }
    }

    private void getLastLocation() {
        try{
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if(task.isSuccessful() && task.getResult() != null){
                                mLocation=task.getResult();
                            }else{
                                Log.e("Women Safety Alert App","Failed to get Location");
                            }
                        }
                    });
        }catch(SecurityException ex){
            Log.e("Women Safety Alert App","Lost Location Permission. "+ex);
        }
    }

    private void createLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MIL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MIL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void onNewLocation(Location lastLocation) {
        mLocation=lastLocation;
        EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));

        //Update Notification If running as foreground service
        if(serviceIsRunningInForeground(this)){
            mNotificationManager.notify(NOTI_ID,getNotification(this));
        }
    }

    private Notification getNotification(Context context) {
        Intent intent=new Intent(this,MainActivity.class);
        String text=Common.getLocationText(mLocation);

        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION,true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(intent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent servicePendingIntent=PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent activityPendingIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

                builder.setContentText(text)
                .setContentTitle("Notification")
                        .setSmallIcon(R.drawable.ic_baseline_launch_24)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());
//        .addAction(R.drawable.ic_baseline_launch_24,"Launch",activityPendingIntent)
//                .addAction(R.drawable.ic_baseline_cancel_24,"Remove",servicePendingIntent)
//.setContentTitle(Common.getLocationTitle(this))
        //Set Channel Id for Android Q
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder.setChannelId(CHANNEL_ID);
        }

        //mNotificationManager.notify(NOTI_ID,builder.build());
        return builder.build();
    }

    private boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service:manager.getRunningServices(Integer.MAX_VALUE))
            if(getClass().getName().equals(service.service.getClassName()))
                if(service.foreground)
                    return true;
        return false;
    }

    public void requestLocationUpdates() {
        Common.setRequestingLocationUpdates(this,true);
        startService(new Intent(getApplicationContext(), com.anjali.womensafetyalertapplication.MyBackgroundService.class));
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
        }catch (SecurityException ex){
            Log.e("Women Safety Alert App","Lost location permission. Can't Request. "+ex);
        }
    }

    public class LocalBinder extends Binder {
        com.anjali.womensafetyalertapplication.MyBackgroundService getService() {return  com.anjali.womensafetyalertapplication.MyBackgroundService.this;}
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration=false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration=false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {

        if(!mChangingConfiguration && Common.requestingLocationUpdates(this)){
            startForeground(NOTI_ID,getNotification(this));
        }
        return true;
    }


    @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacks(null);
        super.onDestroy();
    }
}
