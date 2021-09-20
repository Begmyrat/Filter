package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyInfoListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.InfoObject;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class InformationActivity extends AppCompatActivity implements MyInfoListAdapter.ItemClickListener{

    Dialog builder;
    String username="", user_id="", username_self="", user_profil_doluluk="", count_arkadas="", count_istek="";
    EditText e_info;
    Bundle extras;
    JSONObject jsonObject;
    JSONParser jsonParser;
    SharedPreferences preferences;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<InfoObject> infoObjects;
    MyInfoListAdapter adapter;
    DisplayMetrics displayMetrics;
    int height, width, lastIndex;
    String userProfileHidden="0", userProfileUrl="";
    Button b_profilimiGizle, b_duzenle;
    ImageView i_avatar;
    TextView t_username, t_profilDoluluk, b_cikis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prepareMe();

//        getUser();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUser();
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlueTop));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        extras = getIntent().getExtras();
        if (extras!=null){
            username = extras.getString("username");
        }
        i_avatar = findViewById(R.id.i_avatar);
        t_username = findViewById(R.id.t_username);
        t_profilDoluluk = findViewById(R.id.t_profilDoluluk);
        username_self = preferences.getString("username_unique", "");
        t_username.setText(username);
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        infoObjects = new ArrayList<>();
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        adapter = new MyInfoListAdapter(this, infoObjects, width);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        b_profilimiGizle = findViewById(R.id.b_profilimiGizle);
        b_duzenle = findViewById(R.id.b_duzenle);
        b_cikis = findViewById(R.id.b_profil);
        if(username.equals(username_self)){
            b_profilimiGizle.setVisibility(View.VISIBLE);
            b_duzenle.setVisibility(View.VISIBLE);
            b_cikis.setVisibility(View.VISIBLE);
        }
        else{
            b_profilimiGizle.setVisibility(View.INVISIBLE);
            b_duzenle.setVisibility(View.INVISIBLE);
            b_cikis.setVisibility(View.INVISIBLE);
        }
    }

    public void popupUyari(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        TextView t_title = view.findViewById(R.id.e_info);
        t_title.setText(title);
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void popupInput(){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_info, null);
        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(infoObjects.get(lastIndex).getTitle());
        e_info = view.findViewById(R.id.e_info);
        e_info.setText(infoObjects.get(lastIndex).getValue());

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickBack(View view) {
        finish();
    }

    private void updateUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/update_user.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("user_adsoyad", infoObjects.get(0).getValue());
                jsonData.put("user_okul1",infoObjects.get(1).getValue());
                jsonData.put("user_okul2",infoObjects.get(2).getValue());
                jsonData.put("user_universite",infoObjects.get(3).getValue());
                jsonData.put("user_takim",infoObjects.get(4).getValue());
                jsonData.put("user_club",infoObjects.get(5).getValue());
                jsonData.put("user_spor",infoObjects.get(6).getValue());
                jsonData.put("user_profile_hidden",userProfileHidden);
                jsonData.put("user_profile_url",userProfileUrl);
                jsonData.put("user_profil_doluluk","7");


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
//                    makeAlert.uyarıVer("Filter","Bilgileriniz güncellendi", InformationActivity.this, false);
                    getUser();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", InformationActivity.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        infoObjects.clear();

        final String url = "https://mobiloby.com/_filter/get_user.php";
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

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            user_id = c.getString("user_id");
                            username = c.getString("user_name_unique");

                            infoObjects.add(new InfoObject("Ad-Soyad", c.getString("user_adsoyad")));
                            infoObjects.add(new InfoObject("Okul 1", c.getString("user_okul1")));
                            infoObjects.add(new InfoObject("Okul 2", c.getString("user_okul2")));
                            infoObjects.add(new InfoObject("Üniversite", c.getString("user_universite")));
                            infoObjects.add(new InfoObject("Takım", c.getString("user_takim")));
                            infoObjects.add(new InfoObject("Kayıt olduğun klüp", c.getString("user_club")));
                            infoObjects.add(new InfoObject("Yaptığın Spor", c.getString("user_spor")));
                            userProfileHidden = c.getString("profil_gizlilik");
                            userProfileUrl = c.getString("user_profile_url");
                            user_profil_doluluk = c.getString("user_profil_doluluk");
                            count_arkadas = c.getString("count_friend");
                            count_istek = c.getString("count_istek");

                            if(userProfileHidden.equals("0")){
                                b_profilimiGizle.setText("Profilimi Gizle");
                            }
                            else{
                                b_profilimiGizle.setText("Profilimi Gizleme");
                            }

                            Glide
                                    .with(InformationActivity.this)
                                    .load("https:mobiloby.com/_filter/assets/profile/" + userProfileUrl)
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_f_char)
                                    .into(i_avatar);

                            int d = Integer.parseInt(user_profil_doluluk);
                            d = d*100/7;
                            t_profilDoluluk.setText("%" + d + " Profil Doluluğu");
//                            t_countArkadas.setText(count_arkadas);
//                            t_countIstek.setText(count_istek);
                        }

                        adapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InformationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", InformationActivity.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickAvatar(View view) {
        if (username.equals(username_self))
            startActivity(new Intent(InformationActivity.this, ActivityAvatar.class));
    }

    public void clickTamam(View view) {
        builder.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {
        lastIndex = position;
        if(username.equals(username_self))
            popupInput();
    }

    public void clickInsertInfo(View view) {
        builder.dismiss();
        infoObjects.get(lastIndex).setValue(e_info.getText().toString());
        updateUser();
    }

    public void clickClose(View view) {
        builder.dismiss();
    }

    public void clickProfilimiGizle(View view) {
        if(userProfileHidden.equals("0")){
            userProfileHidden = "1";
            b_profilimiGizle.setText("Profilimi Gizleme");
            updateUser();
        }
        else{
            userProfileHidden = "0";
            b_profilimiGizle.setText("Profilimi Gizle");
            updateUser();
        }
    }

    public void clickExit(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Oturumu kapatmak istiyor musunuz?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Evet",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), main_screen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Hayır",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }


}