package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MyFriendCircle extends AppCompatActivity {
    private RecyclerView friendCircleRecyclerView;
    private Toolbar friendCircleToolbar;
    static FriendCircleAdapter fAdapter;
    static ArrayList<String> friendnumberC, friendNameC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend_circle);

        friendCircleRecyclerView=findViewById(R.id.friendCircleRecyclerView);
        friendCircleToolbar=findViewById(R.id.friendCircleToolbar);
        setSupportActionBar(friendCircleToolbar);
        friendCircleToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MyFriendCircle.this,AddFriendActivity.class);
                startActivity(in);
            }
        });

        friendNameC=new ArrayList<>();
        friendnumberC=new ArrayList<>();
        fAdapter=new FriendCircleAdapter(MyFriendCircle.this, friendnumberC,friendNameC);
        friendCircleRecyclerView.setAdapter(fAdapter);
        friendCircleRecyclerView.setLayoutManager(new LinearLayoutManager(MyFriendCircle.this));

        for(int i=0;i<AddFriendActivity.myfriends.size();i++){
           // Toast.makeText(this, AddFriendActivity.myfriends.get(i), Toast.LENGTH_SHORT).show();
            friendnumberC.add(AddFriendActivity.myfriends.get(i).toString());
            friendNameC.add(AddFriendActivity.myfriends.get(i).toString());
            fAdapter.notifyDataSetChanged();
        }
    }
}