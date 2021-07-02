package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiloby.filter.R;

import java.util.ArrayList;

public class MyWantedRecycleListAdapter extends RecyclerView.Adapter<MyWantedRecycleListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<String> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public static int row_index = -1;

    // data is passed into the constructor
    public MyWantedRecycleListAdapter(Activity context, ArrayList<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_info_type, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//        if(position<list.size()-1) {
////            holder.textview_title.setVisibility(View.VISIBLE);
////            holder.textview_title.setText(list.get(position).getTodoDescription());
////            holder.i_exchange.setVisibility(View.GONE);
//        }
//        else{
////            holder.textview_title.setVisibility(View.GONE);
////            holder.i_exchange.setVisibility(View.VISIBLE);
//        }

//        holder.r_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                row_index=position;
//                notifyDataSetChanged();
//            }
//        });
        if(row_index==position){
            holder.r_main.setBackground(context.getResources().getDrawable(R.drawable.recycle_background_clicked));
        }
        else
        {
            holder.r_main.setBackground(context.getResources().getDrawable(R.drawable.recycle_background));
        }

        if (position==0) {
            holder.textview_title.setText("Baş");
            holder.i_icon.setImageResource(R.drawable.portrait);
        }
        else if (position==1) {
            holder.textview_title.setText("Gövde");
            holder.i_icon.setImageResource(R.drawable.jacket);
        }
        else if (position==2) {
            holder.textview_title.setText("Bacak");
            holder.i_icon.setImageResource(R.drawable.ayak);
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
        ImageView i_icon;
        RelativeLayout r_main;

        ViewHolder(View itemView) {
            super(itemView);
            textview_title = itemView.findViewById(R.id.t_title);
            r_main = itemView.findViewById(R.id.r_main);
            i_icon = itemView.findViewById(R.id.i_icon);

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
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

