package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobiloby.filter.R;
import com.mobiloby.filter.ShowToastMessage;
import com.mobiloby.filter.databinding.ActivityProfileEditBinding;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityProfileEdit2 extends AppCompatActivity implements View.OnClickListener {

    ActivityProfileEditBinding binding;
    View view;
    ArrayList<Boolean> isDropDownList;
    ArrayList<String> infoList;
    ArrayList<String> titleList;
    ArrayList<String> optionsList;
    int clickedItemIndex = -1, isHidden=0;
    Boolean isSelf = false, isFriend=false;
    SharedPreferences preferences;
    String username="", userProfileUrl="", usernameSelf="", friendStatus="-1", playerId="";
    JSONParser jsonParser;
    JSONObject jsonObject;
    ProgressDialog progressDialog;
    Bundle extras;
    Activity activity;
    Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        activity = this;

        prepareMe();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usernameSelf = preferences.getString("username_unique","");
        if(usernameSelf.equals(username)){
            isSelf = true;
            userProfileUrl = preferences.getString("avatar_id","");
            binding.tProfilHidden.setVisibility(View.VISIBLE);
        }
        else{
            isSelf = false;
            binding.tProfilHidden.setVisibility(View.GONE);
//            binding.tChangeYourAvatar.setVisibility(View.INVISIBLE);
        }

        binding.tUsername.setText(username);
        Glide
                .with(this)
                .load("https:mobiloby.com/_filter/assets/profile/" + userProfileUrl)
                .centerCrop()
                .placeholder(R.drawable.filtryenilogo)
                .into(binding.iAvatar);

        getInfo();
    }

    private void setListeners() {
        binding.iCross.setOnClickListener(this);
        binding.tProfilHidden.setOnClickListener(this);
        binding.tChangeYourAvatar.setOnClickListener(this);
        binding.tNameSurname.setOnClickListener(this);
        binding.tDateOfBirth.setOnClickListener(this);
        binding.tFrom.setOnClickListener(this);
        binding.tHeight.setOnClickListener(this);
        binding.tWeight.setOnClickListener(this);
        binding.tTatto.setOnClickListener(this);
        binding.tPrimarySchool.setOnClickListener(this);
        binding.tMiddleSchool.setOnClickListener(this);
        binding.tHighSchool.setOnClickListener(this);
        binding.tUniversity.setOnClickListener(this);
        binding.tYourJob.setOnClickListener(this);
        binding.tYourDreamJob.setOnClickListener(this);
        binding.tZodiacSign.setOnClickListener(this);
        binding.tColor.setOnClickListener(this);
        binding.tNumber.setOnClickListener(this);
        binding.tFavoriteGenre.setOnClickListener(this);
        binding.tFavoriteArtist.setOnClickListener(this);
        binding.tHobbies.setOnClickListener(this);
        binding.tTalents.setOnClickListener(this);
        binding.tGenre.setOnClickListener(this);
        binding.tFavoriteBook.setOnClickListener(this);
        binding.tFavoriteMeal.setOnClickListener(this);
        binding.tFavoriteFood.setOnClickListener(this);
        binding.tFavoriteMovieGenre.setOnClickListener(this);
        binding.tFavoriteMovie.setOnClickListener(this);
        binding.tFavoriteTheatre.setOnClickListener(this);

    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white

        binding.scrollview.animate().setDuration(1000).alpha(1);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
            playerId = extras.getString("player_id");
            userProfileUrl = extras.getString("userProfileUrl");
        }

        isDropDownList = new ArrayList<>();
        infoList = new ArrayList<>();
        titleList = new ArrayList<>();
        optionsList = new ArrayList<>();
        setDropDownList();
        setOptions();
        setTitles();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fltr");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

    }

    private void setTitles() {
        titleList.add("İsim/Soyisim");
        titleList.add("Doğum Yılı");
        titleList.add("Şehir");
        titleList.add("Boy");
        titleList.add("Kilo");
        titleList.add("Dövme");
        titleList.add("İlkokul");
        titleList.add("Ortaokul");
        titleList.add("Lise");
        titleList.add("Üniversite");
        titleList.add("Meslek");
        titleList.add("Hayalinizdeki Meslek");
        titleList.add("En Sevdiğiniz Renk");
        titleList.add("Şanslı Sayınız");
        titleList.add("Burcunuz");
        titleList.add("Müzik Tarzınız");
        titleList.add("En Sevdiğiniz Sanatçı");
        titleList.add("Hobi");
        titleList.add("Yetenek");
        titleList.add("Sevdiğiniz kitap türü");
        titleList.add("Okumaya doyamadığınız kitap");
        titleList.add("Sevdiğiniz sinema türü");
        titleList.add("İzlemeye doyamadığınız film");
        titleList.add("En sevdiğiniz tiyatro oyunu");
        titleList.add("En sevdiğiniz öğün");
        titleList.add("Yemeye doyamadığınız yemek");
    }

    private void setOptions() {

        optionsList.add("");       // name Surname
        optionsList.add("...-1980,1981-1985,1986-1990,1991-1995,1996-2000,2001-2005,2005-..."); // date of birth
        optionsList.add("Ankara,İstanbul,İzmir");   // from
        optionsList.add("...-1.50,1.51-1.60,1.61-1.70,1.71-1.80,1.81-1.90,1.91-...");       // height
        optionsList.add("...-45,46-50,51-55,56-60,61-65,66-70,71-75,76-80,81-85,86-90,91-95,96-...");   // weight
        optionsList.add("Var,Yok"); // tatto
        optionsList.add("");    // primary school
        optionsList.add("");    // middle school
        optionsList.add("");    // high school
        optionsList.add("");    // university
        optionsList.add("Mühendis,Doktor,Sanatçı");     // job
        optionsList.add("");    // dream job
        optionsList.add("Mor,Kırmızı,Sarı,Siyah");  // fav color
        optionsList.add("0,1,2,3,4,5,6,7,8,9");   // fav number
        optionsList.add("İkizler,Boğa,Aslan");  // zodiac sign
        optionsList.add("Jazz,Rock,Klasik,Blues,Elektronik,Halk Müziği,Arabesk,Türk Pop,Yabancı Pop,Punk,Podcast,RnB");   // fav music genre
        optionsList.add("");    // fav artist
        optionsList.add("Yemek Yapmak,Doğa Yürüyüşü,Resim Heykel Sanat,Koşmak,Dans Etmek,Yoga,Okumak,Video Oyunları,Bahçe İşleri,Tahta İşlemeciliği,Spor,Seyahat Etmek,Balık Tutmak,Fotoğrafçılık,Günlük Tutmak,Lego Yapmak,Oyuncak,Koleksiyon,Enstrüman,Tasarım Yapmak,Yüzmek,Hayal Kurmak,Amatör Radyoculuk,Kumar Oynamak,Örgü Örmek,Yabancı Dil,Araba,At Binmek,DJ,Şarkı Söylemek,Müzik");    // hobby
        optionsList.add("");    // talents
        optionsList.add("Dünya Klasikleri,Roman,Hikaye,Şiir,Felsefe Psikoloji,Korku Gerilim,Polisiye Macera,Biyografi,Bilim Kurgu,Aşk,Araştırma İnceleme,Dergi,Teknoloji,Moda,Kültür Sanat,Türk Klasikleri,Sosyoloji,Deneme,Fantastik,Kişisel Gelişim,Efsane,Mitoloji,Yemek,Sağlık");    // fav book genre
        optionsList.add("");    // fav book
        optionsList.add("Aksiyon,Bilim Kurgu,Korku Gerilim,Belgesel,Komedi,Fantastik,Romantik,Müzikal,Western,Dram");    // fav movie genre
        optionsList.add("");    // fav movie
        optionsList.add("");    // fav theatre
        optionsList.add("Kahvaltı,Öğle Yemeği,Akşam Yemeği");    // fav meal genre
        optionsList.add("");    // fav meal

    }

    public void setDropDownList(){
        isDropDownList.add(false);  // nameSurname
        isDropDownList.add(true);   // dateOfBirth
        isDropDownList.add(true);  // from
        isDropDownList.add(true);  // height
        isDropDownList.add(true);  // weight
        isDropDownList.add(true);  // tatto
        isDropDownList.add(false);  // primarySchool
        isDropDownList.add(false);  // middleSchool
        isDropDownList.add(false);  // highSchool
        isDropDownList.add(false);  // university
        isDropDownList.add(true);  // job
        isDropDownList.add(false);  // dreamJob
        isDropDownList.add(true);  // color
        isDropDownList.add(true);  // number
        isDropDownList.add(true);  // zodiac sign
        isDropDownList.add(true);  // FavGenre
        isDropDownList.add(false);  // FavArtist
        isDropDownList.add(true);  // Hobby
        isDropDownList.add(false);  // talent
        isDropDownList.add(true);  // BookGenre
        isDropDownList.add(false);  // favBook
        isDropDownList.add(true);  // favMovieGenre
        isDropDownList.add(false);  // favMovie
        isDropDownList.add(false);  // favTheatre
        isDropDownList.add(true);  // favMealGenre
        isDropDownList.add(false);  // favMeal
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    @Override
    public void onClick(View view) {
        if(isSelf){
            switch (view.getId()){
                case R.id.t_nameSurname:
                    clickedItemIndex = 0;
                    break;
                case R.id.t_dateOfBirth:
                    clickedItemIndex = 1;
                    break;
                case R.id.t_from:
                    clickedItemIndex = 2;
                    break;
                case R.id.t_height:
                    clickedItemIndex = 3;
                    break;
                case R.id.t_weight:
                    clickedItemIndex = 4;
                    break;
                case R.id.t_tatto:
                    clickedItemIndex = 5;
                    break;
                case R.id.t_primarySchool:
                    clickedItemIndex = 6;
                    break;
                case R.id.t_middleSchool:
                    clickedItemIndex = 7;
                    break;
                case R.id.t_highSchool:
                    clickedItemIndex = 8;
                    break;
                case R.id.t_university:
                    clickedItemIndex = 9;
                    break;
                case R.id.t_yourJob:
                    clickedItemIndex = 10;
                    break;
                case R.id.t_yourDreamJob:
                    clickedItemIndex = 11;
                    break;
                case R.id.t_color:
                    clickedItemIndex = 12;
                    break;
                case R.id.t_number:
                    clickedItemIndex = 13;
                    break;
                case R.id.t_zodiacSign:
                    clickedItemIndex = 14;
                    break;
                case R.id.t_favoriteGenre:
                    clickedItemIndex = 15;
                    break;
                case R.id.t_favoriteArtist:
                    clickedItemIndex = 16;
                    break;
                case R.id.t_hobbies:
                    clickedItemIndex = 17;
                    break;
                case R.id.t_talents:
                    clickedItemIndex = 18;
                    break;
                case R.id.t_genre:
                    clickedItemIndex = 19;
                    break;
                case R.id.t_favoriteBook:
                    clickedItemIndex = 20;
                    break;
                case R.id.t_favoriteMovieGenre:
                    clickedItemIndex = 21;
                    break;
                case R.id.t_favoriteMovie:
                    clickedItemIndex = 22;
                    break;
                case R.id.t_favoriteTheatre:
                    clickedItemIndex = 23;
                    break;
                case R.id.t_favoriteMeal:
                    clickedItemIndex = 24;
                    break;
                case R.id.t_favoriteFood:
                    clickedItemIndex = 25;
                    break;
                case R.id.t_changeYourAvatar:
                    clickedItemIndex = 26;
                    break;
                case R.id.i_cross:
                    clickedItemIndex = 27;
                    break;
                case R.id.t_profilHidden:
                    clickedItemIndex = 28;
                    updateProfil();
                    break;
            }

            if(clickedItemIndex==26){
                // avatar change
                Intent intent = new Intent(getApplicationContext(), ActivityAvatar.class);
                startActivity(intent);
            }
            else if(clickedItemIndex==27){
                // close
                super.onBackPressed();
            }
            else if(clickedItemIndex==28){
                // finish
//                finish();
            }
            else{
                Intent intent = new Intent(getApplicationContext(),ActivityProfileEditForm.class);
                intent.putExtra("username", username);
                intent.putExtra("clickedItemIndex", clickedItemIndex);
                intent.putExtra("isDropDown", isDropDownList.get(clickedItemIndex));
                intent.putExtra("title", titleList.get(clickedItemIndex));
                intent.putExtra("option", optionsList.get(clickedItemIndex));
                intent.putExtra("info", infoList.get(clickedItemIndex));
                startActivity(intent);
            }
        }
        else if(view.getId() == R.id.t_changeYourAvatar && friendStatus.equals("-1")){
            sendIstek();
        }
        else if(view.getId() == R.id.t_changeYourAvatar && friendStatus.equals("3")){
            insertFriend();
        }
        else if(view.getId() == R.id.t_changeYourAvatar && friendStatus.equals("1")){

        }
        else if(view.getId() == R.id.i_cross){
            finish();
        }
    }

    private void getInfo() {
        progressDialog.show();
        infoList.clear();
        final String url = "https://mobiloby.com/_filter/get_user_profile.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("username", username);
                jsonData.put("username_self", usernameSelf);

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
                        JSONObject c;
                        for (int i = 0; i < pro.length(); i++) {
                            c = pro.getJSONObject(i);

                            friendStatus = c.getString("is_friend");
                            if(!usernameSelf.equals(username)){
                                if(friendStatus.equals("1")){
                                    binding.tChangeYourAvatar.setText("Arkadaşsınız");
                                    isFriend = true;
                                }
                                else if(friendStatus.equals("3")){
                                    binding.tChangeYourAvatar.setText("İsteği kabul et");
                                }
                                else if(friendStatus.equals("4")){
                                    binding.tChangeYourAvatar.setText("Beklemede");
                                }
                                else if(friendStatus.equals("-1")){
                                    binding.tChangeYourAvatar.setText("İstek gönder");
                                    isFriend = false;
                                }
                            }

                            infoList.add(c.getString("adsoyad"));
                            infoList.add(c.getString("dogum"));
                            infoList.add(c.getString("sehir"));
                            infoList.add(c.getString("boy"));
                            infoList.add(c.getString("kilo"));
                            infoList.add(c.getString("dovme"));
                            infoList.add(c.getString("ilkokul"));
                            infoList.add(c.getString("ortaokul"));
                            infoList.add(c.getString("lise"));
                            infoList.add(c.getString("universite"));
                            infoList.add(c.getString("meslek"));
                            infoList.add(c.getString("hayal_meslek"));
                            infoList.add(c.getString("renk"));
                            infoList.add(c.getString("sayi"));
                            infoList.add(c.getString("burc"));
                            infoList.add(c.getString("muzik"));
                            infoList.add(c.getString("sanatci"));
                            infoList.add(c.getString("hobi"));
                            infoList.add(c.getString("yetenek"));
                            infoList.add(c.getString("kitap_tur"));
                            infoList.add(c.getString("kitap"));
                            infoList.add(c.getString("film_tur"));
                            infoList.add(c.getString("film"));
                            infoList.add(c.getString("tiyatro"));
                            infoList.add(c.getString("yemek_ogun"));
                            infoList.add(c.getString("yemek"));

                            if((""+c.getString("status")).equals("1")){
                                isHidden = 1;
                                binding.tProfilHidden.setText("Profilimi Gizle");
                            }
                            else{
                                isHidden = 0;
                                binding.tProfilHidden.setText("Profilimi Herkese Aç");
                            }

                            binding.tPercentage.setText("%"+c.getString("percent"));
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("userProfilDoluluk", c.getString("percent"));
                            editor.commit();

                            if(username.equals(usernameSelf)){
                                binding.tFillYourProfile.setText("En iyi eşleşme için profilini doldur.");
                                binding.tContinue.setVisibility(View.VISIBLE);
                                binding.iContinue.setVisibility(View.VISIBLE);
                                setDataToUI();
                            }
                            else if(!username.equals(usernameSelf) && isHidden==0 && !friendStatus.equals("1")){
                                //t_fillYourProfile
                                binding.tFillYourProfile.setText("Bu Profil Gizlidir");
                            }
                            else{
                                binding.tFillYourProfile.setText("Profil Bilgileri");
                                setDataToUI();
                            }
                        }
                    }catch (Exception e){
                    }

                }
                else{
                    ShowToastMessage.show(activity, "Bir hata oldu. Tekrar deneyiniz");
                }

            }
        }.execute(null, null, null);
    }

    private void updateProfil() {
        progressDialog.show();
        infoList.clear();
        final String url = "https://mobiloby.com/_filter/update_profile_hidden.php";

        isHidden = (isHidden+1)%2;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", usernameSelf);
                jsonData.put("status", ""+isHidden);

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

                    if(isHidden == 0){
                        binding.tProfilHidden.setText("Profilimi Herkese Aç");
                    }
                    else{
                        binding.tProfilHidden.setText("Profilimi Gizle");
                    }

                }
                else{
                    ShowToastMessage.show(activity, "Bir hata oldu. Tekrar deneyiniz");
                }

            }
        }.execute(null, null, null);
    }

    private void sendIstek() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", usernameSelf);
                jsonData.put("friend_user_name_unique", username);
                jsonData.put("from_where", "1");

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
                    ShowToastMessage.show(activity, "İstek gönderildi.");
                    getInfo();
                    pushNotification(""+usernameSelf+" size istek gönderdi.");
                }
                else{
                    makeAlert.uyarıVer("Fltr", "Bir hata oldu. Lütfen tekrar deneyiniz.", getApplicationContext(), true);
                }

            }
        }.execute(null, null, null);
    }

    private void insertFriend() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/insert_friend_direk.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", usernameSelf);
                jsonData.put("friend_user_name_unique", username);
                jsonData.put("from_where", "4");

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
                    ShowToastMessage.show(activity, "Arkadaş eklendi");
                    pushNotification(""+usernameSelf+" sizi arkadaş olarak ekledi.");
                    getInfo();
                }
                else{
                    ShowToastMessage.show(activity, "Arkadaş eklemede bir hata oldu. Tekrar deneyiniz.");
                }

            }
        }.execute(null, null, null);
    }

    private void deleteUser() {

        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/delete_user.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", usernameSelf);

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
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.putString("username_unique", "");
                    editor.putString("user_password", "");
                    editor.putString("user_profile_url", "");
                    editor.commit();

                    Intent intent = new Intent(ActivityProfileEdit2.this, ActivityLogin1.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    ShowToastMessage.show(activity, "Hesap silmede bir hata oldu. Tekrar deneyiniz.");
                }

            }
        }.execute(null, null, null);
    }

    private void pushNotification(String message) {

        progressDialog.show();
        System.out.println("PLAYERID: " + playerId);

        final String url = "https://mobiloby.com/_filter/bildirim_gonder_deneme.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("friend_token", ""+playerId);
                jsonData.put("message", message);
                jsonData.put("username", username);

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

                }
                else{
                    ShowToastMessage.show(activity, "Bildirim gonderilemedi");
                }

            }
        }.execute(null, null, null);
    }

    private void setDataToUI() {
        if(infoList.get(0).length()==0)
            binding.tNameSurname.setText(titleList.get(0));
        else
            binding.tNameSurname.setText(infoList.get(0));
        if(infoList.get(1).length()==0)
            binding.tDateOfBirth.setText(titleList.get(1));
        else
            binding.tDateOfBirth.setText(infoList.get(1));
        if(infoList.get(2).length()==0)
            binding.tFrom.setText(titleList.get(2));
        else
            binding.tFrom.setText(infoList.get(2));
        if(infoList.get(3).length()==0)
            binding.tHeight.setText(titleList.get(3));
        else
            binding.tHeight.setText(infoList.get(3));
        if(infoList.get(4).length()==0)
            binding.tWeight.setText(titleList.get(4));
        else
            binding.tWeight.setText(infoList.get(4));
        if(infoList.get(5).length()==0)
            binding.tTatto.setText(titleList.get(5));
        else
            binding.tTatto.setText(infoList.get(5));
        if(infoList.get(6).length()==0)
            binding.tPrimarySchool.setText(titleList.get(6));
        else
            binding.tPrimarySchool.setText(infoList.get(6));
        if(infoList.get(7).length()==0)
            binding.tMiddleSchool.setText(titleList.get(7));
        else
            binding.tMiddleSchool.setText(infoList.get(7));
        if(infoList.get(8).length()==0)
            binding.tHighSchool.setText(titleList.get(8));
        else
            binding.tHighSchool.setText(infoList.get(8));
        if(infoList.get(9).length()==0)
            binding.tUniversity.setText(titleList.get(9));
        else
            binding.tUniversity.setText(infoList.get(9));
        if(infoList.get(10).length()==0)
            binding.tYourJob.setText(titleList.get(10));
        else
            binding.tYourJob.setText(infoList.get(10));
        if(infoList.get(11).length()==0)
            binding.tYourDreamJob.setText(titleList.get(11));
        else
            binding.tYourDreamJob.setText(infoList.get(11));
        if(infoList.get(12).length()==0)
            binding.tColor.setText(titleList.get(12));
        else
            binding.tColor.setText(infoList.get(12));
        if(infoList.get(13).length()==0)
            binding.tNumber.setText(titleList.get(13));
        else
            binding.tNumber.setText(infoList.get(13));
        if(infoList.get(14).length()==0)
            binding.tZodiacSign.setText(titleList.get(14));
        else
            binding.tZodiacSign.setText(infoList.get(14));
        if(infoList.get(15).length()==0)
            binding.tFavoriteGenre.setText(titleList.get(15));
        else
            binding.tFavoriteGenre.setText(infoList.get(15));
        if(infoList.get(16).length()==0)
            binding.tFavoriteArtist.setText(titleList.get(16));
        else
            binding.tFavoriteArtist.setText(infoList.get(16));
        if(infoList.get(17).length()==0)
            binding.tHobbies.setText(titleList.get(17));
        else
            binding.tHobbies.setText(infoList.get(17));
        if(infoList.get(18).length()==0)
            binding.tTalents.setText(titleList.get(18));
        else
            binding.tTalents.setText(infoList.get(18));
        if(infoList.get(19).length()==0)
            binding.tGenre.setText(titleList.get(19));
        else
            binding.tGenre.setText(infoList.get(19));
        if(infoList.get(20).length()==0)
            binding.tFavoriteBook.setText(titleList.get(20));
        else
            binding.tFavoriteBook.setText(infoList.get(20));
        if(infoList.get(21).length()==0)
            binding.tFavoriteMovieGenre.setText(titleList.get(21));
        else
            binding.tFavoriteMovieGenre.setText(infoList.get(21));
        if(infoList.get(22).length()==0)
            binding.tFavoriteMovie.setText(titleList.get(22));
        else
            binding.tFavoriteMovie.setText(infoList.get(22));
        if(infoList.get(23).length()==0)
            binding.tFavoriteTheatre.setText(titleList.get(23));
        else
            binding.tFavoriteTheatre.setText(infoList.get(23));
        if(infoList.get(24).length()==0)
            binding.tFavoriteMeal.setText(titleList.get(24));
        else
            binding.tFavoriteMeal.setText(infoList.get(24));
        if(infoList.get(25).length()==0)
            binding.tFavoriteFood.setText(titleList.get(25));
        else
            binding.tFavoriteFood.setText(infoList.get(25));

    }

    public void clickDeleteAccount(View view) {
        popup();
    }

    private void popup() {
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari_sil, null);

        CardView cardViewEvet = view.findViewById(R.id.cardviewEvet);
        CardView cardViewVazgec = view.findViewById(R.id.cardviewVazgec);

        cardViewEvet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
                deleteUser();
            }
        });

        cardViewVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }
}