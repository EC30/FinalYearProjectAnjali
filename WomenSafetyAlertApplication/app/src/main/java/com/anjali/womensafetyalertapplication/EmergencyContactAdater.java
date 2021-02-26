package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.Arrays;

public class EmergencyContactAdater extends RecyclerView.Adapter<EmergencyContactAdater.ViewHolder> {
    Context context;
//    ArrayList<String> id;
    ArrayList<String> number;
    String contact_number;

    public EmergencyContactAdater( Context context, ArrayList<String> number) {
        this.context = context;
//        this.id = id;
        this.number = number;
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
        //this.position=position;
//        holder.idTextView.setText(String.valueOf(getItemCount()));
        holder.ecNumberTextView.setText(String.valueOf(number.get(position)));
        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final AlertDialog.Builder builder=new AlertDialog.Builder(v.getRootView().getContext());
//                View dialogView=LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.editcontact,null);
//                EditText editCountryPicker;
//                CountryCodePicker edit_phoneText;
//                editCountryPicker=dialogView.findViewById(R.id.editCountryPicker);
//                edit_phoneText=dialogView.findViewById(R.id.edit_phoneText);
//                contact_number=String.valueOf(number.get(position));
//
//
//                builder.setView(dialogView)
////                .setTitle("ToDo task")
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
//                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
                //builder.show();
            }
        });

        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Confirmation !!");
                builder1.setMessage("Do you want to delete ?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
        TextView idTextView, ecNumberTextView;
        ImageView bt_edit, bt_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ecCardView=itemView.findViewById(R.id.eccardView);
            idTextView=itemView.findViewById(R.id.idTextView);
            ecNumberTextView=itemView.findViewById(R.id.ecNumberTextView);
            bt_edit=itemView.findViewById(R.id.bt_edit);
            bt_delete=itemView.findViewById(R.id.bt_delete);
        }
    }
}
