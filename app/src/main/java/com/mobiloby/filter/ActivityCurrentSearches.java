package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCurrentSearches extends AppCompatActivity {

    JSONParser jsonParser;
    JSONObject jsonObject;
    SharedPreferences preferences;
    String username, silinecek_id="";
    ListView listView;
    ArrayList<SocialObject> aramalar;
    MyCurrentSearchListAdapter adapter;
    Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_searches);

        prepareMe();

        getCurrentSearches();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                silinecek_id = aramalar.get(position).getId();
                popupDelete(username, aramalar.get(position).getId());
            }
        });
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("username_unique","");

        listView = findViewById(R.id.listview);
        aramalar = new ArrayList<>();
        adapter = new MyCurrentSearchListAdapter(this, aramalar);
        listView.setAdapter(adapter);
    }

    private void getCurrentSearches() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        aramalar.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_current_searches.php";

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
                        for(int i=0;i<pro.length();i++) {
                            JSONObject c = pro.getJSONObject(i);
                            String id = c.getString("id");
                            String instagram = c.getString("instagram_other");
                            String facebook = c.getString("facebook_other");
                            String snapchat = c.getString("snapchat_other");
                            String tiktok = c.getString("tiktok_other");
                            SocialObject object = new SocialObject(id, instagram, facebook, snapchat, tiktok);
                            aramalar.add(object);
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCurrentSearches.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCurrentSearches.this, true);
                }
                adapter.notifyDataSetChanged();
            }
        }.execute(null, null, null);
    }

    private void deleteCurrentSearches() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/delete_current_searches.php";

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
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCurrentSearches.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickBack(View view) {
        finish();
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
}