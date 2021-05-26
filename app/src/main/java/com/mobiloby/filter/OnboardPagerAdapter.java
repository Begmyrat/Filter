package com.mobiloby.filter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class OnboardPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<OnboardItem> list;

    public OnboardPagerAdapter(Context context, ArrayList<OnboardItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
//        String imageUrl = Media.getImageUrl(myObject.getObjectId(), images.get(position).getImageId());
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_screen, container, false);
//        ImageView pagerImage = layout.findViewById(R.id.pagerImage);
//        Media.setImageFromUrl(pagerImage, imageUrl);//call to GlideApp or Picasso to load the image into the ImageView

        TextView t_title = layout.findViewById(R.id.t_title);
        TextView t_description = layout.findViewById(R.id.t_description);
        ImageView i_image = layout.findViewById(R.id.i_imageView);

        t_title.setText(list.get(position).getTitle());
        t_description.setText(list.get(position).getDescription());
        i_image.setImageResource(list.get(position).getImg());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
