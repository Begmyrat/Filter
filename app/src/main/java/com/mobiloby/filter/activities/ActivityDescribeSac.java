package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyItemListAdapter;
import com.mobiloby.filter.databinding.ActivityDescribeSacBinding;
import java.util.ArrayList;

public class ActivityDescribeSac extends AppCompatActivity  implements MyItemListAdapter.ItemClickListener, View.OnClickListener {

    View view;
    ActivityDescribeSacBinding binding;
    ArrayList<String> list1, list2, list3;
    FlexboxLayoutManager layoutManager1, layoutManager2, layoutManager3;
    MyItemListAdapter adapter1, adapter2, adapter3;
    SharedPreferences preferences;
    Bundle extras;
    String benO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescribeSacBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        prepareMe();
    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        extras = getIntent().getExtras();
        if(extras!=null){
            benO = extras.getString("benO");
        }
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        insertData();
        layoutManager1 = new FlexboxLayoutManager(this);
        layoutManager1.setFlexDirection(FlexDirection.ROW);
        layoutManager1.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexDirection(FlexDirection.ROW);
        layoutManager2.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager3 = new FlexboxLayoutManager(this);
        layoutManager3.setFlexDirection(FlexDirection.ROW);
        layoutManager3.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerview1.setLayoutManager(layoutManager1);
        binding.recyclerview2.setLayoutManager(layoutManager2);
        binding.recyclerview3.setLayoutManager(layoutManager3);
        adapter1 = new MyItemListAdapter(this, list1, 1);
        adapter2 = new MyItemListAdapter(this, list2, 2);
        adapter3 = new MyItemListAdapter(this, list3, 3);
        binding.recyclerview1.setAdapter(adapter1);
        binding.recyclerview2.setAdapter(adapter2);
        binding.recyclerview3.setAdapter(adapter3);
        adapter1.setClickListener(this);
        adapter2.setClickListener(this);
        adapter3.setClickListener(this);

        try {
            adapter1.setSelectedIndex(preferences.getInt(benO+"sac1_index", -1));
            adapter2.setSelectedIndex(preferences.getInt(benO+"sac2_index", -1));
            adapter3.setSelectedIndex(preferences.getInt(benO+"sac3_index", -1));
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
        }catch (Exception e){};
    }

    public void insertData(){
        list1.add("Uzun");
        list1.add("Kısa");
        list1.add("Kıvırcık");

        list2.add("Orta Boy");
        list2.add("Dalgalı");
        list2.add("Düz");

        list3.add("Sarı");
        list3.add("Kahverengi");
        list3.add("Kırmızı");
        list3.add("Renkli");
        list3.add("Siyah");
    }

    @Override
    public void onItemClick(View view, int position, int groupId) {
        if(groupId==1){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(benO+"sac1_index", position);
            editor.putString(benO+"sac1_value", list1.get(position));
            editor.commit();
            adapter1.setSelectedIndex(position);
            adapter1.notifyDataSetChanged();
        }
        else if(groupId == 2){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(benO+"sac2_index", position);
            editor.putString(benO+"sac2_value", list2.get(position));
            editor.commit();
            adapter2.setSelectedIndex(position);
            adapter2.notifyDataSetChanged();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(benO+"sac3_index", position);
            editor.putString(benO+"sac3_value", list3.get(position));
            editor.commit();
            adapter3.setSelectedIndex(position);
            adapter3.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.i_back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}