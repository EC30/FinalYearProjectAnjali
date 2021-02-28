package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private CardView addFriendCardView,viewLocation,addEmergencyContactCardView;
    private Toolbar toolbar;
    private TextView loggedUserPhone, loggedUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String phone_logged2=getIntent().getStringExtra("phone_logged");

//        DbHelper db=new DbHelper(HomeActivity.this);
//        db.delete_wsaa();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        drawer=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        loggedUserName=headerView.findViewById(R.id.loggedUserName);
        loggedUserPhone=headerView.findViewById(R.id.loggedUserPhone);
        addFriendCardView=findViewById(R.id.friendsCardView);
        addEmergencyContactCardView=findViewById(R.id.addEmergencyContactCardView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        nav_view.bringToFront();
        viewLocation=findViewById(R.id.viewLocation);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();



        nav_view.setNavigationItemSelectedListener(HomeActivity.this);
       // nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        nav_view.setCheckedItem(R.id.nav_home);

        loggedUserPhone.setText(phone_logged2);

        addFriendCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, AddFriendActivity.class);
                intent.putExtra("phone_logged",phone_logged2);
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
                intent.putExtra("my_phone",phone_logged2);
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
                editor.commit();
                Intent intent2=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}