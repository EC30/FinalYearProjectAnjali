package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    private CountryCodePicker countryPickerForgotPass;
    private EditText phoneForgotpass;
    private Button continueForgotPass;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        countryPickerForgotPass=findViewById(R.id.fCountryPicker);
        phoneForgotpass=findViewById(R.id.fphoneText);
        continueForgotPass=findViewById(R.id.btn_f_continue);
        loadingBar=new ProgressDialog(this);

        UrlClass myurl = new UrlClass();
        String url = myurl.getUrl();

        continueForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneforgot=countryPickerForgotPass.getSelectedCountryCodeWithPlus().toString()+phoneForgotpass.getText().toString();
                loadingBar.setMessage("Please Wait");
                loadingBar.setTitle("Checking If User Exists.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                checkPhoneExists(url,phoneforgot);
            }
        });

    }

    public void checkPhoneExists(String url,String phone2){
        String postUrl = url+"checkUserExists.php";

        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("User Exists.")){
                    loadingBar.dismiss();
                    Intent intent = new Intent(ForgotPasswordActivity.this, OTPActivity.class);
                    intent.putExtra("phone_extra",phone2);
                    intent.putExtra("from","forget");
                    startActivity(intent);
                }else if(response.contains("User Not Exists.")){
                    loadingBar.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(ForgotPasswordActivity.this, "Some Internal Error Occured. Try Again Later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPasswordActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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