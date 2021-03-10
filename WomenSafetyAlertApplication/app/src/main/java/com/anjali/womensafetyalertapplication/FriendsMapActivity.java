package com.anjali.womensafetyalertapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FriendsMapActivity extends FragmentActivity implements OnMapReadyCallback {

    static GoogleMap mMap;
    String friend_data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(AddFriendActivity.myfriends.size()>0) {
            for (int i = 0; i < AddFriendActivity.myfriends.size(); i++) {
                friend_data += AddFriendActivity.myfriends.get(i).substring(3) + "@@@";
            }
            friend_data=friend_data.substring(0,friend_data.length()-3);
            run_count_down();


        }else{
            Toast.makeText(this, "You don't have any friends.", Toast.LENGTH_SHORT).show();
        }
    }

    public void run_count_down(){
        new CountDownTimer(360000, 60000) {
            public void onFinish() {
                run_count_down();
            }

            public void onTick(long millisUntilFinished) {

                VolleyHandlerFrLoc vhfl=new VolleyHandlerFrLoc();
                vhfl.read_loc(FriendsMapActivity.this,AddFriendActivity.phone_logged,friend_data);
            }

        }.start();
    }
}