package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ResetPasswordActivity extends AppCompatActivity {
    private String phone,passwordNew,confirmPassword;
    private EditText pass1,pass2;
    private ProgressDialog loadingBar;
    private Button resetPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        pass1=findViewById(R.id.input_reset_password);
        pass2=findViewById(R.id.input_confirmResetPassword);
        resetPassBtn=findViewById(R.id.btn_resetPassword);

        loadingBar=new ProgressDialog(this);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();

        phone=getIntent().getStringExtra("phone_logged");

        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordNew=pass1.getText().toString();
                confirmPassword=pass2.getText().toString();
                if(passwordNew.length()<8){
                    Toast.makeText(ResetPasswordActivity.this, "Password must be at least 8 Characters.", Toast.LENGTH_SHORT).show();
                }else if(!passwordNew.equals(confirmPassword)){
                    Toast.makeText(ResetPasswordActivity.this, "Password and Confirm Password don't match.", Toast.LENGTH_SHORT).show();
                }else{
                    //update
                    loadingBar.setMessage("Please Wait");
                    loadingBar.setTitle("Resetting Password.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    updatePassword(url,phone,passwordNew);
                }
            }
        });


   }

    private void updatePassword(String url,String phone2,String passwd){
        String postUrl = url+"forgotpassword.php";
        Toast.makeText(this, postUrl, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(ResetPasswordActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingBar.dismiss();
                if(response.contains("Password Updated Successfully.")){
                    Toast.makeText(ResetPasswordActivity.this, "Password Updated Successfully. Please Login.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if(response.contains("Cannot update password.")){
                    Toast.makeText(ResetPasswordActivity.this, "Sorry, Cannot Reset Password. Try again Later.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this, "Some Internal Error Occured. Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                Toast.makeText(ResetPasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_logged2", phone2);
                params.put("password_new",passwd);
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