package com.mobiloby.filter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
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
import java.util.HashMap;

public class MyWantedListAdapter extends ArrayAdapter<WantedObject> {

    private Activity context;
    private ArrayList<WantedObject> list;
    JSONParser jsonParser;
    JSONObject jsonObject;

    public MyWantedListAdapter(Activity context, ArrayList<WantedObject> list) {
        super(context, R.layout.item_list_wanted, list);

        this.context = context;
        this.list = list;
    }

    static  class ViewHolder
    {
        TextView t_title, t_date, t_similarity;
        ImageView i_delete;
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
            convertView = inflator.inflate(R.layout.item_list_wanted, null);
            viewHolder = new ViewHolder();

            viewHolder.t_title = convertView.findViewById(R.id.t_title);
            viewHolder.t_date = convertView.findViewById(R.id.t_date);
            viewHolder.i_delete = convertView.findViewById(R.id.i_delete);
            viewHolder.r_box = convertView.findViewById(R.id.r_box);
            viewHolder.t_similarity = convertView.findViewById(R.id.t_similarity);
            viewHolder.t_similarity.setVisibility(View.GONE);
            viewHolder.i_delete.setVisibility(View.VISIBLE);

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
            }

            viewHolder.i_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });

        }
        else if(position==list.size()-1){
            viewHolder.r_box.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private void deleteItem(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/delete_wanted_item.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", list.get(position).getWantedID());

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("", ex.getMessage());
                    }
                }
                return String.valueOf(success);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
                    Toast.makeText(context, "Silindi", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
                    list.remove(position);
                    ActivityCategory2.listView.invalidateViews();
                }
                else{
                    Toast.makeText(context, "Error Silinemedi", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }
}

