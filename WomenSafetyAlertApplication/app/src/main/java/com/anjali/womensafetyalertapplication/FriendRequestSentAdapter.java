package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendRequestSentAdapter extends RecyclerView.Adapter<FriendRequestSentAdapter.ViewHolder>{

    Context context;
    ArrayList<String> friendnumberS, friendNameS;
    private LayoutInflater layoutInflater;
    private Integer friendImageS[];

    public FriendRequestSentAdapter( Context context, ArrayList<String> friendnumberS, ArrayList<String> friendNameS) {
        this.context = context;
        this.friendnumberS = friendnumberS;
        this.layoutInflater = LayoutInflater.from(context);
        this.friendNameS = friendNameS;
        //this.friendImageR = friendImageR;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.friendrequestsent, parent, false);
        return new FriendRequestSentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.requestSentName.setText(String.valueOf(friendNameS.get(position)));
        holder.requestSentNumber.setText(String.valueOf(friendnumberS.get(position)));
        //holder.requestImageView.setImageResource(friendImageR[position]);
        holder.deleteSentRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendnumberS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout requestSentLinearLayout;
        TextView requestSentName, requestSentNumber;
        ImageView requestSentImageView;
        Button  deleteSentRequestButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestSentLinearLayout=itemView.findViewById(R.id.requestSentLinearLayout);
            requestSentName=itemView.findViewById(R.id.requestSentName);
            requestSentNumber=itemView.findViewById(R.id.requestSentNumber);
            requestSentImageView=itemView.findViewById(R.id.requestSentImageView);
            deleteSentRequestButton=itemView.findViewById(R.id.deleteSentRequestButton);


        }
    }
}
