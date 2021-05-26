package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ActivityOnboard extends AppCompatActivity {

    ViewPager viewPager;
    OnboardPagerAdapter pagerAdapter;
    ArrayList<OnboardItem> list;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        prepareMe();

    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);

        list = new ArrayList<>();
        list.add(new OnboardItem("title 1", "description 1", R.drawable.ic_facebook));
        list.add(new OnboardItem("title 2", "description 2", R.drawable.ic_snapchat));
        list.add(new OnboardItem("title 3", "description 3", R.drawable.ic_instagram));

        pagerAdapter = new OnboardPagerAdapter(this, list);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(" ");
        tabLayout.getTabAt(1).setText(" ");
        tabLayout.getTabAt(2).setText(" ");

    }

    public void clickSkipButton(View view) {
        startActivity(new Intent(getApplicationContext(), ActivitySignUp.class));
        finish();
    }
}