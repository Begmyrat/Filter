package com.mobiloby.filter.activities;

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

import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText e_username, e_password;
    SharedPreferences preferences;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username, password="1234";
    String android_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepareMe();
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorWhite));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


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
        startActivity(new Intent(LoginActivity.this, ActivitySignUp.class));
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/get_user.php";

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

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);

                            String password = c.getString("user_password");

                            if(password.equals(e_password.getText().toString())){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username_unique", username_unique);
                                editor.putString("user_password", password);
                                editor.putString("avatar_id", c.getString("user_profile_url"));
                                editor.commit();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{
                                makeAlert.uyarıVer("Filter", "Şifre hatalı", LoginActivity.this, true);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "error jiimRecommend", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Kullanıcı adı veya şifre hatalı.", LoginActivity.this, true);
                }

            }
        }.execute(null, null, null);


    }

    public void clickLogin(View view) {
        username = e_username.getText().toString();
        password = e_password.getText().toString();
        getUser();
    }
}