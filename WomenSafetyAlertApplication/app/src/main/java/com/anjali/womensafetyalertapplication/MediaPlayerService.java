//package com.anjali.womensafetyalertapplication;
//
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
////import android.support.v4.media.session.v
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
//import android.telephony.SmsManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.media.VolumeProviderCompat;
//
//public class MediaPlayerService extends Service {
//    private MediaSessionCompat mediaSession;
//    private long lastPressed;
//
//    private static final long DOUBLE_PRESS_INTERVAL = 500;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mediaSession = new MediaSessionCompat(this, "PlayerService");
//        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder()
//                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0) //you simulate a player which plays something.
//                .build());
//
//        //this will only work on Lollipop and up, see https://code.google.com/p/android/issues/detail?id=224134
//        VolumeProviderCompat myVolumeProvider =
//                new VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/100, /*initial volume level*/50) {
//                    @Override
//                    public void onAdjustVolume(int direction) {
//                /*
//                -1 -- volume down
//                1 -- volume up
//                0 -- volume button released
//                 */
//                        long pressTime = System.currentTimeMillis();
//                        if(direction==-1) {
//                            if(pressTime-lastPressed<=DOUBLE_PRESS_INTERVAL) {
//                                //Toast.makeText(this, "Volume - pressed twice", Toast.LENGTH_SHORT).show();
//                                int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
//                                SmsManager smsManager = SmsManager.getDefault();
//                                String sent = "SMS_SENT";
//                                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), MY_PERMISSIONS_REQUEST_SEND_SMS, new Intent(sent), 0);
//                                if (HomeActivity.eccontacts_home.size() > 0) {
//                                    for (int i = 0; i < HomeActivity.eccontacts_home.size(); i++) {
//                                        smsManager.sendTextMessage(HomeActivity.eccontacts_home.get(i), null, Common.LastLocation, pi, null);
//                                    }
//                                }
//                            }else{
//                                lastPressed = pressTime;
//                            }
//                            Log.d("LOB", "Volume clicked");
//                        }
//                    }
//                };
//
//        mediaSession.setPlaybackToRemote(myVolumeProvider);
//        mediaSession.setActive(true);
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mediaSession.release();
//    }
//}