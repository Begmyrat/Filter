package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class ActivityCategory2_Answer extends AppCompatActivity {

    TextView t_soru1, t_soru2, t_soru3, t_soru4, t_soru5, t_title, t_ans11, t_ans12, t_ans13, t_ans21, t_ans22, t_ans23, t_ans31, t_ans32, t_ans33, t_ans41, t_ans42, t_ans43, t_ans51, t_ans52, t_ans53, t_time;
    EditText e_cevap1, e_cevap2, e_cevap3;
    String cevap1="", cevap2="", cevap3="", wantedID="", username="", wanted_username="", friend_username="", soru1="", soru2="", soru3="", soru4="", soru5="";
    String soru="", answer1="", answer2="", answer3="", answer4="", answer5="", answers="";
    Bundle extras;
    JSONObject jsonObject;
    JSONParser jsonParser;
    Boolean isAnswerExist = false;
    CountDownTimer countDownTimer;
    int second = 0, index=0;
    String[] questions, elements;
    Dialog builder;

    TextView t_soru, t_answer1, t_answer2, t_answer3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2__answer);

        prepareMe();

        getCurrentQuestion();

//        getAnswers();

    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        t_title = findViewById(R.id.t_title);
        t_time = findViewById(R.id.t_time);
        t_soru = findViewById(R.id.t_question);
        t_answer1 = findViewById(R.id.t_answer1);
        t_answer2 = findViewById(R.id.t_answer2);
        t_answer3 = findViewById(R.id.t_answer3);

        extras = getIntent().getExtras();
        if(extras!=null){
            soru = extras.getString("soru");
            System.out.println("SORU: " + soru);
            username = extras.getString("username");
            wanted_username = extras.getString("wanted_username");
            wantedID = extras.getString("wanted_id");
            friend_username = extras.getString("friend_username");
//            t_title.setText(wanted_username);

            questions = soru.split("Soru:");
        }

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

    }

    public void clickBack(View view) {
        finish();
    }

    public void clickSave(View view) {
        // save answers
        cevap1 = e_cevap1.getText().toString();
        cevap2 = e_cevap2.getText().toString();
        cevap3 = e_cevap3.getText().toString();
        Toast.makeText(this, "username: " + username + "\nfriend: " + friend_username, Toast.LENGTH_SHORT).show();

        if(cevap1.length()>0 && cevap2.length()>0 && cevap3.length()>0){
            //insert data
            if(isAnswerExist){
                updateAnswers();
            }
            else{
                insertAnswer();
            }
        }
    }

    private void insertAnswer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_wanted_answer.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
                jsonData.put("user_name", username);
                jsonData.put("answer", answers);
                jsonData.put("friend_user_name_unique", friend_username);

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
                    Toast.makeText(ActivityCategory2_Answer.this, "Cevaplarınız gönderilmiştir. Teşekkür ederiz", Toast.LENGTH_SHORT).show();
                    sendQAToChat();
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    Toast.makeText(ActivityCategory2_Answer.this, "Error Insert", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }

    private void updateAnswers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/update_wanted_answers.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
                jsonData.put("user_name", username);
                jsonData.put("answer_1", cevap1);
                jsonData.put("answer_2", cevap2);
                jsonData.put("answer_3", cevap3);

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
                    Toast.makeText(ActivityCategory2_Answer.this, "Cevaplarınız gönderilmiştir. Teşekkür ederiz", Toast.LENGTH_SHORT).show();
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    Toast.makeText(ActivityCategory2_Answer.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }

    private void getAnswers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_wanted_answers.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
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
                            cevap1 = c.getString("answer_1");
                            cevap2 = c.getString("answer_2");
                            cevap3 = c.getString("answer_3");
                        }
                        e_cevap1.setText(cevap1);
                        e_cevap2.setText(cevap2);
                        e_cevap3.setText(cevap3);

                        isAnswerExist = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_Answer.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
//                    Toast.makeText(ActivityCategory2_Answer.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute(null, null, null);
    }

    private void sendQAToChat() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
