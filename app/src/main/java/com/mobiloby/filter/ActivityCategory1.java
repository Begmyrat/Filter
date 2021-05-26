package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory1 extends AppCompatActivity {

    EditText e_search;
    ListView listView;
    ArrayList<UserObject> resultList, resultListCopy;
    MySearchResultListAdapter adapter;
    BottomSheetDialog bd;
    Bundle extras;
    int dolulukOrani = 0;
    String username = "", friend_username_unique="";
    Dialog builder;
    JSONParser jsonParser;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);

        prepareMe();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friend_username_unique = resultList.get(position).getUsername();
                popupConnect(resultList.get(position).getId(), resultList.get(position).getUsername());
            }
        });
    }

    public void popup(){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        builder.setCancelable(false);
        builder.setContentView(view);
        builder.show();
    }

    private void prepareMe() {

        // give color to top bar(date, clock & system widgets)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        e_search = findViewById(R.id.e_search);
        e_search.clearFocus();
        e_search.addTextChangedListener(new InputValidator());
        listView = findViewById(R.id.listview_searchResult);
        resultList = new ArrayList();
        resultListCopy = new ArrayList<>();
        adapter = new MySearchResultListAdapter(this, resultList);
        listView.setAdapter(adapter);

        extras = getIntent().getExtras();
        if(extras!=null){
            String dol = extras.getString("dolulukOrani");
            dolulukOrani = Integer.parseInt(dol);
            username = extras.getString("username");
            if(dolulukOrani<60){
                popup();
            }
        }

        getUserBySimilarity();
    }

    public void clickTemizle(View view) {
        e_search.setText("");
    }

    public void clickBack(View view) {
        finish();
    }

    public void popupConnect(String id, String username){
        bd = new BottomSheetDialog(this);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_layout_connect, null);
        TextView t_username = view.findViewById(R.id.t_username);
        t_username.setText(username);

        bd.setContentView(view);
        bd.show();
    }

    public void clickClose(View view) {
        bd.dismiss();
    }

    public void clickIstekGonder(View view) {
        Toast.makeText(this, "Arkadaşlık isteği gönderildi", Toast.LENGTH_SHORT).show();
        bd.dismiss();
        sendIstek();
    }

    private void sendIstek() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

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
                    getUserBySimilarity();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory1.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void clickTamam(View view) {
        builder.dismiss();
        finish();
    }

    private void getUserBySimilarity() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        resultList.clear();
        resultListCopy.clear();

        Toast.makeText(this, "user: " + username, Toast.LENGTH_SHORT).show();

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
//                            String password = c.getString("user_password");
                            String user_kayit_tarihi = c.getString("user_kayit_tarihi");
                            String user_adsoyad = c.getString("user_adsoyad");
                            String user_okul1 = c.getString("user_okul1");
                            String user_okul2 = c.getString("user_okul2");
                            String user_universite = c.getString("user_universite");
                            String user_takim = c.getString("user_takim");
                            String user_club = c.getString("user_club");
                            String user_spor = c.getString("user_spor");

                            UserObject o = new UserObject(user_id, user_name, user_kayit_tarihi, user_adsoyad, user_okul1, user_okul2, user_universite, user_takim, user_club, user_spor);
//                            o.setUserPassword(password);
                            o.setUsername_visible(username);
                            resultList.add(o);
                            resultListCopy.add(o);
                        }
                        adapter = new MySearchResultListAdapter(ActivityCategory1.this, resultList);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory1.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory1.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void performSearch() {

        resultList.clear();
        String word = e_search.getText().toString().toLowerCase();


            for(int i=0;i<resultListCopy.size();i++){
                if (!resultListCopy.get(i).getUsername().equals("") && (resultListCopy.get(i).getUsername().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getUsername().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getAdSoyad().equals("") && (resultListCopy.get(i).getAdSoyad().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getAdSoyad().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getClub().equals("") && (resultListCopy.get(i).getClub().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getClub().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getOkul1().equals("") && (resultListCopy.get(i).getOkul1().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getOkul1().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getOkul2().equals("") && (resultListCopy.get(i).getOkul2().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getOkul2().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getSpor().equals("") && (resultListCopy.get(i).getSpor().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getSpor().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getTakim().equals("") && (resultListCopy.get(i).getTakim().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getTakim().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
                else if(!resultListCopy.get(i).getUniversite().equals("") && (resultListCopy.get(i).getUniversite().toLowerCase().contains(word) || word.contains(resultListCopy.get(i).getUniversite().toLowerCase()))){
                    resultList.add(resultListCopy.get(i));
                }
            }
            resultListCopy.clear();
            for(int i=0;i<resultList.size();i++)
                resultListCopy.add(resultList.get(i));

        adapter = new MySearchResultListAdapter(ActivityCategory1.this, resultList);
        listView.setAdapter(adapter);
    }

    public void clickCloseBottom(View view) {
        bd.dismiss();
    }

    private class InputValidator implements TextWatcher {

        public void afterTextChanged(Editable s) {

        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()==0){
                getUserBySimilarity();
            }
            else{
//                makeAlert.uyarıVer("haha", "search "+edittext_search.getText().toString(), CategoriesPage.this, false);
                performSearch();
            }
        }
    }
}