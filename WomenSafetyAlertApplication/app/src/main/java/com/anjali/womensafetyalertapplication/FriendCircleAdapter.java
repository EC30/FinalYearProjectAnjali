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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        holder.friendNameCircle.setText(String.valueOf(friendNameC.get(position)).substring(3));
        holder.friendNumberCircle.setText(String.valueOf(friendnumberC.get(position)).substring(3));
       // holder.friendCircleImageView.setImageResource(friendImageC[position]);
        holder.deleteExistingfriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(context);
                builder1.setTitle("Confirmation !!");
                builder1.setMessage("Do you want to delete "+holder.friendNumberCircle.getText().toString()+"?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VolleyHandlerFriends vhf=new VolleyHandlerFriends();
                        String phone1="",phone2="";
                        if(AddFriendActivity.myfriends.get(position).contains("1::")){
                            phone1=AddFriendActivity.myfriends.get(position).substring(3);
                            phone2=AddFriendActivity.phone_logged;
                            //Toast.makeText(context, phone1, Toast.LENGTH_SHORT).show();
                        }else if(AddFriendActivity.myfriends.get(position).contains("2::")){
                            phone1=AddFriendActivity.phone_logged;
                            phone2=AddFriendActivity.myfriends.get(position).substring(3);
                            //Toast.makeText(context, phone2, Toast.LENGTH_SHORT).show();
                        }
                        vhf.process_friend_request(context,phone1,phone2,"verified","delete");
                        friendNameC.remove(holder.getAdapterPosition());
                        friendnumberC.remove(holder.getAdapterPosition());
                        //friendImageC.remove(holder.getAdapterPosition());
                        AddFriendActivity.myfriends.remove(position);
                        MyFriendCircle.fAdapter.notifyDataSetChanged();
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
