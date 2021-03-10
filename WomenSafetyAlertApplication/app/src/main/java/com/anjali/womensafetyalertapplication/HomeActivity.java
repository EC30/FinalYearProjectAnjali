package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private CardView addFriendCardView,viewLocation,addEmergencyContactCardView, followMe,fakecall,sounds;
    private Toolbar toolbar;
    private RelativeLayout followMeRelativeLayout;
    private TextView loggedUserPhone, loggedUserName;
    static String phone_logged_home,fullname_logged_home;
    static ArrayList<String> eccontacts_home;
    private static final String EXTRA_STARTED_FROM_NOTIFICATION2 = "com.anjali.womensafetyalertapplication"+
            ".started_from_notification";
    private NotificationManager mNotificationManager;
    private static String FOLLOW_CHANNEL_ID="follow_up_channel";
    LocationManager locationManager ;
    boolean GpsStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phone_logged_home=getIntent().getStringExtra("phone_logged_main");
        fullname_logged_home=getIntent().getStringExtra("fullname_logged_main");
        //Toast.makeText(HomeActivity.this, phone_logged_home, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, phone_logged_home, Toast.LENGTH_SHORT).show();

//        DbHelper db=new DbHelper(HomeActivity.this);
//        db.delete_wsaa();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        drawer=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        followMeRelativeLayout=findViewById(R.id.followMeRelativeLayout);
        loggedUserName=headerView.findViewById(R.id.loggedUserName);
        loggedUserPhone=headerView.findViewById(R.id.loggedUserPhone);
        addFriendCardView=findViewById(R.id.friendsCardView);
        addEmergencyContactCardView=findViewById(R.id.addEmergencyContactCardView);
        followMe=findViewById(R.id.followMe);
        fakecall=findViewById(R.id.fakecall);
        sounds=findViewById(R.id.sounds);

        eccontacts_home=new ArrayList<>();

        CheckGpsStatus();

        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel mChannel=new NotificationChannel(FOLLOW_CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent int1 = new Intent(HomeActivity.this, AlarmReceiver.class);
        int1.putExtra("action_by", "WSAA_UPDATE_LOC_TO_DB");
        int1.putExtra("user_phone",phone_logged_home);
        PendingIntent pi2 = PendingIntent.getBroadcast(HomeActivity.this, 123678, int1, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi2);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2 * 1000L, 60 * 1000, pi2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        nav_view.bringToFront();
        viewLocation=findViewById(R.id.viewLocation);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        if(MyBackgroundService.isSMSsent){
            followMeRelativeLayout.setBackgroundColor(Color.GREEN);
            //followMeRelativeLayout.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.coloredborder));
        }
        //Toast.makeText(this, String.valueOf(MyBackgroundService.isSMSsent), Toast.LENGTH_SHORT).show();

        nav_view.setNavigationItemSelectedListener(HomeActivity.this);
       // nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        nav_view.setCheckedItem(R.id.nav_home);

        loggedUserPhone.setText(phone_logged_home);
        loggedUserName.setText(fullname_logged_home);
        //loggedUserPhone.setText("mjhgf");

        DbHelper db=new DbHelper(HomeActivity.this);
        Cursor cursor =db.read_wsaa();
        if(cursor.getCount() == 0){
            //Toast.makeText(HomeActivity.this, "No Emergency Contacts found to send SMS. Please Add Emergency Contacts First.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                if(cursor.getString(0).contains("EC")){
                    if(cursor.getString(1).equals("NULL-VAL")) {
                        //Toast.makeText(HomeActivity.this, "No Emergency Contacts found to send SMS. Please Add Emergency Contacts First.", Toast.LENGTH_SHORT).show();
                    }else{
                        //followMe.setBackgroundColor(Color.GREEN);
                        String[] splited=cursor.getString(1).split("@@");
                        String cc=splited[0];
                        String emergencyContact=splited[1];
                        eccontacts_home.add(cc+emergencyContact);
                    }
                }
            }
        }
        db.close();

        followMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyBackgroundService.isSMSsent){
                    MyBackgroundService.isSMSsent=false;
                    followMeRelativeLayout.setBackgroundColor(Color.WHITE);
                    followMeRelativeLayout.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.coloredborder));
                    AlarmManager am=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent i = new Intent(HomeActivity.this, AlarmReceiver.class);
                    PendingIntent pi = PendingIntent.getBroadcast(HomeActivity.this, 1010, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    am.cancel(pi);
                    Toast.makeText(HomeActivity.this, "Sending Follow Up SMS To Emergency Contacts is Stopped.", Toast.LENGTH_SHORT).show();
                    mNotificationManager.cancel(152207);
                } else {
                    if (eccontacts_home.size() > 0) {
                        followMeRelativeLayout.setBackgroundColor(Color.GREEN);
                        //followMeRelativeLayout.setBackground(ContextCompat.getDrawable(HomeActivity.this,R.drawable.coloredborder));
                        String all_contacts = "";
                        for (int i = 0; i < eccontacts_home.size(); i++) {
                            //Toast.makeText(HomeActivity.this, eccontacts_home.get(i), Toast.LENGTH_SHORT).show();
                            all_contacts += eccontacts_home.get(i) + "@@@@";
                        }
                        all_contacts = all_contacts.substring(0, all_contacts.length() - 4);
//                    String[] splitted=all_contacts.split("@@@@");
//                    for(int i=0;i<splitted.length;i++){
//                    Toast.makeText(HomeActivity.this, splitted[i], Toast.LENGTH_SHORT).show();}

                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent i = new Intent(HomeActivity.this, AlarmReceiver.class);
                        i.putExtra("action_by", "Follow_Me_WSAA");
                        i.putExtra("contact_list", all_contacts);
                        PendingIntent pi = PendingIntent.getBroadcast(HomeActivity.this, 1010, i, PendingIntent.FLAG_UPDATE_CURRENT);
                        MyBackgroundService.isSMSsent=true;
                        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2 * 1000L, 2 * 60 * 1000, pi);
                        mNotificationManager.notify(152207,getNotification(HomeActivity.this));

                        Toast.makeText(HomeActivity.this, "Your Location is being sent via SMS to your emergency contacts every 2 mins.", Toast.LENGTH_SHORT).show();
                    } else {
                        followMe.setBackgroundColor(Color.WHITE);
                        Toast.makeText(HomeActivity.this, "No Emergency Contacts found to send SMS. Please Add Emergency Contacts First.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        fakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addFriendCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, AddFriendActivity.class);
                //intent.putExtra("phone_logged",phone_logged_home);
                //intent.putExtra()
                startActivity(intent);
            }
        });

        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,ViewingMapActivity.class);
                startActivity(intent);
            }
        });

        addEmergencyContactCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,AddECActivity.class);
                //intent.putExtra("my_phone",phone_logged_home);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_safetyTips:
                Intent intent=new Intent(HomeActivity.this,SafetyTipsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_defenseTips:
                Intent intent1=new Intent(HomeActivity.this,DefenseTipsActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences("LOGIN_WSAA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user_logged");
                editor.remove("fullname_logged_in");
                editor.commit();
                Intent intent2=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private Notification getNotification(Context context) {
        Intent intent=new Intent(this,MainActivity.class);
        String text="Follow Up Location Detail is being Sent Every 2 Minutes.";

        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION2,true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(intent);
//
//        // Get a PendingIntent containing the entire back stack.
//        PendingIntent notificationPendingIntent =
//                stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        PendingIntent servicePendingIntent=PendingIntent.getService(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent activityPendingIntent=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

        builder.setContentText(text)
                .setContentTitle("Notification")
                .setSmallIcon(R.drawable.ic_baseline_launch_24)
                .setContentIntent(activityPendingIntent)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());
    //Set Channel Id for Android Q
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder.setChannelId(FOLLOW_CHANNEL_ID);
        }

        //mNotificationManager.notify(NOTI_ID,builder.build());
        return builder.build();
    }

    public void CheckGpsStatus(){
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == true) {
           // Toast.makeText(this, "GPS ENABLED.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "GPS DISABLED !!", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1=new AlertDialog.Builder(this);
            builder1.setTitle("WARNING !!");
            builder1.setMessage("You must enable GPS for the proper functioning of this application. Press OK to enable GPS.");
            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent1);
                }
            });
            builder1.setCancelable(false);
            builder1.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckGpsStatus();
    }

}