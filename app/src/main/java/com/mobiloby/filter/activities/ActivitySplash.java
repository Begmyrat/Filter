package com.mobiloby.filter.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.mobiloby.filter.R;

public class ActivitySplash extends AppCompatActivity {

    SharedPreferences preferences;
    Boolean isLoggedIn = false;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));// set status background white

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                if (isLoggedIn) {
                    Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ActivitySplash.this, OnBoardingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);   //1 seconds

    }
}