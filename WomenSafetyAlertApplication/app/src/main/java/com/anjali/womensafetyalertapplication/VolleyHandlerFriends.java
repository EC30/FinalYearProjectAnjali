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

import java.util.HashMap;
import java.util.Map;

public class VolleyHandlerFriends extends AddECActivity {
static  String change_adapter_friends="";
    public void process_friend_request(Context context,String phone1, String phone2,String status,String action){
        ProgressDialog loadingBar=new ProgressDialog(context);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();


        //loadingBar.setTitle("Adding Emergency Contact...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"friendHandler.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Friend Request Accepted.")){
                    change_adapter_friends="yes";
                    loadingBar.dismiss();
                    Toast.makeText(context, "Friend Request Accepted", Toast.LENGTH_SHORT).show();

                }else if(response.contains("Cannot Complete Request.")){
                    change_adapter_friends="no";
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Cannot process request. Try Again Later.";
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                }else if(response.contains("Cannot accept request. The friend whose request you are trying to accept already has 5 friends.")){
                    change_adapter_friends="no";
                    loadingBar.dismiss();
                    Toast.makeText(context, "Cannot accept request. The friend whose request you are trying to accept already has 5 friends.", Toast.LENGTH_SHORT).show();

                }
                else if(response.contains("Deleted Successfully.")) {
                    change_adapter_friends="yes";
                    loadingBar.dismiss();
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                }else{
                    change_adapter_friends="no";
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
                params.put("phone1", phone1);
                params.put("action", action);
                params.put("phone2", phone2);
                params.put("status", status);
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
}
