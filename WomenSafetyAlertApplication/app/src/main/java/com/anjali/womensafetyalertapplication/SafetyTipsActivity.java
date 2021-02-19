package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SafetyTipsActivity extends AppCompatActivity {
    String s1[];
    RecyclerView tipsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_tips);

        tipsRecyclerView=findViewById(R.id.tipsRecyclerView);

        s1=getResources().getStringArray(R.array.SafetyTips);

        TipsAdapter tipsAdapter=new TipsAdapter(this,s1);
        tipsRecyclerView.setAdapter(tipsAdapter);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}