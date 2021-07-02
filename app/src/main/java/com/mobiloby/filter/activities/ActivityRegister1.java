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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONObject;

import java.util.HashMap;


public class ActivityRegister1 extends AppCompatActivity {

    EditText username;
    Toast toast;
    TextView alerttext;
    View toastlayout;
    JSONParser jsonParser;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register1);

        //Custom Toast
        LayoutInflater inflater = getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)findViewById(R.id.alertlayout));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);

        username = findViewById(R.id.username);

        int maxLength = 10;
        username.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        username.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>4 && s.length()<15)
                    findViewById(R.id.phoneacceptbut).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.phoneacceptbut).setVisibility(View.INVISIBLE);

                if(s.length()>15) {
                    username.setText(s.subSequence(0, 15));
                    alerttext.setText("En az 4 ve en fazla 15 rakamlı olacak şekilde kullanıcı adınızı giriniz.");
                    toast.setView(toastlayout);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    if (s.charAt(0) != '5'){
                        alerttext.setText("Eksik veya hatalı giriş yaptınız!");
                        toast.setView(toastlayout);
                        toast.show();}
            }
        });

        ImageView backbut = findViewById(R.id.backbut);


        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                finish();
            }
        });

        ImageButton phoneacceptbut = (ImageButton)findViewById(R.id.phoneacceptbut);
        phoneacceptbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
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

                jsonData.put("user_name_unique", username.getText().toString());

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
                }
                else{
                    Intent intent = new Intent(ActivityRegister1.this, ActivityRegister2.class);
                    intent.putExtra("username", username.getText().toString());
                    startActivity(intent);
                }

            }
        }.execute(null, null, null);


    }
}
