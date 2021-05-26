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

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyCurrentSearchListAdapter extends ArrayAdapter<SocialObject> {

    private Activity context;
    private ArrayList<SocialObject> list;
    JSONParser jsonParser;
    JSONObject jsonObject;

    public MyCurrentSearchListAdapter(Activity context, ArrayList<SocialObject> list) {
        super(context, R.layout.item_list_current_search, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView t_i, t_f, t_s, t_t;
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
            convertView = inflator.inflate(R.layout.item_list_current_search, null);
            viewHolder = new ViewHolder();

            viewHolder.t_i = convertView.findViewById(R.id.t_instagram);
            viewHolder.t_f = convertView.findViewById(R.id.t_facebook);
            viewHolder.t_s = convertView.findViewById(R.id.t_snapchat);
            viewHolder.t_t = convertView.findViewById(R.id.t_tiktok);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null)
        {
            final SocialObject konu = list.get(position);

            if(konu!=null){

            }
        }

        return convertView;
    }

}

