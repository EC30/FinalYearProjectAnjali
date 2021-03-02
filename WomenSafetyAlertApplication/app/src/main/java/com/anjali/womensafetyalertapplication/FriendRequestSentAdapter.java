package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Confirmation !!");
                builder1.setMessage("Do you want to delete "+holder.requestSentNumber.getText().toString()+"?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VolleyHandlerFriends vhf=new VolleyHandlerFriends();
                        String phone1="",phone2="";

                        phone1=AddFriendActivity.phone_logged;
                        phone2=AddFriendActivity.myrequestssent.get(position);

                        vhf.process_friend_request(context,phone1,phone2,"pending","delete");

                        if(!VolleyHandlerFriends.change_adapter_friends.equals("no")){
                            friendNameS.remove(holder.getAdapterPosition());
                            friendnumberS.remove(holder.getAdapterPosition());
                            //friendImageC.remove(holder.getAdapterPosition());
                            AddFriendActivity.myrequestssent.remove(position);
                            SentRequestActivity.frsAdapter.notifyDataSetChanged();
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
