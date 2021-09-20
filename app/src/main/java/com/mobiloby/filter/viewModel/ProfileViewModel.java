package com.mobiloby.filter.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;
import com.mobiloby.filter.models.UserObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    MutableLiveData<List<UserObject>> recommendedUsers = new MutableLiveData<List<UserObject>>();
    MutableLiveData<String> profileURL = new MutableLiveData<>();
    MutableLiveData<Integer> friendNumber = new MutableLiveData<>();
    MutableLiveData<Integer> requestNumber = new MutableLiveData<>();
    MutableLiveData<UserObject> userData = new MutableLiveData<>();
    MutableLiveData<Boolean> userDataError = new MutableLiveData<>();
    MutableLiveData<Boolean> recommendedUsersError = new MutableLiveData<>();
    MutableLiveData<Boolean> recommendedUsersLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getUserDataError() {
        return userDataError;
    }

    public void setUserDataError(MutableLiveData<Boolean> userDataError) {
        this.userDataError = userDataError;
    }

    public MutableLiveData<Boolean> getRecommendedUsersError() {
        return recommendedUsersError;
    }

    public void setRecommendedUsersError(MutableLiveData<Boolean> recommendedUsersError) {
        this.recommendedUsersError = recommendedUsersError;
    }

    public MutableLiveData<Boolean> getRecommendedUsersLoading() {
        return recommendedUsersLoading;
    }

    public void setRecommendedUsersLoading(MutableLiveData<Boolean> recommendedUsersLoading) {
        this.recommendedUsersLoading = recommendedUsersLoading;
    }

    public MutableLiveData<Boolean> getUserDataLoading() {
        return userDataLoading;
    }

    public void setUserDataLoading(MutableLiveData<Boolean> userDataLoading) {
        this.userDataLoading = userDataLoading;
    }

    MutableLiveData<Boolean> userDataLoading = new MutableLiveData<>();

    Context context;
    Activity activity;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username;

    public void init(Activity activity, Context context, String username){
        this.activity = activity;
        this.context = context;
        this.username = username;
    }

    public void getUser() {

        userDataLoading.postValue(true);

        final String url = "https://mobiloby.com/_filter/get_user.php";

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

                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String userPlayerID = c.getString("user_player_id");
                            String userProfileURL = c.getString("user_profile_url");
                            String profilDoluluk = c.getString("user_profil_doluluk");
                            String countArkadas = c.getString("count_friend");
                            String countIstek = c.getString("count_istek");
                            UserObject u = new UserObject();
                            u.setUsername(username);
                            u.setUser_player_id(userPlayerID);
                            u.setAvatar_id(userProfileURL);
                            u.setUserProfilDoluluk(""+Integer.parseInt(profilDoluluk)*100/7);
                            u.setFriendCount(countArkadas);
                            u.setRequestCount(countIstek);
                            userData.postValue(u);
                        }
//                        userDataLoading.postValue(false);
                        userDataError.postValue(false);

                        getUserBySimilarity();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        userDataError.postValue(true);
//                        userDataLoading.postValue(false);
                    }
                }
                else{
                    userDataError.postValue(true);
//                    userDataLoading.postValue(false);
                }

            }
        }.execute(null, null, null);
    }

    public void getUserBySimilarity() {

        recommendedUsersLoading.postValue(true);

        final String url = "https://mobiloby.com/_filter/get_users_by_similarity.php";

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

                if (res.equals("1")) {

                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");
                        ArrayList<UserObject> userObjects = new ArrayList<>();

                        for(int i=0;i<pro.length();i++){

                            JSONObject c = pro.getJSONObject(i);
                            String user_id = c.getString("user_id");
                            String user_name = c.getString("user_name_unique");
                            String user_device_id = c.getString("user_player_id");
                            String similarity = c.getString("user_similarity");
                            String profil_gizlilik = c.getString("profil_gizlilik");
                            String user_profile_url = c.getString("user_profile_url");

                            UserObject o = new UserObject();
                            o.setId(user_id);
                            o.setUsername(user_name);
                            o.setUsername_visible(user_name);
                            double d  = Math.round((Double.parseDouble(similarity)*100/7));
                            o.setSimilarity(""+(int)d);
                            o.setProfil_gizlilik(profil_gizlilik);
                            o.setAvatar_id(user_profile_url);
                            userObjects.add(o);
                        }

                        userObjects.sort((schedule1, schedule2)->{
                            int returnValue = 0;
                            if(Double.parseDouble(schedule1.getSimilarity()) > Double.parseDouble(schedule2.getSimilarity()))	return -1;
                            else if(Double.parseDouble(schedule1.getSimilarity()) < Double.parseDouble(schedule2.getSimilarity()))	return 1;
                            return returnValue;
                        });
                        userObjects.add(new UserObject());

                        recommendedUsers.postValue(userObjects);
                        recommendedUsersLoading.postValue(false);
                        recommendedUsersError.postValue(false);
                        userDataLoading.postValue(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        recommendedUsersLoading.postValue(false);
                        recommendedUsersError.postValue(true);
                        userDataLoading.postValue(false);
                    }
                }
                else{
                    recommendedUsersLoading.postValue(false);
                    recommendedUsersError.postValue(true);
                    userDataLoading.postValue(false);
                }

            }
        }.execute(null, null, null);
    }

    public void updateToken(String token) {

        final String url = "https://mobiloby.com/_filter/update_token.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("user_player_id", token);

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

                if (res.equals("1")) {
                    getUser();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz. TOKEN", activity, true);
                }

            }
        }.execute(null, null, null);
    }

    public MutableLiveData<List<UserObject>> getRecommendedUsers() {
        return recommendedUsers;
    }

    public void setRecommendedUsers(MutableLiveData<List<UserObject>> recommendedUsers) {
        this.recommendedUsers = recommendedUsers;
    }

    public MutableLiveData<UserObject> getUserData() {
        return userData;
    }

    public void setUserData(MutableLiveData<UserObject> userData) {
        this.userData = userData;
    }

    public MutableLiveData<String> getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(MutableLiveData<String> profileURL) {
        this.profileURL = profileURL;
    }

    public MutableLiveData<Integer> getFriendNumber() {
        return friendNumber;
    }

    public void setFriendNumber(MutableLiveData<Integer> friendNumber) {
        this.friendNumber = friendNumber;
    }

    public MutableLiveData<Integer> getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(MutableLiveData<Integer> requestNumber) {
        this.requestNumber = requestNumber;
    }
}
