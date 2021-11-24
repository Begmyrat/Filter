package com.mobiloby.filter.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiloby.filter.R;
import com.mobiloby.filter.ShowToastMessage;
import com.mobiloby.filter.activities.ActivityChat;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MySocialRecycleListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.SocialMediaObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TabFragmentSocial extends Fragment implements View.OnClickListener, MySocialRecycleListAdapter.ItemClickListenerSocial {

    View view;
    MainActivity activity;
    String social_type="instagram", username="";
    SharedPreferences preferences;
    ImageView i_instagram, i_facebook, i_snapchat, i_twitter, i_tiktok;
    RelativeLayout i_history, i_match;
    Dialog builder;
    Dialog builderPopup;
    MySocialRecycleListAdapter adapter;
    ArrayList<SocialMediaObject> list;
    RecyclerView.LayoutManager layoutManager;
    JSONParser jsonParser;
    ProgressDialog progressDialog;
    JSONObject jsonObject;
    EditText e_username, e_username_other;
    View viewPopup;
    TextView t_aramaBulunamadi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_social, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

//        getCurrentSearches();

        i_instagram.setOnClickListener(this);
        i_facebook.setOnClickListener(this);
        i_snapchat.setOnClickListener(this);
        i_twitter.setOnClickListener(this);
        i_tiktok.setOnClickListener(this);
        i_history.setOnClickListener(this);
        i_match.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        social_type = "instagram";
        i_instagram.setImageResource(R.drawable.instagram_active);
        i_instagram.getLayoutParams().width = dpToPx(40, activity);
        i_instagram.getLayoutParams().height = dpToPx(40, activity);
    }

    private void prepareMe() {

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique", "");

        i_instagram = view.findViewById(R.id.i_instagram);
        i_facebook = view.findViewById(R.id.i_facebook);
        i_snapchat = view.findViewById(R.id.i_snapchat);
        i_twitter = view.findViewById(R.id.i_twitter);
        i_tiktok = view.findViewById(R.id.i_tiktok);
        i_history = view.findViewById(R.id.i_history);
        i_match = view.findViewById(R.id.i_match2);
        e_username = view.findViewById(R.id.e_location);
        e_username_other = view.findViewById(R.id.e_todo);

        list = new ArrayList<>();
        adapter = new MySocialRecycleListAdapter(activity, list);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

//        getCurrentSearches();
    }

    @Override
    public void onClick(View v) {

        if (i_instagram.equals(v)) {
            social_type = "instagram";
            initButtons();
            i_instagram.setImageResource(R.drawable.instagram_active);
            i_instagram.getLayoutParams().width = dpToPx(40, activity);
            i_instagram.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_facebook.equals(v)){
            social_type = "facebook";
            initButtons();
            i_facebook.setImageResource(R.drawable.facebook_active);
            i_facebook.getLayoutParams().width = dpToPx(40, activity);
            i_facebook.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_snapchat.equals(v)){
            social_type = "snapchat";
            initButtons();
            i_snapchat.setImageResource(R.drawable.snapchat_active);
            i_snapchat.getLayoutParams().width = dpToPx(40, activity);
            i_snapchat.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_twitter.equals(v)){
            social_type = "twitter";
            initButtons();
            i_twitter.setImageResource(R.drawable.twitter_active);
            i_twitter.getLayoutParams().width = dpToPx(40, activity);
            i_twitter.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_tiktok.equals(v)){
            social_type = "tiktok";
            initButtons();
            i_tiktok.setImageResource(R.drawable.tiktok_active);
            i_tiktok.getLayoutParams().width = dpToPx(40, activity);
            i_tiktok.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_history.equals(v)){
//            if(list.size()>0)
                popup();
        }
        else if(i_match.equals(v)){
            if(e_username.getText().toString().length()>0 && e_username_other.getText().toString().length()>0)
                sendBilgiler();
            else{
//                Toast.makeText(activity, "Eşleşme için gerekli bilgileri doldurunuz.", Toast.LENGTH_SHORT).show();
                ShowToastMessage.show(activity, "Eşleşme için gerekli bilgileri doldurunuz.");
            }
        }
    }

    RecyclerView recyclerView;
    Button b_close;

    public void popup(){
        builderPopup = new Dialog(activity, R.style.AlertDialogCustom);
        viewPopup = LayoutInflater.from(activity).inflate(R.layout.popup_social_history, null);

        adapter = new MySocialRecycleListAdapter(activity, list);
        recyclerView = viewPopup.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setClickListenerSocial(this);
        b_close = viewPopup.findViewById(R.id.b_close);
        t_aramaBulunamadi = viewPopup.findViewById(R.id.t_aramaBulunamadi);

        getCurrentSearches();

        b_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderPopup.dismiss();
            }
        });


        builderPopup.setCancelable(true);
        builderPopup.setContentView(viewPopup);

    }

    public void initButtons(){
        i_instagram.setImageResource(R.drawable.instagram_passive);
        i_facebook.setImageResource(R.drawable.facebook_passive);
        i_snapchat.setImageResource(R.drawable.snapchat_passive);
        i_twitter.setImageResource(R.drawable.twitter_passive);
        i_tiktok.setImageResource(R.drawable.tiktok_passive);

        i_instagram.getLayoutParams().width = dpToPx(34, activity);
        i_instagram.getLayoutParams().height = dpToPx(34, activity);

        i_facebook.getLayoutParams().width = dpToPx(34, activity);
        i_facebook.getLayoutParams().height = dpToPx(34, activity);

        i_snapchat.getLayoutParams().width = dpToPx(34, activity);
        i_snapchat.getLayoutParams().height = dpToPx(34, activity);

        i_twitter.getLayoutParams().width = dpToPx(34, activity);
        i_twitter.getLayoutParams().height = dpToPx(34, activity);

        i_tiktok.getLayoutParams().width = dpToPx(34, activity);
        i_tiktok.getLayoutParams().height = dpToPx(34, activity);
    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onItemClick(View view, int position) {
        // popup sil

        popupUyari(position);


    }

    private void popupUyari(int position) {
        builder = new Dialog(activity, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.popup_uyari_sil, null);

        CardView cardViewEvet = view.findViewById(R.id.cardviewEvet);
        CardView cardViewVazgec = view.findViewById(R.id.cardviewVazgec);
        TextView e_info = view.findViewById(R.id.e_info);
        TextView t_title = view.findViewById(R.id.t_title);

        t_title.setText("Aramayı Sil");
        e_info.setText("Silmek üzeresiniz...");

        cardViewEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                deleteSearch(position);
            }
        });

        cardViewVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    private void sendBilgiler() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_social_media.php";
        final String username_social = e_username.getText().toString();
        final String username_social_other = e_username_other.getText().toString();

        final String type = social_type;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
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
                    makeAlert.uyarıVer("Fltr", "Bilgileriniz tamamlanmıştır. Eşleşme için takipte kalın", activity, false);
                    e_username.setText("");
                    e_username_other.setText("");
