package com.mobiloby.filter.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;

public class TabFragmentSocial extends Fragment implements View.OnClickListener {

    View view;
    MainActivity activity;
    ImageView i_instagram, i_facebook, i_snapchat, i_twitter, i_tiktok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_social, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        i_instagram.setOnClickListener(this);
        i_facebook.setOnClickListener(this);
        i_snapchat.setOnClickListener(this);
        i_twitter.setOnClickListener(this);
        i_tiktok.setOnClickListener(this);

        return view;
    }

    private void prepareMe() {
        i_instagram = view.findViewById(R.id.i_instagram);
        i_facebook = view.findViewById(R.id.i_facebook);
        i_snapchat = view.findViewById(R.id.i_snapchat);
        i_twitter = view.findViewById(R.id.i_twitter);
        i_tiktok = view.findViewById(R.id.i_tiktok);
    }

    @Override
    public void onClick(View v) {
        initButtons();
        if (i_instagram.equals(v)) {
            i_instagram.setImageResource(R.drawable.instagram_active);
            i_instagram.getLayoutParams().width = dpToPx(40, activity);
            i_instagram.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_facebook.equals(v)){
            i_facebook.setImageResource(R.drawable.facebook_active);
            i_facebook.getLayoutParams().width = dpToPx(40, activity);
            i_facebook.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_snapchat.equals(v)){
            i_snapchat.setImageResource(R.drawable.snapchat_active);
            i_snapchat.getLayoutParams().width = dpToPx(40, activity);
            i_snapchat.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_twitter.equals(v)){
            i_twitter.setImageResource(R.drawable.twitter_active);
            i_twitter.getLayoutParams().width = dpToPx(40, activity);
            i_twitter.getLayoutParams().height = dpToPx(40, activity);
        }
        else if(i_tiktok.equals(v)){
            i_tiktok.setImageResource(R.drawable.tiktok_active);
            i_tiktok.getLayoutParams().width = dpToPx(40, activity);
            i_tiktok.getLayoutParams().height = dpToPx(40, activity);
        }
    }

    public void initButtons(){
        i_instagram.setImageResource(R.drawable.instagram_active);
        i_facebook.setImageResource(R.drawable.facebook_passive);
        i_snapchat.setImageResource(R.drawable.snapchat_passive);
        i_twitter.setImageResource(R.drawable.twitter_passive);
        i_tiktok.setImageResource(R.drawable.tiktok_passive);

        i_instagram.getLayoutParams().width = dpToPx(34, activity);
        i_instagram.getLayoutParams().height = dpToPx(34, activity);

        i_facebook.getLayoutParams().width = dpToPx(34, activity);
        i_facebook.getLayoutParams().height = dpToPx(34, activity);

        i_snapchat.getLayoutParams().width = dpToPx(34, activity);
        i_snapchat.getLayoutParams().height = dpToPx(34, activity);

        i_twitter.getLayoutParams().width = dpToPx(34, activity);
        i_twitter.getLayoutParams().height = dpToPx(34, activity);

        i_tiktok.getLayoutParams().width = dpToPx(34, activity);
        i_tiktok.getLayoutParams().height = dpToPx(34, activity);
    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}