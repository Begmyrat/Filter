package com.mobiloby.filter.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobiloby.filter.adapters.ViewPagerFragmentAdapter;
import com.mobiloby.filter.fragments.FragmentDescribePage;
import com.mobiloby.filter.fragments.FragmentDiscoverPage;
import com.mobiloby.filter.fragments.FragmentMessagesPage;
import com.mobiloby.filter.fragments.FragmentProfilePage;
import com.mobiloby.filter.R;
import com.mobiloby.filter.viewModel.MainViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FragmentProfilePage fragmentProfilePage;
    FragmentDiscoverPage fragmentDiscoverPage;
    FragmentMessagesPage fragmentMessagesPage;
    FragmentDescribePage fragmentDescribePage;
    public static Context context;
    ArrayList<Fragment> fragmentList;
    ViewPager2 viewPager;
    ViewPagerFragmentAdapter pagerAdapter;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareMe();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observe();
    }

    private void observe() {
        viewModel.getIndexFragment().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                initNavButtons();
                bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.messages_selected);
                viewPager.setCurrentItem(integer, false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.backgroundColor));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fragmentList = new ArrayList<>();
        context = getApplicationContext();

        bottomNavigationView = findViewById(R.id.bottomNavBar);
//        ft = getSupportFragmentManager().beginTransaction();
        fragmentProfilePage = new FragmentProfilePage();
        fragmentDiscoverPage = new FragmentDiscoverPage();
        fragmentMessagesPage = new FragmentMessagesPage();
        fragmentDescribePage = new FragmentDescribePage();

        fragmentList.add(fragmentDiscoverPage);
        fragmentList.add(fragmentMessagesPage);
        fragmentList.add(fragmentDescribePage);
        fragmentList.add(fragmentProfilePage);

        viewPager = findViewById(R.id.fragment_container);
        pagerAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(3, false);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(4);



//        for(int i=0;i<fragmentList.size();i++){
//            ft.add(R.id.fragment_container, fragmentList.get(i));
//        }
//        ft.commit();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

//    public void popup(String title){
//        builder = new Dialog(this, R.style.AlertDialogCustom);
//        View view;
//        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
//        TextView t_title = view.findViewById(R.id.e_info);
//        t_title.setText(title);
//        builder.setCancelable(true);
//        builder.setContentView(view);
//        builder.show();
//    }
//
//    public void clickTamam(View view) {
//        builder.dismiss();
//    }

    public void clickIstekler(View view) {
        startActivity(new Intent(this, ActivityIstek.class));
    }

    public void clickFriends(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityFriends.class);
        startActivity(intent);
    }

    public void clickMessage(View view) {
        startActivity(new Intent(this, ActivityFriendMessage.class));
    }

//    public void clickProfileTop(View view) {
//        Intent intent = new Intent(this, InformationActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
//    }

    public void initNavButtons(){
        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.discover);
        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.messages);
        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.describe);
        bottomNavigationView.getMenu().getItem(3).setIcon(R.drawable.profil);
    }

    public void hideFragments(FragmentTransaction ft){
        for(int i=0;i<fragmentList.size();i++){
            ft.hide(fragmentList.get(i));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        initNavButtons();

        if(item.getItemId() == R.id.discover){
            item.setIcon(R.drawable.discover_selected);
            viewPager.setCurrentItem(0, false);
            return true;
        }
        else if(item.getItemId() == R.id.messages){
            item.setIcon(R.drawable.messages_selected);
            viewPager.setCurrentItem(1, false);
            return true;
        }
        else if(item.getItemId() == R.id.describe){
            item.setIcon(R.drawable.describe_selected);
            viewPager.setCurrentItem(2, false);
            return true;
        }
        else if(item.getItemId() == R.id.profile){
            item.setIcon(R.drawable.profil_selected);
            viewPager.setCurrentItem(3, false);
            return true;
        }

        return true;
    }

    public void changeCurrentItem(int index){
        initNavButtons();
        if(index==0)
            bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.discover_selected);
        else if(index==1)
            bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.messages_selected);
        else if(index==2)
            bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.describe_selected);
        else if(index==3)
            bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.profil_selected);

        viewPager.setCurrentItem(index, false);
    }
}