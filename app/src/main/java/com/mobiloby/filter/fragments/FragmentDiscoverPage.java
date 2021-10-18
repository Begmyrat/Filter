package com.mobiloby.filter.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityCategory3;
import com.mobiloby.filter.activities.ActivityProfileEdit2;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyRecycleListAdapter;
import com.mobiloby.filter.adapters.MyTodoResultListAdapter;
import com.mobiloby.filter.adapters.MyTodoResultListAdapter2;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.TodoObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDiscoverPage extends Fragment implements MyTodoResultListAdapter2.ItemClickListener{

    View view;
    MainActivity activity;
    MyTodoResultListAdapter2 adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TodoObject> todoObjects, todoObjectsAll;
    JSONParser jsonParser;
    JSONObject jsonObject;
    SharedPreferences preferences;
    String username, userImgUrl="";
    TextView t_activity, t_location, t_time;
    ImageView i_activity, i_avatar;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        t_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "activity" , Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        userImgUrl = preferences.getString("avatar_id","");

        Glide
                .with(activity)
                .load("https:mobiloby.com/_filter/assets/profile/" + userImgUrl)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.filtryenilogo)
                .into(i_avatar);

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground3));// set status background white
        todoObjects.clear();
        todoObjectsAll.clear();
        getTodo();
    }

    private void prepareMe() {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique","");
        i_avatar = view.findViewById(R.id.i_avatar);
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        todoObjects = new ArrayList<>();
        todoObjectsAll = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyTodoResultListAdapter2(activity, todoObjects, todoObjectsAll);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        todoObjects.add(new TodoObject(0));

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        t_activity = view.findViewById(R.id.t_activity);
        t_location = view.findViewById(R.id.t_location);
        t_time = view.findViewById(R.id.t_time);
        i_activity = view.findViewById(R.id.i_activity);
    }

    private void getTodo() {

        progressDialog.show();
        todoObjects.clear();
        todoObjectsAll.clear();

        TodoObject o = new TodoObject();
        o.setView_type(0);
        todoObjects.add(o);
        todoObjectsAll.add(o);

        final String url = "https://mobiloby.com/_filter/get_todo_by_user.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);

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



                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String todo_id = c.getString("todo_id");
                            String user_name = c.getString("user_name");
                            String todo_desc = c.getString("todo_description");
                            String todo_loc = c.getString("todo_location");

                            String todo_minutes = c.getString("todo_minutes");
                            int minutes = Integer.parseInt(todo_minutes);
                            String hour = "";
                            String message = "";
                            if(minutes/60>0){
                                hour += " "+minutes/60;
                                minutes -= 60*(minutes/60);
                                message += hour + "s";
                            }
                            if(minutes>0){
                                message += " " + minutes + "d";
                            }
                            t_time.setText(message);
                            t_location.setText(todo_loc);
                            t_activity.setText(todo_desc);
                        }

                        getUsersByTODO();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(activity, "error jiim getTODO", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    popup();
                    getUsersByTODO();
//                    adapter.notifyDataSetChanged();
                }

            }
        }.execute(null, null, null);
    }

    private void getUsersByTODO() {


        final String url = "https://mobiloby.com/_filter/get_users_by_todo.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);

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

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String todo_id = c.getString("todo_id");
                            String user_name = c.getString("user_name");
                            String todo_desc = c.getString("todo_description");
                            String todo_location = c.getString("todo_location");
                            String todo_is_friend = c.getString("todo_is_friend");
                            String minutes = c.getString("todo_minutes");
                            String user_profile_url = c.getString("user_profile_url");
                            TodoObject o = new TodoObject(todo_id, user_name, todo_desc, todo_location);
                            o.setTime(minutes);
                            o.setUserProfileUrl(user_profile_url);
                            o.setTodoIsFriend(todo_is_friend);
                            o.setView_type(1);
                            todoObjects.add(o);
                            todoObjectsAll.add(o);
                            Toast.makeText(activity, "minYusuf: " + minutes, Toast.LENGTH_SHORT).show();
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "error jiim getUserByTODO", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);

                }
                progressDialog.dismiss();
            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position, ArrayList<TodoObject> list) {
        Intent intent = new Intent(activity, ActivityProfileEdit2.class);
        intent.putExtra("username", list.get(position).getUsername());
        intent.putExtra("userProfileUrl", list.get(position).getUserProfileUrl());
        startActivity(intent);
    }
}