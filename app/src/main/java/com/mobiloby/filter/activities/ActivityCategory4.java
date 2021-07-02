package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.adapters.MySocialMediaRecycleListAdapter;
import com.mobiloby.filter.adapters.MySocialRecycleListAdapter;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.SocialObject;
import com.mobiloby.filter.helpers.makeAlert;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityCategory4 extends AppCompatActivity implements MySocialMediaRecycleListAdapter.ItemClickListener, MySocialRecycleListAdapter.ItemClickListenerSocial {

    TextView t_instagram1, t_instagram2, t_facebook1, t_facebook2, t_snapchat1, t_snapchat2, t_tiktok1, t_tiktok2;
    String i1="",i2="",f1="",f2="",s1="",s2="",t1="",t2="", type="", user_name_unique="", silinecek_id="-1", social_type="Facebook";
    Dialog builder;
    EditText e_username, e_username_other;
    JSONParser jsonParser;
    JSONObject jsonObject;
    SharedPreferences preferences;
    ArrayList<SocialObject> aramalar;
    ArrayList<String> socialMedias;
    RecyclerView recyclerView;
    MySocialMediaRecycleListAdapter adapter;
    MySocialRecycleListAdapter adapterSocialMedia;
    RecyclerView.LayoutManager layoutManager, layoutManagerSocialMedia;
    Spinner spinner;
    PowerSpinnerView spinnerDsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category4);

        prepareMe();

        getCurrentSearches();

        spinnerDsl.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<Object>() {

            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
//                Toast.makeText(ActivityCategory4.this, ""+i1, Toast.LENGTH_SHORT).show();
                social_type = getResources().getStringArray(R.array.social_medias)[i1];
                Toast.makeText(ActivityCategory4.this, ""+social_type, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user_name_unique = preferences.getString("username_unique", "");

        aramalar = new ArrayList<>();
        socialMedias = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleView);
//        recyclerViewSocialMedia = findViewById(R.id.recycleViewSocialMedia);
        adapter = new MySocialMediaRecycleListAdapter(this, aramalar);
        adapterSocialMedia = new MySocialRecycleListAdapter(this, socialMedias);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSocialMedia = new GridLayoutManager(this, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        adapterSocialMedia.setClickListenerSocial(this);

//        spinner = findViewById(R.id.spinner_social);
        spinnerDsl = findViewById(R.id.spinner_social);

        List<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
        iconSpinnerItems.add(new IconSpinnerItem("Facebook", getResources().getDrawable(R.drawable.ic_facebook)));
        iconSpinnerItems.add(new IconSpinnerItem("Instagram", getResources().getDrawable(R.drawable.ic_instagram)));
        iconSpinnerItems.add(new IconSpinnerItem("Snapchat", getResources().getDrawable(R.drawable.ic_snapchat)));
        iconSpinnerItems.add(new IconSpinnerItem("Tiktok", getResources().getDrawable(R.drawable.ic_facebook)));

        IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(spinnerDsl);
        spinnerDsl.setSpinnerAdapter(iconSpinnerAdapter);
        spinnerDsl.setItems(iconSpinnerItems);
        spinnerDsl.selectItemByIndex(0);
        spinnerDsl.setLifecycleOwner(this);

        spinnerDsl.getSpinnerRecyclerView().setLayoutManager(layoutManagerSocialMedia);

        e_username = findViewById(R.id.e_username);
        e_username_other = findViewById(R.id.e_username_other);
//
//        t_instagram1 = findViewById(R.id.t_instagram1);
//        t_instagram2 = findViewById(R.id.t_instagram2);
//        t_facebook1 = findViewById(R.id.t_facebook1);
//        t_facebook2 = findViewById(R.id.t_facebook2);
//        t_snapchat1 = findViewById(R.id.t_snapchat1);
//        t_snapchat2 = findViewById(R.id.t_snapchat2);
//        t_tiktok1 = findViewById(R.id.t_tiktok1);
//        t_tiktok2 = findViewById(R.id.t_tiktok2);

    }

    public void clickBack(View view) {
        finish();
    }

    public void clickInstagram1(View view) {
//        type = "i1";
//        popup("Instagram");
        social_type = "Instagram";
        makeInit();
        findViewById(R.id.i_instagram).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
    }

    public void clickInstagram2(View view) {
        type = "i2";
        popup("Instagram");
    }

    public void clickFacebook1(View view) {
//        type = "f1";
//        popup("Facebook");
        social_type = "Facebook";
        makeInit();
        findViewById(R.id.i_facebook).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
    }

    public void clickFacebook2(View view) {
        type = "f2";
        popup("Facebook");
    }

    public void clickSnapchat1(View view) {
//        type = "s1";
//        popup("Snapchat");
        social_type = "Snapchat";
        makeInit();
        findViewById(R.id.i_snapchat).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
    }

    public void clickSnapchat2(View view) {
        type = "s2";
        popup("Snapchat");
    }

    public void clickTiktok1(View view) {
//        type = "t1";
//        popup("TikTok");
        social_type = "Tiktok";
        makeInit();
        findViewById(R.id.i_tiktok).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
    }

    public void clickTiktok2(View view) {
        type = "t2";
        popup("TikTok");
    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_socialmedia, null);

        TextView t_title = view.findViewById(R.id.t_title);
        e_username = view.findViewById(R.id.e_username);
        if(type.contains("2"))
            e_username.setHint("Kullanıcı adını giriniz");
        else
            e_username.setHint("Kullanıcı adınızı giriniz");
        t_title.setText(title);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickPopupVazgec(View view) {
        builder.dismiss();
    }

    public void clickPopupTamam(View view) {
        builder.dismiss();
        if(e_username.getText().toString().length()>0) {
            if (type.equals("i1")) {
                t_instagram1.setText(e_username.getText().toString());
                i1 = e_username.getText().toString();
            }
            else if (type.equals("i2")) {
                t_instagram2.setText(e_username.getText().toString());
                i2 = e_username.getText().toString();
            }
            else if (type.equals("f1")){
                t_facebook1.setText(e_username.getText().toString());
                f1 = e_username.getText().toString();
            }
            else if (type.equals("f2")) {
                t_facebook2.setText(e_username.getText().toString());
                f2 = e_username.getText().toString();
            }
            else if (type.equals("s1")) {
                t_snapchat1.setText(e_username.getText().toString());
                s1 = e_username.getText().toString();
            }
            else if (type.equals("s2")) {
                t_snapchat2.setText(e_username.getText().toString());
                s2 = e_username.getText().toString();
            }
            else if (type.equals("t1")) {
                t_tiktok1.setText(e_username.getText().toString());
                t1 = e_username.getText().toString();
            }
            else if (type.equals("t2")) {
                t_tiktok2.setText(e_username.getText().toString());
                t2 = e_username.getText().toString();
            }
        }
    }

    private void sendBilgiler() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/insert_social_media.php";
        final String username_social = e_username.getText().toString();
        final String username_social_other = e_username_other.getText().toString();

        final String type = social_type;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", user_name_unique);
                jsonData.put("type", type);
                jsonData.put("user_name_social", username_social);
                jsonData.put("user_name_social_other", username_social_other);

                int successem = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    successem = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(successem);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
                    makeAlert.uyarıVer("Filter", "Bilgileriniz başarıyla yüklenmiştir. En kısa zamanda bulacağız. Teşekkür ederiz.", ActivityCategory4.this, false);
                    e_username.setText("");
                    e_username_other.setText("");
                    spinnerDsl.selectItemByIndex(0);
                    getCurrentSearches();
                }
                else if(res.equals("2")){
                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");
                        JSONObject c = pro.getJSONObject(0);
                        String friend_user_name_unique = c.getString("friend_user_name_unique");
                        makeAlert.uyarıVer("Filter", "Sizi arayan birisi bulundu\n"+friend_user_name_unique, ActivityCategory4.this, false);
                        e_username.setText("");
                        e_username_other.setText("");
                        spinnerDsl.selectItemByIndex(0);
                        insertFriend(friend_user_name_unique);

                    }catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory4.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. socil", ActivityCategory4.this, true);
                }


            }
        }.execute(null, null, null);
    }

    private void makeInit() {
//        t_instagram1.setText("Kullanıcı Adınız");
//        t_instagram2.setText("Kullanıcı Adı");
//        t_facebook1.setText("Kullanıcı Adınız");
//        t_facebook2.setText("Kullanıcı Adı");
//        t_snapchat1.setText("Kullanıcı Adınız");
//        t_snapchat2.setText("Kullanıcı Adı");
//        t_tiktok1.setText("Kullanıcı Adınız");
//        t_tiktok2.setText("Kullanıcı Adı");
        findViewById(R.id.i_facebook).setBackground(null);
        findViewById(R.id.i_instagram).setBackground(null);
        findViewById(R.id.i_snapchat).setBackground(null);
        findViewById(R.id.i_tiktok).setBackground(null);
    }

    public void clickGonder(View view) {
        sendBilgiler();
    }

    private void insertFriend(String friend_username_unique) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/insert_friend_direk.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", user_name_unique);
                jsonData.put("friend_user_name_unique", friend_username_unique);
                jsonData.put("from_where", "4");

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
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. 3", ActivityCategory4.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickCurrentSearches(View view) {
        startActivity(new Intent(this, ActivityCurrentSearches.class));
    }

    private void getCurrentSearches() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        aramalar.clear();

        final String url = "http://mobiloby.com/_filter/get_current_searches.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", user_name_unique);

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
                        for(int i=0;i<pro.length();i++) {
                            JSONObject c = pro.getJSONObject(i);
                            String id = c.getString("id");
                            String username = c.getString("username_social");
                            String username_other = c.getString("username_social_other");
                            String type = c.getString("type");
                            String tarih = c.getString("tarih");

                            SocialObject object = new SocialObject(id, type, username, username_other, tarih);
                            aramalar.add(object);
                        }

                        adapter.notifyDataSetChanged();

                        if(aramalar.size()==0){
//                            findViewById(R.id.t_aramaTarihi).setVisibility(View.GONE);
                        }
                        else{
//                            findViewById(R.id.t_aramaTarihi).setVisibility(View.VISIBLE);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory4.this, "error social", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCurrentSearches.this, true);
                }

            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {
        silinecek_id = aramalar.get(position).getId();
        popupDelete(user_name_unique, aramalar.get(position).getId());
    }

    public void popupDelete(String friend_username, String id){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(friend_username);
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("Bu aramayı gerçekten de silmek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Hayır");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Evet");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);
        l_profile.setVisibility(View.GONE);

        view.findViewById(R.id.l_answers).setVisibility(View.GONE);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickReddet(View view) {
        builder.dismiss();
    }

    public void clickKabulEt(View view) {
        builder.dismiss();
        deleteCurrentSearches();
    }

    private void deleteCurrentSearches() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/delete_current_searches.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("id", silinecek_id);

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
                    getCurrentSearches();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory4.this, true);
                }

            }
        }.execute(null, null, null);
    }
}