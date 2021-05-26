package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityAvatar extends AppCompatActivity {

    GridView gridView;
    RelativeLayout r_box;
    ArrayList<Avatars> avatars;
    MyCustomGridViewAdapter adapter;
    String secilenAvatarID = "", username_unique="", username_visible="", user_password="", player_id="";
    SharedPreferences preferences;
    JSONObject jsonObject;
    JSONParser jsonParser;
    Boolean isSignUp = false;
    public static int secilenPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        prepareMe();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ActivityAvatar.this, ""+avatars.get(position).getId(), Toast.LENGTH_SHORT).show();
//                r_box.setBackgroundColor(avatars.get(position).getColor());
                r_box.getBackground().setTint(r_box.getResources().getColor(avatars.get(position).getColor()));
                findViewById(R.id.i_right).setVisibility(View.VISIBLE);
                secilenAvatarID = avatars.get(position).getId();
                secilenPos = position;
                adapter = new MyCustomGridViewAdapter(getApplicationContext(), avatars);
                gridView.setAdapter(adapter);
            }
        });


    }

    private void prepareMe() {

        gridView = findViewById(R.id.gridview_avatar);
        r_box = findViewById(R.id.r_box);
        avatars = new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        secilenAvatarID = preferences.getString("avatar_id", "");

        if(secilenAvatarID.equals("")){
            isSignUp = true;
        }
        else{
            isSignUp = false;
        }

        username_unique = preferences.getString("username_unique","");
        username_visible = preferences.getString("username_visible","");
        user_password = preferences.getString("user_password","");
        player_id = preferences.getString("player_id","");

        avatars.add(new Avatars("1",R.drawable.man, R.color.colorPurple));
        avatars.add(new Avatars("2",R.drawable.girl, R.color.colorPalet1));
        avatars.add(new Avatars("3",R.drawable.man_old, R.color.colorPalet2));
        avatars.add(new Avatars("4",R.drawable.boy, R.color.colorPalet3));
        avatars.add(new Avatars("5",R.drawable.avatar1, R.color.colorPalet4));
        avatars.add(new Avatars("6",R.drawable.avatar2, R.color.colorPalet5));
        avatars.add(new Avatars("7",R.drawable.avatar3, R.color.colorPalet6));
        avatars.add(new Avatars("8",R.drawable.avatar4, R.color.colorPalet7));
        avatars.add(new Avatars("9",R.drawable.avatar5, R.color.colorPalet8));
        avatars.add(new Avatars("10",R.drawable.avatar6, R.color.colorPalet9));
        avatars.add(new Avatars("11",R.drawable.avatar7, R.color.colorYellow));
        avatars.add(new Avatars("12",R.drawable.avatar8, R.color.colorBlue));
        avatars.add(new Avatars("13",R.drawable.avatar9, R.color.colorOrange));
        avatars.add(new Avatars("14",R.drawable.avatar10, R.color.colorPalet1));
        avatars.add(new Avatars("15",R.drawable.avatar11, R.color.colorPalet2));
        avatars.add(new Avatars("16",R.drawable.avatar12, R.color.colorPalet9));
        avatars.add(new Avatars("17",R.drawable.avatar13, R.color.colorPalet7));
        avatars.add(new Avatars("18",R.drawable.avatar14, R.color.colorPalet6));

        for(int i=0;i<avatars.size();i++){
            if(avatars.get(i).getId().equals(secilenAvatarID)){
                secilenPos = i;
            }
        }

        adapter = new MyCustomGridViewAdapter(this, avatars);
        gridView.setAdapter(adapter);
    }

    public void clickAvatarSec(View view) {
        if(!secilenAvatarID.equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("avatar_id", secilenAvatarID);
            editor.commit();

            if(isSignUp)
                insertUser();
            else{
                finish();
            }
        }
    }

    private void insertUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_user.php";

//        final String username_visible = username;
//        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username_unique);
                jsonData.put("user_name_visible", username_visible);
                jsonData.put("user_password", user_password);
                jsonData.put("player_id", player_id);
                jsonData.put("avatar_id", secilenAvatarID);


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
                    Intent intent = new Intent(ActivityAvatar.this, MainActivity .class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityAvatar.this, true);
                }

            }
        }.execute(null, null, null);
    }
}