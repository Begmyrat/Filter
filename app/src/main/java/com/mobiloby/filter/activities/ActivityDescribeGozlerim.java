package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyItemListAdapter;
import com.mobiloby.filter.databinding.ActivityDescribeGozlerimBinding;

import java.util.ArrayList;

public class ActivityDescribeGozlerim extends AppCompatActivity implements MyItemListAdapter.ItemClickListener, View.OnClickListener {

    View view;
    ActivityDescribeGozlerimBinding binding;
    ArrayList<String> list;
    FlexboxLayoutManager layoutManager;
    MyItemListAdapter adapter;
    SharedPreferences preferences;
    int selectedIndex = -1;
    Bundle extras;
    String benO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescribeGozlerimBinding.inflate(getLayoutInflater());
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
        try {
            selectedIndex = preferences.getInt(benO+"goz_index", -1);
        }catch (Exception e){
            selectedIndex = -1;
        };

        list = new ArrayList<>();
        insertData();

        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new MyItemListAdapter(this, list, 1);
        binding.recyclerview.setAdapter(adapter);
        adapter.setClickListener(this);
        adapter.setSelectedIndex(selectedIndex);
        adapter.notifyDataSetChanged();
    }

    public void insertData(){
        list.add("Mavi");
        list.add("Yeşil");
        list.add("Koyu kahve");
        list.add("Kahve");
        list.add("Ela");
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
            editor.putInt(benO+"goz_index", position);
            editor.putString(benO+"goz_value", list.get(position));
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