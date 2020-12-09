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

    }


}