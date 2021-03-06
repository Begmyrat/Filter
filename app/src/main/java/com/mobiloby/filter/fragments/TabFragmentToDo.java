package com.mobiloby.filter.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TabFragmentToDo extends Fragment {

    View view;
    MainActivity activity;
    JSONParser jsonParser;
    JSONObject jsonObject;
    SharedPreferences preferences;
    String username="", location="", todo="";
    ImageView i_discover;
    TextView t_discover;
    EditText e_location, e_todo;
    boolean isTodoExist = false;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_to_do, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getTodo();

        t_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = e_location.getText().toString();
                todo = e_todo.getText().toString();
                if(location.length()>0 && todo.length()>0){
                    if(isTodoExist){
                        updateTodo();
                    }
                    else{
                        insertTodo();
                    }
                }
            }
        });

        i_discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = e_location.getText().toString();
                todo = e_todo.getText().toString();
                if(location.length()>0 && todo.length()>0){
                    if(isTodoExist){
                        updateTodo();
                    }
                    else{
                        insertTodo();
                    }
                }
            }
        });

        return view;
    }

    private void prepareMe() {
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique", "");
        t_discover = view.findViewById(R.id.t_discover);
        i_discover = view.findViewById(R.id.i_login);
        e_location = view.findViewById(R.id.e_location);
        e_todo = view.findViewById(R.id.e_todo);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("????leminiz ger??ekle??tiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

    }

    private void getTodo() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/get_todo_by_user.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);

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

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String todo_id = c.getString("todo_id");
                            String user_name = c.getString("user_name");
                            String todo_desc = c.getString("todo_description");
                            String todo_location = c.getString("todo_location");

                            String todo_minutes = c.getString("todo_minutes");
//                            int minutes = Integer.parseInt(todo_minutes);
//                            String hour = "";
//                            String message = "Bu Aktivite i??in son";
//                            if(minutes/60>0){
//                                hour += " "+minutes/60;
//                                minutes -= 60*(minutes/60);
//                                message += hour + " saat";
//                            }
//                            if(minutes>0){
//                                message += " " + minutes + " dakika";
//                            }
                            e_location.setText(todo_location);
                            e_todo.setText(todo_desc);
                            isTodoExist = true;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
//                    makeAlert.uyar??Ver("E-Mobil Saglyk", "Bir hata oldu. L??tfen tekrar deneyiniz.", ActivityCategory3.this, true);

                }

            }
        }.execute(null, null, null);
    }

    private void insertTodo() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("????leminiz ger??ekle??tiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_todo.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("todo_description", todo);
                jsonData.put("todo_location", location);

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

                if (res.equals("1") || res.equals("2") || res.equals("3")) {
//                    getTodo();
                    activity.changeCurrentItem(0);
                }
                else{
                    makeAlert.uyar??Ver("Fltr", "Bir hata oldu. L??tfen tekrar deneyiniz.", activity, true);
                }

            }
        }.execute(null, null, null);
    }

    private void updateTodo() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/update_todo.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("todo_description", todo);
                jsonData.put("todo_location", location);

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
                    activity.changeCurrentItem(0);
                }
                else{
                    makeAlert.uyar??Ver("Fltr", "Bir hata oldu. L??tfen tekrar deneyiniz.", activity, true);
                }

            }
        }.execute(null, null, null);
    }
}