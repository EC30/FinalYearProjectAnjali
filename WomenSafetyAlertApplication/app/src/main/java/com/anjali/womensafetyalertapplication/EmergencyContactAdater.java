package com.anjali.womensafetyalertapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
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

public class EmergencyContactAdater extends RecyclerView.Adapter<EmergencyContactAdater.ViewHolder> {
    Context context;
    ArrayList<String> number, ecCountryCode,ecnum;
    private ProgressDialog loadingBar;


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

    @Override
    public void onBindViewHolder(@NonNull EmergencyContactAdater.ViewHolder holder, final int position) {
        holder.ecPhoneNumberTextView.setText(String.valueOf(number.get(position)));
        holder.ecCountryCodeTextView.setText(String.valueOf(ecCountryCode.get(position)));
        holder.ecnumTextView.setText(String.valueOf(ecnum.get(position)));

        holder.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                Toast.makeText(context, holder.ecnumTextView.getText().toString(), Toast.LENGTH_SHORT).show();

                                VolleyHandlerEC vh=new VolleyHandlerEC();
                                vh.add_to_db(context,"update",new_num,holder.ecnumTextView.getText().toString(),AddECActivity.phone_logged2);

                                DbHelper db = new DbHelper(v.getContext());

                                db.update_wsaa(holder.ecnumTextView.getText().toString(),new_num);
                                db.close();

                                number.set(holder.getAdapterPosition(),edit_phoneText.getText().toString());
                                ecCountryCode.set(holder.getAdapterPosition(),editCountryPicker.getSelectedCountryCodeWithPlus().toString());

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
