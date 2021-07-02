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
import com.mobiloby.filter.models.UserObject;

import java.util.ArrayList;

public class MyRecommendListAdapter extends RecyclerView.Adapter<MyRecommendListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<UserObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int[] avatars = {R.drawable.man, R.drawable.man_old, R.drawable.girl, R.drawable.boy};

    // data is passed into the constructor
    public MyRecommendListAdapter(Activity context, ArrayList<UserObject> list) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        holder.t_title.setText(list.get(position).getBaslik());

        holder.t_username.setText(list.get(position).getUsername());
        holder.t_status.setText("%"+(Math.round((Double.parseDouble(list.get(position).getSimilarity())*100/7)*10)/10.0));

        try{
            int avatar_index = Integer.parseInt(list.get(position).getAvatar_id());
            holder.i_avatar.setImageResource(avatars[avatar_index%4]);
        }catch (Exception e){

        }

//        if(position%10==0){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPaletteBlue));
//        }
//        else if(position%10==1){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPaletteRed));
//        }
//        else if(position%10==2){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPalettePurpleDark));
//        }
//        else if(position%10==3){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPaletteGreen));
//        }
//        else if(position%10==4){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPaletteYellow));
//        }
//        else if(position%10==5){
//            holder.r_box.getBackground().setTint(context.getResources().getColor(R.color.colorPaletteRed));
//        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout r_follow;
        TextView t_username,t_status;
        ImageView i_avatar;

        ViewHolder(View itemView) {
            super(itemView);
            t_username = itemView.findViewById(R.id.t_username);
            t_status = itemView.findViewById(R.id.t_status);
            r_follow = itemView.findViewById(R.id.r_follow);
            i_avatar = itemView.findViewById(R.id.i_icon);

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

