package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;

public class MyFriendListAdapter extends RecyclerView.Adapter<MyFriendListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<UserObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int width;
    SharedPreferences preferences;
    String username;

    // data is passed into the constructor
    public MyFriendListAdapter(Activity context, ArrayList<UserObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.width = width;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        username = preferences.getString("username_unique", "");
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_friends, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String lastMessage = preferences.getString(username + "-" + list.get(position).getUsername(), "");

        if(list.get(position).getLastMessage()==null || list.get(position).getLastMessage().equals("")){
            holder.t_message.setVisibility(View.GONE);
        }
        else{
            holder.t_message.setVisibility(View.VISIBLE);
            if(!list.get(position).getLastMessage().equals(lastMessage)){
//            holder.t_message.setText(list.get(position).getLastMessage());
                holder.t_message.setText("New message");
//            holder.t_message.setTypeface(null, Typeface.BOLD);
                holder.t_message.setTypeface(holder.t_message.getTypeface(), Typeface.BOLD);
            }
            else{
                holder.t_message.setText(list.get(position).getLastMessage());
//            holder.t_message.setTypeface(null, Typeface.NORMAL);
                holder.t_message.setTypeface(holder.t_message.getTypeface(), Typeface.NORMAL);
            }
        }

        holder.t_username.setText(list.get(position).getUsername());

        try{
            Glide
                    .with(context)
                    .load("https:mobiloby.com/_filter/assets/profile/" + list.get(position).getAvatar_id())
                    .centerCrop()
                    .placeholder(R.drawable.ic_f_char)
                    .into(holder.i_avatar);
        }catch (Exception e){

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_username, t_message;
        ImageView i_avatar;

        ViewHolder(View itemView) {
            super(itemView);
            t_username = itemView.findViewById(R.id.t_username);
            t_message = itemView.findViewById(R.id.t_message);
            i_avatar = itemView.findViewById(R.id.i_avatar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public UserObject getItem(int id) {
        return list.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

