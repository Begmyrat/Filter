package com.mobiloby.filter.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobiloby.filter.R;
import com.mobiloby.filter.activities.ActivityChat;
import com.mobiloby.filter.activities.ActivityFriends;
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyFriendListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TabFragmentFriends extends Fragment implements MyFriendListAdapter.ItemClickListener {

    View view;
    MainActivity activity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<UserObject> friendList, friendListAll;
    JSONParser jsonParser;
    JSONObject jsonObject;
    MyFriendListAdapter adapter;
    SharedPreferences preferences;
    String username, userProfileUrl;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_tab_friends, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        getRequests();
    }

    public void initList(){
        try {
            friendList.clear();
            friendList.addAll(friendListAll);
            adapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }

    public void performSearch(String input){
        friendList.clear();
        for(int i=0;i<friendListAll.size();i++){
            if(friendListAll.get(i).getUsername().contains(input) || input.contains(friendListAll.get(i).getUsername())){
                friendList.add(friendListAll.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void prepareMe() {

        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique", "");
        userProfileUrl = preferences.getString("user_profile_url", "");
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        friendList = new ArrayList<>();
        friendListAll = new ArrayList<>();
        adapter = new MyFriendListAdapter(activity, friendList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void getFriends() {

//        progressDialog.show();

//        friendList.clear();
//        friendListAll.clear();

        final String url = "https://mobiloby.com/_filter/get_friends.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);

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
                            String user_id = c.getString("id");
                            String friend_user_name = c.getString("friend_user_name_unique");

                            UserObject o = new UserObject(user_id, friend_user_name, "", "", "", "", "", "", "", "");
                            o.setAvatar_id(c.getString("user_profile_url"));
                            o.setFromWhere(c.getString("from_where"));
                            o.setUser_player_id(c.getString("user_player_id"));
                            o.setFriend(true);
                            friendList.add(o);
                            friendListAll.add(o);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            }
        }.execute(null, null, null);
    }

    private void getRequests() {

        progressDialog.show();

        friendList.clear();
        friendListAll.clear();

        final String url = "https://mobiloby.com/_filter/get_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);

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

//                progressDialog.dismiss();

                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String user_id = c.getString("id");
                            String friend_user_name = c.getString("friend_user_name_unique");

                            UserObject o = new UserObject(user_id, friend_user_name, "", "", "", "", "", "", "", "");
                            o.setAvatar_id(c.getString("user_profile_url"));
                            o.setFromWhere(c.getString("from_where"));
                            o.setFriend(false);
                            friendList.add(o);
                            friendListAll.add(o);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
                getFriends();
            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {
        if(friendList.get(position).getFriend()){
            Intent intent = new Intent(activity, ActivityChat.class);
            intent.putExtra("username", username);
            intent.putExtra("username_friend", friendList.get(position).getUsername());
            intent.putExtra("user_profile_url", userProfileUrl);
            intent.putExtra("user_player_id_other", friendList.get(position).getUser_player_id());
            intent.putExtra("user_profile_url_other", friendList.get(position).getAvatar_id());
            intent.putExtra("user_profile_url", friendList.get(position).getAvatar_id());
            startActivity(intent);
        }
        else{
            // profile gidecek
        }
    }
}