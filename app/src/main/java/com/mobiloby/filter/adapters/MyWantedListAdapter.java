package com.mobiloby.filter.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityCategory2;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.models.WantedObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MyWantedListAdapter extends RecyclerView.Adapter<MyWantedListAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<WantedObject> list;
    int selected_pos;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    JSONParser jsonParser;
    JSONObject jsonObject;

    // data is passed into the constructor
    public MyWantedListAdapter(Activity context, ArrayList<WantedObject> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list_wanted, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final WantedObject konu = list.get(position);

        if(position< list.size()-1) {
            holder.r_box.setVisibility(View.VISIBLE);
            holder.t_title.setText(konu.getWantedTitle());
            holder.t_date.setText(konu.getWantedDate());
        }
        else{
            holder.r_box.setVisibility(View.INVISIBLE);
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t_title, t_date, t_similarity;
        ImageView i_delete;
        RelativeLayout r_box;

        ViewHolder(View itemView) {
            super(itemView);

            t_title = itemView.findViewById(R.id.t_title);
            t_date = itemView.findViewById(R.id.t_date);
            i_delete = itemView.findViewById(R.id.i_delete);
            r_box = itemView.findViewById(R.id.r_box);
            t_similarity = itemView.findViewById(R.id.t_similarity);
            t_similarity.setVisibility(View.GONE);
            i_delete.setVisibility(View.VISIBLE);

            i_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrettyDialog prettyDialog = new PrettyDialog(context);
                    prettyDialog
                            .setTitle("Filter")
                            .setMessage("Bu aramayı silmek istiyor musunuz?")
                            .setIcon(R.drawable.ic_f_char)
                            .addButton(
                                    "Evet",
                                    R.color.colorWhite,
                                    R.color.colorPalet2,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            deleteItem(getAdapterPosition());
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .addButton(
                                    "Hayır",
                                    R.color.colorWhite,
                                    R.color.colorDarkGray,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .show();
                }
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public WantedObject getItem(int id) {
        return list.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void deleteItem(final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/delete_wanted_item.php";

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
                    notifyDataSetChanged();
                }
                else{
                    Toast.makeText(context, "Error Silinemedi", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }
}