//                    getCurrentSearches();
                }
                else if(res.equals("2")){
                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");
                        JSONObject c = pro.getJSONObject(0);
                        String friend_user_name_unique = c.getString("friend_user_name_unique");
                        makeAlert.uyarıVer("Fltr", "Yeni bir eşleşmeniz var!\n"+friend_user_name_unique, activity, false);
                        e_username.setText("");
                        e_username_other.setText("");
                        insertFriend(friend_user_name_unique);

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", activity, true);
                }


            }
        }.execute(null, null, null);
    }

    private void insertFriend(String friend_username_unique) {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_friend_direk.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
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
                    pushNotification(friend_username_unique);
                }
                else{
                }

            }
        }.execute(null, null, null);
    }

    private void pushNotification(String friend_username_unique) {

        final String url = "https://mobiloby.com/_filter/bildirim_gonder_deneme.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("title", "");
                jsonData.put("username", friend_username_unique);
                jsonData.put("message", username + " adlı kişi ile sosyal medya aramasından eşleştiniz.");


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

                if (res.equals("1")) {

                }
                else{
//                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityChat.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void deleteSearch(int index) {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/delete_current_searches.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("id", list.get(index).getId());

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
                    list.remove(index);
                    adapter.notifyDataSetChanged();
                    if(list.size()==0 && builderPopup!=null){
                        t_aramaBulunamadi.setVisibility(View.VISIBLE);
                        builderPopup.dismiss();
                    }
                    else{
                        t_aramaBulunamadi.setVisibility(View.GONE);
                    }
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", activity, true);
                }

            }
        }.execute(null, null, null);
    }


    private void getCurrentSearches() {

        progressDialog.show();

        list.clear();

        final String url = "https://mobiloby.com/_filter/get_current_searches.php";

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
                            String social_name = c.getString("username_social");
                            String social_name_other = c.getString("username_social_other");
                            String type = c.getString("type");
                            String tarih = c.getString("tarih");

                            SocialMediaObject object = new SocialMediaObject(id, username, social_name, social_name_other, type, tarih);
                            list.add(object);
                        }

                        if(list.size()==0){
                            t_aramaBulunamadi.setVisibility(View.VISIBLE);
                        }
                        else{
//                            list.add(new SocialMediaObject("","","","","",""));
                            t_aramaBulunamadi.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    t_aramaBulunamadi.setVisibility(View.VISIBLE);
                }
                if(builderPopup!=null)
                    builderPopup.show();

            }
        }.execute(null, null, null);
    }
}