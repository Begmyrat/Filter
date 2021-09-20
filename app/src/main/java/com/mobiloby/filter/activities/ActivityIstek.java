package com.mobiloby.filter.activities;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyFriendListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityIstek extends AppCompatActivity implements MyFriendListAdapter.ItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyFriendListAdapter adapterIstekler;
    ArrayList<UserObject> istekObjects;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username, countArkadas, countIstek, profilDoluluk, userProfileUrl;
    Dialog builder;
    SharedPreferences preferences;
    DisplayMetrics displayMetrics;
    int height, width, istekPos;
    ImageView i_avatar;
    TextView t_countFriend, t_countIstek, t_username, t_profilDoluluk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istek);

    }

    @Override
    protected void onResume() {
        super.onResume();

        prepareMe();
        getIstekler();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlueTop));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("username_unique", "");
        profilDoluluk = preferences.getString("profil_doluluk", "");
        countArkadas = preferences.getString("count_arkadas", "");
        countIstek = preferences.getString("count_istek", "");
        userProfileUrl = preferences.getString("user_profile_url", "");
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        istekObjects = new ArrayList<>();
        adapterIstekler = new MyFriendListAdapter(this, istekObjects);
        adapterIstekler.setClickListener(this);
        recyclerView.setAdapter(adapterIstekler);
        t_countFriend = findViewById(R.id.t_countArkadas);
        t_countIstek = findViewById(R.id.t_countIstek);
        t_username = findViewById(R.id.t_username);
        t_profilDoluluk = findViewById(R.id.t_profilDoluluk);
        t_username.setText(username);
        t_countFriend.setText(countArkadas);
        t_countIstek.setText(countIstek);
        i_avatar = findViewById(R.id.i_avatar);
        int d  = Integer.parseInt(profilDoluluk);
        d = d*100/7;
        t_profilDoluluk.setText("%" + d + " Profil Doluluğu");

        Glide
                .with(this)
                .load("https:mobiloby.com/_filter/assets/profile/" + userProfileUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_f_char)
                .into(i_avatar);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));
    }

    private void getIstekler() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        istekObjects.clear();

        final String url = "https://mobiloby.com/_filter/get_istek.php";

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

                            String id = c.getString("id");
                            String friend_user_name_unique = c.getString("friend_user_name_unique");
                            String user_profile_url = c.getString("user_profile_url");
                            String wanted_id = c.getString("wanted_id");
                            String from_where = c.getString("from_where");
                            String wanted_question = c.getString("wanted_question");
                            String wanted_answer = c.getString("wanted_answer");
                            String profil_gizlilik = c.getString("profil_gizlilik");

                            UserObject o = new UserObject(id, friend_user_name_unique, "", "", "", "", "", "", "", "");
                            o.setAvatar_id(user_profile_url);
                            o.setFromWhere(from_where);
                            o.setWanted_id(wanted_id);
                            o.setWantedQuestion(wanted_question);
                            o.setWantedAnswer(wanted_answer);
                            o.setProfil_gizlilik(profil_gizlilik);
                            istekObjects.add(o);
                        }
                        adapterIstekler.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityIstek.this, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            }
        }.execute(null, null, null);
    }

    private void istekKabul(int position) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_friend_direk.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istekObjects.get(position).getUsername());
                jsonData.put("from_where", istekObjects.get(position).getFromWhere());


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

                    istekObjects.remove(position);
                    adapterIstekler.notifyDataSetChanged();

                    int istek = Integer.parseInt(countIstek) - 1;
                    int arkadas = Integer.parseInt(countArkadas) + 1;

                    t_countIstek.setText(""+istek);
                    t_countFriend.setText(""+arkadas);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("count_istek", ""+istek);
                    editor.putString("count_arkadas", ""+arkadas);

                }
                else{

                }
            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {
        istekPos = position;
        popupKabul(position);
    }

    private void popupKabul(int position) {
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(istekObjects.get(position).getUsername());
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("Bu isteği kabul etmek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Hayır");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Evet");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);
        LinearLayout l_answers = view.findViewById(R.id.l_answers);

        if(istekObjects.get(position).getWanted_id().equals("-1"))
            view.findViewById(R.id.l_answers).setVisibility(View.GONE);
        else
            view.findViewById(R.id.l_answers).setVisibility(View.VISIBLE);

        l_answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

                Intent intent = new Intent(getApplicationContext(), ActivityCategory2_ShowAnswers.class);
                intent.putExtra("wanted_soru", istekObjects.get(istekPos).getWantedQuestion());
                intent.putExtra("wanted_cevap", istekObjects.get(position).getWantedAnswer());
                intent.putExtra("user_name_unique", username);
                intent.putExtra("friend_user_name_unique", istekObjects.get(position).getUsername());
                startActivity(intent);
            }
        });

        l_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
                intent.putExtra("username", istekObjects.get(istekPos).getUsername());
                startActivity(intent);
            }
        });

        view.findViewById(R.id.r_clickReddet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        view.findViewById(R.id.r_clickKabulEt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                istekKabul(istekPos);
            }
        });

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickProfileTop(View view) {
        Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}