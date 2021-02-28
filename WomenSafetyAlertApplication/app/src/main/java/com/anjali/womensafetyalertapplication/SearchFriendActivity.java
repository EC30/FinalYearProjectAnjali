package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SearchFriendActivity extends AppCompatActivity {
    private RecyclerView searchFriendRecyclerView;
    private Toolbar searchFiendToolbar;
    private TextView searchFriendsText;
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        searchFriendRecyclerView=findViewById(R.id.searchFriendRecyclerView);
        searchFiendToolbar=findViewById(R.id.searchFriendToolbar);
        searchFriendsText=findViewById(R.id.searchFriendsText);
        searchEditText=findViewById(R.id.searchEditText);

        setSupportActionBar(searchFiendToolbar);
        getSupportActionBar().setTitle("Search Friend");


    }
}