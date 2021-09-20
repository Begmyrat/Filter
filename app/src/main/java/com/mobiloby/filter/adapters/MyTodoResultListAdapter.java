package com.mobiloby.filter.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.TodoObject;

import java.util.ArrayList;

public class MyTodoResultListAdapter extends RecyclerView.Adapter<MyTodoResultListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<TodoObject> list;
    private LayoutInflater mInflater;
    private ItemClickListenerResult mClickListener;


    // data is passed into the constructor
    public MyTodoResultListAdapter(Activity context, ArrayList<TodoObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_search_result, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.c_main.getLayoutParams().width = width/2-20;

        holder.textview_title.setText(list.get(position).getUsername());
        holder.t_todo.setText(list.get(position).getTodoDescription());

        int minutes = Integer.parseInt(list.get(position).getTime());
        String hour = "";
        String message = "";
        if(minutes/60>0){
            hour += " "+minutes/60;
            minutes -= 60*(minutes/60);
            message += hour + " saat";
        }
        if(minutes>0){
            message += " " + minutes + " dakika";
        }
        holder.t_time.setText(message);

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

        TextView textview_title, t_todo, t_time;
        ImageView i_avatar;

        ViewHolder(View itemView) {
            super(itemView);
            t_time = itemView.findViewById(R.id.t_time);
            t_todo = itemView.findViewById(R.id.t_address);
            textview_title = itemView.findViewById(R.id.t_title_big);
            i_avatar = itemView.findViewById(R.id.i_avatar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClickResult(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public TodoObject getItem(int id) {
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

