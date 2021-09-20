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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobiloby.filter.adapters.MyWantedSimilarListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.WantedObject;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory2_List extends AppCompatActivity implements MyWantedSimilarListAdapter.ItemClickListenerResult{

    ArrayList<WantedObject> wantedList;
    MyWantedSimilarListAdapter adapter;
    Bundle extras;
    String wantedID="", wantedTitle, username;
    JSONParser jsonParser;
    JSONObject jsonObject;
    TextView t_title, t_subtitle;
    Dialog bd;
    int pos;
    SharedPreferences preferences;
    FlowLayout f_bash;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2__list);

        prepareMe();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getWantedList();
    }

    public void popupConnect(String username){
        bd = new BottomSheetDialog(this);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_connect, null);
        TextView t_username = view.findViewById(R.id.t_username);
        TextView t_subtitle = view.findViewById(R.id.t_subtitle);
        t_subtitle.setText("Sohbete başlayabilmeniz için soruları cevaplamanız gerekmektedir.");
        TextView t_buttonText = view.findViewById(R.id.t_buttonText);
        t_buttonText.setText("Cevapla");
        t_username.setText(username);

        f_bash = view.findViewById(R.id.f_bash);

        String topSelf = wantedList.get(pos).getGiyimTop();
        String topDiger = wantedList.get(pos).getGiyimTopDiger();
        String middleSelf = wantedList.get(pos).getGiyimMiddle();
        String middleDiger = wantedList.get(pos).getGiyimMiddleDiger();
        String bottomSelf = wantedList.get(pos).getGiyimBottom();
        String bottomDiger = wantedList.get(pos).getGiyimBottomDiger();

        String[] sts = topSelf.split(",");
        String[] std = topDiger.split(",");
        String[] sms = middleSelf.split(",");
        String[] smd = middleDiger.split(",");
        String[] sbs = bottomSelf.split(",");
        String[] sbd = bottomDiger.split(",");

        f_bash = view.findViewById(R.id.flowLayout);

        for(int i=0;i<sts.length;i++){
            if(!sts[i].contains("-") && !std[i].contains("-") && sts[i].equals(std[i])){
                TextView temp = buildLabel(sts[i]);
                f_bash.addView(temp);
            }
        }
        for(int i=0;i<sms.length;i++){
            if(!sms[i].contains("-") && !smd[i].contains("-") && sms[i].equals(smd[i])){
                TextView temp = buildLabel(sms[i]);
                f_bash.addView(temp);
            }
        }
        for(int i=0;i<sbs.length;i++){
            if(!sbs[i].contains("-") && !sbd[i].contains("-") && sbs[i].equals(sbd[i])){
                TextView temp = buildLabel(sbs[i]);
                f_bash.addView(temp);
            }
        }

        bd.setContentView(view);
        bd.show();
    }

    private TextView buildLabel(String text) {
        TextView textView = new TextView(ActivityCategory2_List.this);
        textView.setText("#"+text.replace("=",":")+"\t");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setPadding(0,0,20,20);
//        textView.setBackgroundResource(R.drawable.info_box_background_fill8);

        return textView;
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("username_unique","");

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        wantedList = new ArrayList<>();
        adapter = new MyWantedSimilarListAdapter(this, wantedList);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        t_title = findViewById(R.id.t_title);
        t_subtitle = findViewById(R.id.t_subtitle);

        extras = getIntent().getExtras();
        if(extras!=null){
            wantedID = extras.getString("wanted_id");
            wantedTitle = extras.getString("wanted_title");
            t_title.setText(wantedTitle);
        }
    }

    private void getWantedList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        wantedList.clear();

        final String url = "https://mobiloby.com/_filter/get_wanted_users_by_similarity.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
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
                            WantedObject o = new WantedObject();

                            String giyim_top_self = c.getString("giyim_top_diger");
                            String giyim_middle_self = c.getString("giyim_middle_diger");
                            String giyim_bottom_self = c.getString("giyim_bottom_diger");

                            o.setGiyimTopDiger(giyim_top_self);
                            o.setGiyimMiddleDiger(giyim_middle_self);
                            o.setGiyimBottomDiger(giyim_bottom_self);

                            o.setWantedID(c.getString("wanted_id"));
                            o.setUserName(c.getString("user_name"));
                            o.setWantedUserName(c.getString("wanted_user_name"));
                            o.setWantedTitle(c.getString("wanted_title"));
                            o.setGiyimTop(c.getString("giyim_top"));
                            o.setGiyimMiddle(c.getString("giyim_middle"));
                            o.setGiyimBottom(c.getString("giyim_bottom"));
                            o.setWantedDate(c.getString("wanted_tarih"));
                            String sim = c.getString("wanted_similarity");
                            String question = c.getString("wanted_question");
                            String profile_url = c.getString("user_profile_url");
                            try {
                                String user_profil_doluluk = c.getString("user_profil_doluluk");
                                Double doluluk = Double.parseDouble(user_profil_doluluk)*100/8;
                                o.setDoluluk(doluluk);
                            }
                            catch (Exception e){
                                o.setDoluluk(0.0);
                            }
                            o.setSoru(question);

                            Integer similarity = Integer.parseInt(sim)*100/21;
                            o.setSimilarity(""+similarity);
                            o.setUserProfileUrl(profile_url);
                            wantedList.add(o);
                        }

                        wantedList.sort((schedule1, schedule2)->{
                            int returnValue = 0;
                            if(Double.parseDouble(schedule1.getSimilarity()) > Double.parseDouble(schedule2.getSimilarity()))	return -1;
                            else if(Double.parseDouble(schedule1.getSimilarity()) < Double.parseDouble(schedule2.getSimilarity()))	return 1;
                            else{
                                if(schedule1.getDoluluk() > schedule2.getDoluluk())	return -1;
                                else if(schedule1.getDoluluk() < schedule2.getDoluluk())	return 1;
                            }
                            return returnValue;
                        });

