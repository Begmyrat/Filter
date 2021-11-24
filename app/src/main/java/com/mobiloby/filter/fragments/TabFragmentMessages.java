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
import com.mobiloby.filter.activities.MainActivity;
import com.mobiloby.filter.adapters.MyFriendListAdapter;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TabFragmentMessages extends Fragment implements MyFriendListAdapter.ItemClickListener{

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
    int lastMessageOwner;
    HashSet<String> hashSet;
    HashMap<String, String> hashMap;
    HashMap<String, String> hashMapDate;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab_messages, container, false);
        activity = (MainActivity) getActivity();

        prepareMe();

        getFriendMessages();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getFriendMessages();

    }

    public void initList(){
        friendList.clear();
        friendList.addAll(friendListAll);
        adapter.notifyDataSetChanged();
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
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        username = preferences.getString("username_unique", "");
        userProfileUrl = preferences.getString("avatar_id", "");
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        friendList = new ArrayList<>();
        friendListAll = new ArrayList<>();
        adapter = new MyFriendListAdapter(activity, friendList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        hashSet = new HashSet<>();
        hashMap = new HashMap<>();
        hashMapDate = new HashMap<>();
    }

    private void getFriendMessages() {
//        progressDialog.show();

        friendList.clear();
        friendListAll.clear();
        hashMap.clear();
        hashMapDate.clear();
        hashSet.clear();

        final String url = "https://mobiloby.com/_filter/get_friends_message.php";

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
                            String friend_user_name = c.getString("friend_user_name_unique");
                            String user_profile_url = c.getString("user_profile_url");
                            String user_player_id = c.getString("user_player_id");

                            String last_message_left = c.getString("last_message_left");
                            String last_message_id_left = c.getString("last_message_id_left");
                            String last_message_right = c.getString("last_message_right");
                            String last_message_id_right = c.getString("last_message_id_right");

                            UserObject o = new UserObject("", friend_user_name, "", "", "", "", "", "", "", "");
                            o.setAvatar_id(user_profile_url);
                            o.setUser_player_id(user_player_id);

                            String lastMessage = preferences.getString(username+"-"+friend_user_name, "");
                            o.setLastMessage(lastMessage);
                            o.setFriend(true);
                            if(!hashSet.contains(friend_user_name)){
                                friendList.add(o);
                                friendListAll.add(o);
                            }

                            hashSet.add(friend_user_name);
                            if(!hashMap.containsKey(friend_user_name)){
                                if(last_message_left.equals("")) {
                                    hashMap.put(friend_user_name, last_message_right);
                                    hashMapDate.put(friend_user_name, last_message_id_right);
                                }
                                else {
                                    hashMap.put(friend_user_name, last_message_left);
                                    hashMapDate.put(friend_user_name, last_message_id_left);
                                }
                            }
                            else{
                                String oldMessageDate = hashMapDate.get(friend_user_name);
                                if(last_message_id_left.equals("") && oldMessageDate.compareTo(last_message_id_right)<0){
                                    hashMap.put(friend_user_name, last_message_right);
                                    hashMapDate.put(friend_user_name, last_message_id_right);
                                }
                                else if(last_message_id_right.equals("") && oldMessageDate.compareTo(last_message_id_left)<0){
                                    hashMap.put(friend_user_name, last_message_left);
                                    hashMapDate.put(friend_user_name, last_message_id_left);
                                }
                            }
                        }

                        for(int i=0;i<friendList.size();i++){
                            String lastMessage = preferences.getString(username+"-"+friendList.get(i).getUsername(), "");
                            if(!lastMessage.equals(hashMap.get(friendList.get(i).getUsername()))) {
                                friendList.get(i).setLastMessage(hashMap.get(friendList.get(i).getUsername()));
                                friendList.get(i).setNewMessage(true);

                                friendListAll.get(i).setLastMessage(hashMap.get(friendListAll.get(i).getUsername()));
                                friendListAll.get(i).setNewMessage(true);
                            }
                            else{
                                friendList.get(i).setLastMessage(lastMessage);
                                friendList.get(i).setNewMessage(false);

                                friendListAll.get(i).setLastMessage(lastMessage);
                                friendListAll.get(i).setNewMessage(false);
                            }
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
//                        Toast.makeText(activity, "error jiimFriend", Toast.LENGTH_SHORT).show();
                    }

                }
                else{

                }
            }
        }.execute(null, null, null);
    }

    @Override
    public void onItemClick(View view, int position) {



        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(username+"-"+friendList.get(position).getUsername(), hashMap.get(friendList.get(position).getUsername()));
        editor.commit();

        Intent intent = new Intent(activity, ActivityChat.class);
        intent.putExtra("username", username);
        intent.putExtra("username_friend", friendList.get(position).getUsername());
        intent.putExtra("user_profile_url", userProfileUrl);
        intent.putExtra("user_player_id_other", friendList.get(position).getUser_player_id());
        intent.putExtra("user_profile_url_other", friendList.get(position).getAvatar_id());
        intent.putExtra("user_profile_url", friendList.get(position).getAvatar_id());
        startActivity(intent);
    }
}