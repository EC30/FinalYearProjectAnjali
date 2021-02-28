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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyHandlerEC extends AddECActivity {

    public void add_to_db(Context context,String action, String new_contact,String contact_to_update,String phone_logged_in){
        ProgressDialog loadingBar=new ProgressDialog(context);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();


        //loadingBar.setTitle("Adding Emergency Contact...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"emergencycontact.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Updated EC Successfully.")){
                    loadingBar.dismiss();
                    Toast.makeText(context, "Process Successful", Toast.LENGTH_SHORT).show();
                }else if(response.contains("Cannot Update EC")){
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Cannot Add Contact. Try Again Later.";
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }else if(response.contains("read_value_wsaa_ec::")) {
                    loadingBar.dismiss();
                    String[] ec_data=response.split("::");
                    DbHelper db=new DbHelper(context);
                    db.add_wsaa("EC1",ec_data[1]);
                    //Toast.makeText(context, "EC1:"+ec_data[1], Toast.LENGTH_SHORT).show();
                    db.add_wsaa("EC2",ec_data[2]);
                    //Toast.makeText(context, "EC2:"+ec_data[2], Toast.LENGTH_SHORT).show();
                    db.add_wsaa("EC3",ec_data[3]);
                    //Toast.makeText(context, "EC3:"+ec_data[3], Toast.LENGTH_SHORT).show();
                    db.close();
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
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_logged_in", phone_logged_in);
                params.put("action", action);
                params.put("contact_to_update", contact_to_update);
                params.put("new_contact", new_contact);
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
