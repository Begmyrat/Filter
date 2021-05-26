package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class ActivitySignUp extends AppCompatActivity {

    EditText e_username, e_password;
    SharedPreferences preferences;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username, password="1234";
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        prepareMe();
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        android_id = Settings.Secure.getString(getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e_username = findViewById(R.id.e_username);
        e_username.clearFocus();
        e_password = findViewById(R.id.e_password);
        e_password.clearFocus();

    }

    public void clickTemizle(View view) {
        // x e tiklayinca
        e_username.setText("");
    }

    public void clickSignUp(View view) {
        username = e_username.getText().toString();
        password = e_password.getText().toString();
        if(username.length()>=6 && password.length()>=6){
            getUser();
        }
        else if(username.length()<6){
            e_username.setError("Kullanıcı adı en az 6 karakterden oluşmalıdır.");
        }
        else{
            e_password.setError("Şifre en az 6 karakterden oluşmalıdır.");
        }
    }

    private void insertUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_user.php";

        final String username_visible = username;
        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username_unique);
                jsonData.put("user_name_visible", username_visible);
                jsonData.put("user_password", password);
                jsonData.put("player_id", android_id);


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
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username_visible", username_visible);
                    editor.putString("username_unique", username_unique);
                    editor.putString("user_password", password);
                    editor.commit();
                    Intent intent = new Intent(ActivitySignUp.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivitySignUp.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_user.php";

        final String username_unique = username.replaceAll("İ", "i").replaceAll("ı", "i").replaceAll("Ğ", "g").replaceAll("ğ","g").toLowerCase();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username_unique);

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
                    makeAlert.uyarıVer("Filter", "Bu kullanıcı adı sistemimizde zaten mevcuttur. Lütfen başka bir kullanıcı adı seçiniz.", ActivitySignUp.this, true);
                }
                else{

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username_unique", username_unique);
                    editor.putString("username_visible", username);
                    editor.putString("user_password", password);
                    editor.putString("player_id", android_id);
                    editor.commit();

                    startActivity(new Intent(getApplicationContext(), ActivityAvatar.class));
                    finish();
                }

            }
        }.execute(null, null, null);


    }

    public void clickLogin(View view) {
        startActivity(new Intent(ActivitySignUp.this, LoginActivity.class));
    }
}