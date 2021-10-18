package com.mobiloby.filter.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityAvatar;
import com.mobiloby.filter.activities.ActivityLogin1;
import com.mobiloby.filter.activities.ActivityProfileEdit2;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyRecommendListAdapter;
import com.mobiloby.filter.adapters.ViewPagerFragmentAdapter;
import com.mobiloby.filter.databinding.ActivityProfileEditBinding;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.UserObject;
import com.mobiloby.filter.viewModel.MainViewModel;
import com.mobiloby.filter.viewModel.MessageViewModel;
import com.mobiloby.filter.viewModel.ProfileViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentProfilePage extends Fragment implements MyRecommendListAdapter.ItemClickListener, TabLayout.OnTabSelectedListener, View.OnClickListener {

    View view;
    MainActivity activity;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ArrayList<Fragment> fragments;
    FragmentManager fm;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserObject> userObjects;
    MyRecommendListAdapter adapter;
    ViewPagerFragmentAdapter pagerAdapter;
    String username, token, userProfileUrl="", userProfilDoluluk="";
    SharedPreferences preferences;
    TextView t_friendCount, t_requestCount, t_username, t_percentage, t_friendCountTitle, t_requestCountTitle;
    ImageView i_avatar, i_profile, i_settings;
    CircularProgressBar circularProgressBar;
    int width, height;
    CardView cardView_avatar;
    ProgressDialog progressDialog;
    ProfileViewModel profileViewModel;
    MessageViewModel messageViewModel;
    MainViewModel mainViewModel;
    UserObject userSelf;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

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

    private void prepareMe() {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique", "");
        token = preferences.getString("token","");
        cardView_avatar = view.findViewById(R.id.cardview_avatar);
        userSelf = new UserObject();
        userObjects = new ArrayList<>();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init(activity, activity.getApplicationContext(), username);
        messageViewModel = new ViewModelProvider(activity).get(MessageViewModel.class);
        mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        observe();

        i_avatar = view.findViewById(R.id.i_avatar);
        t_friendCount = view.findViewById(R.id.t_friendNumber);
        t_friendCountTitle = view.findViewById(R.id.t_friendsTitle);
        t_requestCount = view.findViewById(R.id.t_requestNumber);
        t_requestCountTitle = view.findViewById(R.id.t_requestTitle);
        t_username = view.findViewById(R.id.t_username);
        i_settings = view.findViewById(R.id.i_settings);
        i_settings.setOnClickListener(this);
        t_percentage = view.findViewById(R.id.t_percentage);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);

        tabLayout = view.findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.smileyface));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.socailmedia));
        viewPager = view.findViewById(R.id.viewPager);
        fragments = new ArrayList<>();
        fragments.add(new TabFragmentToDo());
        fragments.add(new TabFragmentSocial());
        fm = getChildFragmentManager();
        pagerAdapter = new ViewPagerFragmentAdapter(fm, getLifecycle(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setSaveEnabled(false);

        width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;

        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRecommendListAdapter(activity, userObjects);
        adapter.setWH(width,height);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.notifyDataSetChanged();

        i_profile = view.findViewById(R.id.i_profile);
        i_avatar = view.findViewById(R.id.i_avatar);

        i_profile.setOnClickListener(this);
        t_requestCount.setOnClickListener(this);
        t_requestCountTitle.setOnClickListener(this);
        t_friendCount.setOnClickListener(this);
        t_friendCountTitle.setOnClickListener(this);


        if(height<1790){
            cardView_avatar.getLayoutParams().width = dpToPx(80, activity);
            cardView_avatar.getLayoutParams().height = dpToPx(80, activity);
            circularProgressBar.getLayoutParams().width = dpToPx(85, activity);
            circularProgressBar.getLayoutParams().height = dpToPx(85, activity);
            circularProgressBar.setTranslationY(dpToPx(-20, activity));
            cardView_avatar.setTranslationY(dpToPx(-20, activity));
            view.findViewById(R.id.t_friendsTitle).setTranslationY(dpToPx(-130, activity));
            view.findViewById(R.id.t_requestTitle).setTranslationY(dpToPx(-130, activity));
            view.findViewById(R.id.t_friendNumber).setTranslationY(dpToPx(-130, activity));
            view.findViewById(R.id.t_requestNumber).setTranslationY(dpToPx(-130, activity));
            view.findViewById(R.id.t_friendsTitle).setTranslationX(dpToPx(-30, activity));
            view.findViewById(R.id.t_requestTitle).setTranslationX(dpToPx(30, activity));
            view.findViewById(R.id.t_friendNumber).setTranslationX(dpToPx(-30, activity));
            view.findViewById(R.id.t_requestNumber).setTranslationX(dpToPx(30, activity));
            view.findViewById(R.id.t_percentage).setTranslationY(dpToPx(-30, activity));
            view.findViewById(R.id.c_recyclerview).setTranslationY(dpToPx(-40, activity));
        }
        else if(height>2200){
            cardView_avatar.getLayoutParams().width = dpToPx(110, activity);
            cardView_avatar.getLayoutParams().height = dpToPx(110, activity);
            circularProgressBar.getLayoutParams().width = dpToPx(120, activity);
            circularProgressBar.getLayoutParams().height = dpToPx(120, activity);
        }

        LocalBroadcastManager.getInstance(activity).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("Hello");

            }
        }, new IntentFilter("SomeData"));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String currentToken = task.getResult();

                        System.out.println("CURRENTTOKEN: " + currentToken);
                        System.out.println("TOKEN: " + token);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", currentToken);
                        editor.commit();
                        token = currentToken;
