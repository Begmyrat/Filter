package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;
import com.mobiloby.filter.helpers.makeAlert;

import org.json.JSONObject;

import java.util.HashMap;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActivityCategory2_ShowAnswers extends AppCompatActivity {

    Bundle extras;
    String soru="", cevap="", username="", istek_username="";
    TextView t_soru1, t_soru2, t_soru3, t_soru4, t_soru5, t_ans11, t_ans12, t_ans13, t_ans21, t_ans22, t_ans23, t_ans31, t_ans32, t_ans33, t_ans41, t_ans42, t_ans43, t_ans51, t_ans52, t_ans53;
    String[] questions, elements, answers;
    JSONParser jsonParser;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2__show_answers);

        prepareMe();

    }

    private void prepareMe() {
        // give color to top bar(date, clock & system widgets)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        // remove focus to edittext
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        t_soru1 = findViewById(R.id.t_question1);
        t_soru2 = findViewById(R.id.t_question2);
        t_soru3 = findViewById(R.id.t_question3);
        t_soru4 = findViewById(R.id.t_question4);
        t_soru5 = findViewById(R.id.t_question5);
        t_ans11 = findViewById(R.id.t_answer11);
        t_ans12 = findViewById(R.id.t_answer12);
        t_ans13 = findViewById(R.id.t_answer13);
        t_ans21 = findViewById(R.id.t_answer21);
        t_ans22 = findViewById(R.id.t_answer22);
        t_ans23 = findViewById(R.id.t_answer23);
        t_ans31 = findViewById(R.id.t_answer31);
        t_ans32 = findViewById(R.id.t_answer32);
        t_ans33 = findViewById(R.id.t_answer33);
        t_ans41 = findViewById(R.id.t_answer41);
        t_ans42 = findViewById(R.id.t_answer42);
        t_ans43 = findViewById(R.id.t_answer43);
        t_ans51 = findViewById(R.id.t_answer51);
        t_ans52 = findViewById(R.id.t_answer52);
        t_ans53 = findViewById(R.id.t_answer53);

        extras = getIntent().getExtras();
        if(extras!=null){
            soru = extras.getString("wanted_soru");
            cevap = extras.getString("wanted_cevap");
            istek_username = extras.getString("user_name_unique");
            username = extras.getString("friend_user_name_unique");



        }

//        soru = "Soru:kzdan geken soru 1 Cevap:cevap 1kCevap:cevap2k Cevap:cevap 3kSoru:kzdan geken soru 1Cevap:cevap 11kCevap:cevap 22kCevap:cevap 33k";
//        cevap = "Soru:kzdan geken soru 1 Cevap:cevap 1kSoru:kzdan geken soru 1Cevap:cevap 33k";

        questions = soru.split("SoruMobiloby:");
        answers = cevap.split("CevapMobiloby:");

        System.out.println("Cevaplar:");

        for(int i=1;i<questions.length;i++){
            elements = questions[i].split("CevapMobiloby:");
            if(answers[i].contains("SoruMobiloby:")){
                answers[i] = answers[i].substring(0, answers[i].indexOf("SoruMobiloby:"));
            }
            if(i==1){
                findViewById(R.id.l_questions1).setVisibility(View.VISIBLE);
                t_soru1.setText(elements[0]);
                t_ans11.setText(elements[1]);
                t_ans12.setText(elements[2]);
                t_ans13.setText(elements[3]);
                if(elements[1].equals(answers[i]))
                    findViewById(R.id.t_answer11).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[2].equals(answers[i]))
                    findViewById(R.id.t_answer12).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[3].equals(answers[i]))
                    findViewById(R.id.t_answer13).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
            }
            else if(i==2){
                findViewById(R.id.l_questions2).setVisibility(View.VISIBLE);
                t_soru2.setText(elements[0]);
                t_ans21.setText(elements[1]);
                t_ans22.setText(elements[2]);
                t_ans23.setText(elements[3]);
                if(elements[1].equals(answers[i]))
                    findViewById(R.id.t_answer21).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[2].equals(answers[i]))
                    findViewById(R.id.t_answer22).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[3].equals(answers[i]))
                    findViewById(R.id.t_answer23).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
            }
            else if(i==3){
                findViewById(R.id.l_questions3).setVisibility(View.VISIBLE);
                t_soru3.setText(elements[0]);
                t_ans31.setText(elements[1]);
                t_ans32.setText(elements[2]);
                t_ans33.setText(elements[3]);
                if(elements[1].equals(answers[i]))
                    findViewById(R.id.t_answer31).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[2].equals(answers[i]))
                    findViewById(R.id.t_answer32).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[3].equals(answers[i]))
                    findViewById(R.id.t_answer33).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
            }
            else if(i==4){
                findViewById(R.id.l_questions4).setVisibility(View.VISIBLE);
                t_soru3.setText(elements[0]);
                t_ans31.setText(elements[1]);
                t_ans32.setText(elements[2]);
                t_ans33.setText(elements[3]);
                if(elements[1].equals(answers[i]))
                    findViewById(R.id.t_answer41).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[2].equals(answers[i]))
                    findViewById(R.id.t_answer42).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[3].equals(answers[i]))
                    findViewById(R.id.t_answer43).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
            }
            else if(i==5){
                findViewById(R.id.l_questions5).setVisibility(View.VISIBLE);
                t_soru4.setText(elements[0]);
                t_ans41.setText(elements[1]);
                t_ans42.setText(elements[2]);
                t_ans43.setText(elements[3]);
                if(elements[1].equals(answers[i]))
                    findViewById(R.id.t_answer51).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[2].equals(answers[i]))
                    findViewById(R.id.t_answer52).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
                else if(elements[3].equals(answers[i]))
                    findViewById(R.id.t_answer53).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
            }
        }

    }

    public void clickBack(View view) {
        finish();
    }

    public void clickKabulEt(View view) {
//        istekKabulEt();

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Bu isteği kabul etmek istiyor musunuz?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Evet",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                istekKabulEt();
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorTextColor,
                        R.color.gray_btn_bg_color,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();

    }

    public void clickReddet(View view) {
//        istekReddet();

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Bu isteği reddetmek istiyor musunuz?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Evet",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                istekReddet();
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorTextColor,
                        R.color.gray_btn_bg_color,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    private void istekReddet() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);

        final String url = "https://mobiloby.com/_filter/delete_istek.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", istek_username);

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
                    finish();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory2_ShowAnswers.this, true);
                }

            }
        }.execute(null, null, null);
    }

    private void istekKabulEt() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();


        final String url = "https://mobiloby.com/_filter/insert_friend.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", istek_username);
                jsonData.put("friend_user_name_unique", username);

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
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String countArkadas = preferences.getString("count_arkadas", "");
                    String countIstek = preferences.getString("count_istek", "");
                    int ca = Integer.parseInt(countArkadas);
                    int ci = Integer.parseInt(countIstek);
                    ca++;
                    ci--;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("count_arkadas", ""+ca);
                    editor.putString("count_istek", ""+ci);
                    editor.commit();
                    finish();
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory2_ShowAnswers.this, true);
                }

            }
        }.execute(null, null, null);
    }

}