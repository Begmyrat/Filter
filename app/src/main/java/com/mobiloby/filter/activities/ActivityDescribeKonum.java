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
import com.mobiloby.filter.databinding.ActivityDescribeKonumBinding;
import com.mobiloby.filter.databinding.ActivityDescribeTenBinding;

import java.util.ArrayList;

public class ActivityDescribeKonum extends AppCompatActivity implements MyItemListAdapter.ItemClickListener, View.OnClickListener {

    View view;
    ActivityDescribeKonumBinding binding;
    ArrayList<String> list;
    FlexboxLayoutManager layoutManager;
    MyItemListAdapter adapter;
    SharedPreferences preferences;
    String benO;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescribeKonumBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        prepareMe();
    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        extras = getIntent().getExtras();
        if(extras!=null){
            benO = extras.getString("benO");
        }
        list = new ArrayList<>();
        insertData();
        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new MyItemListAdapter(this, list, 1);
        binding.recyclerview.setAdapter(adapter);
        adapter.setClickListener(this);

        try {
            adapter.setSelectedIndex(preferences.getInt(benO+"konum_index", -1));
            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }

    public void insertData(){
        list.add("Sokak");
        list.add("Okul");
        list.add("Tiyatro");
        list.add("Sinema");
        list.add("Konser");
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
    public void onItemClick(View view, int position, int groupId) {
        if(groupId==1){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(benO+"konum_index", position);
            editor.putString(benO+"konum_value", list.get(position));
            editor.commit();
            adapter.setSelectedIndex(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}