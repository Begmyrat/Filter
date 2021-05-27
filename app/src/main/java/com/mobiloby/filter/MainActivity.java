package com.mobiloby.filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "101";
    ListView listView;
    MyCategoryListAdapter adapter;
    ArrayList<CategoryObject> categoryObjects;
    SharedPreferences preferences;
    String username="", token="";
    JSONParser jsonParser;
    JSONObject jsonObject;
    String user_id, user_name_unique, user_name_visible, user_password, user_device_id, user_kayit_tarihi, user_adsoyad, user_okul1, user_okul2, user_universite, user_takim, user_club, user_spor;
    RelativeLayout r_box;
    Dialog builder;
    int dolulukOrani = 0;
    public static Context context;
    public static int h=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareMe();

        context = getApplicationContext();

//        Intent intent=new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
//        startActivity(intent);

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("Hello");

                // Do something with the data

                Toast.makeText(context, ""+data+ " helo", Toast.LENGTH_SHORT).show();
            }
        }, new IntentFilter("SomeData"));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String currentToken = task.getResult();

                        System.out.println("CURRENTTOKEN: " + currentToken);
                        System.out.println("TOKEN: " + token);

                        if(!currentToken.equals(token)){
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", currentToken);
                            editor.commit();
                            token = currentToken;
                            updateToken();
                        }


                    }
                });

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("isLoggedIn", false);
//        editor.commit();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActivityCategory1.class);
                intent.putExtra("category",categoryObjects.get(position).getTitle());
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        getUser();
    }



    private void updateToken() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/update_token.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("user_player_id", token);

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

                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. TOKEN", MainActivity.this, true);
                }

            }
        }.execute(null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUser();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        r_box = findViewById(R.id.r_box);
        r_box.getBackground().setTint(r_box.getResources().getColor(R.color.colorBackground));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = preferences.getString("token","");
        username = preferences.getString("username_unique","");
        Toast.makeText(this, "user: " + username, Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.listview_categories);
        categoryObjects = new ArrayList<>();
        addCategories();
        adapter = new MyCategoryListAdapter(this, categoryObjects);
        listView.setAdapter(adapter);
    }

    private void addCategories() {
        categoryObjects.add(new CategoryObject("Very soon (within 48 hours)", ""));
        categoryObjects.add(new CategoryObject("Soon (this week)", ""));
        categoryObjects.add(new CategoryObject("No rush (this month)", ""));
        categoryObjects.add(new CategoryObject("Just browsing", ""));
    }

    public void clickProfile(View view) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("isLoggedIn", false);
//        editor.commit();
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();

        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("user_name",user_name_unique);
        intent.putExtra("user_device_id",user_device_id);
        intent.putExtra("user_kayit_tarihi",user_kayit_tarihi);
        intent.putExtra("user_adsoyad",user_adsoyad);
        intent.putExtra("user_okul1",user_okul1);
        intent.putExtra("user_okul2",user_okul2);
        intent.putExtra("user_universite",user_universite);
        intent.putExtra("user_takim",user_takim);
        intent.putExtra("user_club",user_club);
        intent.putExtra("user_spor",user_spor);
        intent.putExtra("isSelf", true);
        startActivity(intent);
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_user.php";
//        ftp://pherguBt@ftp.hergunbirbilgi.com/httpdocs/FilterMobil/db_connect.php

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);

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

                        int profilDolulukOrani = 0;

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            user_id = c.getString("user_id");
                            user_name_unique = c.getString("user_name_unique");
                            user_name_visible = c.getString("user_name_visible");
                            user_password = c.getString("user_password");
                            user_device_id = c.getString("user_player_id");
                            user_kayit_tarihi = c.getString("user_kayit_tarihi");
                            user_adsoyad = c.getString("user_adsoyad");
                            user_okul1 = c.getString("user_okul1");
                            user_okul2 = c.getString("user_okul2");
                            user_universite = c.getString("user_universite");
                            user_takim = c.getString("user_takim");
                            user_club = c.getString("user_club");
                            user_spor = c.getString("user_spor");

                            if(user_id!=null && !user_id.equals("")) profilDolulukOrani++;
                            else user_id = "";
                            if(user_name_unique!=null && !user_name_unique.equals("")) profilDolulukOrani++;
                            else user_name_unique = "";
                            if(user_device_id!=null && !user_device_id.equals("")) profilDolulukOrani++;
                            else user_device_id = "";
                            if(user_kayit_tarihi!=null && !user_kayit_tarihi.equals("")) profilDolulukOrani++;
                            else user_kayit_tarihi = "";
                            if(user_adsoyad!=null && !user_adsoyad.equals("")) profilDolulukOrani++;
                            else user_adsoyad = "";
                            if(user_okul1!=null && !user_okul1.equals("")) profilDolulukOrani++;
                            else user_okul1 = "";
                            if(user_okul2!=null && !user_okul2.equals("")) profilDolulukOrani++;
                            else user_okul2 = "";
                            if(user_universite!=null && !user_universite.equals("")) profilDolulukOrani++;
                            else user_universite = "";
                            if(user_takim!=null && !user_takim.equals("")) profilDolulukOrani++;
                            else user_takim = "";
                            if(user_club!=null && !user_club.equals("")) profilDolulukOrani++;
                            else user_club = "";
                            if(user_spor!=null && !user_spor.equals("")) profilDolulukOrani++;
                            else user_spor = "";

                        }

                        dolulukOrani = (profilDolulukOrani*100)/11;
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("geçmişProfil", "%"+dolulukOrani);
                        editor.commit();

//                        t_gechmishProfil.setText("%"+dolulukOrani);
//                        circularProgressBar.setProgressValue(dolulukOrani);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", MainActivity.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickCategory1(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityFriends.class);
        intent.putExtra("category",categoryObjects.get(0).getTitle());
        intent.putExtra("username", username);
        intent.putExtra("dolulukOrani", ""+dolulukOrani);
        startActivity(intent);
    }

    public void clickCategory3(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityCategory3.class);
        intent.putExtra("category",categoryObjects.get(1).getTitle());
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void clickCategory2(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityCategory2.class);
        intent.putExtra("category",categoryObjects.get(1).getTitle());
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void clickCategory4(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityCategory4.class);
        intent.putExtra("category",categoryObjects.get(1).getTitle());
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void clickExit(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("avatar_id","");
        editor.commit();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void clickCategory1_Info(View view) {
        popup("Arkadashlar ile ilgili bilgilendirme mesaji gelecek burada, uygun bi mesaj bulunca yazilir");
    }

    public void clickCategory2_Info(View view) {
        popup("Tarif et ki bulalmmm ile ilgili bilgilendirme mesaji gelecek burada, uygun bi mesaj bulunca yazilir");
    }

    public void clickCategory3_Info(View view) {
        popup("Anlik aktiviteler ile ilgili bilgilendirme mesaji gelecek burada, uygun bi mesaj bulunca yazilir");
    }

    public void clickCategory4_Info(View view) {
        popup("Sosyal Medya ile ilgili bilgilendirme mesaji gelecek burada, uygun bi mesaj bulunca yazilir");
    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        TextView t_title = view.findViewById(R.id.e_info);
        t_title.setText(title);
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickTamam(View view) {
        builder.dismiss();
    }
}