package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private CardView addFriendCardView,viewLocation;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawer=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        addFriendCardView=findViewById(R.id.friendsCardView);
        setSupportActionBar(toolbar);
        nav_view.bringToFront();
        viewLocation=findViewById(R.id.viewLocation);
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        nav_view.setNavigationItemSelectedListener(HomeActivity.this);
       // nav_view.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        nav_view.setCheckedItem(R.id.nav_home);

        addFriendCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, AddFriendActivity.class);
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
                Intent intent2=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}