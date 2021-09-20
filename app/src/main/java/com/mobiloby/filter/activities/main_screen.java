package com.mobiloby.filter.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiloby.filter.R;

public class main_screen extends AppCompatActivity {

    public Button btn_login;
    public Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);



            btn_login=findViewById(R.id.primarybut);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login = new Intent(main_screen.this, ActivityLogin1.class);
                    startActivity(login);
                }
            });



            btn_register=findViewById(R.id.secondarybut);
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent register = new Intent(main_screen.this, ActivityRegister1.class);
                    startActivity(register);
                }
            });



//        TextView test = (TextView)findViewById(R.id.cont);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                Intent login = new Intent(main_screen.this, mapscreen.class);
//                login.putExtra("giris","demo");
//                startActivity(login);
//            }
//        });

    }


    @Override
    public void onBackPressed() {

    }


}
