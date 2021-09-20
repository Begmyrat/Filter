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

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.WantedObject;

import java.util.ArrayList;

public class MyWantedSimilarListAdapter extends RecyclerView.Adapter<MyWantedSimilarListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<WantedObject> list;
    private LayoutInflater mInflater;
    private ItemClickListenerResult mClickListener;


    // data is passed into the constructor
    public MyWantedSimilarListAdapter(Activity context, ArrayList<WantedObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_wanted_similar, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final WantedObject konu = list.get(position);

//        holder.c_main.getLayoutParams().width = width/2-20;
        holder.r_box.setVisibility(View.VISIBLE);
        holder.t_title.setText(konu.getWantedTitle());
        holder.t_date.setText(konu.getWantedDate());
        holder.t_similarity.setText("Uyuşma oranı %"+konu.getSimilarity());

        try{
            Glide
                    .with(context)
                    .load("https:mobiloby.com/_filter/assets/profile/" + list.get(position).getUserProfileUrl())
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

        TextView t_title, t_date, t_similarity;
        ImageView i_avatar;
        RelativeLayout r_box;

        ViewHolder(View itemView) {
            super(itemView);

            t_title = itemView.findViewById(R.id.t_title);
            t_date = itemView.findViewById(R.id.t_date);
            r_box = itemView.findViewById(R.id.r_box);
            t_similarity = itemView.findViewById(R.id.t_titleSimilarity);
            i_avatar = itemView.findViewById(R.id.i_avatar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickResult(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public WantedObject getItem(int id) {
        return list.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListenerResult itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListenerResult {
        void onItemClickResult(View view, int position);
    }
}

