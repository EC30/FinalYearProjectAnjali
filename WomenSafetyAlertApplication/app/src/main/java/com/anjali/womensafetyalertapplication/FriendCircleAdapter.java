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

public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.ViewHolder>{

    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> friendnumberC, friendNameC;
    private Integer friendImageC[];

    public FriendCircleAdapter( Context context, ArrayList<String> friendnumberC, ArrayList<String> friendNameC) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.friendnumberC = friendnumberC;
        this.friendNameC = friendNameC;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.friendcircle, parent, false);
        return new FriendCircleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.friendNameCircle.setText(String.valueOf(friendNameC.get(position)));
        holder.friendNumberCircle.setText(String.valueOf(friendnumberC.get(position)));
       // holder.friendCircleImageView.setImageResource(friendImageC[position]);
        holder.deleteExistingfriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendNameC.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout friendCircleLinearLayout;
        TextView friendNumberCircle, friendNameCircle;
        ImageView friendCircleImageView;
        Button deleteExistingfriendButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendCircleLinearLayout=itemView.findViewById(R.id.friendCircleLinearLayout);
            friendNameCircle=itemView.findViewById(R.id.friendCircleName);
            friendNumberCircle=itemView.findViewById(R.id.friendCircleNumber);
            friendCircleImageView=itemView.findViewById(R.id.friendCircleImageView);
            deleteExistingfriendButton=itemView.findViewById(R.id.deleteExistingFriendButton);


        }
    }

}
