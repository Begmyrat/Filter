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

import java.util.ArrayList;

public class MyChatListAdapter extends ArrayAdapter<ChatObject> {

    private Activity context;
    private ArrayList<ChatObject> list;
    String username = "";

    public MyChatListAdapter(Activity context, ArrayList<ChatObject> list, String username) {
        super(context, R.layout.item_list_chat, list);

        this.context = context;
        this.list = list;
        this.username = username;
    }

    static  class ViewHolder
    {
        TextView t_chatLeft, t_chatRight, t_dateLeft, t_dateRight;
        RelativeLayout r_left, r_right;
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
            convertView = inflator.inflate(R.layout.item_list_chat, null);
            viewHolder = new ViewHolder();

            viewHolder.r_left = convertView.findViewById(R.id.r_left);
            viewHolder.r_right = convertView.findViewById(R.id.r_right);
            viewHolder.t_chatLeft = convertView.findViewById(R.id.t_chatLeft);
            viewHolder.t_dateLeft = convertView.findViewById(R.id.t_dateLeft);
            viewHolder.t_chatRight = convertView.findViewById(R.id.t_chatRight);
            viewHolder.t_dateRight = convertView.findViewById(R.id.t_dateRight);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null)
        {
            final ChatObject chat = list.get(position);

            if(chat!=null){
                if(chat.kimdenKime.equals(username)){
                    viewHolder.r_left.setVisibility(View.GONE);
                    viewHolder.r_right.setVisibility(View.VISIBLE);
                    viewHolder.t_chatRight.setText(chat.getMessage());
                    viewHolder.t_dateRight.setText(chat.getDate());
                }
                else{
                    viewHolder.r_left.setVisibility(View.VISIBLE);
                    viewHolder.r_right.setVisibility(View.GONE);
                    viewHolder.t_chatLeft.setText(chat.getMessage());
                    viewHolder.t_dateLeft.setText(chat.getDate());
                }
            }


        }

        return convertView;
    }

}

