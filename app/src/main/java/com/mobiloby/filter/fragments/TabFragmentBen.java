package com.mobiloby.filter.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityDescribeAksesuarim;
import com.mobiloby.filter.activities.ActivityDescribeGozlerim;
import com.mobiloby.filter.activities.ActivityDescribeKonum;
import com.mobiloby.filter.activities.ActivityDescribeSac;
import com.mobiloby.filter.activities.ActivityDescribeTen;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyDescribeListAdapter;
import com.mobiloby.filter.databinding.FragmentTabBenBinding;
import com.mobiloby.filter.models.DescribeObject;

import java.util.ArrayList;

public class TabFragmentBen extends Fragment implements MyDescribeListAdapter.ItemClickListener {

    View view;
    FragmentTabBenBinding binding;
    ArrayList<DescribeObject> list;
    MyDescribeListAdapter adapter;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTabBenBinding.inflate(inflater);
        view = binding.getRoot();
        activity = (MainActivity) getActivity();

        prepareMe();



        return view;
    }

    private void prepareMe() {
        list = new ArrayList<>();
        list.add(new DescribeObject(R.drawable.ic_sac, "Saç"));
        list.add(new DescribeObject(R.drawable.ic_goz, "Göz"));
        list.add(new DescribeObject(R.drawable.ic_cilt, "Cilt"));
        list.add(new DescribeObject(R.drawable.ic_kiyafet, "Kıyafet"));
        list.add(new DescribeObject(R.drawable.ic_aksesuar, "Aksesuar"));
        list.add(new DescribeObject(R.drawable.ic_konum, "Konum"));
        adapter = new MyDescribeListAdapter(activity, list);
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(position==0){
            Intent intent = new Intent(activity, ActivityDescribeSac.class);
            intent.putExtra("benO", "ben");
            startActivity(intent);
        }
        else if(position==1){
            Intent intent = new Intent(activity, ActivityDescribeGozlerim.class);
            intent.putExtra("benO", "ben");
            startActivity(intent);
        }
        else if(position==2){
            Intent intent = new Intent(activity, ActivityDescribeTen.class);
            intent.putExtra("benO", "ben");
            startActivity(intent);
        }
        else if(position==3){

        }
        else if(position==4){
            Intent intent = new Intent(activity, ActivityDescribeAksesuarim.class);
            intent.putExtra("benO", "ben");
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(activity, ActivityDescribeKonum.class);
            intent.putExtra("benO", "ben");
            startActivity(intent);
        }
    }
}