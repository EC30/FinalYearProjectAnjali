package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PermissionActivity extends AppCompatActivity {
    private Button SMSPButton, LocPButton;
    MyBackgroundService mService=null;
    boolean mBound=false;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private int REQUEST_PERMISSIONS_REQUEST_CODE=123;
    private int SMS_REQUEST_PERMISSIONS_REQUEST_CODE=1234;
    private String abcdefghij;

    private static final String TAG = PermissionActivity.class.getSimpleName();
    private ServiceConnection mServiceConnection=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        SMSPButton=findViewById(R.id.SPButton);
        LocPButton=findViewById(R.id.LPButton);
        abcdefghij=getIntent().getStringExtra("phone_logged_main");

        mServiceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                MyBackgroundService.LocalBinder binder=(MyBackgroundService.LocalBinder)iBinder;
                mService=binder.getService();
                mService.requestLocationUpdates();
                mBound=true;
                Toast.makeText(mService, "Location Updates Started", Toast.LENGTH_SHORT).show();
                checkBothGranted();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService=null;
                mBound=false;
            }
        };



        if(checkLocationPermissions()){
            LocPButton.setText("Location Permission Granted");
            LocPButton.setBackgroundColor(Color.GREEN);
            LocPButton.setEnabled(false);
            //setButtonState(Common.requestingLocationUpdates(MainActivity.this));
            bindService(new Intent(PermissionActivity.this, MyBackgroundService.class),
                    mServiceConnection,
                    Context.BIND_AUTO_CREATE);

        }

        if(checkSMSPermission()){
            SMSPButton.setText("SEND SMS PERMISSSION GRANTED");
            SMSPButton.setBackgroundColor(Color.GREEN);
            SMSPButton.setEnabled(false);
            checkBothGranted();
        }

        SMSPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkSMSPermission()) {
                    requestSMSPermission();
                } else {
                    SMSPButton.setText("SEND SMS PERMISSSION GRANTED");
                    SMSPButton.setBackgroundColor(Color.GREEN);
                    SMSPButton.setEnabled(false);
                    checkBothGranted();
                }
            }
        });

        LocPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkLocationPermissions()) {
                    requestPermissions();
                } else {
                    LocPButton.setText("Location Permission Granted");
                    LocPButton.setBackgroundColor(Color.GREEN);
                    LocPButton.setEnabled(false);
                    //setButtonState(Common.requestingLocationUpdates(MainActivity.this));
                    bindService(new Intent(PermissionActivity.this, MyBackgroundService.class),
                            mServiceConnection,
                            Context.BIND_AUTO_CREATE);

                }
            }
        });
    }

    private void checkBothGranted(){

//        if(checkSMSPermission()){
//            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
//        }
//
//        if(checkLocationPermissions()){
//            Toast.makeText(this, "here loc", Toast.LENGTH_SHORT).show();
//        }

        if(checkSMSPermission() && checkLocationPermissions()){
            Intent intent= new Intent(PermissionActivity.this,HomeActivity.class);
            intent.putExtra("phone_logged_main",abcdefghij);
            startActivity(intent);
        }
    }

    private boolean checkSMSPermission(){
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSMSPermission(){
        ActivityCompat.requestPermissions(PermissionActivity.this,
                new String[]{Manifest.permission.SEND_SMS},
                SMS_REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private boolean checkLocationPermissions() {
        int permissionState=PackageManager.PERMISSION_GRANTED;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            permissionState = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionState3 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        return permissionState == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED && permissionState3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        boolean shouldProvideRationale2=false;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            shouldProvideRationale2 =
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }


        if (shouldProvideRationale || shouldProvideRationale2) {

            Snackbar.make(
                    findViewById(R.id.activity_main),
                    "Location permission is needed for core functionality",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    })
                    .show();

        } else {
            startLocationPermissionRequest();
        }
    }

    private void startLocationPermissionRequest() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(PermissionActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }else{
            ActivityCompat.requestPermissions(PermissionActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
                requestPermissions();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocPButton.setText("Location Permission Granted");
                LocPButton.setBackgroundColor(Color.GREEN);
                LocPButton.setEnabled(false);
                //setButtonState(Common.requestingLocationUpdates(MainActivity.this));
                bindService(new Intent(PermissionActivity.this, MyBackgroundService.class),
                        mServiceConnection,
                        Context.BIND_AUTO_CREATE);
                checkBothGranted();
            } else {
                // Permission denied.
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        "Background Location is required for the core functionality of the program. Please go to permission of app from the setting button here and select 'ALLOW ALL THE TIME' for location access. ",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }else if(requestCode == SMS_REQUEST_PERMISSIONS_REQUEST_CODE){
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
                requestPermissions();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SMSPButton.setText("SEND SMS PERMISSSION GRANTED");
                SMSPButton.setBackgroundColor(Color.GREEN);
                SMSPButton.setEnabled(false);
                checkBothGranted();
            } else {
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        "SMS Permission is core functionality of this app. Please allow.",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //PreferenceManager.getDefaultSharedPreferences(this)
          //      .registerOnSharedPreferenceChangeListener(this);
        //EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(mBound){
            unbindService(mServiceConnection);
            mBound=false;

        }
        //PreferenceManager.getDefaultSharedPreferences(this)
          //      .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    protected void onResume() {
        if(checkLocationPermissions()){
            LocPButton.setText("Location Permission Granted");
            LocPButton.setBackgroundColor(Color.GREEN);
            LocPButton.setEnabled(false);
            //setButtonState(Common.requestingLocationUpdates(MainActivity.this));
            bindService(new Intent(PermissionActivity.this, MyBackgroundService.class),
                    mServiceConnection,
                    Context.BIND_AUTO_CREATE);

        }

        if(checkSMSPermission()){
            SMSPButton.setText("SEND SMS PERMISSSION GRANTED");
            SMSPButton.setBackgroundColor(Color.GREEN);
            SMSPButton.setEnabled(false);

        }

        checkBothGranted();
        super.onResume();
    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals(Common.KEY_REQUESTING_LOCATION_UPDATES)){
////            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATES,false));
//        }
//
//    }

    @Subscribe(sticky =true,threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event){
        if(event !=null){
            String data=new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            //Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
        }
    }
}