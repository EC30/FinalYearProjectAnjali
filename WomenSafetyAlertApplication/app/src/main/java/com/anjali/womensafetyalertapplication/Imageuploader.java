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

public class Imageuploader extends HomeActivity{
    public void process_image(Context context, String action, String uploaded_image, String phone_logged_in){
        ProgressDialog loadingBar=new ProgressDialog(context);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();

        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"fileupload.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Updated EC Successfully.")){
                    loadingBar.dismiss();
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
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
                params.put("uploaded_image", uploaded_image);
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
