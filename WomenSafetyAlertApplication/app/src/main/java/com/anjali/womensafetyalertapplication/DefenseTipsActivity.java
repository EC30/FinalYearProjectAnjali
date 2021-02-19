package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class DefenseTipsActivity extends AppCompatActivity {
    String s2[],s3[];
    Integer s4[]={R.drawable.target,R.drawable.damagingmoves,R.drawable.defense3,R.drawable.defense4,R.drawable.defense5,R.drawable.defense6};
    RecyclerView defenseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defense_tips);

        defenseRecyclerView=findViewById(R.id.defenseRecyclerView);

        s2=getResources().getStringArray(R.array.defenseTipsTitle);
        s3=getResources().getStringArray(R.array.defenseTipsBody);
        // s2=getResources().getStringArray(R.array.SafetyTips);

        DefenseAdapter defenseAdapter=new DefenseAdapter(this,s2,s3,s4);
        defenseRecyclerView.setAdapter(defenseAdapter);
        defenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}