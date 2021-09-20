package com.mobiloby.filter.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;

public class TabFragmentToDo extends Fragment {

    View view;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_to_do, container, false);
        activity = (MainActivity) getActivity();

        return view;
    }
}