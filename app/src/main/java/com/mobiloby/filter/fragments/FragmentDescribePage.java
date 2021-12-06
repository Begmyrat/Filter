package com.mobiloby.filter.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.ViewPagerFragmentAdapter;
import com.mobiloby.filter.databinding.FragmentDescribePageBinding;

import java.util.ArrayList;

public class FragmentDescribePage extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    View view;
    FragmentDescribePageBinding binding;
    MainActivity activity;
    ArrayList<Fragment> fragments;
    TabFragmentBen tabFragmentBen;
    TabFragmentO tabFragmentO;
    FragmentManager fm;
    ViewPagerFragmentAdapter pagerAdapter;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDescribePageBinding.inflate(inflater);
        view = binding.getRoot();
        activity = (MainActivity) getActivity();

        prepareMe();
        setListeners();

        binding.tabLayout.setOnTabSelectedListener(this);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        return view;
    }

    private void setListeners() {
        binding.iKaydet.setOnClickListener(this);
        binding.iGecmis.setOnClickListener(this);
    }

    private void prepareMe() {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Ben"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("O"));
//        viewPager = view.findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        tabFragmentBen = new TabFragmentBen();
        tabFragmentO = new TabFragmentO();
        fragments.add(tabFragmentBen);
        fragments.add(tabFragmentO);
        fm = activity.getSupportFragmentManager();
        pagerAdapter = new ViewPagerFragmentAdapter(fm, getLifecycle(), fragments);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.setSaveEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;

    }

    @Override
    public void onResume() {
        super.onResume();

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground));// set status background white
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.i_kaydet:
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("benaksesuar_index","");
                editor.putString("benaksesuar_value","");
                editor.putString("bengoz_index","");
                editor.putString("bengoz_value","");
                editor.putString("benkonum_index","");
                editor.putString("benkonum_value","");
                editor.putString("bensac1_index","");
                editor.putString("bensac1_value","");
                editor.putString("bensac2_index","");
                editor.putString("bensac2_value","");
                editor.putString("bensac3_index","");
                editor.putString("bensac3_value","");
                editor.putString("benten_index","");
                editor.putString("benten_value","");

                editor.putString("oaksesuar_index","");
                editor.putString("oaksesuar_value","");
                editor.putString("ogoz_index","");
                editor.putString("ogoz_value","");
                editor.putString("okonum_index","");
                editor.putString("okonum_value","");
                editor.putString("osac1_index","");
                editor.putString("osac1_value","");
                editor.putString("osac2_index","");
                editor.putString("osac2_value","");
                editor.putString("osac3_index","");
                editor.putString("osac3_value","");
                editor.putString("oten_index","");
                editor.putString("oten_value","");

                editor.commit();
                break;
            case R.id.i_gecmis:
                break;
        }
    }
}