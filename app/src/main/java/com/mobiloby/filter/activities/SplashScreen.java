package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.mobiloby.filter.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences preferences;
    Boolean isLoggedIn = false;
    VideoView videoView;
    ImageView i_bubble1;
    int c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));// set status background white

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        i_bubble1 = findViewById(R.id.i_ortaDaire);

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                if(c%2==1){
//                    c=0;
//                    i_bubble1.animate().alpha(1).scaleX(1).scaleY(1).setDuration(1000);
//                }
//                else{
//                    c=1;
//                    i_bubble1.animate().alpha(0).scaleX(0).scaleY(0).setDuration(1000);
//                }
//                handler.postDelayed(this, 1000);
//            }
//        };
//
//        handler.postDelayed(runnable, 1000);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
//                    Intent intent = new Intent(SplashScreen.this, ActivityLogin1.class);
//                    startActivity(intent);
//                    finish();
                if (isLoggedIn) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreen.this, OnBoardingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);   //5 seconds

//        videoView.setZOrderOnTop(true);
//        videoView.start();

    }
}