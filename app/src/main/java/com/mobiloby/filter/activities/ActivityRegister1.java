package com.mobiloby.filter.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.ProfilInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ActivityRegister1 extends AppCompatActivity {

    EditText e_answer;
    Toast toast;
    TextView alerttext, t_question;
    View toastlayout;
    JSONParser jsonParser;
    JSONObject jsonObject;
    ImageView i_continue;
    int currentIndex=0, totalIndex=2;
    ArrayList<ProfilInfo> profilInfos;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground2));// set status background white

        //Custom Toast
        LayoutInflater inflater = getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)findViewById(R.id.alertlayout));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);

        t_question = findViewById(R.id.t_question);
        e_answer = findViewById(R.id.e_answer);
        i_continue = findViewById(R.id.i_continue);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100/totalIndex+ (100/totalIndex)*currentIndex);
        profilInfos = new ArrayList<>();
        profilInfos.add(new ProfilInfo("Okay, Let's get started with your name?",""));
        profilInfos.add(new ProfilInfo("Choose your password",""));
        t_question.setText(profilInfos.get(0).getTitle());

        e_answer.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>=3) {
//                    alerttext.setText("En az 4 ve en fazla 15 rakamlı olacak şekilde kullanıcı adınızı giriniz.");
//                    toast.setView(toastlayout);
//                    toast.show();
                    i_continue.setImageResource(R.drawable.contunie_active);
                }
                else{
                    i_continue.setImageResource(R.drawable.contunie_passive);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "current: " + currentIndex, Toast.LENGTH_SHORT).show();
        if(currentIndex==0){
            finish();
        }
        else{
            t_question.animate().alpha(0).setDuration(600);
            e_answer.animate().alpha(0).setDuration(600);

            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    profilInfos.get(currentIndex).setInfo(e_answer.getText().toString());
                    currentIndex--;
                    t_question.setText(profilInfos.get(currentIndex).getTitle());
                    e_answer.setText(profilInfos.get(currentIndex).getInfo());
                    progressBar.setProgress(100/totalIndex+ (100/totalIndex)*currentIndex);
                    t_question.animate().alpha(1).setDuration(600);
                    e_answer.animate().alpha(1).setDuration(600);
                }
            };

            handler.postDelayed(runnable, 600);
        }
    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/get_user.php";

//        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", e_answer.getText().toString());

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
                    makeAlert.uyarıVer("Filter", "Bu kullanıcı adı sistemimizde zaten mevcuttur. Lütfen başka bir kullanıcı adı seçiniz.", ActivityRegister1.this, true);
                    alerttext.setText("Bu kullanıcı adı sistemimizde zaten mevcuttur. Lütfen başka bir kullanıcı adı seçiniz.");
                    toast.setView(toastlayout);
                    toast.show();
                }
                else{
//                    Intent intent = new Intent(ActivityRegister1.this, ActivityRegister2.class);
//                    intent.putExtra("username", e_answer.getText().toString());
//                    startActivity(intent);
                    Intent intent = new Intent(ActivityRegister1.this, ActivityAvatar.class);
                    intent.putExtra("username", profilInfos.get(0).getInfo());
                    intent.putExtra("password", profilInfos.get(1).getInfo());
                    startActivity(intent);
                }

            }
        }.execute(null, null, null);
    }

    public void clickContinue(View view) {

        if(currentIndex==totalIndex-1){
            getUser();
        }
        else{
            i_continue.setImageResource(R.drawable.contunie_passive);

            t_question.animate().alpha(0).setDuration(600);
            e_answer.animate().alpha(0).setDuration(600);

            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    profilInfos.get(currentIndex).setInfo(e_answer.getText().toString());

                    currentIndex++;
                    progressBar.setProgress(100/totalIndex+ (100/totalIndex)*currentIndex);
                    t_question.setText(profilInfos.get(currentIndex).getTitle());
                    e_answer.setText(profilInfos.get(currentIndex).getInfo());
                    t_question.animate().alpha(1).setDuration(600);
                    e_answer.animate().alpha(1).setDuration(600);
                }
            };

            handler.postDelayed(runnable, 600);
        }
    }
}
