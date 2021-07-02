package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class InformationActivity extends AppCompatActivity {

    Dialog builder;
    String info, adSoyad, okul1, okul2, universite, takim, title, spor, club, user_id="", user_name;
    EditText e_info;
    RelativeLayout r_adSoyad, r_okul1, r_okul2, r_universite, r_takim, r_spor, r_club;
    TextView t_adSoyad, t_okul1, t_okul2, t_universite, t_takim, t_spor, t_club, t_adSoyad1, t_okul11, t_okul22, t_universite1, t_takim1, t_spor1, t_club1, t_information, t_title, t_subtitle;
    ImageView i_sport, i_adSoyad, i_okul1, i_okul2, i_universite, i_takim, i_club;
    Bundle extras;
    JSONObject jsonObject;
    JSONParser jsonParser;
    Boolean isSelf = false, isProfilHidden = false;
    int profilDolulukOrani = 0;
    Switch switchButton;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        prepareMe();

        getUser();

        checkData();

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isProfilHidden = isChecked;
                updateUser();
                Toast.makeText(InformationActivity.this, ""+isProfilHidden, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!user_id.equals(""))
            updateUser();
    }

    private void prepareMe() {

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        r_adSoyad = findViewById(R.id.r_adSoyad);
        r_okul1 = findViewById(R.id.r_okul1);
        r_okul2 = findViewById(R.id.r_okul2);
        r_universite = findViewById(R.id.r_universite);
        r_takim = findViewById(R.id.r_takim);
        r_spor = findViewById(R.id.r_sport);
        r_club = findViewById(R.id.r_club);

        t_adSoyad = findViewById(R.id.t_adSoyad);
        t_okul1 = findViewById(R.id.t_okul1);
        t_okul2 = findViewById(R.id.t_okul2);
        t_universite = findViewById(R.id.t_universite);
        t_takim = findViewById(R.id.t_takim);
        t_spor = findViewById(R.id.t_sport);
        t_club = findViewById(R.id.t_club);
        t_adSoyad1 = findViewById(R.id.t_adSoyad1);
        t_okul11 = findViewById(R.id.t_okul11);
        t_okul22 = findViewById(R.id.t_okul22);
        t_universite1 = findViewById(R.id.t_universite1);
        t_takim1 = findViewById(R.id.t_takim1);
        t_spor1 = findViewById(R.id.t_sport1);
        t_club1 = findViewById(R.id.t_club1);

        i_sport = findViewById(R.id.i_sport);
        i_adSoyad = findViewById(R.id.i_adsoyad);
        i_okul1 = findViewById(R.id.i_okul1);
        i_okul2 = findViewById(R.id.i_okul2);
        i_universite = findViewById(R.id.i_universite);
        i_club = findViewById(R.id.i_club);
        i_takim = findViewById(R.id.i_takim);
        t_information = findViewById(R.id.t_information);
        t_title = findViewById(R.id.t_title);
        t_subtitle = findViewById(R.id.t_subtitle);
        extras = getIntent().getExtras();
        switchButton = findViewById(R.id.switchButton);

        if(extras!=null){
            user_id = extras.getString("user_id");
            user_name = extras.getString("user_name");
            adSoyad = extras.getString("user_adsoyad");
            okul1 = extras.getString("user_okul1");
            okul2 = extras.getString("user_okul2");
            universite = extras.getString("user_universite");
            takim = extras.getString("user_takim");
            club = extras.getString("user_club");
            spor = extras.getString("user_spor");
            isSelf = extras.getBoolean("isSelf");
            if(isSelf){
                t_information.setText("Soluk gözüken kutucukların üzerine tıklayarak profil bilgilerinizi girebilirsiniz");
                findViewById(R.id.r_gizlilik).setVisibility(View.VISIBLE);
            }
            else{
                t_information.setText(user_name + " kişisine ait profil bilgileri. Merak ettiğiniz bilgilere buraadn erişebilirsiniz.");
                t_title.setText(user_name);
                t_subtitle.setText("");
                findViewById(R.id.r_gizlilik).setVisibility(View.GONE);
            }
            checkData();
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

    private void checkData() {
        if(!adSoyad.equals("")) {
            r_adSoyad.setBackgroundResource(R.drawable.info_box_background_fill);
            t_adSoyad.setTextColor(getResources().getColor(R.color.colorWhite));
            t_adSoyad1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_adSoyad.setText(adSoyad);
            i_adSoyad.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!okul1.equals("")) {
            r_okul1.setBackgroundResource(R.drawable.info_box_background_fill3);
            t_okul1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_okul11.setTextColor(getResources().getColor(R.color.colorWhite));
            t_okul1.setText(okul1);
            i_okul1.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!okul2.equals("")) {
            r_okul2.setBackgroundResource(R.drawable.info_box_background_fill5);
            t_okul2.setTextColor(getResources().getColor(R.color.colorWhite));
            t_okul22.setTextColor(getResources().getColor(R.color.colorWhite));
            t_okul2.setText(okul2);
            i_okul2.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!universite.equals("")) {
            r_universite.setBackgroundResource(R.drawable.info_box_background_fill2);
            t_universite.setTextColor(getResources().getColor(R.color.colorWhite));
            t_universite1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_universite.setText(universite);
            i_universite.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!takim.equals("")) {
            r_takim.setBackgroundResource(R.drawable.info_box_background_fill1);
            t_takim.setTextColor(getResources().getColor(R.color.colorWhite));
            t_takim1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_takim.setText(takim);
            i_takim.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!club.equals("")) {
            r_club.setBackgroundResource(R.drawable.info_box_background_fill8);
            t_club.setTextColor(getResources().getColor(R.color.colorWhite));
            t_club1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_club.setText(club);
            i_club.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if(!spor.equals("")) {
            r_spor.setBackgroundResource(R.drawable.info_box_background_fill7);
            t_spor.setTextColor(getResources().getColor(R.color.colorWhite));
            t_spor1.setTextColor(getResources().getColor(R.color.colorWhite));
            t_spor.setText(spor);
            i_sport.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        t_subtitle.setText("Doluluk oranı: %" + ((profilDolulukOrani*100)/11));
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickAdSoyad(View view) {
        if(isSelf)
            popup("Ad Soyad");
    }

    public void clickOkul1(View view) {
        if(isSelf)
        popup("Okul 1");
    }

    public void clickUniversite(View view) {
        if(isSelf)
        popup("Üniversite");
    }

    public void clickOkul2(View view) {
        if(isSelf)
        popup("Okul 2");
    }

    public void clickTakim(View view) {
        if(isSelf)
        popup("Tuttuğu Takım");
    }
    public void clickSport(View view) {
        if(isSelf)
        popup("İlgilendiği Spor");
    }
    public void clickClub(View view) {
        if(isSelf)
        popup("`Sosyal Klüp`");
    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        this.title = title;
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_info, null);
        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(title);
        e_info = view.findViewById(R.id.e_info);
        e_info.setHint(title+" giriniz");
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }


    public void clickInsertInfo(View view) {
        info = e_info.getText().toString();
        if(info.length()>0){
            info = e_info.getText().toString();
            builder.dismiss();

            if(title.equals("Ad Soyad")) {
                adSoyad = info;
                r_adSoyad.setBackgroundResource(R.drawable.info_box_background_fill);
                t_adSoyad.setTextColor(getResources().getColor(R.color.colorWhite));
                t_adSoyad1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_adSoyad.setText(info);
                i_adSoyad.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("Okul 1")) {
                okul1 = info;
                r_okul1.setBackgroundResource(R.drawable.info_box_background_fill3);
                t_okul1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_okul11.setTextColor(getResources().getColor(R.color.colorWhite));
                t_okul1.setText(info);
                i_okul1.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("Okul 2")) {
                okul2 = info;
                r_okul2.setBackgroundResource(R.drawable.info_box_background_fill5);
                t_okul2.setTextColor(getResources().getColor(R.color.colorWhite));
                t_okul22.setTextColor(getResources().getColor(R.color.colorWhite));
                t_okul2.setText(info);
                i_okul2.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("Üniversite")) {
                universite = info;
                r_universite.setBackgroundResource(R.drawable.info_box_background_fill2);
                t_universite.setTextColor(getResources().getColor(R.color.colorWhite));
                t_universite1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_universite.setText(info);
                i_universite.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("Tuttuğu Takım")) {
                takim = info;
                r_takim.setBackgroundResource(R.drawable.info_box_background_fill);
                t_takim.setTextColor(getResources().getColor(R.color.colorWhite));
                t_takim1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_takim.setText(info);
                i_takim.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("Sosyal Klüp")) {
                club = info;
                r_club.setBackgroundResource(R.drawable.info_box_background_fill3);
                t_club.setTextColor(getResources().getColor(R.color.colorWhite));
                t_club1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_club.setText(info);
                i_club.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            else if(title.equals("İlgilendiği Spor")) {
                spor = info;
                r_spor.setBackgroundResource(R.drawable.info_box_background_fill5);
                t_spor.setTextColor(getResources().getColor(R.color.colorWhite));
                t_spor1.setTextColor(getResources().getColor(R.color.colorWhite));
                t_spor.setText(info);
                i_sport.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            updateUser();
        }
    }

    public void clickClose(View view) {
        builder.dismiss();
    }

    private void updateUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        String avatar_id = preferences.getString("avatar_id","-1");

        final String url = "http://mobiloby.com/_filter/update_user.php";

        int pOran = 0;
        if(!user_name.equals("")) pOran++;
        if(!adSoyad.equals("")) pOran++;
        if(!okul1.equals("")) pOran++;
        if(!okul2.equals("")) pOran++;
        if(!universite.equals("")) pOran++;
        if(!takim.equals("")) pOran++;
        if(!club.equals("")) pOran++;
        if(!spor.equals("")) pOran++;

        int finalPOran = pOran;
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", user_name);
                jsonData.put("user_adsoyad", adSoyad.toLowerCase());
                jsonData.put("user_okul1",okul1.toLowerCase());
                jsonData.put("user_okul2",okul2.toLowerCase());
                jsonData.put("user_universite",universite.toLowerCase());
                jsonData.put("user_takim",takim.toLowerCase());
                jsonData.put("user_club",club.toLowerCase());
                jsonData.put("user_spor",spor.toLowerCase());
                jsonData.put("user_avatar_id", avatar_id);
                jsonData.put("user_profil_doluluk", ""+finalPOran);

                if(isProfilHidden)
                    jsonData.put("user_profile_hidden","1");
                else
                    jsonData.put("user_profile_hidden","0");

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

        profilDolulukOrani = 0;

        final String url = "http://mobiloby.com/_filter/get_user.php";
//        ftp://pherguBt@ftp.hergunbirbilgi.com/httpdocs/FilterMobil/db_connect.php

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", user_name);

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
                            user_name = c.getString("user_name_unique");
                            adSoyad = c.getString("user_adsoyad");
                            okul1 = c.getString("user_okul1");
                            okul2 = c.getString("user_okul2");
                            universite = c.getString("user_universite");
                            takim = c.getString("user_takim");
                            club = c.getString("user_club");
                            spor = c.getString("user_spor");
                            String user_device_id = c.getString("user_player_id");
                            String user_kayit_tarihi = c.getString("user_kayit_tarihi");

                            if(user_id!=null && !user_id.equals("")) profilDolulukOrani++;
                            else user_id = "";
                            if(user_name!=null && !user_name.equals("")) profilDolulukOrani++;
                            else user_name = "";
                            if(user_device_id!=null && !user_device_id.equals("")) profilDolulukOrani++;
                            else user_device_id = "";
                            if(user_kayit_tarihi!=null && !user_kayit_tarihi.equals("")) profilDolulukOrani++;
                            else user_kayit_tarihi = "";
                            if(adSoyad!=null && !adSoyad.equals("")) profilDolulukOrani++;
                            else adSoyad = "";
                            if(okul1!=null && !okul1.equals("")) profilDolulukOrani++;
                            else okul1 = "";
                            if(okul2!=null && !okul2.equals("")) profilDolulukOrani++;
                            else okul2 = "";
                            if(universite!=null && !universite.equals("")) profilDolulukOrani++;
                            else universite = "";
                            if(takim!=null && !takim.equals("")) profilDolulukOrani++;
                            else takim = "";
                            if(club!=null && !club.equals("")) profilDolulukOrani++;
                            else club = "";
                            if(spor!=null && !spor.equals("")) profilDolulukOrani++;
                            else spor = "";

                            checkData();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InformationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", InformationActivity.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickAvatar(View view) {
        if (isSelf)
            startActivity(new Intent(InformationActivity.this, ActivityAvatar.class));
    }

    public void clickTamam(View view) {

    }
}