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

        textForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneLogin=countryPickerLogin.getSelectedCountryCodeWithPlus().toString()+input_phoneText.getText().toString();
                String passLogin=input_passwordText.getText().toString();

                UrlClass myurl=new UrlClass();
                String url=myurl.getUrl();
                String loginUrl = url+"login.php";

                if(passLogin.length()>7 && !input_phoneText.getText().toString().equals("")) {
                    loadingBar.setTitle("Logging In...");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("Logged in Successfully.")){
                                loadingBar.dismiss();
                                Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                                intent.putExtra("phone_extra",phoneLogin);
                                intent.putExtra("from","login");
                                startActivity(intent);
                            }else if(response.contains("Cannot Login. Username or password incorrect")){
                                loadingBar.dismiss();
                                textErrorMain.setText("* Username or Password Incorrect.");
                            }else{
                                loadingBar.dismiss();
                                textErrorMain.setText("* Internal Error. Please try again later.");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //content.setText("Error : "+error);
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("phone_login", phoneLogin);
                            params.put("passwd_login", passLogin);
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
                }else{
                    textErrorMain.setText("* Please Fill all required fields. Password must be at least 8 characters.");
                }
            }
        });
    }
}