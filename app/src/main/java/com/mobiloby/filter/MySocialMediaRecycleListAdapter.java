package com.mobiloby.filter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySocialMediaRecycleListAdapter extends RecyclerView.Adapter<MySocialMediaRecycleListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<SocialObject> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MySocialMediaRecycleListAdapter(Activity context, ArrayList<SocialObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_onerilenler, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull MySocialMediaRecycleListAdapter.ViewHolder holder, int position) {

        SocialObject object = list.get(position);

        holder.t_username.setText(object.getUsername());
        holder.t_username_other.setText(object.getUsername_other());
        holder.t_follow.setText("sil");

        if(object.getType().toLowerCase().equals("facebook")){
            holder.i_logo.setImageResource(R.drawable.ic_facebook);
        }
        else if(object.getType().toLowerCase().equals("instagram")){
            holder.i_logo.setImageResource(R.drawable.ic_instagram);
        }
        else if(object.getType().toLowerCase().equals("snapchat")){
            holder.i_logo.setImageResource(R.drawable.ic_snapchat);
        }
        else{
            holder.i_logo.setImageResource(R.drawable.tiktokicon);
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_username, t_username_other, t_follow;
        ImageView i_logo;
//        RelativeLayout r_stick;

        ViewHolder(View itemView) {
            super(itemView);

            i_logo = itemView.findViewById(R.id.i_icon);
            t_username = itemView.findViewById(R.id.t_username);
            t_username_other = itemView.findViewById(R.id.t_status);
            t_follow = itemView.findViewById(R.id.t_follow);
//            r_stick = itemView.findViewById(R.id.r_stick);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public SocialObject getItem(int id) {
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

