package com.anjali.womensafetyalertapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

public class AddECActivity extends AppCompatActivity {
    FloatingActionButton floatingButton;
    TextView empty;
    private Dialog dialog;
    ArrayList<String> id, emergencynumber;
    RecyclerView emergencyContactRecyclerView;
    EmergencyContactAdater ecAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ec);


        floatingButton=findViewById(R.id.floatingButton);
        empty=findViewById(R.id.empty);
        emergencyContactRecyclerView=findViewById(R.id.emergencyContactRecyclerView);

        emergencynumber=new ArrayList<>();
        ecAdapter=new EmergencyContactAdater(AddECActivity.this, emergencynumber);
        emergencyContactRecyclerView.setAdapter(ecAdapter);
        emergencyContactRecyclerView.setLayoutManager(new LinearLayoutManager(AddECActivity.this));

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emergencynumber.size() == 3) {
                    Toast.makeText(AddECActivity.this, "Sorry! You can only add 3 emergency contacts", Toast.LENGTH_SHORT).show();
                    return;
                }
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
//        id=new ArrayList<>();
//        id.add("1");
//        id.add("2");
//        id.add("3");

        builder.setView(dialogView)
//                .setTitle("ToDo task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String emergencyContact = countryCode.getSelectedCountryCodeWithPlus().toString() + input_emergencyphoneText.getText().toString();
                        emergencynumber.add(emergencyContact);
                        ecAdapter.notifyDataSetChanged();
                        emergencyContactRecyclerView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.INVISIBLE);
                    }
                });
        builder.show();
//        dialog = new Dialog(AddECActivity.this);
//        dialog.setContentView(R.layout.addcontact);
//        View dialogView= LayoutInflater.from(AddECActivity.this).inflate(R.layout.addcontact,null);
//
//        TextView addECTextView, Cancel;
//        addECTextView=dialogView.findViewById(R.id.addECText);
//        Cancel=dialogView.findViewById(R.id.cancelText);
//        EditText input_emergencyphoneText;
//        CountryCodePicker countryCode;
//
//        String user_phone="+9779843646330";
//        id=new ArrayList<>();
//        emergencynumber=new ArrayList<>();
//        id.add("1");
//        id.add("2");
//        id.add("3");
//
//        input_emergencyphoneText=dialogView.findViewById(R.id.input_emergencyphoneText);
//        countryCode=dialogView.findViewById(R.id.countryPicker);
//
//
//        Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//        addECTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddECActivity.this, "aa", Toast.LENGTH_SHORT).show();
//                if(input_emergencyphoneText.getText().toString().isEmpty()){
//                    Toast.makeText(AddECActivity.this, "Please add an number", Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//                    String emergencyContact = countryCode.getSelectedCountryCodeWithPlus().toString() + input_emergencyphoneText.getText().toString();
//                    emergencynumber.add(emergencyContact);
//                    EmergencyContactAdater ecAdapter=new EmergencyContactAdater(AddECActivity.this,id,emergencynumber);
//                    emergencyContactRecyclerView.setAdapter(ecAdapter);
//                    emergencyContactRecyclerView.setLayoutManager(new LinearLayoutManager(AddECActivity.this));
//                    ecAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
    }


}