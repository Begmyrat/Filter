package com.mobiloby.filter.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;

public class FragmentDescribePage extends Fragment {

    View view;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_describe_page, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        return view;
    }

    private void prepareMe() {
    }

    @Override
    public void onResume() {
        super.onResume();

        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,R.color.colorBackground));// set status background white
    }
}