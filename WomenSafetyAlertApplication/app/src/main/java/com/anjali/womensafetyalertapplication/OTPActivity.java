package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    private TextView numberText;
    private EditText Code,secondCode,thirdCode,fourthCode,fifthCode,sixthCode;
    private Button btn_resend,btn_continue;

    private FirebaseAuth mAuth;
    private String mVerification;
    private String fullname,phone,passwd,gender,from;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks rCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth=FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        numberText=findViewById(R.id.numberText);
        btn_resend=findViewById(R.id.btn_resend);
        btn_continue=findViewById(R.id.btn_continue);

        phone=getIntent().getStringExtra("phone_extra");
        from=getIntent().getStringExtra("from");

        numberText.setText(phone);
        Code=findViewById(R.id.Code);

        rCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTPActivity.this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerification=s;
                mResendToken=forceResendingToken;
                loadingBar.dismiss();
                Toast.makeText(OTPActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                btn_resend.setEnabled(false);
                new CountDownTimer(60000, 1000) {
                    public void onFinish() {
                        btn_resend.setText("RESEND CODE");
                        btn_resend.setEnabled(true);
                    }

                    public void onTick(long millisUntilFinished) {
                        btn_resend.setText("RESEND CODE ("+String.valueOf(millisUntilFinished/1000)+")");
                    }

                }.start();
            }
        };

        if(from.equals("login")){
            resendOTP(phone);
            fullname=getIntent().getStringExtra("fullname_logged");
        }else if (from.equals("forget")){
            resendOTP(phone);
        }else{
            mVerification=getIntent().getStringExtra("code");
            fullname=getIntent().getStringExtra("fullname_extra");
            passwd=getIntent().getStringExtra("passwd_extra");
            gender=getIntent().getStringExtra("gender_extra");
        }

        new CountDownTimer(60000, 1000) {
            public void onFinish() {
                btn_resend.setText("RESEND CODE");
                btn_resend.setEnabled(true);
            }

            public void onTick(long millisUntilFinished) {
                btn_resend.setText("RESEND CODE ("+String.valueOf(millisUntilFinished/1000)+")");
            }
        }.start();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Code.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_SHORT).show();
                } else{
                    loadingBar.setTitle("Verifying Code...");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerification,Code.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Sending Code");
                loadingBar.setMessage("Please wait");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                resendOTP(phone);
            }
        });
    }

    private void resendOTP(String phone2){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone2)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(rCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(OTPActivity.this, "OTP Verified.", Toast.LENGTH_SHORT).show();
                            if(from.equals("login")) {
                                sendUserToMainActivity();
                            }else if(from.equals("forget")) {
                                Intent intent=new Intent(OTPActivity.this,ResetPasswordActivity.class);
                                intent.putExtra("phone_logged",phone);
                                startActivity(intent);
                            }else {
                                UrlClass myurl = new UrlClass();
                                String url = myurl.getUrl();
                                registerUser(url, fullname, phone, passwd, gender);
                            }
                        } else {
                            loadingBar.dismiss();
                            String e=task.getException().toString();
                            Toast.makeText(OTPActivity.this, "Invalid code" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerUser(String url, String fullname,String phone, String passwd,String gender){
        loadingBar.setTitle("Registering User...");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        String postUrl = url+"reg.php";
        RequestQueue requestQueue = Volley.newRequestQueue(OTPActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Inserted Successfully.")){
                    loadingBar.dismiss();
                    sendUserToMainActivity();
                }else if(response.contains("Cannot Insert.")){
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Cannot Register User. Try Again Later.";
                    Toast.makeText(OTPActivity.this, error, Toast.LENGTH_SHORT).show();
                }else{
                    loadingBar.dismiss();
                    String error="* Some Internal Error Occured. Try Again Later.";
                    Toast.makeText(OTPActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //content.setText("Error : "+error);
                Toast.makeText(OTPActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname_post", fullname);
                params.put("phone_post", phone);
                params.put("passwd_post", passwd);
                params.put("gender_post", gender);
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

    public void sendUserToMainActivity(){
        mAuth.signOut();
        preferences = getSharedPreferences("LOGIN_WSAA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("fullname_logged_in",fullname);
        editor.putString("user_logged",phone);
        editor.commit();

        DbHelper db=new DbHelper(OTPActivity.this);
        db.delete_wsaa();
        db.close();

        VolleyHandlerEC vh=new VolleyHandlerEC();
        vh.process_to_db(OTPActivity.this,"read","","",phone);

        Intent intent=new Intent(OTPActivity.this,PermissionActivity.class);
        intent.putExtra("phone_logged_main",phone);
        intent.putExtra("fullname_logged_main",fullname);
        startActivity(intent);
    }
}