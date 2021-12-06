package com.mobiloby.filter.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    TabFragmentFriends tabFragmentFriends;
    TabFragmentMessages tabFragmentMessages;
    FragmentManager fm;
    ViewPagerFragmentAdapter pagerAdapter;
    MessageViewModel viewModel;
    EditText e_search;
    boolean isMessagesFragment = true;
    public TextView t_messages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        t_messages = view.findViewById(R.id.t_messages);

        e_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(isMessagesFragment){
                    if(charSequence.length()==0){
                        tabFragmentMessages.initList();
                    }
                    else{
                        tabFragmentMessages.performSearch(charSequence.toString());
                    }
                }
                else{
                    if(charSequence.length()==0){
                        tabFragmentFriends.initList();
                    }
                    else{
                        tabFragmentFriends.performSearch(charSequence.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        viewModel = new ViewModelProvider(activity).get(MessageViewModel.class);

        observe();

        tabLayout.setOnTabSelectedListener(this);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                e_search.setText("");
                e_search.clearFocus();

                if(position==0){
                    isMessagesFragment = true;
                    t_messages.setText("Mesajlar");
                }
                else{
                    t_messages.setText("Arkadaşlar");
                    isMessagesFragment = false;
                }

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
                    isMessagesFragment = false;
                }
                else{
                    viewPager.setCurrentItem(0);
                    isMessagesFragment = true;
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
        tabFragmentMessages = new TabFragmentMessages();
        tabFragmentFriends = new TabFragmentFriends();
        fragments.add(tabFragmentMessages);
        fragments.add(tabFragmentFriends);
        fm = getChildFragmentManager();
        pagerAdapter = new ViewPagerFragmentAdapter(fm, getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);

        e_search = view.findViewById(R.id.e_search);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        e_search.setText("");
        e_search.clearFocus();
        if(tab.getPosition()==0){
            isMessagesFragment = true;
            t_messages.setText("Mesajlar");
        }
        else{
            isMessagesFragment = false;
            t_messages.setText("Arkadaşlar");
        }
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}