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

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityCategory3;
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
    String username;
    TextView t_activity, t_feeling, t_location, t_time;
    ImageView i_activity, i_feeling;
    Dialog builder;
    EditText e_todo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();
        getTodo();

        t_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "activity" , Toast.LENGTH_SHORT).show();
            }
        });

        t_feeling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "feeling", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground3));// set status background white
    }

    private void prepareMe() {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique","");
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        todoObjects = new ArrayList<>();
        todoObjectsAll = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyTodoResultListAdapter2(activity, todoObjects, todoObjectsAll);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        todoObjects.add(new TodoObject(0));

        t_activity = view.findViewById(R.id.t_activity);
        t_feeling = view.findViewById(R.id.t_feeling);
        t_location = view.findViewById(R.id.t_location);
        t_time = view.findViewById(R.id.t_time);
        i_activity = view.findViewById(R.id.i_activity);
        i_feeling = view.findViewById(R.id.i_feeling);
    }

    private void getTodo() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        todoObjects.clear();

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

                progressDialog.dismiss();

                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String todo_id = c.getString("todo_id");
                            String user_name = c.getString("user_name");
                            String todo_desc = c.getString("todo_description");

                            String todo_minutes = c.getString("todo_minutes");
                            int minutes = Integer.parseInt(todo_minutes);
                            String hour = "";
                            String message = "Bu Aktivite için son";
                            if(minutes/60>0){
                                hour += " "+minutes/60;
                                minutes -= 60*(minutes/60);
                                message += hour + " saat";
                            }
                            if(minutes>0){
                                message += " " + minutes + " dakika";
                            }
                            t_time.setText(message);
                            t_location.setText("Cinemax, ANKARA");
                            t_feeling.setText("Feeling excited");
                            t_activity.setText(todo_desc);
                        }

                        for(int i=0;i<10;i++){
                            todoObjects.add(new TodoObject("1", ""+i, "i: " + i, "Feeling sad", "Ankara: " + i));
                        }
                        Toast.makeText(activity, "size: " + todoObjects.size(), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();

//                        getUsersByTODO();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "error jiim getTODO", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    popup();
                    for(int i=0;i<10;i++){
                        TodoObject o = new TodoObject("1", ""+i, "i: " + i, "Feeling sad", "Ankara: " + i);
                        o.setView_type(1);
                        o.setUsername("hehehe");
                        TodoObject o1 = new TodoObject("1", ""+i, "i: " + i, "Feeling sad", "Ankara: " + i);
                        o1.setView_type(1);
                        o1.setUsername("hehehe");
                        todoObjects.add(o);
                        todoObjectsAll.add(o1);
                    }
                    Toast.makeText(activity, "size: " + todoObjects.size(), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }

            }
        }.execute(null, null, null);
    }

    private void getUsersByTODO() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        todoObjects.clear();

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
                            String minutes = c.getString("todo_minutes");
                            String user_profile_url = c.getString("user_profile_url");
                            TodoObject o = new TodoObject(todo_id, user_name, todo_desc);
                            o.setTime(minutes);
                            o.setUserProfileUrl(user_profile_url);
                            todoObjects.add(o);
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

            }
        }.execute(null, null, null);
    }

    public void popupIstekGonder(int pos){

        builder = new Dialog(activity, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(todoObjects.get(pos).getUsername());
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("İstek göndermek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Hayır");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Evet");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);

        view.findViewById(R.id.l_answers).setVisibility(View.GONE);


//        if(todoObjects.get(pos).getProfil_gizlilik().equals("1"))
//            l_profile.setVisibility(View.GONE);
//        else
//            l_profile.setVisibility(View.VISIBLE);
//
//        l_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.dismiss();
//                Intent intent = new Intent(activity, InformationActivity.class);
//                intent.putExtra("username", list.get(0).getRecommendedUsers().get(istek_pos).getUsername());
//                activity.startActivity(intent);
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

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    private void sendIstek(final String friend_username_unique, int pos) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
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
//                    notifyDataSetChanged();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. 3", activity, true);
                }

            }
        }.execute(null, null, null);
    }

    public void popup(Boolean isExist){
        builder = new Dialog(activity, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.popup_layout_todo, null);
        e_todo = view.findViewById(R.id.e_info);
        e_todo.setHint("örnek: kitap veya müzik adı girebilirsiniz...");

        TextView t_title = view.findViewById(R.id.t_title);
        if(isExist){
            t_title.setText("Aktiviteyi Güncelle");
        }
        else{
            t_title.setText("Aktivite Ekle");
        }

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    @Override
    public void onItemClick(View view, int position, ArrayList<TodoObject> list) {
        Toast.makeText(activity, "pos: " + position, Toast.LENGTH_SHORT).show();
    }
}