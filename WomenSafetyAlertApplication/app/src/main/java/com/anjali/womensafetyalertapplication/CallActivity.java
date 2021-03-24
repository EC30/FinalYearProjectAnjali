package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import pl.droidsonroids.gif.GifImageView;

public class CallActivity extends AppCompatActivity {
    private GifImageView gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        gif=findViewById(R.id.gif);
        //final MediaPlayer rings = MediaPlayer.create(FakeCall.this, R.raw.ringtone);
        // ring.start();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringtone = RingtoneManager.getRingtone(CallActivity.this, alarmUri);
        ringtone.play();
//        SystemClock.sleep(20000);
//        ringtone.stop();
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                Intent intent=new Intent(CallActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}