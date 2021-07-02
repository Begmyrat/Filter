package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobiloby.filter.models.InfoObject;
import com.mobiloby.filter.R;

import java.util.ArrayList;

public class MyInfoListAdapter extends ArrayAdapter<InfoObject> {

    private Activity context;
    private ArrayList<InfoObject> list;

    public MyInfoListAdapter(Activity context, ArrayList<InfoObject> list) {
        super(context, R.layout.item_list_categories, list);

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
            convertView = inflator.inflate(R.layout.item_list_categories, null);
            viewHolder = new ViewHolder();

            viewHolder.textview_title = convertView.findViewById(R.id.t_title);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null && list.size() > 0)
        {
            final InfoObject konu = list.get(position);

//            if(position%11==0){
//                viewHolder.layout.getBackground().setTint(context.getResources().getColor(R.color.colorLight1));
//            }

            if(konu!=null){
                viewHolder.textview_title.setText(konu.getInfo());
            }

        }
        return convertView;
    }
}

