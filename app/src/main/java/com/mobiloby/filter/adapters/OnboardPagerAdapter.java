package com.mobiloby.filter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mobiloby.filter.R;
import com.mobiloby.filter.models.OnBoardItemObject;

import java.util.ArrayList;

public class OnboardPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<OnBoardItemObject> list;

    public OnboardPagerAdapter(Context context, ArrayList<OnBoardItemObject> list) {
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
//        i_image.setImageResource(list.get(position).getImg());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
