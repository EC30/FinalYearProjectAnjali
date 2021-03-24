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

    public void process_to_db(Context context,String action, String new_contact,String contact_to_update,String phone_logged_in){
        ProgressDialog loadingBar=new ProgressDialog(context);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();


        //loadingBar.setTitle("Adding Emergency Contact...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"ecdemo.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Updated EC Successfully.")){
                    loadingBar.dismiss();
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }else if(response.contains("Cannot Complete Request.")){
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Cannot Add Contact. Try Again Later.";
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }else if(response.contains("read_value_wsaa_ec::")) {
                    loadingBar.dismiss();
                    String[] ec_data=response.split("::");
                    DbHelper db=new DbHelper(context);
                    //Toast.makeText(context, String.valueOf(ec_data.length), Toast.LENGTH_SHORT).show();
                    for (int a=1; a<ec_data.length;a++){
                        db.add_wsaa("EC"+String.valueOf(a),ec_data[a]);
                        //Toast.makeText(context, "EC"+String.valueOf(a)+":"+ec_data[a], Toast.LENGTH_SHORT).show();
                    }

                    if(ec_data.length<4){
                        int n=4-ec_data.length;
                        for(int b=ec_data.length;b<4;b++){
                            //Toast.makeText(context, "EC"+String.valueOf(b), Toast.LENGTH_SHORT).show();
                            db.add_wsaa("EC"+String.valueOf(b),"NULL-VAL");
                        }
                    }
                    //Toast.makeText(context, "EC1:"+ec_data[1], Toast.LENGTH_SHORT).show();
                    //db.add_wsaa("EC2",ec_data[2]);
                    //Toast.makeText(context, "EC2:"+ec_data[2], Toast.LENGTH_SHORT).show();
                    //db.add_wsaa("EC3",ec_data[3]);
                    //Toast.makeText(context, "EC3:"+ec_data[3], Toast.LENGTH_SHORT).show();
                    db.close();
                }else if (response.contains("Deleted Successfully.")){
                    loadingBar.dismiss();
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                }else if(response.contains("Added Successfully.")){
                    loadingBar.dismiss();
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
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
                params.put("action", action);
                params.put("ecphone", contact_to_update);
                params.put("new_ecphone", new_contact);
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
