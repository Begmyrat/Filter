package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;
import com.mobiloby.filter.models.WantedObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyWantedSimilarListAdapter extends ArrayAdapter<WantedObject> {

    private Activity context;
    private ArrayList<WantedObject> list;
    JSONParser jsonParser;
    JSONObject jsonObject;

    public MyWantedSimilarListAdapter(Activity context, ArrayList<WantedObject> list) {
        super(context, R.layout.item_list_wanted_similar, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView t_title, t_date, t_similarity;
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
            convertView = inflator.inflate(R.layout.item_list_wanted_similar, null);
            viewHolder = new ViewHolder();

            viewHolder.t_title = convertView.findViewById(R.id.t_title);
            viewHolder.t_date = convertView.findViewById(R.id.t_date);
            viewHolder.r_box = convertView.findViewById(R.id.r_box);
            viewHolder.t_similarity = convertView.findViewById(R.id.t_titleSimilarity);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null && list.size() > 0 && position<list.size()-1)
        {
            final WantedObject konu = list.get(position);

            if(konu!=null){
                viewHolder.r_box.setVisibility(View.VISIBLE);
                viewHolder.t_title.setText(konu.getWantedTitle());
                viewHolder.t_date.setText(konu.getWantedDate());
                viewHolder.t_similarity.setText("Uyuşma oranı %"+konu.getSimilarity() + " Pro: " + konu.getDoluluk());
            }

        }
        else if(position==list.size()-1){
            viewHolder.r_box.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}

