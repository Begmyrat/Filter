package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyRecycleAnswersListAdapter;
import com.mobiloby.filter.databinding.ActivityProfileEditFormBinding;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityProfileEditForm extends AppCompatActivity implements MyRecycleAnswersListAdapter.ItemClickListener, View.OnClickListener{

    ActivityProfileEditFormBinding binding;
    View view;
    String title="", info="", option="", username="";
    Boolean isDropDown=false;
    int index = -1, clickedItemIndex=-1;
    Bundle extras;
    ArrayList<String> answers;
    MyRecycleAnswersListAdapter adapter;
    JSONParser jsonParser;
    JSONObject jsonObject;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditFormBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        prepareMe();

        setListeners();

    }

    private void setListeners() {
        binding.iCross.setOnClickListener(this);
        binding.iTick.setOnClickListener(this);
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        extras = getIntent().getExtras();
        username = extras.getString("username","");
        title = extras.getString("title");
        clickedItemIndex = extras.getInt("clickedItemIndex",0);
        info = extras.getString("info");
        option = extras.getString("option");
        isDropDown = extras.getBoolean("isDropDown");
        binding.tTitle.setText(title);

        if(isDropDown){
            binding.eInfo.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.VISIBLE);
            answers = new ArrayList<>();
            String[] a = option.split(",");
            for(int i=0;i<a.length;i++)
                answers.add(a[i]);
            index = answers.indexOf(info);
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new MyRecycleAnswersListAdapter(this, answers, index);
            binding.recyclerview.setAdapter(adapter);
            adapter.setClickListener(this);
        }
        else{
            binding.eInfo.setVisibility(View.VISIBLE);
            binding.recyclerview.setVisibility(View.GONE);
            if(info.length()==0){
                binding.eInfo.setHint("Buraya giriniz");
            }
            else{
                binding.eInfo.setText(info);
            }
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        index = position;
        adapter.selected_pos = position;
        adapter.notifyDataSetChanged();
    }

    private void updateData() {
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/update_user_profile.php";
        String icerik = "";
        if(isDropDown){
            icerik = answers.get(index);
        }
        else{
            icerik = binding.eInfo.getText().toString();
        }

        String finalIcerik = icerik;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("username", username);
                jsonData.put("secilen_index", ""+clickedItemIndex);
                jsonData.put("secilen_icerik", finalIcerik);

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
                    finish();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", getApplicationContext(), true);
                }

            }
        }.execute(null, null, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.i_cross:
                finish();
                break;
            case R.id.i_tick:
                updateData();
                break;
        }
    }
}