package com.mobiloby.filter.activities;

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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.adapters.MyWantedListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.WantedObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory2 extends AppCompatActivity implements MyWantedListAdapter.ItemClickListener{

    ArrayList<WantedObject> wantedList, wantedListCopy;
    JSONParser jsonParser;
    JSONObject jsonObject;
    Dialog builder;
    SharedPreferences preferences;
    MyWantedListAdapter adapter;
    String username="";
    Bundle extras;
    Integer pos = -1;
    Boolean isUyariShown = false;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ImageView i_avatar;
    TextView t_countArkadas, t_countIstek, t_username, t_profilDoluluk;
    String countIstek, countArkadas, profileURL, profilDoluluk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        prepareMe();

        getWantedList();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.MainBlue));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview_searchResult);
        wantedList = new ArrayList<>();
        wantedListCopy = new ArrayList<>();
        adapter = new MyWantedListAdapter(this, wantedList);
        adapter.setClickListener(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
        }

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        t_countIstek = findViewById(R.id.t_countIstek);
        t_countArkadas = findViewById(R.id.t_countArkadas);
        countIstek = preferences.getString("count_istek", "");
        countArkadas = preferences.getString("count_arkadas", "");
        profileURL = preferences.getString("user_profile_url", "");
        profilDoluluk = preferences.getString("profil_doluluk", "");
        t_countIstek.setText(countIstek);
        t_countArkadas.setText(countArkadas);
        t_profilDoluluk = findViewById(R.id.t_profilDoluluk);
        int d  = Integer.parseInt(profilDoluluk);
        d = d*100/7;
        t_profilDoluluk.setText("%" + d + " Profil Doluluğu");
        i_avatar = findViewById(R.id.i_avatar);
        t_username = findViewById(R.id.t_username);
        t_username.setText(username);

        Glide
                .with(this)
                .load("https:mobiloby.com/_filter/assets/profile/" + profileURL)
                .centerCrop()
                .placeholder(R.drawable.ic_f_char)
                .into(i_avatar);

    }

    public void getWantedList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        wantedList.clear();
        wantedListCopy.clear();

        final String url = "https://mobiloby.com/_filter/get_wanted_list.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);

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
                            WantedObject o = new WantedObject();
                            o.setWantedID(c.getString("wanted_id"));
                            o.setWantedTitle(c.getString("wanted_title"));
                            o.setWantedDate(c.getString("wanted_tarih"));
                            wantedList.add(o);
                            wantedListCopy.add(o);
                        }

                        WantedObject o = new WantedObject();
                        o.setWantedID("wanted_id");
                        o.setUserName("user_name");
                        o.setWantedUserName("wanted_user_name");
                        o.setWantedTitle("Lisenin papatyası");
                        o.setGiyimTop("giyim_top");
                        o.setGiyimMiddle("giyim_middle");
                        o.setGiyimBottom("giyim_bottom");
                        o.setGiyimAyakkabi("giyim_ayakkabi");
                        o.setWantedBoy("wanted_boy");
                        o.setWantedDate("17.01.2016");
                        wantedList.add(o);

//                        Collections.reverse(wantedList);
//                        adapter = new MyWantedListAdapter(ActivityCategory2.this, wantedList);
//                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    if(!isUyariShown)
                        popup();
                    isUyariShown = true;
                }

            }
        }.execute(null, null, null);
    }

    private void popup() {
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        TextView t_body = view.findViewById(R.id.e_info);
        t_body.setText("Daha önce arama işleminde bulunmamışsınız. İsterseniz gördüğünüz kişinin bilgilerini girerek başlayabilirsiniz.");
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    private void popupOption() {
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_option, null);
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickInsertWanted(View view) {
        // insert new wanted object to the list
        Intent intent = new Intent(getApplicationContext(), ActivityCategory2_Detail2.class);
        intent.putExtra("username", username);
        intent.putExtra("wanted_id", "-1");
        intent.putExtra("isUpdate", "false");
        startActivity(intent);
    }

    public void clickTamam(View view) {
        builder.dismiss();
    }

    public void clickArkadaslariListele(View view) {
        builder.dismiss();
        Intent intent = new Intent(getApplicationContext(), ActivityCategory2_List.class);
        intent.putExtra("wanted_id", wantedList.get(pos).getWantedID());
        intent.putExtra("username", username);
        intent.putExtra("wanted_title", wantedList.get(pos).getWantedTitle());
        startActivity(intent);
    }

    public void clickGuncelle(View view) {
        builder.dismiss();
        Intent intent = new Intent(getApplicationContext(), ActivityCategory2_Detail2.class);
        intent.putExtra("username", username);
        intent.putExtra("wanted_id", wantedList.get(pos).getWantedID());
        intent.putExtra("isUpdate", "true");
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        pos = position;
        popupOption();
    }

    public void clickProfileTop(View view) {
        Intent intent = new Intent(this, InformationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}