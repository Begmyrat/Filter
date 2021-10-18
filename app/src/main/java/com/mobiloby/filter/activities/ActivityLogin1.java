package com.mobiloby.filter.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ActivityLogin1 extends AppCompatActivity {

    EditText e_username, e_password;
    Toast toast;
    TextView alerttext;
    View toastlayout;
    JSONParser jsonParser;
    JSONObject jsonObject;
//    ImageView i_balloon;
    ImageView i_fltr;
    String username, password;
    SharedPreferences preferences;
    int maxLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        prepareMe();

//        e_username.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
//        e_username.addTextChangedListener(new TextWatcher() {
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(s.length()>4 && s.length()<15)
//                    findViewById(R.id.phoneacceptbut).setVisibility(View.VISIBLE);
//                else
//                    findViewById(R.id.phoneacceptbut).setVisibility(View.INVISIBLE);
//
//                if(s.length()>15) {
//                    e_username.setText(s.subSequence(0, 15));
//                    alerttext.setText("En az 4 ve en fazla 15 rakamlı olacak şekilde kullanıcı adınızı giriniz.");
//                    toast.setView(toastlayout);
//                    toast.show();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
////                if (s.length() > 0)
////                    if (s.charAt(0) != '5'){
////                        alerttext.setText("Eksik veya hatalı giriş yaptınız!");
////                        toast.setView(toastlayout);
////                        toast.show();}
//            }
//        });
    }

    private void prepareMe() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;


//        i_balloon = findViewById(R.id.i_filtryenilogo);
        i_fltr = findViewById(R.id.i_fltr);

        if(height<1780){
//            i_balloon.getLayoutParams().width = dpToPx(70, this);
//            i_balloon.getLayoutParams().height = dpToPx(70, this);

            i_fltr.getLayoutParams().width = dpToPx(50, this);
            i_fltr.getLayoutParams().height = dpToPx(50, this);
        }

        //Custom Toast
        LayoutInflater inflater = getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)findViewById(R.id.alertlayout));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);

        e_username = findViewById(R.id.e_username);
        e_password = findViewById(R.id.e_password);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void clickCreateAnAccouunt(View view) {
        startActivity(new Intent(this, ActivityRegister1.class));
    }

    public void clickLogin(View view) {
        username = e_username.getText().toString();
        password = e_password.getText().toString();

        getUser();
    }

    private void getUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

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

                progressDialog.dismiss();

                if (res.equals("1")) {
                    try {
                        JSONArray pro = jsonObject.getJSONArray("pro");

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);

                            String pass = c.getString("user_password");

                            if(pass.equals(password)){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username_unique", username);
                                editor.putString("user_password", password);
                                editor.putString("user_profile_url", c.getString("user_profile_url"));
                                editor.commit();
                                Intent intent = new Intent(ActivityLogin1.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{
                                makeAlert.uyarıVer("Filter", "Kullanıcı adı veya şifre hatalı.", ActivityLogin1.this, true);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityLogin1.this, "error jiimRecommend", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    makeAlert.uyarıVer("Filter", "Kullanıcı adı veya şifre hatalı.", ActivityLogin1.this, true);
                }

            }
        }.execute(null, null, null);


    }
}