//        progressDialog.show();

        Toast.makeText(this, "user: " + username, Toast.LENGTH_SHORT).show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_qa_to_chat.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name_unique", username);
                jsonData.put("friend_user_name_unique", friend_username);
                jsonData.put("soru1", soru1);
                jsonData.put("soru2", soru2);
                jsonData.put("soru3", soru3);
                jsonData.put("soru4", soru4);
                jsonData.put("soru5", soru5);
                jsonData.put("cevap1", answer1);
                jsonData.put("cevap2", answer2);
                jsonData.put("cevap3", answer3);
                jsonData.put("cevap4", answer4);
                jsonData.put("cevap5", answer5);
                jsonData.put("numberOfQuestions", ""+(index-1));

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
                    // sorular ve cevaplar message tablosiuna eklendi
                }
                else{
                    makeAlert.uyarıVer("Filter", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory2_Answer.this, true);
                }

            }
        }.execute(null, null, null);
    }

    public void getCurrentQuestion(){

        makeInit();

        index++;

        findViewById(R.id.l_questions).setAlpha(0);

        findViewById(R.id.l_questions).animate()
                .setDuration(1500)
                .alpha(1);

        if(index==questions.length){
            findViewById(R.id.l_questions).setVisibility(View.GONE);
            popup();
        }
        else{
            elements = questions[index].split("Cevap:");
            t_soru.setText(elements[0]);
            t_answer1.setText(elements[1]);
            t_answer2.setText(elements[2]);
            t_answer3.setText(elements[3]);

            answers += "Soru:" + elements[0] + "Cevap:";

            if(index==1){
                soru1 = elements[0];
            }
            else if(index==2){
                soru2 = elements[0];
            }
            else if(index==3){
                soru3 = elements[0];
            }
            else if(index==4){
                soru4 = elements[0];
            }
            else if(index==5){
                soru5 = elements[0];
            }

            if (countDownTimer != null)
                countDownTimer.cancel();
            second = 11;
            countDownTimer = new CountDownTimer((11) * 1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    second -= 1;
                    t_time.setText(""+second);
                }

                public void onFinish() {
                    getCurrentQuestion();
                }

            }.start();
        }
    }

    private void popup() {

        if (countDownTimer != null)
            countDownTimer.cancel();

        insertAnswer();

        t_time.setText("");

        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        TextView t_body = view.findViewById(R.id.e_info);
        t_body.setText("Sorulara verdiğiniz yanıtlar karşı tarafa iletilmiş ve istek olarak gönderilmiştir. Güzel haberleri bekleyiniz!!!");
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickTamam(View view) {
        builder.dismiss();
        finish();
    }

    public void clickAnswer1(View view) {

        answers += elements[1];

        if(index==1){
            answer1 = elements[1];
        }
        else if(index==2){
            answer2 = elements[1];
        }
        else if(index==3){
            answer3 = elements[1];
        }
        else if(index==4){
            answer4 = elements[1];
        }
        else if(index==5){
            answer5 = elements[1];
        }

        makeInit();
        t_answer1.setTextColor(getResources().getColor(R.color.colorWhite));
        findViewById(R.id.r_answer1).setBackground(getResources().getDrawable(R.drawable.yellow_background_selected));

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                getCurrentQuestion();
            }
        }, 1500);
    }

    public void clickAnswer2(View view) {

        answers += elements[2];

        if(index==1){
            answer1 = elements[2];
        }
        else if(index==2){
            answer2 = elements[2];
        }
        else if(index==3){
            answer3 = elements[2];
        }
        else if(index==4){
            answer4 = elements[2];
        }
        else if(index==5){
            answer5 = elements[2];
        }

        makeInit();
        t_answer2.setTextColor(getResources().getColor(R.color.colorWhite));
        findViewById(R.id.r_answer2).setBackground(getResources().getDrawable(R.drawable.yellow_background_selected));
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                getCurrentQuestion();
            }
        }, 1500);
    }

    public void clickAnswer3(View view) {

        answers += elements[3];

        if(index==1){
            answer1 = elements[3];
        }
        else if(index==2){
            answer2 = elements[3];
        }
        else if(index==3){
            answer3 = elements[3];
        }
        else if(index==4){
            answer4 = elements[3];
        }
        else if(index==5){
            answer5 = elements[3];
        }

        makeInit();
        t_answer3.setTextColor(getResources().getColor(R.color.colorWhite));
        findViewById(R.id.r_answer3).setBackground(getResources().getDrawable(R.drawable.yellow_background_selected));
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                getCurrentQuestion();
            }
        }, 1500);
    }

    public void makeInit(){
        t_answer1.setTextColor(getResources().getColor(R.color.colorDarkGray));
        t_answer2.setTextColor(getResources().getColor(R.color.colorDarkGray));
        t_answer3.setTextColor(getResources().getColor(R.color.colorDarkGray));
        findViewById(R.id.r_answer1).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
        findViewById(R.id.r_answer2).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
        findViewById(R.id.r_answer3).setBackground(getResources().getDrawable(R.drawable.rounded_stroked_background));
    }

    private void checkAnswer(final int checkedId) {

//        findViewById(R.id.r_unclick).setVisibility(View.VISIBLE);
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                // yourMethod();
//                if (checkedId != correctAnswer)
//                    makeWrong(checkedId);
//
//                makeCorrect(correctAnswer);
//            }
//        }, 500);
//
//        Handler handler2 = new Handler();
//        handler2.postDelayed(new Runnable() {
//            public void run() {
////                getCurrentQuestion();
//
//            }
//        }, 1500);
    }
}