//                        updateToken();
//                        profileViewModel.updateToken(token);

                    }
                });
//        profileViewModel.getUser();
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground));// set status background white

        userProfilDoluluk = preferences.getString("userProfilDoluluk", "0");
        t_percentage.setText("%"+userProfilDoluluk);

        profileViewModel.updateToken(token);
    }

    private void observe() {
        profileViewModel.getUserData().observe(activity, new Observer<UserObject>() {
            @Override
            public void onChanged(UserObject userObject) {
                t_username.setText(username);
                t_friendCount.setText(userObject.getFriendCount());
                t_requestCount.setText(userObject.getRequestCount());
                t_friendCount.setText(userObject.getFriendCount());
                t_requestCount.setText(userObject.getRequestCount());
//                t_percentage.setText(userObject.getUserProfilDoluluk());
                userProfileUrl = userObject.getAvatar_id();

                circularProgressBar.setProgress(Float.parseFloat(userObject.getUserProfilDoluluk()));

                Glide
                        .with(activity)
                        .load("https:mobiloby.com/_filter/assets/profile/" + userProfileUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_f_char)
                        .into(i_avatar);

            }
        });

        profileViewModel.getUserDataError().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    makeAlert.uyarıVer("Filter", "Kullanıcı bilgileri alınamadı. Lütfen tekrar deneyiniz.", activity, true);
                }
            }
        });

        profileViewModel.getUserDataLoading().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressDialog.show();
                }
                else{
                    progressDialog.dismiss();
                }
            }
        });

        profileViewModel.getRecommendedUsers().observe(activity, new Observer<List<UserObject>>() {
            @Override
            public void onChanged(List<UserObject> userObjectsList) {
                if(userObjectsList.size()>0){
                    userObjects.clear();
                    userObjects.addAll(userObjectsList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        profileViewModel.getRecommendedUsersError().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    makeAlert.uyarıVer("Filter", "Önerilenler alınamadı. Lütfen tekrar deneyiniz.", activity, true);
                }
            }
        });

        profileViewModel.getRecommendedUsersLoading().observe(activity, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressDialog.show();
                }
                else{
                    progressDialog.dismiss();
                }
            }
        });


    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(activity, ActivityProfileEdit2.class);
        intent.putExtra("username", userObjects.get(position).getUsername());
        intent.putExtra("userProfileUrl", userObjects.get(position).getAvatar_id());
        startActivity(intent);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.i_settings:
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.commit();
                startActivity(new Intent(activity, ActivityLogin1.class));
                break;
            case R.id.i_profile:
                Intent intent = new Intent(activity, ActivityProfileEdit2.class);
                intent.putExtra("username", username);
                intent.putExtra("userProfileUrl", userProfileUrl);
                startActivity(intent);
                break;
            case R.id.t_friendNumber:
                messageViewModel.setIsFriends(true);
                mainViewModel.setIndexFragment(1);
                break;
            case R.id.t_friendsTitle:
                messageViewModel.setIsFriends(true);
                mainViewModel.setIndexFragment(1);
                break;
            case R.id.t_requestNumber:
                messageViewModel.setIsFriends(true);
                mainViewModel.setIndexFragment(1);
                break;
            case R.id.t_requestTitle:
                messageViewModel.setIsFriends(true);
                mainViewModel.setIndexFragment(1);
                break;
            case R.id.i_avatar:
                break;

        }
    }
}