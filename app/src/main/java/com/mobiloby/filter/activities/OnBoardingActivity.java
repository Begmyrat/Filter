package com.mobiloby.filter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.mobiloby.filter.R;
import com.mobiloby.filter.adapters.OnBoard_Adapter;
import com.mobiloby.filter.models.OnBoardItemObject;

import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity {



    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPager onboard_pager;
    OnBoard_Adapter mAdapter;
    int previous_pos=0;
    ArrayList<OnBoardItemObject> onBoardItems=new ArrayList<>();

    String userId;
    String access_token;
    TextView tvNext, tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_on_boarding);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));// set status background white

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        Intent getintent = getIntent();
        userId = getintent.getStringExtra("userId");
        access_token = getintent.getStringExtra("userId");

        onboard_pager = (ViewPager) findViewById(R.id.pager_introduction);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        loadData();

        mAdapter = new OnBoard_Adapter(this,onBoardItems);
        onboard_pager.setAdapter(mAdapter);
        onboard_pager.setCurrentItem(0);

        SharedPreferences settings = getSharedPreferences("session", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();


        tvSkip = (TextView)findViewById(R.id.tvSkip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, ActivityLogin1.class);
                startActivity(intent);

            }
        });

        tvNext = (TextView)findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNext.getText().equals("Sonraki")){
                    onboard_pager.setCurrentItem(onboard_pager.getCurrentItem()+1);
                }else{
                    Intent intent = new Intent(OnBoardingActivity.this, ActivityLogin1.class);
                    startActivity(intent);
                }


            }
        });

        onboard_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.selected_item_dot));


                int pos=position+1;

                if(pos==dotsCount&&previous_pos==(dotsCount-1)){
                    //show_animation();
                    tvNext.setText("Giriş Yap");
                } else if(pos==(dotsCount-1)&&previous_pos==dotsCount){
                    //hide_animation();
                    tvNext.setText("Sonraki");
                }


                previous_pos=pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUiPageViewController();

    }

    // Load data into the viewpager

    public void loadData()
    {

        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3, R.string.ob_header4, R.string.ob_header5 };
        int[] desc = {R.string.ob_desc1, R.string.ob_desc2, R.string.ob_desc3, R.string.ob_desc4, R.string.ob_desc5 };
        int[] imageId = {R.drawable.frame_3, R.drawable.frame_4, R.drawable.frame_5, R.drawable.frame_6};

        for(int i=0;i<imageId.length;i++)
        {
            OnBoardItemObject item=new OnBoardItemObject();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);
        }
    }

    // setup the
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.non_selected_item_dot));

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.selected_item_dot));
    }

}


