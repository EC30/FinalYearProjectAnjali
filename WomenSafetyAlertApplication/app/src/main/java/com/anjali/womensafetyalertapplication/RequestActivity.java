package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    private RecyclerView friendRequestRecyclerView;
    private Toolbar friendRequestToolbar;
    static ArrayList<String> friendnumberR, friendNameR;
    private FriendRequestAdapter frAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        friendRequestRecyclerView=findViewById(R.id.friendRequestRecyclerView);
        friendRequestToolbar=findViewById(R.id.friendRequestToolbar);
        setSupportActionBar(friendRequestToolbar);
        getSupportActionBar().setTitle("My Request");

        friendNameR=new ArrayList<>();
        friendnumberR=new ArrayList<>();
        frAdapter=new FriendRequestAdapter(RequestActivity.this, friendnumberR,friendNameR);
        friendRequestRecyclerView.setAdapter(frAdapter);
        friendRequestRecyclerView.setLayoutManager(new LinearLayoutManager(RequestActivity.this));

        for(int i=0;i<AddFriendActivity.myrequestsreceived.size();i++){
            //Toast.makeText(this, AddFriendActivity.myrequestsreceived.get(i), Toast.LENGTH_SHORT).show();
            friendnumberR.add(AddFriendActivity.myrequestsreceived.get(i).toString());
            friendNameR.add(AddFriendActivity.myrequestsreceived.get(i).toString());
            frAdapter.notifyDataSetChanged();
        }
    }
}