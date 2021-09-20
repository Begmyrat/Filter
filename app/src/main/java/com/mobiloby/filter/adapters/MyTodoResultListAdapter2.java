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
        isFriendsActive = true;
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
        }
        else{
            ResultHolder resultHolder = (ResultHolder) holder;
            TodoObject todoObject = list.get(position);
            try{
//                resultHolder.t_username.setText(""+todoObject.getUsername());
                resultHolder.t_activity.setText(""+todoObject.getTodoDescription());
                resultHolder.t_feeling.setText(""+todoObject.getFeeling());
                resultHolder.t_location.setText(""+todoObject.getLocation());
                resultHolder.t_time.setText(""+todoObject.getTime());
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
        popupIstekGonder(istek_pos);
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
                    if(isFriendsActive){
                        i_personActivePassive.setImageResource(R.drawable.person_active);
                    }
                    else{
                        i_personActivePassive.setImageResource(R.drawable.person_passive);
                    }
                }
            });

            e_search.addTextChangedListener(new TextWatcher() {

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for (int i=list.size()-1;i>0;i--){
                        list.remove(i);
                    }
                    if(s.length()==0){
                        for(int i=0;i<listAll.size(); i++){
                            list.add(listAll.get(i));
                        }
                    }
                    else{
                        for(int i=1;i<listAll.size();i++){
//                            if(list.contains(listAll.get(i)))
                                if(listAll.get(i).getFeeling().contains(s) || listAll.get(i).getLocation().contains(s) || listAll.get(i).getTodoDescription().contains(s)){
                                    list.add(listAll.get(i));
                                }
                        }
                    }
                    notifyDataSetChanged();
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

    public class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView t_username, t_activity, t_feeling, t_location, t_time;
        ImageView i_avatar, i_activity, i_feeling;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

//            t_username = itemView.findViewById(R.id.t_username);
            t_activity = itemView.findViewById(R.id.t_activity);
            t_feeling = itemView.findViewById(R.id.t_feeling);
            t_location = itemView.findViewById(R.id.t_location);
            t_time = itemView.findViewById(R.id.t_time);
            i_avatar = itemView.findViewById(R.id.i_avatar);
            i_activity = itemView.findViewById(R.id.i_activity);
            i_feeling = itemView.findViewById(R.id.i_feeling);

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

    public void popupIstekGonder(int pos){

//        builder = new Dialog(context, R.style.AlertDialogCustom);
//        View view;
//        view = LayoutInflater.from(context).inflate(R.layout.popup_istek_kabul, null);
//
//        TextView t_username = view.findViewById(R.id.t_username);
//        t_username.setText(list.get(0).getRecommendedUsers().get(pos).getUsername());
//        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
//        t_subtitle.setText("İstek göndermek istiyor musunuz?");
//        TextView t_reddet = view.findViewById(R.id.t_reddet);
//        t_reddet.setText("Hayır");
//        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
//        t_kabulEt.setText("Evet");
//        LinearLayout l_profile = view.findViewById(R.id.l_profile);
//
//        view.findViewById(R.id.l_answers).setVisibility(View.GONE);
//
//
//        if(list.get(0).getRecommendedUsers().get(istek_pos).getProfil_gizlilik().equals("1"))
//            l_profile.setVisibility(View.GONE);
//        else
//            l_profile.setVisibility(View.VISIBLE);
//
//        l_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//                Intent intent = new Intent(context, InformationActivity.class);
//                intent.putExtra("username", list.get(0).getRecommendedUsers().get(istek_pos).getUsername());
//                context.startActivity(intent);
//            }
//        });
//
//        view.findViewById(R.id.r_clickReddet).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.r_clickKabulEt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//                sendIstek(list.get(0).getRecommendedUsers().get(istek_pos).getUsername(), istek_pos);
//            }
//        });
//
//        builder.setCancelable(true);
//        builder.setContentView(view);
//        builder.show();
    }

    private void sendIstek(final String friend_username_unique, int pos) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username_unique);
                jsonData.put("from_where", "1");

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
//                    list.get(0).getRecommendedUsers().remove(pos);
//                    mainPageObjectsAdapter.notifyItemRemoved(pos);
                    notifyDataSetChanged();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. 3", context, true);
                }

            }
        }.execute(null, null, null);
    }
}

