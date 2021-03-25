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

public class RequestActivity extends AppCompatActivity {

    private RecyclerView friendRequestRecyclerView;
    private Toolbar friendRequestToolbar;
    static ArrayList<String> friendnumberR, friendNameR;
    static FriendRequestAdapter frAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        friendRequestRecyclerView=findViewById(R.id.friendRequestRecyclerView);
        friendRequestToolbar=findViewById(R.id.friendRequestToolbar);
        setSupportActionBar(friendRequestToolbar);
        friendRequestToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(RequestActivity.this,AddFriendActivity.class);
                startActivity(in);
            }
        });

        friendNameR=new ArrayList<>();
        friendnumberR=new ArrayList<>();
        frAdapter=new FriendRequestAdapter(RequestActivity.this, friendnumberR,friendNameR);
        friendRequestRecyclerView.setAdapter(frAdapter);
        friendRequestRecyclerView.setLayoutManager(new LinearLayoutManager(RequestActivity.this));

        for(int i=0;i<AddFriendActivity.myrequestsreceived.size();i++){
            //Toast.makeText(this, AddFriendActivity.myrequestsreceived.get(i), Toast.LENGTH_SHORT).show();
            friendnumberR.add(AddFriendActivity.myrequestsreceived.get(i).toString());
            friendNameR.add(AddFriendActivity.friendName.get(AddFriendActivity.myrequestsreceived.get(i).toString()));
            frAdapter.notifyDataSetChanged();
        }
    }
}