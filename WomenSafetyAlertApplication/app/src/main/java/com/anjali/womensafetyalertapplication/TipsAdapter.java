package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private String s1[];

    public TipsAdapter(Context context, String s1[]){
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
        this.s1=s1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//         layoutInflater=LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.tipslayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tipsTextView.setText(s1[position]);
    }

    @Override
    public int getItemCount() {
        return s1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView userCardView;
        TextView tipsTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userCardView = itemView.findViewById(R.id.userCardView);
            tipsTextView = itemView.findViewById(R.id.tipsTextView);

        }
    }
}
