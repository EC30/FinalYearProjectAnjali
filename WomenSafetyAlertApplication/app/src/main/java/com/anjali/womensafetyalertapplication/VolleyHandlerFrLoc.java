package com.anjali.womensafetyalertapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class VolleyHandlerFrLoc extends AddFriendActivity {

    public void read_loc(Context context,String phone_logged_in,String friends){
        ProgressDialog loadingBar=new ProgressDialog(context);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();


        //loadingBar.setTitle("Adding Emergency Contact...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"loc_handler.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Resultoffriendsloc-->")){
                    //Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    if(!response.contains("not found")) {
                        String[] friends_splitted=response.substring(21).split("::::");
                        FriendsMapActivity.mMap.clear();
                        for(int i=0; i<friends_splitted.length;i++) {
                            String[] individual_split=friends_splitted[i].split("::");

                            LatLng friend_loc = new LatLng(Double.valueOf(individual_split[1]), Double.valueOf(individual_split[2]));
                            //new LatLng()
                            FriendsMapActivity.mMap.addMarker(new MarkerOptions().position(friend_loc).title(AddFriendActivity.friendName.get(individual_split[0])));
                            FriendsMapActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(friend_loc));
                            Toast.makeText(context, "Process Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                        loadingBar.dismiss();
                        String error="* Some Internal Error Occured. Try Again Later.";
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //content.setText("Error : "+error);
                loadingBar.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_logged_in", phone_logged_in);
                params.put("friends_data", friends);
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

    public void  update_loc(Context context,String phone_logged_in,String lat,String lon){
        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();

        String postUrl = url+"update_loc.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_logged_in", phone_logged_in);
                params.put("latitude", lat);
                params.put("longitude", lon);
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
