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

import com.mobiloby.filter.R;
import com.mobiloby.filter.models.TodoObject;

import java.util.ArrayList;

public class MyRecycleAnswersListAdapter extends RecyclerView.Adapter<MyRecycleAnswersListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<String> list;
    public static int selected_pos=-1;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecycleAnswersListAdapter(Activity context, ArrayList<String> list, int index) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.selected_pos = index;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_answers, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.t_title.setText(list.get(position));

        if(position==selected_pos){
            holder.cons_box.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorBackground3));
        }
        else{
            holder.cons_box.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_title;
        ConstraintLayout cons_box;

        ViewHolder(View itemView) {
            super(itemView);
            t_title = itemView.findViewById(R.id.t_title);
            cons_box = itemView.findViewById(R.id.cons_box);

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

