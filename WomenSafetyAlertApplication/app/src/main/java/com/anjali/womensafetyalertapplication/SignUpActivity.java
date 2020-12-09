package com.anjali.womensafetyalertapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.hbb20.CountryCodePicker;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private Button signUp;
    public EditText input_signUp_phone,input_signUp_password,input_confirmPassword,fullName;
    private CountryCodePicker countryPicker;
    private RadioGroup radioGender;
    private RadioButton radioMale,radioFemale;
    private TextView textError;
    private FirebaseAuth mAuth;
    private String mVerification;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;
    private String fullname,phone,passwd,passwd2,gender,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingBar = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        fullName=findViewById(R.id.fullNameText);
        input_signUp_phone=findViewById(R.id.input_signUp_phone);
        input_signUp_password=findViewById(R.id.input_signUp_password);
        input_confirmPassword=findViewById(R.id.input_confirmPassword);
        countryPicker=findViewById(R.id.countryPicker);
        radioGender=findViewById(R.id.radioGender);
        radioMale=findViewById(R.id.radioMale);
        radioFemale=findViewById(R.id.radioFemale);
        textError=findViewById(R.id.textError);
        signUp=findViewById(R.id.signUP);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender2 = "";
                if (radioMale.isChecked()) {
                    gender2 = "m";
                } else {
                    gender2 = "f";
                }

                UrlClass myurl = new UrlClass();
                url = myurl.getUrl();

                fullname = fullName.getText().toString();
                phone = countryPicker.getSelectedCountryCodeWithPlus().toString() + input_signUp_phone.getText().toString();
                passwd = input_signUp_password.getText().toString();
                passwd2 = input_confirmPassword.getText().toString();
                gender = gender2;

                if(passwd.equals(passwd2) && !passwd.equals("") && !fullname.equals("") && !input_signUp_phone.getText().toString().equals("")) {
                    loadingBar.setTitle("Checking If User Already Exist");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    checkPhoneExists(url, phone);
                }else{
                    String error="* ";
                    if(input_signUp_phone.getText().toString().equals("")){
                        error+="Phone Number cannot be empty.";
                    }else if(fullname.length()<6){
                        error+="Full Name must be at least 6 characters.";
                    }else if(passwd.length()<8){
                        error+="Password must be at least 8 characters.";
                    }else if(!passwd.equals(passwd2)){
                        error+="Password and Confirm Password don't match.";
                    }else{
                        error="";
                    }
                    textError.setText(error);
                }
            }
        });

        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(SignUpActivity.this, "Invalid Phone number", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerification=s;
                mResendToken=forceResendingToken;
                loadingBar.dismiss();
                Toast.makeText(SignUpActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, OTPActivity.class);
                intent.putExtra("code", mVerification);
                intent.putExtra("fullname_extra",fullname);
                intent.putExtra("phone_extra",phone);
                intent.putExtra("passwd_extra",passwd);
                intent.putExtra("gender_extra",gender);
                intent.putExtra("from","signup");
                startActivity(intent);
            }
        };
    }

    public void checkPhoneExists(String url,String phone2){
        String postUrl = url+"checkUserExists.php";

        RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("User Exists.")){
                    textError.setText("* Phone Number Already Exists.");
                    loadingBar.dismiss();
                }else if(response.contains("User Not Exists.")){
                    loadingBar.dismiss();
                    loadingBar.setTitle("Sending OTP Code");
                    loadingBar.setMessage("Please wait");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    initiateOTP(phone2);
                }else{
                    textError.setText("* Some Internal Error Occured. Try Again Later.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
//                            Toast.makeText(SignUpActivity.this, "You are now logged in", Toast.LENGTH_SHORT).show();
//                            OTPActivity bbaa=new OTPActivity();
//                            bbaa.registerUser(url, fullname, phone, passwd, gender);
                        } else {
                            loadingBar.dismiss();
                            String e=task.getException().toString();
                            Toast.makeText(SignUpActivity.this, "Invalid code" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initiateOTP(String phoneNumber){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(SignUpActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}