//                        WantedObject o = new WantedObject();
//                        o.setWantedID("wanted_id");
//                        o.setUserName("user_name");
//                        o.setWantedUserName("wanted_user_name");
//                        o.setWantedTitle("Lisenin papatyası");
//                        o.setGiyimTop("giyim_top");
//                        o.setGiyimMiddle("giyim_middle");
//                        o.setGiyimBottom("giyim_bottom");
//                        o.setGiyimAyakkabi("giyim_ayakkabi");
//                        o.setWantedBoy("wanted_boy");
//                        o.setWantedDate("17.01.2016");
//                        wantedList.add(o);
//                        adapter = new M 0yWantedSimilarListAdapter(ActivityCategory2_List.this, wantedList);
//                        listView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
//
//                        adapter = new MyWantedSimilarListAdapter(ActivityCategory2_List.this, wantedList);
//                        recyclerView.setAdapter(adapter);

                        t_subtitle.setText((wantedList.size()) + " tane sonuç bulundu");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_List.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    Toast.makeText(ActivityCategory2_List.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }

    public void clickBack(View view) {
        finish();
    }

    public void clickIstekGonder(View view) {
        // cevaplamaya gonderio
        bd.dismiss();
        Intent intent = new Intent(this, ActivityCategory2_Answer.class);
        intent.putExtra("friend_username", wantedList.get(pos).getUserName());
        intent.putExtra("soru", wantedList.get(pos).getSoru());
        intent.putExtra("wanted_username", wantedList.get(pos).getUserName());
        intent.putExtra("username", username);
        intent.putExtra("wanted_id", wantedList.get(pos).getWantedID());
        startActivity(intent);
    }

    @Override
    public void onItemClickResult(View view, int position) {
        pos = position;
        popupConnect(wantedList.get(position).getUserName());
    }
}