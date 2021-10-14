package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiloby.filter.R;
import com.mobiloby.filter.models.SocialMediaObject;

import java.util.ArrayList;

public class MySocialRecycleListAdapter extends RecyclerView.Adapter<MySocialRecycleListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<SocialMediaObject> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListenerSocial mClickListener;

    // data is passed into the constructor
    public MySocialRecycleListAdapter(Activity context, ArrayList<SocialMediaObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_social_history, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        if(position == list.size()-1){
////            holder.c_box.getLayoutParams().height = 60;
//            holder.c_box.setVisibility(View.INVISIBLE);
//        }
//        else{
            SocialMediaObject o = list.get(position);
            holder.t_username.setText(o.getSocial_type());
            holder.t_social_username.setText(o.getSocial_username());
            holder.t_social_username_other.setText(o.getSocial_username_other());
            holder.c_box.setVisibility(View.VISIBLE);

            if(o.getSocial_type().equals("instagram")){
                holder.i_avatar.setImageResource(R.drawable.instagram_active);
            }
            else if(o.getSocial_type().equals("snapchat")){
                holder.i_avatar.setImageResource(R.drawable.snapchat_active);
            }
            else if(o.getSocial_type().equals("tiktok")){
                holder.i_avatar.setImageResource(R.drawable.tiktok_active);
            }
            else if(o.getSocial_type().equals("facebook")){
                holder.i_avatar.setImageResource(R.drawable.facebook_active);
            }
            else{
                holder.i_avatar.setImageResource(R.drawable.twitter_active);
            }
//        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView i_avatar;
        TextView t_username, t_social_username, t_social_username_other;
        ConstraintLayout c_box;

        ViewHolder(View itemView) {
            super(itemView);

            i_avatar = itemView.findViewById(R.id.i_avatar);
            t_username = itemView.findViewById(R.id.t_username);
            t_social_username = itemView.findViewById(R.id.t_activity);
            t_social_username_other = itemView.findViewById(R.id.t_location);
            c_box = itemView.findViewById(R.id.c_box);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public static int getValueInDP(Context context, int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }


    // convenience method for getting data at click position
    public SocialMediaObject getItem(int id) {
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

