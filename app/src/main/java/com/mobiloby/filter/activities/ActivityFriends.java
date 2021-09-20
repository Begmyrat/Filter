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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.adapters.MyFriendListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.adapters.MyRecommendListAdapter;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityFriends extends AppCompatActivity implements MyFriendListAdapter.ItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyFriendListAdapter adapterFriends;
    ArrayList<UserObject> friendObjects;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username, countArkadas, countIstek, profilDoluluk, userProfileUrl;
    Dialog builder;
    SharedPreferences preferences;
    DisplayMetrics displayMetrics;
    int height, width;
    ImageView i_avatar;
    TextView t_countFriend, t_countIstek, t_username, t_profilDoluluk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        prepareMe();

        getFriends();
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
        friendObjects = new ArrayList<>();
        adapterFriends = new MyFriendListAdapter(this, friendObjects);
        adapterFriends.setClickListener(this);
        recyclerView.setAdapter(adapterFriends);
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

    private void getFriends() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        friendObjects.clear();

        final String url = "https://mobiloby.com/_filter/get_friends.php";

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
                            String user_id = c.getString("id");
                            String friend_user_name = c.getString("friend_user_name_unique");

                            UserObject o = new UserObject(user_id, friend_user_name, "", "", "", "", "", "", "", "");
                            o.setAvatar_id(c.getString("user_profile_url"));
                            o.setFromWhere(c.getString("from_where"));
                            o.setUser_player_id(c.getString("user_player_id"));
                            friendObjects.add(o);
                        }
                        adapterFriends.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityFriends.this, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            }
        }.execute(null, null, null);
    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(getApplicationContext(), ActivityChat.class);
        intent.putExtra("username", username);
        intent.putExtra("username_friend", friendObjects.get(position).getUsername());
        intent.putExtra("user_profile_url", userProfileUrl);
        intent.putExtra("user_player_id_other", friendObjects.get(position).getUser_player_id());
        intent.putExtra("user_profile_url_other", friendObjects.get(position).getAvatar_id());
        intent.putExtra("user_profile_url", friendObjects.get(position).getAvatar_id());
        startActivity(intent);

    }

    public void clickTamam(View view) {
        builder.dismiss();
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