package com.mobiloby.filter.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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


public class ActivityRegister2 extends AppCompatActivity {

    EditText username;
    Toast toast;
    TextView alerttext;
    View toastlayout;
    Bundle extras;
    String name="", password="";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        extras = getIntent().getExtras();

        if(extras!=null)
            name = extras.getString("username");

        //Custom Toast
        LayoutInflater inflater = getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)findViewById(R.id.alertlayout));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);

        username = findViewById(R.id.username);

        int maxLength = 10;
        username.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        username.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>4 && s.length()<15)
                    findViewById(R.id.phoneacceptbut).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.phoneacceptbut).setVisibility(View.INVISIBLE);

                if(s.length()>15) {
                    username.setText(s.subSequence(0, 15));
                    alerttext.setText("En az 4 ve en fazla 15 rakamlı olacak şekilde kullanıcı adınızı giriniz.");
                    toast.setView(toastlayout);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    if (s.charAt(0) != '5'){
                        alerttext.setText("Eksik veya hatalı giriş yaptınız!");
                        toast.setView(toastlayout);
                        toast.show();}
            }
        });

        ImageView backbut = findViewById(R.id.backbut);


        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                finish();
            }
        });

        ImageButton phoneacceptbut = (ImageButton)findViewById(R.id.phoneacceptbut);
        phoneacceptbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister2.this, ActivityAvatar.class);
                intent.putExtra("username", name);
                intent.putExtra("password", username.getText().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();
    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
