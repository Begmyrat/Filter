package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityCategory2_ShowAnswers;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyFriendListAdapter extends RecyclerView.Adapter<MyFriendListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<UserObject> list;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int width;
    SharedPreferences preferences;
    String username;
    JSONParser jsonParser;
    JSONObject jsonObject;
    ProgressDialog progressDialog;

    // data is passed into the constructor
    public MyFriendListAdapter(Activity context, ArrayList<UserObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.width = width;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        username = preferences.getString("username_unique", "");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_friends, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(list.get(position).getFriend()){
            holder.t_kabulEt.setVisibility(View.GONE);
            holder.i_reddet.setVisibility(View.GONE);
        }
        else{
            holder.t_kabulEt.setVisibility(View.VISIBLE);
            holder.i_reddet.setVisibility(View.VISIBLE);
        }

        String lastMessage = preferences.getString(username + "-" + list.get(position).getUsername(), "");

        if(list.get(position).getLastMessage()==null || list.get(position).getLastMessage().equals("")){
            holder.t_message.setVisibility(View.GONE);
        }
        else{
            holder.t_message.setVisibility(View.VISIBLE);
            if(!list.get(position).getLastMessage().equals(lastMessage)){
//            holder.t_message.setText(list.get(position).getLastMessage());
                holder.t_message.setText("New message");
//            holder.t_message.setTypeface(null, Typeface.BOLD);
                holder.t_message.setTypeface(holder.t_message.getTypeface(), Typeface.BOLD);
            }
            else{
                holder.t_message.setText(list.get(position).getLastMessage());
//            holder.t_message.setTypeface(null, Typeface.NORMAL);
                holder.t_message.setTypeface(holder.t_message.getTypeface(), Typeface.NORMAL);
            }
        }

        holder.t_username.setText(list.get(position).getUsername());

        try{
            Glide
                    .with(context)
                    .load("https:mobiloby.com/_filter/assets/profile/" + list.get(position).getAvatar_id())
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

        TextView t_username, t_message, t_kabulEt;
        ImageView i_avatar, i_reddet;

        ViewHolder(View itemView) {
            super(itemView);
            t_username = itemView.findViewById(R.id.t_username);
            t_message = itemView.findViewById(R.id.t_message);
            i_avatar = itemView.findViewById(R.id.i_avatar);
            i_reddet = itemView.findViewById(R.id.i_cross);
            t_kabulEt = itemView.findViewById(R.id.t_kabulEt);

            t_kabulEt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    istekKabulEt(getAdapterPosition(), list.get(getAdapterPosition()).getUsername());
                }
            });

            i_reddet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    istekReddet(getAdapterPosition(), list.get(getAdapterPosition()).getUsername());
                }
            });

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

    private void istekReddet(int index, String istek_username) {
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/delete_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istek_username);

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(success);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
                    list.remove(index);
                    notifyDataSetChanged();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", context, true);
                }

            }
        }.execute(null, null, null);
    }

    private void istekKabulEt(int index, String istek_username) {

        progressDialog.show();


        final String url = "https://mobiloby.com/_filter/insert_friend.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istek_username);

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(success);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
                    list.get(index).setFriend(true);
                    notifyDataSetChanged();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", context, true);
                }

            }
        }.execute(null, null, null);
    }
}

