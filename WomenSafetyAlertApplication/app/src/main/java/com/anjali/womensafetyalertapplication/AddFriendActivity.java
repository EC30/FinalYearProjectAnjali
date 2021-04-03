package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddFriendActivity extends AppCompatActivity {
    private CardView myCircleCardView,viewFriendsLocationCardView,viewRequestCardView, addFriendsCardView,sentRequestCardView;
    static ArrayList<String> myfriends,myrequestsreceived,myrequestssent;
    ProgressDialog loadingBar;
    static String phone_logged;
    static Map<String, String> friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        friendName= new HashMap<String, String>();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFriendActivity.this,HomeActivity.class);
                intent.putExtra("phone_logged_main",HomeActivity.phone_logged_home);
                intent.putExtra("fullname_logged_main",HomeActivity.fullname_logged_home);
                startActivity(intent);
            }
        });

        viewFriendsLocationCardView=findViewById(R.id.viewFriendsLocationCardView);
        myCircleCardView=findViewById(R.id.myCircleCardView);
        viewRequestCardView=findViewById(R.id.viewRequestCardView);
        addFriendsCardView=findViewById(R.id.addFriendsCardView);
        sentRequestCardView=findViewById(R.id.sentRequestCardView);
        phone_logged=HomeActivity.phone_logged_home;
        loadingBar=new ProgressDialog(AddFriendActivity.this);

        myfriends= new ArrayList<>();
        myrequestsreceived= new ArrayList<>();
        myrequestssent= new ArrayList<>();

        myCircleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddFriendActivity.this, MyFriendCircle.class);
                in.putExtra("phone_logged",phone_logged);
                startActivity(in);
            }
        });
        viewRequestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddFriendActivity.this, RequestActivity.class);
                in.putExtra("phone_logged",phone_logged);
                startActivity(in);
            }
        });
        addFriendsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddFriendActivity.this, SearchFriendActivity.class);
                in.putExtra("phone_logged",phone_logged);
                startActivity(in);
            }
        });
        sentRequestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(AddFriendActivity.this, SentRequestActivity.class);
                in.putExtra("phone_logged",phone_logged);
                startActivity(in);
            }
        });

        viewFriendsLocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddFriendActivity.this, FriendsMapActivity.class);
                startActivity(intent);
            }
        });

        get_friends(phone_logged);

        //Toast.makeText(this, phone_logged, Toast.LENGTH_SHORT).show();


    }

    public void get_friends(String phone_logged_in){
        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();


        loadingBar.setTitle("Getting Friends Info...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"friends.php";
        RequestQueue requestQueue = Volley.newRequestQueue(AddFriendActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Resultoffriends-->")){
                    loadingBar.dismiss();
                    if(!response.substring(18).equals("not found")){
                        String[] friends_splitted=response.substring(18).split("::::");
                        for(int i=0; i<friends_splitted.length;i++){
                            String[] individual_split=friends_splitted[i].split("::");

                            if(individual_split[2].equals("verified")){
                                if(!individual_split[0].equals(phone_logged_in)){
                                    myfriends.add("1::"+individual_split[0]);
                                    friendName.put(individual_split[0],individual_split[3]);
                                }else if(!individual_split[1].equals(phone_logged_in)){
                                    myfriends.add("2::"+individual_split[1]);
                                    friendName.put(individual_split[1],individual_split[3]);
                                }
                            }else if(individual_split[2].equals("pending")){
                                if(individual_split[0].equals(phone_logged_in)){
                                    myrequestssent.add(individual_split[1]);
                                    friendName.put(individual_split[1],individual_split[3]);
                                }else if(individual_split[1].equals(phone_logged_in)){
                                    myrequestsreceived.add(individual_split[0]);
                                    friendName.put(individual_split[0],individual_split[3]);
                                }
                            }else{

                            }
                        }
                    }else{
                        Toast.makeText(AddFriendActivity.this, "No Friend Activity Found.", Toast.LENGTH_SHORT).show();
                    }

//                    Toast.makeText(AddFriendActivity.this, "Friend  :"+myfriends.get(0), Toast.LENGTH_SHORT).show();
//                    if(myrequestssent.size()>0){
//                    Toast.makeText(AddFriendActivity.this, "Sent Request:  "+myrequestssent.get(0), Toast.LENGTH_SHORT).show();}
//                    if(myrequestsreceived.size()>0){Toast.makeText(AddFriendActivity.this, "Received Request:  "+myrequestsreceived.get(0), Toast.LENGTH_SHORT).show();}

                }else{
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Try Again Later.";
                    Toast.makeText(AddFriendActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //content.setText("Error : "+error);
                Toast.makeText(AddFriendActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_logged_in", phone_logged_in);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                //params.put("Cookie", cookie);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.nav_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id=item.getItemId();
//        if(id == R.id.lShare){
//            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
//        }
//        if(id == R.id.lStop){
//            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
//        }
//
//        return true;
//
//    }
}