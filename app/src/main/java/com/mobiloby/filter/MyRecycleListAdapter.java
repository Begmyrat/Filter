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

public class MyRecycleListAdapter extends RecyclerView.Adapter<MyRecycleListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<TodoObject> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecycleListAdapter(Activity context, ArrayList<TodoObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_recycle, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position<list.size()-1) {
            holder.textview_title.setVisibility(View.VISIBLE);
            holder.textview_title.setText(list.get(position).getTodoDescription());
            holder.i_exchange.setVisibility(View.GONE);
        }
        else{
            holder.textview_title.setVisibility(View.GONE);
            holder.i_exchange.setVisibility(View.VISIBLE);
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textview_title;
        ImageView i_exchange;

        ViewHolder(View itemView) {
            super(itemView);
            textview_title = itemView.findViewById(R.id.t_title);
            i_exchange = itemView.findViewById(R.id.i_exchange);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public TodoObject getItem(int id) {
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

