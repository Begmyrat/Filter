package com.mobiloby.filter.activities;

import android.app.Activity;
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
import androidx.core.content.ContextCompat;

import com.mobiloby.filter.R;
import com.mobiloby.filter.ShowToastMessage;


public class ActivityRegister2 extends AppCompatActivity {

    EditText e_password;
    Toast toast;
    TextView alerttext;
    View toastlayout;
    Bundle extras;
    String name="";
    ImageView i_continue;
    Boolean isValid = false;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground2));// set status background white

        prepareMe();

        e_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>6 && charSequence.length()<20){
                    i_continue.setImageResource(R.drawable.contunie_active);
                    isValid = true;
                }
                else{
                    i_continue.setImageResource(R.drawable.contunie_passive);
                    isValid = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void prepareMe() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        activity = this;
        extras = getIntent().getExtras();
        if(extras!=null){
            name = extras.getString("name");
        }
        LayoutInflater inflater = getLayoutInflater();
        toastlayout = inflater.inflate(R.layout.alertbox, (ViewGroup)findViewById(R.id.alertlayout));
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        alerttext = (TextView) toastlayout.findViewById(R.id.alerttext);
        i_continue = findViewById(R.id.i_continue);
        e_password = findViewById(R.id.e_password);
    }

    public void clickContinue(View view) {
        if(isValid){
            Intent intent = new Intent(ActivityRegister2.this, ActivityAvatar.class);
            intent.putExtra("password", e_password.getText().toString());
            intent.putExtra("username", name);
            startActivity(intent);
        }
        else{
            ShowToastMessage.show(activity, "Şifre en az 7 ve en fazla 19 karakter olmalıdır");
        }
    }
}
