package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ActivityChat extends AppCompatActivity {

    ListView listView;
    ArrayList<ChatObject> chatObjects;
    MyChatListAdapter adapter;
    EditText e_message;
    int d=0;
    JSONParser jsonParser;
    JSONObject jsonObject;
    Bundle extras;
    String username="", friend_username="";
    TextView t_friendUsername;
    Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prepareMe();

        Toast.makeText(this, "self: "+username + " friend: " + friend_username, Toast.LENGTH_SHORT).show();

//        getChatsStatik();
        getChats();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorChatBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorChatBackground));

        extras = getIntent().getExtras();
        t_friendUsername = findViewById(R.id.t_usernameFriend);
        if(extras!=null){
            username = extras.getString("username");
            friend_username = extras.getString("friend_username");
            t_friendUsername.setText(friend_username);
        }

        listView = findViewById(R.id.listview_chat);
        chatObjects = new ArrayList<>();
        adapter = new MyChatListAdapter(this, chatObjects, username);
        listView.setAdapter(adapter);
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

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_chat.php";

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

                    chatObjects.add(new ChatObject("-1", username, friend_username, e_message.getText().toString(), hs+":"+ms, username));
                    adapter.notifyDataSetChanged();
                    listView.setSelection(chatObjects.size()-1);
                    e_message.setText("");
//                    getChats();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getChats() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        chatObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_chats.php";

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
                            chatObjects.add(o);
                        }
                        adapter.notifyDataSetChanged();
                        listView.setSelection(chatObjects.size()-1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityChat.this, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
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

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/delete_friend.php";

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
//                else if(res.equals("2")){
//                    makeAlert.uyarıVer("Filter", "Mesajlar da silinmistir", ActivityChat.this, true);
//                }
//                else if(res.equals("3")){
//                    makeAlert.uyarıVer("Filter", "Friend silindi ama mesajlar silinemedi", ActivityChat.this, true);
//                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
    }
}