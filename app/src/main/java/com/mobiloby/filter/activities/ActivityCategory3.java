package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobiloby.filter.adapters.MyTodoResultListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.adapters.MyRecycleListAdapter;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.TodoObject;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory3 extends AppCompatActivity implements MyRecycleListAdapter.ItemClickListener, MyTodoResultListAdapter.ItemClickListenerResult {

    SharedPreferences preferences;
    Bundle extras;
    String username = "", istek_username;
    ArrayList<TodoObject> todoResultList, todoList;
    MyTodoResultListAdapter adapter;
    MyRecycleListAdapter adapterRecycle;
    RecyclerView recyclerView, recyclerViewResult;
    RecyclerView.LayoutManager layoutManager, layoutManagerResult;
    JSONParser jsonParser;
    JSONObject jsonObject;
    Dialog builder;
    EditText e_todo;
    Boolean isTodoExist = false;
    BottomSheetDialog bd;
    TextView t_subtitle;
    int istek_pos = -1;
    ImageView i_avatar;
    TextView t_countArkadas, t_countIstek, t_username, t_profilDoluluk;
    String countIstek, countArkadas, profileURL, profilDoluluk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category3);

        prepareMe();

        getTodo();
    }

    public void popupConnect(String id, String username){
        bd = new BottomSheetDialog(this);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_connect, null);
        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(username);

        bd.setContentView(view);
        bd.show();
    }

    public void clickCloseBottom(View view) {
        bd.dismiss();
    }

    public void clickIstekGonder(View view) {
        Toast.makeText(this, "Arkadaşlık isteği gönderildi", Toast.LENGTH_SHORT).show();
        bd.dismiss();
        sendIstek();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.MainBlue));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        recyclerViewResult = findViewById(R.id.recyclerviewResult);
        recyclerView = findViewById(R.id.recycleView);
        todoList = new ArrayList<>();
        todoResultList = new ArrayList<>();
        adapter = new MyTodoResultListAdapter(this, todoResultList);
        adapterRecycle = new MyRecycleListAdapter(this, todoList);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerResult = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewResult.setLayoutManager(layoutManagerResult);
        t_subtitle = findViewById(R.id.t_subtitle);

        adapterRecycle.setClickListener(this);
        recyclerViewResult.setAdapter(adapter);
        adapter.setClickListener(this);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
        }

        t_countIstek = findViewById(R.id.t_countIstek);
        t_countArkadas = findViewById(R.id.t_countArkadas);
        countIstek = preferences.getString("count_istek", "");
        countArkadas = preferences.getString("count_arkadas", "");
        profileURL = preferences.getString("user_profile_url", "");
        profilDoluluk = preferences.getString("profil_doluluk", "");
        t_countIstek.setText(countIstek);
        t_countArkadas.setText(countArkadas);
        t_profilDoluluk = findViewById(R.id.t_profilDoluluk);
        int d  = Integer.parseInt(profilDoluluk);
        d = d*100/7;
        t_profilDoluluk.setText("%" + d + " Profil Doluluğu");
        i_avatar = findViewById(R.id.i_avatar);
        t_username = findViewById(R.id.t_username);
        t_username.setText(username);

        Glide
                .with(this)
                .load("https:mobiloby.com/_filter/assets/profile/" + profileURL)
                .centerCrop()
                .placeholder(R.drawable.ic_f_char)
                .into(i_avatar);
    }

    private void sendIstek() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istek_username);
                jsonData.put("from_where", "3");

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
                    todoResultList.remove(istek_pos);
                    adapter.notifyDataSetChanged();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickTemizle(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, ""+todoList.get(position).getTodoDescription(), Toast.LENGTH_SHORT).show();
//        if(todoList.get(position).getTodoDescription().equals("+")){
            popup(isTodoExist);
//        }
    }

    public void popup(Boolean isExist){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_todo, null);
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

    private void getTodo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        todoList.clear();

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
                            Toast.makeText(ActivityCategory3.this, "min: " + todo_minutes, Toast.LENGTH_SHORT).show();
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
                            t_profilDoluluk.setText(message);

                            TodoObject o = new TodoObject(todo_id, user_name, todo_desc);
                            todoList.add(o);
                        }
                        todoList.add(new TodoObject("-1","-1","+"));
                        adapterRecycle = new MyRecycleListAdapter(ActivityCategory3.this, todoList);
                        adapterRecycle.setClickListener(ActivityCategory3.this);
                        recyclerView.setAdapter(adapterRecycle);
                        isTodoExist = true;
                        getUsersByTODO();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory3.this, "error jiim getTODO", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    popup(isTodoExist);
                }

            }
        }.execute(null, null, null);
    }

    private void getUsersByTODO() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        todoResultList.clear();

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
                            todoResultList.add(o);
                            Toast.makeText(ActivityCategory3.this, "minYusuf: " + minutes, Toast.LENGTH_SHORT).show();
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory3.this, "error jiim getUserByTODO", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickClose(View view) {
        builder.dismiss();
        finish();
    }

    public void clickInsertTodo(View view) {
        builder.dismiss();
        String todo = e_todo.getText().toString();
        if(todo.length()>0){
            if(isTodoExist)
                updateTodo(todo);
            else
                insertTodo(todo);
        }
    }

    private void insertTodo(String todo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String todoDes = todo;

        final String url = "https://mobiloby.com/_filter/insert_todo.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("todo_description", todoDes);

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

                if (res.equals("1") || res.equals("2") || res.equals("3")) {
                    getTodo();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void updateTodo(String todo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String todoDes = todo;

        final String url = "https://mobiloby.com/_filter/update_todo.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("todo_description", todoDes);

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
                    getTodo();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickProfileTop(View view) {
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void onItemClickResult(View view, int position) {
        // result list click
        istek_username = todoResultList.get(position).getUsername();
        istek_pos = position;
        popupConnect(todoResultList.get(position).getTodoID(), istek_username);
    }
}