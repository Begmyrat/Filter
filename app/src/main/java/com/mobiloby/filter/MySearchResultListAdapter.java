package com.mobiloby.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MySearchResultListAdapter extends ArrayAdapter<UserObject> {

    private Activity context;
    private ArrayList<UserObject> list;
    int selected_pos;

    public MySearchResultListAdapter(Activity context, ArrayList<UserObject> list) {
        super(context, R.layout.item_list_search_result, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView textview_title;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.item_list_search_result, null);
            viewHolder = new ViewHolder();

            viewHolder.textview_title = convertView.findViewById(R.id.t_title_big);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null && list.size() > 0)
        {
            final UserObject konu = list.get(position);

//            if(position%11==0){
//                viewHolder.layout.getBackground().setTint(context.getResources().getColor(R.color.colorLight1));
//            }

            if(konu!=null){
                viewHolder.textview_title.setText(konu.getUsername());
            }

        }
        return convertView;
    }
}

