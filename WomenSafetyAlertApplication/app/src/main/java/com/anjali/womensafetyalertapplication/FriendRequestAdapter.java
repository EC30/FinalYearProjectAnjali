package com.anjali.womensafetyalertapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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

        UrlClass my_url= new UrlClass();
        String load_url=my_url.getUrl()+"upload/"+String.valueOf(friendnumberR.get(position)).substring(1)+".jpg";
        //Toast.makeText(context, load_url, Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(Uri.parse(load_url)).placeholder(R.drawable.user_image).into(holder.requestImageView);
        //holder.requestImageView.setImageResource(friendImageR[position]);
        holder.confirmRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddFriendActivity.myfriends.size()>4) {
                    Toast.makeText(context, "Friend Limit Exceeded. Max Friends Limit=5.", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("Confirmation !!");
                    builder1.setMessage("Are you sure to accept " + holder.friendNumberRequest.getText().toString() + " as your friend ?");
                    builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VolleyHandlerFriends vhf = new VolleyHandlerFriends();
                            String phone1 = "", phone2 = "";

                            phone2 = AddFriendActivity.phone_logged;
                            phone1 = AddFriendActivity.myrequestsreceived.get(position);

                            vhf.process_friend_request(context, phone1, phone2, "pending", "confirm");
                            //Toast.makeText(context, VolleyHandlerFriends.change_adapter_friends, Toast.LENGTH_SHORT).show();

                            if(!VolleyHandlerFriends.change_adapter_friends.equals("no")) {
                                friendNameR.remove(holder.getAdapterPosition());
                                friendnumberR.remove(holder.getAdapterPosition());
                                //friendImageC.remove(holder.getAdapterPosition());
                                AddFriendActivity.myrequestsreceived.remove(position);
                                AddFriendActivity.myfriends.add("1::" + holder.friendNumberRequest.getText().toString());

                                RequestActivity.frAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder1.setNegativeButton("No", null);
                    builder1.setCancelable(false);
                    builder1.show();
                }
            }
        });

        holder.deleteRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Confirmation !!");
                builder1.setMessage("Do you want to delete request from "+holder.friendNumberRequest.getText().toString()+"?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VolleyHandlerFriends vhf=new VolleyHandlerFriends();
                        String phone1="",phone2="";

                        phone2=AddFriendActivity.phone_logged;
                        phone1=AddFriendActivity.myrequestsreceived.get(position);

                        vhf.process_friend_request(context,phone1,phone2,"pending","delete");
                        friendNameR.remove(holder.getAdapterPosition());
                        friendnumberR.remove(holder.getAdapterPosition());
                        //friendImageC.remove(holder.getAdapterPosition());
                        AddFriendActivity.myrequestsreceived.remove(position);
                        RequestActivity.frAdapter.notifyDataSetChanged();
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
