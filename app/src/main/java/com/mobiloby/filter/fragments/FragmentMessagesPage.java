package com.mobiloby.filter.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyRecommendListAdapter;
import com.mobiloby.filter.adapters.ViewPagerFragmentAdapter;
import com.mobiloby.filter.viewModel.MessageViewModel;

import java.util.ArrayList;

public class FragmentMessagesPage extends Fragment implements TabLayout.OnTabSelectedListener, View.OnClickListener  {

    View view;
    MainActivity activity;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ArrayList<Fragment> fragments;
    FragmentManager fm;
    ViewPagerFragmentAdapter pagerAdapter;
    MessageViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        viewModel = new ViewModelProvider(activity).get(MessageViewModel.class);

        observe();

        tabLayout.setOnTabSelectedListener(this);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        return view;
    }

    private void observe() {
        viewModel.getIsFriends().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    viewPager.setCurrentItem(1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground));// set status background white
    }

    private void prepareMe() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.messages));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.friendicon));
        viewPager = view.findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        fragments.add(new TabFragmentMessages());
        fragments.add(new TabFragmentFriends());
        fm = getChildFragmentManager();
        pagerAdapter = new ViewPagerFragmentAdapter(fm, getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}