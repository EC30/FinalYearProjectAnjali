package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFriendActivity extends AppCompatActivity {
    private RecyclerView searchFriendRecyclerView;
    private Toolbar searchFiendToolbar;
    private CountryCodePicker countryPickerSearch;
    private TextView searchFriendsText;
    private EditText searchEditText;
    static ArrayList<String> friendnumberSearch, friendNameSearch;
    static SearchFriendAdapter searchFriendAdapter;
    private String url;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        searchFriendRecyclerView=findViewById(R.id.searchFriendRecyclerView);
        searchFiendToolbar=findViewById(R.id.searchFriendToolbar);
        searchFriendsText=findViewById(R.id.searchFriendsText);
        searchEditText=findViewById(R.id.searchEditText);
        countryPickerSearch=findViewById(R.id.countryPickerSearch);
        loadingBar=new ProgressDialog(this);

        setSupportActionBar(searchFiendToolbar);
       // getSupportActionBar().setTitle("Search Friend");
        searchFiendToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SearchFriendActivity.this,AddFriendActivity.class);
                startActivity(in);
            }
        });

        friendNameSearch=new ArrayList<>();
        friendnumberSearch=new ArrayList<>();
        searchFriendAdapter=new SearchFriendAdapter(SearchFriendActivity.this, friendnumberSearch,friendNameSearch);
        searchFriendRecyclerView.setAdapter(searchFriendAdapter);
        searchFriendRecyclerView.setLayoutManager(new LinearLayoutManager(SearchFriendActivity.this));


        searchFriendsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //friendnumberSearch.add("jhg");
                //friendNameSearch.add("kjjh");
                //searchFriendAdapter.notifyDataSetChanged();
                friendNameSearch.clear();
                friendnumberSearch.clear();
                searchFriendAdapter.notifyDataSetChanged();
                String cc=countryPickerSearch.getSelectedCountryCodeWithPlus().toString();
                String num=searchEditText.getText().toString();
                //Toast.makeText(SearchFriendActivity.this, cc+num, Toast.LENGTH_SHORT).show();
                //if("1::"+cc+num )
                if(AddFriendActivity.myfriends.contains("1::"+cc+num) || AddFriendActivity.myfriends.contains("2::"+cc+num)){
                //if(AddFriendActivity.myfriends.contains("1::"+CCnum) || AddFriendActivity.myfriends.contains("2::"+num)){
                    Toast.makeText(SearchFriendActivity.this, "You are already friends with this person.", Toast.LENGTH_SHORT).show();
                }else if(AddFriendActivity.myrequestssent.contains(cc+num)){
                    Toast.makeText(SearchFriendActivity.this, "You have already sent friend request to this user.", Toast.LENGTH_SHORT).show();
                }else if(AddFriendActivity.myrequestsreceived.contains(cc+num)){
                    Toast.makeText(SearchFriendActivity.this, "You have already received friend request from this user.", Toast.LENGTH_SHORT).show();
                }else if(AddFriendActivity.phone_logged.equals(cc+num)) {
                    Toast.makeText(SearchFriendActivity.this, "You have searched yourself.", Toast.LENGTH_SHORT).show();
                }else{
                    UrlClass myurl=new UrlClass();
                    url = myurl.getUrl();
                    loadingBar.setTitle("Searching User");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    checkPhoneExists(url,cc+num);
                }
            }


        });

    }

    public void checkPhoneExists(String url,String phone2){
        String postUrl = url+"checkUserExists.php";

        RequestQueue requestQueue = Volley.newRequestQueue(SearchFriendActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("User Exists.")){
                    //textError.setText("* Phone Number Already Exists.");
                    loadingBar.dismiss();
                    friendnumberSearch.add(phone2);
                    friendNameSearch.add("User");
                    searchFriendAdapter.notifyDataSetChanged();

                }else if(response.contains("User Not Exists.")){
                    loadingBar.dismiss();
                    Toast.makeText(SearchFriendActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                    //initiateOTP(phone2);
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(SearchFriendActivity.this, "* Some Internal Error Occured. Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                Toast.makeText(SearchFriendActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_to_check", phone2);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}