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

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>{
    Context context;
    ArrayList<String> friendnumberR, friendNameR;
    private LayoutInflater layoutInflater;
    private Integer friendImageR[];

    public FriendRequestAdapter( Context context, ArrayList<String> friendnumberR, ArrayList<String> friendNameR) {
        this.context = context;
        this.friendnumberR = friendnumberR;
        this.layoutInflater = LayoutInflater.from(context);
        this.friendNameR = friendNameR;
        //this.friendImageR = friendImageR;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.friendrequest, parent, false);
        return new FriendRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.friendNameRequest.setText(String.valueOf(friendNameR.get(position)));
        holder.friendNumberRequest.setText(String.valueOf(friendnumberR.get(position)));
        //holder.requestImageView.setImageResource(friendImageR[position]);
        holder.confirmRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.deleteRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendnumberR.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout friendRequestLinearLayout;
        TextView friendNumberRequest, friendNameRequest;
        ImageView requestImageView;
        Button confirmRequestButton, deleteRequestButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendRequestLinearLayout=itemView.findViewById(R.id.friendRequestLinearLayout);
            friendNameRequest=itemView.findViewById(R.id.friendNameRequest);
            friendNumberRequest=itemView.findViewById(R.id.friendNumberRequest);
            requestImageView=itemView.findViewById(R.id.requestimageView);
            confirmRequestButton=itemView.findViewById(R.id.confirmRequestButton);
            deleteRequestButton=itemView.findViewById(R.id.deleteRequestButton);

        }
    }
}
