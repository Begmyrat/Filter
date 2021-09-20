package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.InfoObject;
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;

public class MyInfoListAdapter extends RecyclerView.Adapter<MyInfoListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<InfoObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int width;

    // data is passed into the constructor
    public MyInfoListAdapter(Activity context, ArrayList<InfoObject> list, int width) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.width = width;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_infobox, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.c_main.getLayoutParams().width = width/2-20;
        holder.t_title.setText(list.get(position).getTitle());
        holder.t_value.setText(list.get(position).getValue());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_title, t_value;
        ConstraintLayout c_main;

        ViewHolder(View itemView) {
            super(itemView);
            t_title = itemView.findViewById(R.id.t_title);
            t_value = itemView.findViewById(R.id.t_value);
            c_main = itemView.findViewById(R.id.c_main);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public InfoObject getItem(int id) {
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

