package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.Avatars;
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;

public class MyAvatarListAdapter extends RecyclerView.Adapter<MyAvatarListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Avatars> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public static String secilenURL = "";
    int width;

    // data is passed into the constructor
    public MyAvatarListAdapter(Activity context, ArrayList<Avatars> list, int width, String secilenURL) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.width = width;
        this.secilenURL = secilenURL;
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

        holder.r_main.getLayoutParams().width = width/3-60;
        holder.r_main.getLayoutParams().height = width/3-60;

        if(secilenURL.equals(list.get(position).getUrl())){
            holder.v_border.setVisibility(View.VISIBLE);
        }
        else{
            holder.v_border.setVisibility(View.GONE);
        }

        try{
            Glide
                    .with(context)
                    .load("https:mobiloby.com/_filter/assets/profile/" + list.get(position).getUrl())
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_f_char)
                    .into(holder.icon);
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

        ImageView icon;
        View v_border;
        RelativeLayout r_main;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            v_border = itemView.findViewById(R.id.v_border);
            r_main = itemView.findViewById(R.id.r_main);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Avatars getItem(int id) {
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

