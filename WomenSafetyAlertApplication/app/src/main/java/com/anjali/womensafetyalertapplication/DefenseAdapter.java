package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DefenseAdapter extends RecyclerView.Adapter<DefenseAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private String s2[];
    private String s3[];
    private Integer s4[];

    public DefenseAdapter(Context context, String s2[], String s3[], Integer s4[]){
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
        this.s2=s2;
        this.s3=s3;
        this.s4=s4;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_defense, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dTitle.setText(s2[position]);
        holder.dBody.setText(s3[position]);
        holder.defenseImageView.setImageResource(s4[position]);
    }

    @Override
    public int getItemCount() {
        return s4.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView defenseCardView;
        TextView dTitle,dBody;
        ImageView defenseImageView,tipsTickImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            defenseCardView=itemView.findViewById(R.id.defenseCardView);
            dTitle=itemView.findViewById(R.id.dTitle);
            dBody=itemView.findViewById(R.id.dBody);
            defenseImageView=itemView.findViewById(R.id.defenseImageView);
            tipsTickImageView=itemView.findViewById(R.id.tipsTickImageView);
           // userCardView = itemView.findViewById(R.id.userCardView);
            //tipsTextView = itemView.findViewById(R.id.tipsTextView);

        }
    }
}
