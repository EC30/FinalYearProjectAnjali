package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddECActivity extends AppCompatActivity {
    static FloatingActionButton floatingButton;
    static TextView empty;
    private Dialog dialog;
    private static ProgressDialog loadingBar;
    static ArrayList<String> ecnum, ecCountryCode, emergencynumber, edataof, econtacts;
    static RecyclerView emergencyContactRecyclerView;
    static EmergencyContactAdater ecAdapter;
    static String phone_logged2;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    boolean isVerified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ec);
        phone_logged2=getIntent().getStringExtra("my_phone");
        //Toast.makeText(this, phone_logged2, Toast.LENGTH_SHORT).show();

        floatingButton=findViewById(R.id.floatingButton);
        empty=findViewById(R.id.empty);
        emergencyContactRecyclerView=findViewById(R.id.emergencyContactRecyclerView);

        emergencynumber=new ArrayList<>();
        ecCountryCode=new ArrayList<>();
        econtacts=new ArrayList<>();
        ecnum=new ArrayList<>();
        edataof=new ArrayList<>();
        loadingBar=new ProgressDialog(this);
        ecAdapter=new EmergencyContactAdater(AddECActivity.this, emergencynumber,ecCountryCode,ecnum);
        emergencyContactRecyclerView.setAdapter(ecAdapter);
        emergencyContactRecyclerView.setLayoutManager(new LinearLayoutManager(AddECActivity.this));


        DbHelper db=new DbHelper(AddECActivity.this);
        Cursor cursor =db.read_wsaa();
        if(cursor.getCount() == 0){
            db.add_wsaa("EC1","NULL-VAL");
            db.add_wsaa("EC2","NULL-VAL");
            db.add_wsaa("EC3","NULL-VAL");
            edataof.add("EC1");
            edataof.add("EC2");
            edataof.add("EC3");
            econtacts.add("NULL-VAL");
            econtacts.add("NULL-VAL");
            econtacts.add("NULL-VAL");
        }else{
            while (cursor.moveToNext()){
                String data_of1=cursor.getString(0);
                if(data_of1.contains("EC")){
                    edataof.add(data_of1);
                    econtacts.add(cursor.getString(1));

                    //Toast.makeText(this, cursor.getString(0)+":"+cursor.getString(1), Toast.LENGTH_SHORT).show();
                    //aa++;
                }
            }
        }
        db.close();

        for (int abc=0;abc<econtacts.size();abc++){
            if(!econtacts.get(abc).equals("NULL-VAL")) {
                String[] splited=econtacts.get(abc).split("@@");
                String cc=splited[0];
                String emergencyContact=splited[1];
                ecnum.add(edataof.get(abc));
                ecCountryCode.add(cc);
                emergencynumber.add(emergencyContact);
                ecAdapter.notifyDataSetChanged();
                emergencyContactRecyclerView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.INVISIBLE);
                if (emergencynumber.size() == 3) {
                    floatingButton.setVisibility(View.INVISIBLE);
                }
            }
        }

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddContact();
            }
        });
    }


    public void AddContact(){
        AlertDialog.Builder builder=new AlertDialog.Builder(AddECActivity.this);
        View dialogView= LayoutInflater.from(AddECActivity.this).inflate(R.layout.addcontact,null);

        EditText input_emergencyphoneText;
        CountryCodePicker countryCode;

        input_emergencyphoneText=dialogView.findViewById(R.id.input_emergencyphoneText);
        countryCode=dialogView.findViewById(R.id.countryPicker);

        builder.setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cc=countryCode.getSelectedCountryCodeWithPlus().toString();
                        String emergencyContact = input_emergencyphoneText.getText().toString();

                        int new_index = econtacts.indexOf("NULL-VAL");
                        //Toast.makeText(AddECActivity.this, String.valueOf(new_index), Toast.LENGTH_SHORT).show();

                        //new_index+=1;

                        VolleyHandlerEC vh = new VolleyHandlerEC();
                        vh.add_to_db(AddECActivity.this, "update", cc + "@@" + emergencyContact, edataof.get(new_index),phone_logged2);

                        DbHelper db = new DbHelper(AddECActivity.this);
                        db.update_wsaa(edataof.get(new_index), cc + "@@" + emergencyContact);
                        econtacts.set(new_index, cc + "@@" + emergencyContact);
                        db.close();

                        ecnum.add(edataof.get(new_index));
                        ecCountryCode.add(cc);
                        emergencynumber.add(emergencyContact);
                        ecAdapter.notifyDataSetChanged();
                        emergencyContactRecyclerView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.INVISIBLE);
                        if (emergencynumber.size() == 3) {
                            floatingButton.setVisibility(View.INVISIBLE);
                        } else {
                            floatingButton.setVisibility(View.VISIBLE);
                        }
                    }

                });
        builder.show();
    }

//    public void add_to_db(String url, String action, String new_contact,String contact_to_update){
//        loadingBar.setTitle("Adding Emergency Contact...");
//        loadingBar.setMessage("Please wait");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();
//        String postUrl = url+"emergencycontact.php";
//        RequestQueue requestQueue = Volley.newRequestQueue(AddECActivity.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response.contains("Updated EC Successfully.")){
//                    loadingBar.dismiss();
//                    Toast.makeText(AddECActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
//                }else if(response.contains("Cannot Update EC")){
//                    loadingBar.dismiss();
//                    String error="* Some Internal Error Occured. Cannot Add Contact. Try Again Later.";
//                    Toast.makeText(AddECActivity.this, error, Toast.LENGTH_SHORT).show();
//                }else{
//                    loadingBar.dismiss();
//                    String error="* Some Internal Error Occured. Try Again Later.";
//                    Toast.makeText(AddECActivity.this, error, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //content.setText("Error : "+error);
//                Toast.makeText(AddECActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("phone_logged_in", phone_logged2);
//                params.put("action", action);
//                params.put("contact_to_update", contact_to_update);
//                params.put("new_contact", new_contact);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                //params.put("Cookie", cookie);
//
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}