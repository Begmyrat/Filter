package com.mobiloby.filter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityFriends extends AppCompatActivity implements MyRecommendListAdapter.ItemClickListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecommendListAdapter adapterRecommends;
    ArrayList<UserObject> onerilenObjects;
    ListView listView_friends, listView_istekler;
    MyFriendsListAdapter adapterFriends;
    MyFriendsListAdapter adapterIstekler;
    ArrayList<UserObject> friendObjects, istekObjects;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username, istek_username;
    Bundle extras;
    TextView t_title, t_countArkadas, t_countIstek;
    Dialog builder;
    Boolean istekGonderildimi = false, isFirstTime = true;
    int istek_pos = -1;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        prepareMe();

        listView_istekler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    istek_username = istekObjects.get(position).getUsername();
                    istekGonderildimi = false;
                    istek_pos = position;
                    popup(istekObjects.get(position).getUsername());
            }
        });

        listView_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), ActivityChat.class);
                    intent.putExtra("friend_username", friendObjects.get(position).getUsername());
                    intent.putExtra("username", username);
                    intent.putExtra("token_friend", friendObjects.get(position).getUser_player_id());
                    startActivity(intent);
            }
        });

    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isFirstTime = preferences.getBoolean("isFirstTime", true);

        onerilenObjects = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(ActivityFriends.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterRecommends = new MyRecommendListAdapter(this, onerilenObjects);
        recyclerView.setAdapter(adapterRecommends);
        listView_friends = findViewById(R.id.listview_friend);
        listView_istekler = findViewById(R.id.listview_istekler);
        friendObjects = new ArrayList<>();
        adapterFriends = new MyFriendsListAdapter(this, friendObjects);
        listView_friends.setAdapter(adapterFriends);
        adapterRecommends.setClickListener(this);
        t_title = findViewById(R.id.t_title);
        t_countArkadas = findViewById(R.id.t_countArkadas);
        t_countIstek = findViewById(R.id.t_countIstek);

        istekObjects = new ArrayList<>();
        adapterIstekler = new MyFriendsListAdapter(this, istekObjects);
        listView_istekler.setAdapter(adapterIstekler);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
        }

        if(isFirstTime){
            popupUyari("Sizler için daha iyi arkadaş öneriler yapabilmemiz için, lütfen profil doluluk oranınızı yüksek tutunuz.");
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstTime", false);
        editor.commit();

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

    private void getUserBySimilarity() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        onerilenObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_users_by_similarity.php";

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

            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();


                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);

                            String user_id = c.getString("user_id");
                            String user_name = c.getString("user_name_unique");
                            String user_device_id = c.getString("user_player_id");
                            String user_kayit_tarihi = c.getString("user_kayit_tarihi");
                            String user_adsoyad = c.getString("user_adsoyad");
                            String user_okul1 = c.getString("user_okul1");
                            String user_okul2 = c.getString("user_okul2");
                            String user_universite = c.getString("user_universite");
                            String user_takim = c.getString("user_takim");
                            String user_club = c.getString("user_club");
                            String user_spor = c.getString("user_spor");
                            String similarity = c.getString("user_similarity");
                            String profil_gizlilik = c.getString("profil_gizlilik");

                            UserObject o = new UserObject(user_id, user_name, user_kayit_tarihi, user_adsoyad, user_okul1, user_okul2, user_universite, user_takim, user_club, user_spor);
                            o.setUsername_visible(username);
                            o.setSimilarity(similarity);
                            o.setProfil_gizlilik(profil_gizlilik);
                            o.setAvatar_id(c.getString("user_avatar_id"));
                            onerilenObjects.add(o);
                        }

                        onerilenObjects.sort((schedule1, schedule2)->{
                            int returnValue = 0;
                            if(Double.parseDouble(schedule1.getSimilarity()) > Double.parseDouble(schedule2.getSimilarity()))	return -1;
                            else if(Double.parseDouble(schedule1.getSimilarity()) < Double.parseDouble(schedule2.getSimilarity()))	return 1;
                            return returnValue;
                        });

                        adapterRecommends.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityFriends.this, "error jiimRecommend", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. 1", ActivityFriends.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getFriends() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        friendObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_friends.php";

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
                            o.setAvatar_id(c.getString("user_avatar_id"));
                            o.setFromWhere(c.getString("from_where"));
                            o.setUser_player_id(c.getString("user_player_id"));
                            friendObjects.add(o);
                        }
                        adapterFriends = new MyFriendsListAdapter(ActivityFriends.this, friendObjects);
                        listView_friends.setAdapter(adapterFriends);
                        t_countArkadas.setText(""+friendObjects.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityFriends.this, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }


                    getIstek();
                }
                else{
                    // arkadashi yok demek bu
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. " + res, ActivityFriends.this, true);
                    getIstek();
                }
                getUserBySimilarity();
            }
        }.execute(null, null, null);
    }

    private void sendIstek(final String friend_username_unique, int pos) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        Toast.makeText(this, "user: " + username, Toast.LENGTH_SHORT).show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username_unique);
                jsonData.put("from_where", "1");

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
                    onerilenObjects.remove(pos);
                    adapterRecommends.notifyItemRemoved(pos);
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. 3", ActivityFriends.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getIstek() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        istekObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_istek.php";

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
                            System.out.println("userID: " + user_id);
                            String friend_user_name = c.getString("friend_user_name_unique");
                            System.out.println("wantedName: " + friend_user_name);
                            String wanted_id = c.getString("wanted_id");
                            System.out.println("wantedID: " + wanted_id);
                            String wanted_question = c.getString("wanted_question");
                            System.out.println("wantedQuestion: " + wanted_question);
                            String wanted_answer = c.getString("wanted_answer");
                            System.out.println("wantedAnswer: " + wanted_answer);

                            UserObject o = new UserObject(user_id, friend_user_name, "", "", "", "", "", "", "", "");
                            o.setWanted_id(wanted_id);
                            o.setSoru(wanted_question);
                            o.setCevap(wanted_answer);
                            o.setAvatar_id(c.getString("user_avatar_id"));
                            o.setFromWhere(c.getString("from_where"));

                            istekObjects.add(o);
                        }
                        t_countIstek.setText(""+istekObjects.size());
                        adapterIstekler.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityFriends.this, "error jiim getIstek", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityFriends.this, true);
                    t_countIstek.setText("0");
                    adapterIstekler.notifyDataSetChanged();
                }

            }
        }.execute(null, null, null);
    }

    private void istekKabulEt() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        friendObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_friend.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istek_username);

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
                    getIstek();
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityFriends.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void istekReddet() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        friendObjects.clear();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/delete_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", istek_username);
                jsonData.put("friend_user_name_unique", username);

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
                    getIstek();
                }
                else{
//                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityFriends.this, true);
                }

            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, ""+onerilenObjects.get(position).getUsername(), Toast.LENGTH_SHORT).show();
        istek_username = onerilenObjects.get(position).getUsername();
        istek_pos = position;
        popupIstekGonder(istek_username);
    }

    public void clickIstekGonderenler(View view) {
        listView_istekler.setVisibility(View.VISIBLE);
        listView_friends.setVisibility(View.GONE);
        t_title.setText("İstek Gönderenler");
        getIstek();
    }

    public void clickFriends(View view) {
        listView_friends.setAdapter(adapterFriends);
        listView_istekler.setVisibility(View.GONE);
        listView_friends.setVisibility(View.VISIBLE);
        t_title.setText("Arkadaşlar");
        getFriends();
    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(title);
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("İsteği kabul etmek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Reddet");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Kabul Et");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);
        l_profile.setVisibility(View.VISIBLE);

        LinearLayout l_answers = view.findViewById(R.id.l_answers);
        if(istekObjects.get(istek_pos).getWanted_id().equals("-1")){
            l_answers.setVisibility(View.GONE);
        }
        else{
            l_answers.setVisibility(View.VISIBLE);
        }

//        f_bash = view.findViewById(R.id.f_bash);
//
//        TextView t = buildLabel("Hillo");
//        f_bash.addView(t);
//        TextView t1 = buildLabel("fsfs");
//        f_bash.addView(t1);
//        TextView t2 = buildLabel("salam");
//        f_bash.addView(t2);
//        TextView t3 = buildLabel("suasfsd");
//        f_bash.addView(t3);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getFriends();
    }

    public void popupIstekGonder(String title){

        istekGonderildimi = true;

        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_istek_kabul, null);

        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(title);
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("İstek göndermek istiyor musunuz?");
        TextView t_reddet = view.findViewById(R.id.t_reddet);
        t_reddet.setText("Hayır");
        TextView t_kabulEt = view.findViewById(R.id.t_kabulEt);
        t_kabulEt.setText("Evet");
        LinearLayout l_profile = view.findViewById(R.id.l_profile);

        view.findViewById(R.id.l_answers).setVisibility(View.GONE);


        if(onerilenObjects.get(istek_pos).getProfil_gizlilik().equals("1"))
            l_profile.setVisibility(View.GONE);
        else
            l_profile.setVisibility(View.VISIBLE);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    private TextView buildLabel(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(10,10,10,10);
//        textView.setPadding((int)dpToPx(16), (int)dpToPx(8), (int)dpToPx(16), (int)dpToPx(8));
        textView.setBackgroundResource(R.drawable.info_box_background_fill);

        return textView;
    }

    public void clickProfile(View view) {
        builder.dismiss();
        Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
        intent.putExtra("user_id","");
        intent.putExtra("user_name",istek_username);
        intent.putExtra("user_device_id","");
        intent.putExtra("user_kayit_tarihi","");
        intent.putExtra("user_adsoyad","");
        intent.putExtra("user_okul1","");
        intent.putExtra("user_okul2","");
        intent.putExtra("user_universite","");
        intent.putExtra("user_takim","");
        intent.putExtra("user_club","");
        intent.putExtra("user_spor","");
        intent.putExtra("isSelf", false);
        startActivity(intent);
    }

    public void clickReddet(View view) {
        builder.dismiss();
        if(istekGonderildimi){

        }
        else{
            istekReddet();
        }
    }

    public void clickKabulEt(View view) {
        builder.dismiss();
        if(istekGonderildimi){
            sendIstek(istek_username, istek_pos);
        }
        else{
            istekKabulEt();
        }
    }

    public void clickTamam(View view) {
        builder.dismiss();
    }

    public void clickShowAnswers(View view) {
        builder.dismiss();
        Intent intent = new Intent(this, ActivityCategory2_ShowAnswers.class);
        intent.putExtra("wanted_soru",istekObjects.get(istek_pos).getSoru());
        intent.putExtra("wanted_cevap",istekObjects.get(istek_pos).getCevap());
        intent.putExtra("wanted_user_name",istekObjects.get(istek_pos).getUsername());
        intent.putExtra("user_name_unique",istek_username);
        intent.putExtra("friend_user_name_unique",username);
        startActivity(intent);
    }

    public void clickBack(View view) {
        finish();
    }
}