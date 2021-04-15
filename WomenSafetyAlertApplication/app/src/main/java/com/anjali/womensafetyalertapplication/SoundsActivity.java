package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SoundsActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private Button btn_play,btn_pause,btn_play2,btn_pause2,btn_play3,btn_pause3;
    Integer currentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);

        Toolbar soundsToolbar=findViewById(R.id.soundsToolbar);
        setSupportActionBar(soundsToolbar);
        soundsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SoundsActivity.this,HomeActivity.class);
                intent.putExtra("phone_logged_main",HomeActivity.phone_logged_home);
                intent.putExtra("fullname_logged_main",HomeActivity.fullname_logged_home);
                startActivity(intent);
            }
        });

        ArrayList<Integer> sound=new ArrayList<>();
        sound.add(0,R.raw.policecarsiren);
        sound.add(1,R.raw.ambulancesiren);
        sound.add(2,R.raw.emergencycar);


//        for(int i=0;i<=sound.size();i++){
//            mp=MediaPlayer.create(getApplicationContext(),sound.get(0));
//        }

        btn_play=findViewById(R.id.bt_play);
        btn_pause=findViewById(R.id.bt_pause);
        btn_play2=findViewById(R.id.bt_play2);
        btn_pause2=findViewById(R.id.bt_pause2);
        btn_play3=findViewById(R.id.bt_play3);
        btn_pause3=findViewById(R.id.bt_pause3);



        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                btn_play.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp=MediaPlayer.create(getApplicationContext(),sound.get(0));
                mp.start();
                btn_play.setVisibility(View.INVISIBLE);
                btn_pause.setVisibility(View.VISIBLE);
            }
        });

        btn_pause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                btn_play2.setVisibility(View.VISIBLE);
                btn_pause2.setVisibility(View.INVISIBLE);
            }
        });
        btn_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp=MediaPlayer.create(getApplicationContext(),sound.get(1));
                mp.start();
                btn_play2.setVisibility(View.INVISIBLE);
                btn_pause2.setVisibility(View.VISIBLE);

            }
        });
        btn_pause3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}