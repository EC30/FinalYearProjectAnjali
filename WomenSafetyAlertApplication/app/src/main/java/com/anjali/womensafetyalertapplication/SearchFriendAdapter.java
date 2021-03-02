package com.anjali.womensafetyalertapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFriendAdapter extends RecyclerView.Adapter<SearchFriendAdapter.ViewHolder>{
    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> friendnumberSearch, friendNameSearch;
   // private Integer friendImage[];

    public SearchFriendAdapter( Context context, ArrayList<String> friendnumberSearch, ArrayList<String> friendNameSearch) {
        this.context = context;
        this.friendnumberSearch = friendnumberSearch;
        this.layoutInflater = LayoutInflater.from(context);
        this.friendNameSearch = friendNameSearch;
        //this.friendImage = friendImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.friendadd, parent, false);
        return new SearchFriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.addFriendName.setText(String.valueOf(friendNameSearch.get(position)));
        holder.addFriendNumber.setText(String.valueOf(friendnumberSearch.get(position)));
       // holder.addFriendImageView.setImageResource(friendImage[position]);
        holder.addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return friendnumberSearch.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutAddFriend;
        TextView addFriendNumber, addFriendName;
        ImageView addFriendImageView;
        Button addFriendButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayoutAddFriend=itemView.findViewById(R.id.linearLayoutAddFriend);
            addFriendNumber=itemView.findViewById(R.id.addFriendNumber);
            addFriendName=itemView.findViewById(R.id.addFriendName);
            addFriendImageView=itemView.findViewById(R.id.addFriendImageView);
            addFriendButton=itemView.findViewById(R.id.addFriendButton);

        }
    }
}
