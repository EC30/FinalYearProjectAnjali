package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button logIn,signUp;
    private TextView textErrorMain,textForgetPassword;
    private EditText input_phoneText,input_passwordText;
    private CountryCodePicker countryPickerLogin;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn=findViewById(R.id.btn_login);
        signUp=findViewById(R.id.btn_signUP);
        countryPickerLogin=findViewById(R.id.countryPickerLogin);
        input_phoneText=findViewById(R.id.input_phoneText);
        input_passwordText=findViewById(R.id.input_passwordText);
        textForgetPassword=findViewById(R.id.textForgetPassword);
        textErrorMain=findViewById(R.id.textErrorMain);
        loadingBar = new ProgressDialog(this);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}