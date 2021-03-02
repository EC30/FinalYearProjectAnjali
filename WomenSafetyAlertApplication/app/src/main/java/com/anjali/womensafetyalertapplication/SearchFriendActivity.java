package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFriendActivity extends AppCompatActivity {
    private RecyclerView searchFriendRecyclerView;
    private Toolbar searchFiendToolbar;
    private TextView searchFriendsText;
    private EditText searchEditText;
    static ArrayList<String> friendnumberSearch, friendNameSearch;
    private SearchFriendAdapter searchFriendAdapter;
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

        friendNameSearch=new ArrayList<>();
        friendnumberSearch=new ArrayList<>();
        searchFriendAdapter=new SearchFriendAdapter(SearchFriendActivity.this, friendnumberSearch,friendNameSearch);
        searchFriendRecyclerView.setAdapter(searchFriendAdapter);
        searchFriendRecyclerView.setLayoutManager(new LinearLayoutManager(SearchFriendActivity.this));


        searchFriendsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendnumberSearch.add("jhg");
                friendNameSearch.add("kjjh");
                searchFriendAdapter.notifyDataSetChanged();

            }
        });

    }
}