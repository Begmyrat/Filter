package com.mobiloby.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyTodoResultListAdapter extends ArrayAdapter<TodoObject> {

    private Activity context;
    private ArrayList<TodoObject> list;
    int selected_pos;

    public MyTodoResultListAdapter(Activity context, ArrayList<TodoObject> list) {
        super(context, R.layout.item_list_search_result, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView textview_title, t_address, t_time;
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
            viewHolder.t_address = convertView.findViewById(R.id.t_address);
            viewHolder.t_time = convertView.findViewById(R.id.t_time);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null && list.size() > 0)
        {
            final TodoObject konu = list.get(position);

//            if(position%11==0){
//                viewHolder.layout.getBackground().setTint(context.getResources().getColor(R.color.colorLight1));
//            }

            if(konu!=null){
                viewHolder.textview_title.setText(konu.getUsername());
                viewHolder.t_address.setText("#"+konu.getTodoDescription());

                String minutes = konu.getTime();
                int m = Integer.parseInt(minutes);
                int h = m/60;
                m -= h*60;

                viewHolder.t_time.setText(""+h+" s, " + m + " dk");
            }

        }
        return convertView;
    }
}

