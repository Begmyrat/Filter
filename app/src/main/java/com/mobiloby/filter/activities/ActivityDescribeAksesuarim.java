package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.MyItemListAdapter;
import com.mobiloby.filter.databinding.ActivityDescribeAksesuarimBinding;

import java.util.ArrayList;

public class ActivityDescribeAksesuarim extends AppCompatActivity implements MyItemListAdapter.ItemClickListener, View.OnClickListener {

    View view;
    ActivityDescribeAksesuarimBinding binding;
    ArrayList<String> list;
    FlexboxLayoutManager layoutManager;
    MyItemListAdapter adapter;
    SharedPreferences preferences;
    ArrayList<Integer> indexes;
    String benO="";
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescribeAksesuarimBinding.inflate(getLayoutInflater());
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
        list = new ArrayList<>();
        indexes = new ArrayList<>();
        insertData();
        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter = new MyItemListAdapter(this, list, 1);
        binding.recyclerview.setAdapter(adapter);
        adapter.setClickListener(this);

        extras = getIntent().getExtras();
        if(extras!=null){
            benO = extras.getString("benO");
        }

        try {
            String ix = preferences.getString(benO+"aksesuar_index", "");
            Toast.makeText(this, ""+ix, Toast.LENGTH_SHORT).show();
            String[] arr = ix.split(",");
            for(int i=0;i<arr.length;i++){
                indexes.add(Integer.parseInt(arr[i]));
            }
            adapter.setIndexes(indexes);
            adapter.notifyDataSetChanged();
        }catch (Exception e){}



    }

    public void insertData(){
        list.add("Gözlük");
        list.add("Güneş Gözlüğü");
        list.add("Şapka");
        list.add("Bere");
        list.add("Fular");
        list.add("Dövme");
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
        if(groupId==1) {
            if (indexes.contains(position)){
                for (int i = 0; i < indexes.size(); i++) {
                    if (indexes.get(i) == position) {
                        indexes.remove(i);
                        break;
                    }
                }
            }
            else
                indexes.add(position);

            String ix = "", vx = "";
            for(int i=0;i<indexes.size()-1;i++) {
                ix += indexes.get(i) + ",";
                vx += list.get(indexes.get(i)) + ",";
            }
            if(indexes.size()>0) {
                ix += indexes.get(indexes.size() - 1);
                vx += list.get(indexes.get(indexes.size()-1));
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(benO+"aksesuar_index", ix);
            editor.putString(benO+"aksesuar_value", vx);
            editor.commit();
            adapter.setIndexes(indexes);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }
}