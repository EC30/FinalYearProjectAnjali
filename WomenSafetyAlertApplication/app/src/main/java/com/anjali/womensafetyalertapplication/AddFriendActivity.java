package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddFriendActivity extends AppCompatActivity {
    private CardView viewLocationCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        viewLocationCardView=findViewById(R.id.viewLocationCardView);
        viewLocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFriendActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}