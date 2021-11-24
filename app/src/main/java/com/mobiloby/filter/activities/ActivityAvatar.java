package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiloby.filter.adapters.MyAvatarListAdapter;
import com.mobiloby.filter.models.Avatars;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityAvatar extends AppCompatActivity implements MyAvatarListAdapter.ItemClickListener{

    RelativeLayout r_box;
    ArrayList<Avatars> avatars;
    String secilenAvatarID = "", username_unique="", username_visible="", user_password="", player_id="";
    SharedPreferences preferences;
    JSONObject jsonObject;
    JSONParser jsonParser;
    Boolean isSignUp = false;
    Bundle extras;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyAvatarListAdapter adapter;
    DisplayMetrics displayMetrics;
    int height, width;
    ImageView i_continue;
    ProgressDialog progressDialog;
    TextView t_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        prepareMe();

        getAvatars();
    }

    private void prepareMe() {

        recyclerView = findViewById(R.id.recyclerView);
        i_continue = findViewById(R.id.i_continue);
        avatars = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        secilenAvatarID = preferences.getString("avatar_id", "");
        if(secilenAvatarID.equals("")){
            i_continue.setImageResource(R.drawable.contunie_passive);
        }
        else{
            i_continue.setImageResource(R.drawable.contunie_active);
        }

        isSignUp = preferences.getBoolean("isLoggedIn", false);
        t_continue = findViewById(R.id.t_continue);

        username_visible = preferences.getString("username_visible","");
        user_password = preferences.getString("user_password","");
        player_id = preferences.getString("player_id","");

        player_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        extras = getIntent().getExtras();
        if(isSignUp){
            username_unique = preferences.getString("username_unique","");
            t_continue.setText("Güncelle");
        }
        else{
            if(extras!=null){

                username_unique = extras.getString("username");
                user_password = extras.getString("password");
                t_continue.setText("Kayıt Ol");
            }
        }
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAvatarListAdapter(this, avatars, width, secilenAvatarID);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    public void clickAvatarSec(View view) {
        if(!secilenAvatarID.equals("")){

            if(!isSignUp)
                insertUser();
            else{
                updateAvatar();
            }
        }
    }

    private void insertUser() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_user.php";

//        final String username_visible = username;
//        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username_unique);
                jsonData.put("user_password", user_password);
                jsonData.put("player_id", player_id);
                jsonData.put("user_profile_url", secilenAvatarID);


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
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username_visible", username_visible);
                    editor.putString("username_unique", username_unique);
                    editor.putString("user_password", user_password);
                    editor.putString("avatar_id", secilenAvatarID);
                    editor.commit();
                    Intent intent = new Intent(ActivityAvatar.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityAvatar.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void updateAvatar() {
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/update_avatar.php";

//        final String username_visible = username;
//        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username_unique);
                jsonData.put("user_profile_url", secilenAvatarID);


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
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("avatar_id", secilenAvatarID);
                    editor.commit();
//                    Intent intent = new Intent(ActivityAvatar.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.putExtra("username", preferences.getString("username_unique", ""));
//                    startActivity(intent);
                    finish();
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityAvatar.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getAvatars() {
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/get_avatars.php";

//        final String username_visible = username;
//        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

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

                        for (int i = 0; i < pro.length(); i++) {
                            JSONObject c = pro.getJSONObject(i);
                            String avatar_id = c.getString("avatar_id");
                            String avatar_url = c.getString("avatar_url");
                            avatars.add(new Avatars(avatar_id, avatar_url));
                        }

                        adapter.notifyDataSetChanged();
                    }catch (Exception e){

                    }
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityAvatar.this, true);
                }

            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {
        i_continue.setImageResource(R.drawable.contunie_active);
        secilenAvatarID = avatars.get(position).getUrl();
        MyAvatarListAdapter.secilenURL = secilenAvatarID;
        adapter.notifyDataSetChanged();
//        findViewById(R.id.i_right).setVisibility(View.VISIBLE);
    }
}