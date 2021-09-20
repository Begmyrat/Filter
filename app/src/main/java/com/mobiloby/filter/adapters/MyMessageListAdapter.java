package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.content.SharedPreferences;
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
import com.mobiloby.filter.models.ChatObject;
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;

public class MyMessageListAdapter extends RecyclerView.Adapter<MyMessageListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<ChatObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    String username, friendUsername;
    SharedPreferences preferences;

    // data is passed into the constructor
    public MyMessageListAdapter(Activity context, ArrayList<ChatObject> list, String username, String friendUsername) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.username = username;
        this.friendUsername = friendUsername;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(view);
    }

    public ArrayList<ChatObject> getList() {
        return list;
    }

    public void setList(ArrayList<ChatObject> list) {
        this.list = list;
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChatObject chat = list.get(position);

        if(position==list.size()-1){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(username+"-"+friendUsername, list.get(position).getMessage());
            editor.commit();
        }

        if(chat!=null){
            if(chat.getKimdenKime().equals(username)){
                holder.r_left.setVisibility(View.GONE);
                holder.r_right.setVisibility(View.VISIBLE);
                holder.t_chatRight.setText(chat.getMessage());
                holder.t_dateRight.setText(chat.getDate());
            }
            else{
                holder.r_left.setVisibility(View.VISIBLE);
                holder.r_right.setVisibility(View.GONE);
                holder.t_chatLeft.setText(chat.getMessage());
                holder.t_dateLeft.setText(chat.getDate());
            }
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_chatLeft, t_chatRight, t_dateLeft, t_dateRight;
        RelativeLayout r_left, r_right;

        ViewHolder(View itemView) {
            super(itemView);

            r_left = itemView.findViewById(R.id.r_left);
            r_right = itemView.findViewById(R.id.r_right);
            t_chatLeft = itemView.findViewById(R.id.t_chatLeft);
            t_dateLeft = itemView.findViewById(R.id.t_dateLeft);
            t_chatRight = itemView.findViewById(R.id.t_chatRight);
            t_dateRight = itemView.findViewById(R.id.t_dateRight);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public ChatObject getItem(int id) {
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

