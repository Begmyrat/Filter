package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;
import java.util.List;

public class MyRecommendListAdapter extends RecyclerView.Adapter<MyRecommendListAdapter.ViewHolder> {

    private Activity context;
    private List<UserObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int width=0, height=0;

    // data is passed into the constructor
    public MyRecommendListAdapter(Activity context, List<UserObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    public void setWH(int width, int height){
        this.width = width;
        this.height = height;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(height>=700){
            holder.i_avatar.getLayoutParams().height = dpToPx(70, context);
            holder.i_avatar.getLayoutParams().width = dpToPx(70, context);
            holder.r_main.getLayoutParams().height = dpToPx(110, context);
            holder.r_main.getLayoutParams().width = dpToPx(70, context);
        }
        else{
            holder.i_avatar.getLayoutParams().height = dpToPx(60, context);
            holder.i_avatar.getLayoutParams().width = dpToPx(60, context);
            holder.r_main.getLayoutParams().height = dpToPx(100, context);
            holder.r_main.getLayoutParams().width = dpToPx(60, context);
            holder.t_percentage.setTextSize(8);
            holder.t_percentage.getLayoutParams().width = dpToPx(27, context);
            holder.t_percentage.getLayoutParams().height = dpToPx(12, context);
        }

        holder.t_percentage.setVisibility(View.VISIBLE);
        holder.t_percentage.setText("%"+list.get(position).getSimilarity());
        holder.t_username2.setText(""+list.get(position).getUsername());
        try{
            Glide
                    .with(context)
                    .load("https:mobiloby.com/_filter/assets/profile/" + list.get(position).getAvatar_id())
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.filtryenilogo)
                    .into(holder.i_avatar);
        }catch (Exception e){

        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView t_percentage, t_username2;
        ImageView i_avatar;
        RelativeLayout r_main;
//        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            i_avatar = itemView.findViewById(R.id.i_avatar);
            t_percentage = itemView.findViewById(R.id.t_percentage);
//            cardView = itemView.findViewById(R.id.cardview2);
            t_username2 = itemView.findViewById(R.id.t_username2);
            r_main = itemView.findViewById(R.id.r_main);

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

