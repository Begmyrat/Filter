package com.mobiloby.filter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MySocialRecycleListAdapter extends RecyclerView.Adapter<MySocialRecycleListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<String> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListenerSocial mClickListener;

    // data is passed into the constructor
    MySocialRecycleListAdapter(Activity context, ArrayList<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_avatars, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String object = list.get(position);

        if(object.toLowerCase().equals("facebook")){
            holder.i_logo.setImageResource(R.drawable.ic_facebook);
        }
        else if(object.toLowerCase().equals("instagram")){
            holder.i_logo.setImageResource(R.drawable.ic_instagram);
        }
        else if(object.toLowerCase().equals("snapchat")){
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

        ImageView i_logo;

        ViewHolder(View itemView) {
            super(itemView);

            i_logo = itemView.findViewById(R.id.icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return list.get(id);
    }

    // allows clicks events to be caught
    public void setClickListenerSocial(ItemClickListenerSocial itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListenerSocial {
        void onItemClick(View view, int position);
    }
}

