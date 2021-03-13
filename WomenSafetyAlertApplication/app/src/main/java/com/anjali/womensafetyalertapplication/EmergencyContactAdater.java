package com.anjali.womensafetyalertapplication;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class EmergencyContactAdater extends RecyclerView.Adapter<EmergencyContactAdater.ViewHolder> {
    Context context;
    ArrayList<String> number, ecCountryCode,ecnum;
    private ProgressDialog loadingBar;
    private String phoneNo, message;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    public EmergencyContactAdater( Context context, ArrayList<String> number, ArrayList<String> ecCountryCode, ArrayList<String> ecnum) {
        this.context = context;
        this.ecCountryCode = ecCountryCode;
        this.number = number;
        this.ecnum = ecnum;
        loadingBar=new ProgressDialog(context);
    }

    @NonNull
    @Override
    public EmergencyContactAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator=LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.emergency_contact_layout, parent, false);
        return new ViewHolder(view);
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }else{
            sendSMS();
        }
    }

    public void sendSMS(){
        SmsManager smsManager = SmsManager.getDefault();
        String sent="SMS_SENT";
        PendingIntent pi=PendingIntent.getBroadcast(context,MY_PERMISSIONS_REQUEST_SEND_SMS,new Intent(sent),0);
        smsManager.sendTextMessage(phoneNo, null, message, pi, null);
        //Toast.makeText(context, "SMS sent to "+phoneNo, Toast.LENGTH_LONG).show();
    }

    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    Toast.makeText(context,
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyContactAdater.ViewHolder holder, final int position) {
        holder.ecPhoneNumberTextView.setText(String.valueOf(number.get(position)));
        holder.ecCountryCodeTextView.setText(String.valueOf(ecCountryCode.get(position)));
        holder.ecnumTextView.setText(String.valueOf(ecnum.get(position)));

        holder.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo=ecCountryCode.get(position)+number.get(position);
                message=AddECActivity.phone_logged2+" has added you as their emergency contact. Thank You";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                sendSMSMessage();
            }
        });

        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getRootView().getContext());
                View dialogView=LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.editcontact,null);
                CountryCodePicker editCountryPicker;
                EditText edit_phoneText;
                editCountryPicker=dialogView.findViewById(R.id.editCountryPicker);
                edit_phoneText=dialogView.findViewById(R.id.edit_phoneText);
                editCountryPicker.setCountryForPhoneCode(Integer.valueOf(ecCountryCode.get(position).substring(1)));
                String contact_number=String.valueOf(number.get(position));
                edit_phoneText.setText(contact_number);

                builder.setView(dialogView)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String old_num=holder.ecCountryCodeTextView.getText().toString()+holder.ecPhoneNumberTextView.getText().toString();

                                String new_num=editCountryPicker.getSelectedCountryCodeWithPlus().toString()+"@@"+edit_phoneText.getText().toString();
                                PhoneNumberUtil pu=PhoneNumberUtil.createInstance(context);
                                Phonenumber.PhoneNumber num=new Phonenumber.PhoneNumber();
                                num.setCountryCode(Integer.valueOf(editCountryPicker.getSelectedCountryCodeWithPlus().toString()));
                                num.setNationalNumber(Long.valueOf(edit_phoneText.getText().toString()));
                                //Toast.makeText(context, holder.ecnumTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                                if (AddECActivity.phone_logged2.equals(editCountryPicker.getSelectedCountryCodeWithPlus().toString()+edit_phoneText.getText().toString())) {
                                    Toast.makeText(context, "You cannot add yourself as an emergency contact", Toast.LENGTH_SHORT).show();
                                } else if (AddECActivity.econtacts.contains(new_num)) {
                                    Toast.makeText(context, "Emergency contacts already added.", Toast.LENGTH_SHORT).show();
                                } else if(!pu.isValidNumber(num)){
                                    Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show();
                                }else {


                                    VolleyHandlerEC vh = new VolleyHandlerEC();
                                    vh.add_to_db(context, "update", new_num, holder.ecnumTextView.getText().toString(), AddECActivity.phone_logged2);

                                    DbHelper db = new DbHelper(v.getContext());

                                    db.update_wsaa(holder.ecnumTextView.getText().toString(), new_num);
                                    db.close();

                                    number.set(holder.getAdapterPosition(), edit_phoneText.getText().toString());


                                    ecCountryCode.set(holder.getAdapterPosition(), editCountryPicker.getSelectedCountryCodeWithPlus().toString());

                                    for (int j = 0; j < HomeActivity.eccontacts_home.size(); j++) {
                                        if (old_num.equals(HomeActivity.eccontacts_home.get(j))) {
                                            HomeActivity.eccontacts_home.set(j, editCountryPicker.getSelectedCountryCodeWithPlus().toString() + edit_phoneText.getText().toString());
                                        }
                                    }
                                }
                                AddECActivity.ecAdapter.notifyDataSetChanged();
                            }
                        });
                builder.show();
            }
        });

        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Confirmation !!");
                builder1.setMessage("Do you want to delete "+ holder.ecCountryCodeTextView.getText()+holder.ecPhoneNumberTextView.getText() + " ?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String num_to_del=holder.ecCountryCodeTextView.getText().toString()+holder.ecPhoneNumberTextView.getText().toString();
                        VolleyHandlerEC vh=new VolleyHandlerEC();
                        vh.add_to_db(context,"update","NULL-VAL",holder.ecnumTextView.getText().toString(),AddECActivity.phone_logged2);

                        DbHelper db = new DbHelper(view.getContext());

                        //AddECActivity.empty.setVisibility(View.VISIBLE);
                        db.update_wsaa(holder.ecnumTextView.getText().toString(),"NULL-VAL");
                        db.close();
                        number.remove(holder.getAdapterPosition());
                        ecCountryCode.remove(holder.getAdapterPosition());
                        ecnum.remove(holder.getAdapterPosition());
                        AddECActivity.econtacts.set(holder.getAdapterPosition(),"NULL-VAL");
                        AddECActivity.ecAdapter.notifyDataSetChanged();

                        for(int j=0;j<HomeActivity.eccontacts_home.size();j++){
                            if(num_to_del.equals(HomeActivity.eccontacts_home.get(j))){
                                HomeActivity.eccontacts_home.remove(j);
                            }
                        }
                        AddECActivity.ecAdapter.notifyDataSetChanged();
                        if (number.size() == 0) {
                            AddECActivity.emergencyContactRecyclerView.setVisibility(View.INVISIBLE);
                            AddECActivity.empty.setVisibility(View.VISIBLE);
                        }else if(number.size()<3){
                            AddECActivity.floatingButton.setVisibility(View.VISIBLE);
                            AddECActivity.empty.setVisibility(View.INVISIBLE);
                        }


                    }
                });
                builder1.setNegativeButton("No", null);
                builder1.setCancelable(false);
                builder1.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return number.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView ecCardView;
        TextView ecCountryCodeTextView, ecPhoneNumberTextView,ecnumTextView;
        ImageView bt_edit, bt_delete;
        Button testButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ecCardView=itemView.findViewById(R.id.eccardView);
            ecCountryCodeTextView=itemView.findViewById(R.id.ecCountryCodeTextView);
            ecPhoneNumberTextView=itemView.findViewById(R.id.ecPhoneNumberTextView);
            ecnumTextView=itemView.findViewById(R.id.ecNumber);
            bt_edit=itemView.findViewById(R.id.bt_edit);
            bt_delete=itemView.findViewById(R.id.bt_delete);
            testButton=itemView.findViewById(R.id.testButton);
        }
    }


}
