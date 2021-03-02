package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SentRequestActivity extends AppCompatActivity {
    private RecyclerView friendRequestSentRecyclerView;
    private Toolbar friendRequestSentToolbar;
    static ArrayList<String> friendnumberS, friendNameS;
    private FriendRequestSentAdapter frsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_request);

        friendRequestSentRecyclerView=findViewById(R.id.friendRequestSentRecyclerView);
        friendRequestSentToolbar=findViewById(R.id.friendRequestSentToolbar);
        setSupportActionBar(friendRequestSentToolbar);
        getSupportActionBar().setTitle("Sent Request");

        friendNameS=new ArrayList<>();
        friendnumberS=new ArrayList<>();
        frsAdapter=new FriendRequestSentAdapter(SentRequestActivity.this, friendnumberS,friendNameS);
        friendRequestSentRecyclerView.setAdapter(frsAdapter);
        friendRequestSentRecyclerView.setLayoutManager(new LinearLayoutManager(SentRequestActivity.this));

        for(int i=0;i<AddFriendActivity.myrequestssent.size();i++){
            friendnumberS.add(AddFriendActivity.myrequestssent.get(i).toString());
            friendNameS.add(AddFriendActivity.myrequestssent.get(i).toString());
            frsAdapter.notifyDataSetChanged();
        }
    }
}