package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiloby.filter.adapters.MyMessageListAdapter;
import com.mobiloby.filter.models.ChatObject;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.adapters.MyChatListAdapter;
import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

public class ActivityChat extends AppCompatActivity {

    public static RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public static ArrayList<ChatObject> chatObjects, newChats;
    public static MyMessageListAdapter adapter;
    EditText e_message;
    int d=0;
    public static JSONParser jsonParser;
    public static JSONObject jsonObject;
    public static Bundle extras;
    public static String username="", friend_username="", user_profile_url="", user_profile_url_other="", user_player_id_other="";
    public static TextView t_friendUsername;
    public static Dialog builder;
    public static HashSet<String> hashSet;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prepareMe();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getChats();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorChatBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorChatBackground));
        hashSet = new HashSet<>();
        extras = getIntent().getExtras();
        t_friendUsername = findViewById(R.id.t_usernameFriend);
        if(extras!=null){
            username = extras.getString("username");
            friend_username = extras.getString("username_friend");
            t_friendUsername.setText(friend_username);
//            friend_token = extras.getString("token_friend");
            user_player_id_other = extras.getString("user_player_id_other");
            user_profile_url = extras.getString("user_profile_url");
            user_profile_url_other = extras.getString("user_profile_url_other");
        }

        recyclerView = findViewById(R.id.recyclerview_chat);
        chatObjects = new ArrayList<>();
        newChats = new ArrayList<>();
        adapter = new MyMessageListAdapter(this, chatObjects, username, friend_username);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        e_message = findViewById(R.id.e_message);
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickSend(View view) {
        sendChat();
    }

    private void sendChat() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        Toast.makeText(this, "user: " + username, Toast.LENGTH_SHORT).show();

        final String url = "https://mobiloby.com/_filter/insert_chat.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username);
                jsonData.put("message", e_message.getText().toString());

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

                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    String hs = ""+hour;
                    if(hs.length()<2){
                        hs = "0"+hs;
                    }
                    int minute = calendar.get(Calendar.MINUTE);
                    String ms = ""+minute;
                    if(ms.length()<2){
                        ms = "0"+ms;
                    }

//                    chatObjects.add(new ChatObject("-1", username, friend_username, e_message.getText().toString(), hs+":"+ms, username));
//                    adapter.notifyDataSetChanged();
//                    recyclerView.scrollToPosition(chatObjects.size()-1);
                    pushNotification();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private static void getChats() {

        final String url = "https://mobiloby.com/_filter/get_chats.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username);

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
                            String chat_id = c.getString("id");
                            String chat_user_name = c.getString("user_name_unique");
                            String chat_friend_user_name = c.getString("friend_user_name_unique");
                            String chat_message = c.getString("message");
                            String chat_date = c.getString("message_date");
                            String chat_kimdenKime = c.getString("kimden_kime");


                            ChatObject o = new ChatObject(chat_id, chat_user_name, chat_friend_user_name, chat_message, chat_date, chat_kimdenKime);
                            if(!hashSet.contains(chat_id)){
                                chatObjects.add(o);
                            }
                            hashSet.add(chat_id);
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatObjects.size()-1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{

                }

            }
        }.execute(null, null, null);
    }

    private void pushNotification() {

        final String url = "https://mobiloby.com/_filter/bildirim_gonder_deneme.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("friend_token", user_player_id_other);
                jsonData.put("message", e_message.getText().toString());

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

                e_message.setText("");

                if (res.equals("1")) {
                    Toast.makeText(ActivityChat.this, "gonderdik", Toast.LENGTH_SHORT).show();
                }
//                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
//                }

            }
        }.execute(null, null, null);
    }

    public static void insertChatObject(String from, String to, String message, String date, String kimdenKime){
        getChats();
//        adapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(chatObjects.size()-1);
    }

    public void clickMore(View view) {
        popupDelete();
    }

    public void popupDelete(){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(friend_username);
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("Arkadaşınızı gerçekten de silmek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Hayır");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Evet");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);
        l_profile.setVisibility(View.GONE);
        LinearLayout l_answers = view.findViewById(R.id.l_answers);
        l_answers.setVisibility(View.GONE);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickReddet(View view) {
        builder.dismiss();
    }

    public void clickKabulEt(View view) {
        builder.dismiss();
        // delete friend
        deleteFriend();
    }

    private void deleteFriend() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        chatObjects.clear();

        final String url = "https://mobiloby.com/_filter/delete_friend.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username);

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
                    finish();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
    }
}