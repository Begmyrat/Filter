package com.mobiloby.filter;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory2 extends AppCompatActivity {

    public static ListView listView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        prepareMe();

        wantedList.clear();

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
        wantedList.add(o);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<wantedList.size()-1){
                    pos = position;
//                    popupOption();
                    Intent intent = new Intent(getApplicationContext(), ActivityCategory2_List.class);
                    intent.putExtra("wanted_id", wantedList.get(pos).getWantedID());
                    intent.putExtra("username", username);
                    intent.putExtra("wanted_title", wantedList.get(pos).getWantedTitle());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(wantedList!=null){
            getWantedList();
            adapter = new MyWantedListAdapter(ActivityCategory2.this, wantedList);
            listView.setAdapter(adapter);
        }
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        listView = findViewById(R.id.listview_searchResult);
        wantedList = new ArrayList<>();
        wantedListCopy = new ArrayList<>();
        adapter = new MyWantedListAdapter(this, wantedList);
        listView.setAdapter(adapter);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
        }

        adapter.notifyDataSetChanged();

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

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

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_wanted_list.php";

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
                        adapter = new MyWantedListAdapter(ActivityCategory2.this, wantedList);
                        listView.setAdapter(adapter);


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
        RelativeLayout temp = view.findViewById(R.id.r_connect);
        temp.setVisibility(View.GONE);
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
        Intent intent = new Intent(getApplicationContext(), ActivityCategory2_Detail.class);
        intent.putExtra("username", username);
        intent.putExtra("wanted_id", wantedList.get(pos).getWantedID());
        intent.putExtra("isUpdate", "true");
        startActivity(intent);
    }
}