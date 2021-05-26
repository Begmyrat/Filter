package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class ActivityRegister extends AppCompatActivity {

    EditText e_username, e_mail, e_password;
    String username, mail, password;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorDarkGray));// set status background white

        prepareMe();
    }

    private void prepareMe() {
        e_username = findViewById(R.id.e_username);
        e_mail = findViewById(R.id.e_mail);
        e_password = findViewById(R.id.e_password);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void clickBack(View view) {
//        startActivity(new Intent(this, ActivityLogin.class));
//        finish();
    }

    public void clickSignUp(View view) {
        username = e_username.getText().toString();
        mail = e_mail.getText().toString();
        password = e_password.getText().toString();

        if(username.length()<6)
            e_username.setError("Kullanıcı adı en az 6 karakterden oluşmalı");
        if(password.length()<6)
            e_password.setError("Şifre en az 6 karakterden oluşmalı");

        if(username.length()>=6 && password.length()>=6 && mail.length()>0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.putString("mail", mail);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}