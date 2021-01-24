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

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private CardView addFriendCardView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


       // phoneLogged=getIntent().getStringExtra("phone_logged");
        //addFriendsCardView=findViewById(R.id.addFriendsCradView);
        drawer=findViewById(R.id.drawer);
        nav_view=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        addFriendCardView=findViewById(R.id.friendsCardView);
        setSupportActionBar(toolbar);
        nav_view.bringToFront();
        ActionBarDrawerToggle toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

       // nav_view.setNavigationItemSelectedListener(this);

        addFriendCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, AddFriendActivity.class);
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

}