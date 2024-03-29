package com.anjali.womensafetyalertapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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

        UrlClass my_url= new UrlClass();
        String load_url=my_url.getUrl()+"upload/"+String.valueOf(friendnumberSearch.get(position)).substring(1)+".jpg";
        //Toast.makeText(context, load_url, Toast.LENGTH_SHORT).show();
        Picasso.with(context).load(Uri.parse(load_url)).placeholder(R.drawable.user_image).into(holder.addFriendImageView);

       // holder.addFriendImageView.setImageResource(friendImage[position]);
        holder.addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyHandlerFriends vhf=new VolleyHandlerFriends();
                vhf.process_friend_request(context,AddFriendActivity.phone_logged,holder.addFriendNumber.getText().toString(),"pending","insert");
                AddFriendActivity.myrequestssent.add(holder.addFriendNumber.getText().toString());
                AddFriendActivity.friendName.put(holder.addFriendNumber.getText().toString(),holder.addFriendName.getText().toString());
                friendnumberSearch.remove(holder.getAdapterPosition());
                friendNameSearch.remove(holder.getAdapterPosition());
                SearchFriendActivity.searchFriendAdapter.notifyDataSetChanged();
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
