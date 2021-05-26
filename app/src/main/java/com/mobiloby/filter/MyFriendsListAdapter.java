package com.mobiloby.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyFriendsListAdapter extends ArrayAdapter<UserObject> {

    private Activity context;
    private ArrayList<UserObject> list;
    int[] avatars = {R.drawable.man, R.drawable.man_old, R.drawable.girl, R.drawable.boy};

    public MyFriendsListAdapter(Activity context, ArrayList<UserObject> list) {
        super(context, R.layout.item_list_friends, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView t_title, t_date, t_similarity;
        ImageView i_avatar, i_image_from;
        RelativeLayout r_box;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.item_list_friends, null);
            viewHolder = new ViewHolder();

            viewHolder.t_title = convertView.findViewById(R.id.t_title);
            viewHolder.i_avatar = convertView.findViewById(R.id.i_icon);
            viewHolder.i_image_from = convertView.findViewById(R.id.i_image_from);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null)
        {
            final UserObject konu = list.get(position);

            if(konu!=null){
                viewHolder.t_title.setText(konu.getUsername());
                try {
                    int avatar_id = Integer.parseInt(list.get(position).getAvatar_id());
                    viewHolder.i_avatar.setImageResource(avatars[avatar_id%4]);
                }
                catch (Exception e){

                }

                if(konu.getFromWhere().equals("1")){
                    viewHolder.i_image_from.setImageResource(R.drawable.ic_friends_colorful);
                }
                else if(konu.getFromWhere().equals("2")){
                    viewHolder.i_image_from.setImageResource(R.drawable.ic_balloon_colorful);
                }
                else if(konu.getFromWhere().equals("3")){
                    viewHolder.i_image_from.setImageResource(R.drawable.ic_dice_colorful);
                }
                else{
                    viewHolder.i_image_from.setImageResource(R.drawable.ic_spy_colorful);
                }

            }


        }
        return convertView;
    }

}

