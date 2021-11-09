package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.InformationActivity;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.CategoryObject;
import com.mobiloby.filter.models.CurrentActivityObject;
import com.mobiloby.filter.models.TodoObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTodoResultListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyRecommendListAdapter.ItemClickListener{

    private Activity context;
    public ArrayList<TodoObject> list, listAll;
    static ItemClickListener mClickListener = null;
    int istek_pos;
    Dialog builder;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username;
    boolean isFriendsActive;

    public MyTodoResultListAdapter2(Activity context, ArrayList<TodoObject> list, ArrayList<TodoObject> listAll) {
        this.context = context;
        this.list = list;
        this.listAll = listAll;
        isFriendsActive = false;
    }

    public void setFriendsPassive(){
        isFriendsActive = false;
    }

    public List<TodoObject> getItems(){
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo_header, parent, false);
            HeaderHolder headerHolder = new HeaderHolder(view);
            return headerHolder;
        }
        else if(viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo_result, parent, false);
            ResultHolder resultHolder = new ResultHolder(view);
            return resultHolder;
        }
        return null;

    }

    public ArrayList<TodoObject> getList(){
        return list;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==0){
            HeaderHolder headerHolder = (HeaderHolder) holder;
            ((HeaderHolder) holder).e_search.requestFocus();

            if(isFriendsActive){
                ((HeaderHolder) holder).i_personActivePassive.setImageResource(R.drawable.person_active);
            }
            else{
                ((HeaderHolder) holder).i_personActivePassive.setImageResource(R.drawable.person_passive);
            }
        }
        else{
            ResultHolder resultHolder = (ResultHolder) holder;
            TodoObject todoObject = list.get(position);
            try{
                resultHolder.t_username.setText(""+todoObject.getUsername());
                resultHolder.t_activity.setText(""+todoObject.getTodoDescription());
                resultHolder.t_location.setText(""+todoObject.getLocation());
                resultHolder.t_time.setText(""+todoObject.getTime());

//                String todo_minutes = todoObject.getTime();
//                int minutes = Integer.parseInt(todo_minutes);
//                String hour = "";
//                String message = "";
//                if(minutes/60>0){
//                    hour += " "+minutes/60;
//                    minutes -= 60*(minutes/60);
//                    message += hour + "s";
//                }
//                if(minutes>0){
//                    message += " " + minutes + "d";
//                }

                resultHolder.t_time.setText(""+todoObject.getTime());

                Glide
                        .with(context)
                        .load("https:mobiloby.com/_filter/assets/profile/" + todoObject.getUserProfileUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_f_char)
                        .into(((ResultHolder) holder).i_avatar);

            }catch (Exception e){}

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getView_type();
    }

    @Override
    public void onItemClick(View view, int position) {
        // click horizontal recommend object
        istek_pos = position;
    }

    public class HeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        EditText e_search;
        ImageView i_personActivePassive;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);

            e_search = itemView.findViewById(R.id.e_search);
            i_personActivePassive = itemView.findViewById(R.id.i_personActivePassive);

            i_personActivePassive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFriendsActive = !isFriendsActive;
                    e_search.setText("");
                    if(isFriendsActive){
                        i_personActivePassive.setImageResource(R.drawable.person_active);
                    }
                    else{
                        i_personActivePassive.setImageResource(R.drawable.person_passive);
                    }
                    performFilter();
                }
            });

            e_search.addTextChangedListener(new TextWatcher() {

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    for (int i=list.size()-1;i>0;i--){
                        list.remove(i);
                    }
                    if(s.length()==0){
                        for(int i=1;i<listAll.size();i++)
                            list.add(listAll.get(i));
                    }
                    else{
                        for(int i=1;i<listAll.size();i++){
                            if(listAll.get(i).getLocation().contains(s) || listAll.get(i).getTodoDescription().contains(s)){
                                list.add(listAll.get(i));
                            }
                        }
                    }
                    notifyDataSetChanged();
//                    e_search.setSelection(0);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), getList());
        }
    }

    private void performFilter() {
        list.clear();

        if(isFriendsActive){
            TodoObject o = new TodoObject();
            o.setView_type(0);
            list.add(o);
            for(int i=1;i<listAll.size();i++){
                if(listAll.get(i).getTodoIsFriend().equals("1")){
                    list.add(listAll.get(i));
                }
            }
        }
        else{
            list.addAll(listAll);
        }
        notifyDataSetChanged();
    }

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView t_username, t_activity, t_location, t_time;
        ImageView i_avatar, i_activity;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

//            t_username = itemView.findViewById(R.id.t_username);
            t_activity = itemView.findViewById(R.id.t_activity);
            t_username = itemView.findViewById(R.id.t_username);
            t_location = itemView.findViewById(R.id.t_location);
            t_time = itemView.findViewById(R.id.t_time);
            i_avatar = itemView.findViewById(R.id.i_avatar);
            i_activity = itemView.findViewById(R.id.i_activity);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition(), getList());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, ArrayList<TodoObject> list);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}

