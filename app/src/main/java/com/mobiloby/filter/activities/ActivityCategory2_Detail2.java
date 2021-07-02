package com.mobiloby.filter.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiloby.filter.helpers.JSONParser;
import com.mobiloby.filter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActivityCategory2_Detail2 extends AppCompatActivity{

    RelativeLayout r_information;
    SharedPreferences preferences;
    boolean isBenClicked=true, isDigerClicked=false, isSoruClicked=false, isProfileExist=false;
    boolean isShapka=false, isShapkaDiger=false, isGozluk=false, isGozlukDiger=false, isKupe=false, isKupeDiger=false, isMaske=false, isMaskeDiger=false, isSach=false, isSachDiger=false, isGoz=false, isGozDiger=false, isAtki=false, isAtkiDiger=false, isRuj=false, isRujDiger=false, isKravat=false, isKravatDiger=false;
    boolean isMont=false, isMontDiger=false, isUst=false, isUstDiger=false, isEldiven=false, isEldivenDiger=false, isDovme=false, isDovmeDiger=false, isMayo=false, isMayoDiger=false, isSaat=false, isSaatDiger=false;
    boolean isAyakkabi=false, isAyakkabiDiger=false, isAlt=false, isAltDiger=false, isAltMayo=false, isAltMayoDiger=false, isAltDovme=false, isAltDovmeDiger=false;
    String tarih="-", cinsiyetBen="-", cinsiyetDiger="-", yerBen="-", yerDiger="-", eylemBen="-", eylemDiger="-";
    String shapkaEsharp="-", shapkaEsharpDiger="-", gozluk="-", gozlukDiger="-", kupe="-", kupeDiger="-", maske="-", maskeDiger="-", ruj="-", rujDiger="-", sach="-", sachDiger="-", goz="-", gozDiger="-", atki="-", atkiDiger="-", kravat="-", kravatDiger="-", elbise="-", elbiseDiger="-";
    String mont="-", montDiger="-", ust="-", ustDiger="-", eldiven="-", eldivenDiger="-", dovme="-", dovmeDiger="-", mayo="-", mayoDiger="-", saat="-", saatDiger="-";
    String ayakkabi="-", ayakkabiDiger="-", alt="-", altDiger="-", altMayo="-", altMayoDiger="-", altDovme="-", altDovmeDiger="-";
    TextView t_ben, t_diger, t_soru;
    TextView t_goz, t_gozDiger, t_shapkaEsharp, t_shapkaEsharpDiger, t_gozluk, t_gozlukDiger, t_kupe, t_kupeDiger, t_maske, t_maskeDiger, t_sach, t_sachDiger, t_atki, t_atkiDiger, t_ruj, t_rujDiger, t_kravat, t_kravatDiger;
    TextView t_mont, t_montDiger, t_ust, t_ustDiger, t_eldiven, t_eldivenDiger, t_dovme, t_dovmeDiger, t_mayo, t_mayoDiger, t_saat, t_saatDiger, t_elbise, t_elbiseDiger;
    TextView t_ayakkabi, t_ayakkabiDiger, t_alt, t_altDiger, t_altMayo, t_altMayoDiger, t_altDovme, t_altDovmeDiger;
    Dialog builder;
    RadioGroup rg_tarih,rg_genderBen, rg_genderDiger;
    RadioButton r_tarih;
    Boolean isTamam = false, isTamamDiger = false, isInformationOpen = false;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username="-", secilenTitle="-", wantedID="-1";
    Bundle extras;
    ImageView imageView;
    View popupView;
    float montX=-1, montY=-1, montXDiger=-1, montYDiger=-1, elbiseX=-1, elbiseY=-1, elbiseDigerX=-1, elbiseDigerY=-1, ustX=-1, ustXDiger=1, ustY=-1, ustYDiger=-1, midMayoX=-1, midMayoXDiger=-1, midMayoY=-1, midMayoYDiger=-1, ayakkabiX=-1, ayakkabiXDiger=-1, ayakkabiY=-1, ayakkabiYDiger=-1, altX=-1, altXDiger=-1, altY=-1, altYDiger=-1, botMayoX=-1, botMayoXDiger=-1, botMayoY=-1, botMayoYDiger=-1;
    EditText e_q1, e_q2, e_q3, e_q4, e_q5, e_a11, e_a12, e_a13, e_a21, e_a22, e_a23, e_a31, e_a32, e_a33, e_a41, e_a42, e_a43, e_a51, e_a52, e_a53;
    int questionNumber=1;
    EditText e_title;
    Spinner spinnerYerBen, spinnerYerDiger, spinnerEylemBen, spinnerEylemDiger;
    ImageView i_body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2new);

        prepareMe();

        rg_tarih.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.r_bugun){
                    tarih = "bugun";
                    r_tarih.setText("Tarih seçiniz");
                }
                else if(checkedId == R.id.r_dun){
                    tarih = "dun";
                    r_tarih.setText("Tarih seçiniz");
                }
            }
        });

        rg_genderBen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.r_kizBen) {
                    cinsiyetBen = "kiz";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));
                }
                else {
                    cinsiyetBen = "oglan";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
                }
            }
        });

        rg_genderDiger.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.r_kizDiger) {
                    cinsiyetDiger = "kiz";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));
                }
                else {
                    cinsiyetDiger = "oglan";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
                }
            }
        });

    }

    private void prepareMe() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        r_information = findViewById(R.id.r_information);
        r_information.startAnimation(outToRightAnimation());
        r_information.setVisibility(View.GONE);
        findViewById(R.id.r_informationDiger).startAnimation(outToRightAnimation());
        findViewById(R.id.r_informationDiger).setVisibility(View.GONE);

        i_body = findViewById(R.id.i_body);


        t_ben = findViewById(R.id.t_ben);
        t_diger = findViewById(R.id.t_diger);
        t_soru = findViewById(R.id.t_soru);

        t_shapkaEsharp = findViewById(R.id.t_topEsharf);
        t_shapkaEsharpDiger = findViewById(R.id.t_topEsharfDiger);
        t_gozluk = findViewById(R.id.t_topGozluk);
        t_gozlukDiger = findViewById(R.id.t_topGozlukDiger);
        t_kupe = findViewById(R.id.t_topKupe);
        t_kupeDiger = findViewById(R.id.t_topKupeDiger);
        t_maske = findViewById(R.id.t_topMaske);
        t_maskeDiger = findViewById(R.id.t_topMaskeDiger);
        t_sach = findViewById(R.id.t_topSach);
        t_sachDiger = findViewById(R.id.t_topSachDiger);
        t_goz = findViewById(R.id.t_topGoz);
        t_gozDiger = findViewById(R.id.t_topGozDiger);
        t_atki = findViewById(R.id.t_topAtki);
        t_ruj = findViewById(R.id.t_topRuj);
        t_rujDiger = findViewById(R.id.t_topRujDiger);
        t_atkiDiger = findViewById(R.id.t_topAtkiDiger);
        t_kravat = findViewById(R.id.t_topKravat);
        t_kravatDiger = findViewById(R.id.t_topKravatDiger);

        t_mont = findViewById(R.id.t_midMont);
        t_montDiger = findViewById(R.id.t_midMontDiger);
        t_elbise = findViewById(R.id.t_midElbise);
        t_elbiseDiger = findViewById(R.id.t_midElbiseDiger);
        t_ust = findViewById(R.id.t_midUst);
        t_ustDiger = findViewById(R.id.t_midUstDiger);
        t_eldiven = findViewById(R.id.t_midEldiven);
        t_eldivenDiger = findViewById(R.id.t_midEldivenDiger);
        t_dovme = findViewById(R.id.t_midDovme);
        t_dovmeDiger = findViewById(R.id.t_midDovmeDiger);
        t_mayo = findViewById(R.id.t_midMayo);
        t_mayoDiger = findViewById(R.id.t_midMayoDiger);
        t_saat = findViewById(R.id.t_midSaat);
        t_saatDiger = findViewById(R.id.t_midSaatDiger);

        t_ayakkabi = findViewById(R.id.t_botAyakkabi);
        t_ayakkabiDiger = findViewById(R.id.t_botAyakkabiDiger);
        t_alt = findViewById(R.id.t_botAlt);
        t_altDiger = findViewById(R.id.t_botAltDiger);
        t_altMayo = findViewById(R.id.t_botMayo);
        t_altMayoDiger = findViewById(R.id.t_botMayoDiger);
        t_altDovme = findViewById(R.id.t_botDovme);
        t_altDovmeDiger = findViewById(R.id.t_botDovmeDiger);

        rg_tarih = findViewById(R.id.rg_tarih);
        r_tarih = findViewById(R.id.r_tarihsec);
        rg_genderBen = findViewById(R.id.rg_genderBen);
        rg_genderDiger = findViewById(R.id.rg_genderDiger);

        findViewById(R.id.r_information).animate()
                .translationXBy(960);

        findViewById(R.id.r_informationDiger).animate()
                .translationXBy(960);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
        }


        e_q1 = findViewById(R.id.e_question1);
        e_q2 = findViewById(R.id.e_question2);
        e_q3 = findViewById(R.id.e_question3);
        e_q4 = findViewById(R.id.e_question4);
        e_q5 = findViewById(R.id.e_question5);

        e_a11 = findViewById(R.id.e_answer1);
        e_a12 = findViewById(R.id.e_answer2);
        e_a13 = findViewById(R.id.e_answer3);
        e_a21 = findViewById(R.id.e_answer12);
        e_a22 = findViewById(R.id.e_answer22);
        e_a23 = findViewById(R.id.e_answer32);
        e_a31 = findViewById(R.id.e_answer13);
        e_a32 = findViewById(R.id.e_answer13);
        e_a33 = findViewById(R.id.e_answer13);
        e_a41 = findViewById(R.id.e_answer14);
        e_a42 = findViewById(R.id.e_answer14);
        e_a43 = findViewById(R.id.e_answer14);
        e_a51 = findViewById(R.id.e_answer15);
        e_a52 = findViewById(R.id.e_answer15);
        e_a53 = findViewById(R.id.e_answer15);

        e_title = findViewById(R.id.e_title);
        spinnerYerBen = findViewById(R.id.spinner_locationBen);
        spinnerYerDiger = findViewById(R.id.spinner_locationDiger);
        spinnerEylemBen = findViewById(R.id.spinner_activityBen);
        spinnerEylemDiger = findViewById(R.id.spinner_activityDiger);
    }


    public void clickBack(View view) {
        finish();
    }

    public void clickSoru(View view) {
        if(!isInformationOpen){
            isBenClicked = false;
            isDigerClicked = false;
            isSoruClicked = true;

            findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background));
            findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background));
            findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));

            t_ben.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_diger.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_soru.setTextColor(getResources().getColor(R.color.colorWhite));

            findViewById(R.id.scrollview_beno).setVisibility(View.INVISIBLE);
            findViewById(R.id.scrollView_questions).setVisibility(View.VISIBLE);
            findViewById(R.id.b_floating).setVisibility(View.VISIBLE);

        }
    }

    public void clickDiger(View view) {
        if(!isInformationOpen){
            isBenClicked = false;
            isDigerClicked = true;
            isSoruClicked = false;

            findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background));
            findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background));

            if(cinsiyetDiger.equals("oglan"))
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
            else
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));

            t_ben.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_diger.setTextColor(getResources().getColor(R.color.colorWhite));
            t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

            findViewById(R.id.scrollview_beno).setVisibility(View.VISIBLE);
            findViewById(R.id.scrollView_questions).setVisibility(View.INVISIBLE);
            findViewById(R.id.b_floating).setVisibility(View.GONE);

            spinnerYerBen.setVisibility(View.INVISIBLE);
            spinnerYerDiger.setVisibility(View.VISIBLE);
            spinnerEylemBen.setVisibility(View.INVISIBLE);
            spinnerEylemDiger.setVisibility(View.VISIBLE);
            rg_genderBen.setVisibility(View.GONE);
            rg_genderDiger.setVisibility(View.VISIBLE);
        }
    }

    public void clickBen(View view) {

        if(!isInformationOpen){
            isBenClicked = true;
            isDigerClicked = false;
            isSoruClicked = false;

            findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background));
            findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background));

            if(cinsiyetBen.equals("oglan"))
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
            else
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));

            t_ben.setTextColor(getResources().getColor(R.color.colorWhite));
            t_diger.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

            findViewById(R.id.scrollview_beno).setVisibility(View.VISIBLE);
            findViewById(R.id.scrollView_questions).setVisibility(View.INVISIBLE);
            findViewById(R.id.b_floating).setVisibility(View.GONE);

            spinnerYerBen.setVisibility(View.VISIBLE);
            spinnerYerDiger.setVisibility(View.INVISIBLE);
            spinnerEylemBen.setVisibility(View.VISIBLE);
            spinnerEylemDiger.setVisibility(View.INVISIBLE);

            rg_genderBen.setVisibility(View.VISIBLE);
            rg_genderDiger.setVisibility(View.GONE);
        }
    }

    public void clickBodyTop(View view) {

        if(!isInformationOpen){
            isInformationOpen = true;

            if(isBenClicked){
                findViewById(R.id.r_information).setVisibility(View.VISIBLE);
                findViewById(R.id.r_informationDiger).setVisibility(View.GONE);
                findViewById(R.id.f_bash).setVisibility(View.VISIBLE);
                findViewById(R.id.f_govde).setVisibility(View.GONE);
                findViewById(R.id.f_bacak).setVisibility(View.GONE);
//            findViewById(R.id.r_information).startAnimation(inFromRightAnimation());
//            findViewById(R.id.r_image).setTranslationX(-200);
                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamam){
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }

            }
            else if(isDigerClicked){
                findViewById(R.id.r_informationDiger).setVisibility(View.VISIBLE);
                findViewById(R.id.r_information).setVisibility(View.GONE);
                findViewById(R.id.f_bashDiger).setVisibility(View.VISIBLE);
                findViewById(R.id.f_govdeDiger).setVisibility(View.GONE);
                findViewById(R.id.f_bacakDiger).setVisibility(View.GONE);
//            findViewById(R.id.r_informationDiger).startAnimation(inFromRightAnimation());

                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamamDiger){
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }

            }
        }

    }

    public void clickBodyMid(View view) {

        if(!isInformationOpen){
            isInformationOpen = true;

            if(isBenClicked){
                findViewById(R.id.r_information).setVisibility(View.VISIBLE);
                findViewById(R.id.r_informationDiger).setVisibility(View.GONE);
                findViewById(R.id.f_bash).setVisibility(View.GONE);
                findViewById(R.id.f_govde).setVisibility(View.VISIBLE);
                findViewById(R.id.f_bacak).setVisibility(View.GONE);

                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamam){
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }
            }
            else if(isDigerClicked){
                findViewById(R.id.r_informationDiger).setVisibility(View.VISIBLE);
                findViewById(R.id.r_information).setVisibility(View.GONE);
                findViewById(R.id.f_bashDiger).setVisibility(View.GONE);
                findViewById(R.id.f_govdeDiger).setVisibility(View.VISIBLE);
                findViewById(R.id.f_bacakDiger).setVisibility(View.GONE);

                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamamDiger){
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }

            }
        }
    }

    public void clickBodyBot(View view) {

        if(!isInformationOpen){
            isInformationOpen = true;

            if(isBenClicked){
                findViewById(R.id.r_information).setVisibility(View.VISIBLE);
                findViewById(R.id.r_informationDiger).setVisibility(View.GONE);
                findViewById(R.id.f_bash).setVisibility(View.GONE);
                findViewById(R.id.f_govde).setVisibility(View.GONE);
                findViewById(R.id.f_bacak).setVisibility(View.VISIBLE);
//            findViewById(R.id.r_information).startAnimation(inFromRightAnimation());

                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamam){
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_information).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }

            }
            else if(isDigerClicked){
                findViewById(R.id.r_informationDiger).setVisibility(View.VISIBLE);
                findViewById(R.id.r_information).setVisibility(View.GONE);
                findViewById(R.id.f_bashDiger).setVisibility(View.GONE);
                findViewById(R.id.f_govdeDiger).setVisibility(View.GONE);
                findViewById(R.id.f_bacakDiger).setVisibility(View.VISIBLE);

                findViewById(R.id.r_image2).animate()
                        .translationXBy(-300)
                        .setDuration(1000);

                if(isTamamDiger){
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-1650)
                            .alpha(1)
                            .setDuration(1000);
                }
                else{
                    findViewById(R.id.r_informationDiger).animate()
                            .translationXBy(-500)
                            .alpha(1)
                            .setDuration(1000);
                }

            }
        }
    }

    public void clickTopGoz(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Göz bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Renkli",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    Toast.makeText(ActivityCategory2_Detail2.this, "clicked BEN", Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_goz.setTextColor(getResources().getColor(R.color.colorWhite));
                                    goz = "renkli";
                                }
                                else{
                                    Toast.makeText(ActivityCategory2_Detail2.this, "clicked O", Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozDiger = "renkli";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Renksiz",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_goz.setTextColor(getResources().getColor(R.color.colorWhite));
                                    goz = "renksiz";
                                }
                                else{
                                    findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozDiger = "renksiz";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_goz.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    goz = "-";
                                }
                                else{
                                    findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_gozDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    gozDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopEsharf(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Şapka var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Evet var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharp = "var";
                                }
                                else{
                                    findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharpDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Hayır yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharp = "yok";
                                }
                                else{
                                    findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharpDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    shapkaEsharp = "-";
                                }
                                else{
                                    findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    shapkaEsharpDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopGozlukk(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Gözlük var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Numaralı gözlük",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozluk.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozluk = "numarali";
                                }
                                else{
                                    findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozlukDiger = "numaraki";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Güneş gözlüğü",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozluk.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozluk = "gunes";
                                }
                                else{
                                    findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozlukDiger = "gunes";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_gozluk.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    gozluk = "-";
                                }
                                else{
                                    findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    gozlukDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopMaske(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Maske bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Renkli",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maske.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maske = "renkli";
                                }
                                else{
                                    findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maskeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maskeDiger = "renkli";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Beyaz",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maske.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maske = "beyaz";
                                }
                                else{
                                    findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maskeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maskeDiger = "beyaz";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_maske.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    maske = "-";
                                }
                                else{
                                    findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_maskeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    maskeDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopSac(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Lütfen saç bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Uzun",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                                if(isBenClicked){
                                    findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sach.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sach = "uzun";
                                }
                                else{
                                    findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sachDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sachDiger = "uzun";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Kısa",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sach.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sach = "kisa";
                                }
                                else{
                                    findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sachDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sachDiger = "kisa";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_sach.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    sach = "-";
                                }
                                else{
                                    findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_sachDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    sachDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void  getLastUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://mobiloby.com/_filter/get_last_wanted_info.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("-", ex.getMessage());
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

                        isProfileExist = true;

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);

                            wantedID = c.getString("wanted_id");
                        }

//                        checkData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_Detail2.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    popup("getLast "+username);
                }

            }
        }.execute(null, null, null);
    }

    public void clickTopAtki(View view) {


        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Atkı var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atki.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atki = "var";
                                }
                                else{
                                    findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atkiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atkiDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atki.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atki = "yok";
                                }
                                else{
                                    findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atkiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atkiDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_atki.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    atki = "-";
                                }
                                else{
                                    findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_atkiDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    atkiDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickMidMont(View view) {
        secilenTitle = "Mont";
        popupColorWheel("Mont");
    }

    public void clickMidElbise(View view) {
        secilenTitle = "Elbise";
        popupColorWheel("Elbise");
    }

    public void clickMidUst(View view) {
        secilenTitle = "Üst";
        popupColorWheel("Üst");
    }

    public void clickMidEldiven(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Eldiven var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldiven.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldiven = "var";
                                }
                                else{
                                    findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldivenDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldiven.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldiven = "yok";
                                }
                                else{
                                    findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldivenDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_eldiven.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    eldiven = "-";
                                }
                                else{
                                    findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    eldivenDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickMidDovme(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Dövme var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovme = "var";
                                }
                                else{
                                    findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovmeDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovme = "yok";
                                }
                                else{
                                    findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovmeDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_dovme.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    dovme = "-";
                                }
                                else{
                                    findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    dovmeDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickMidMayo(View view) {
        secilenTitle = "Mayo";
        popupColorWheel("Mayo");
    }

    public void clickBotAyakkabi(View view) {
        secilenTitle = "Ayakkabı";
        popupColorWheel("Ayakkabı");
    }

    public void clickBotAlt(View view) {
        secilenTitle = "Alt";
        popupColorWheel("Alt");
    }

    public void clickTopKupe(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Küpe var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupe.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupe = "var";
                                }
                                else{
                                    findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupeDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupe.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupe = "yok";
                                }
                                else{
                                    findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupeDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_kupe.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    kupe = "-";
                                }
                                else{
                                    findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_kupeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    kupeDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickInformationTamam(View view) {

        isInformationOpen = false;

        isTamam = true;

        if(isBenClicked){

            findViewById(R.id.r_image2).animate()
                    .translationXBy(+300)
                    .setDuration(1000);

            findViewById(R.id.r_information).animate()
                    .translationXBy(1650)
//                    .alpha(0f)
                    .setDuration(1000);


//            findViewById(R.id.r_information).setVisibility(View.GONE);

        }
        else if(isDigerClicked){
//            findViewById(R.id.r_informationDiger).startAnimation(outToRightAnimation());
            findViewById(R.id.r_informationDiger).setVisibility(View.GONE);
        }
    }

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(500);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(500);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_var_yok, null);

        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(title);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void popupColorWheel(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_color_wheel, null);
        popupView = view;

        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(title);

        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        final Bitmap[] bitmap = {BitmapFactory.decodeResource(getResources(), R.drawable.rectangle_color, myOptions)};
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorButtonBackBackground));

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap[0]);
        final Bitmap[] mutableBitmap = {workingBitmap.copy(Bitmap.Config.ARGB_8888, true)};

        imageView = (ImageView)view.findViewById(R.id.color_wheel);

        if(title.equals("Mont")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(montX + montX/5, montY+montY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(montXDiger + montXDiger/5, montYDiger + montYDiger/5, 25, paint);
            }
        }
        else if(title.equals("Elbise")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(elbiseX + elbiseX/5, elbiseY+elbiseY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(elbiseDigerX + elbiseDigerX/5, elbiseDigerY + elbiseDigerY/5, 25, paint);
            }
        }
        else if(title.equals("Üst")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(ustX + ustX/5, ustY + ustY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(ustXDiger + ustXDiger/5, ustYDiger + ustYDiger/5, 25, paint);
            }
        }
        else if(title.equals("Mayo")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(midMayoX + midMayoX/5, midMayoY + midMayoY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(midMayoXDiger + midMayoXDiger/5, midMayoYDiger + midMayoYDiger/5, 25, paint);
            }
        }
        else if(title.equals("Ayakkabı")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(ayakkabiX + ayakkabiX/5, ayakkabiY + ayakkabiY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(ayakkabiXDiger + ayakkabiXDiger/5, ayakkabiYDiger + ayakkabiYDiger/5, 25, paint);
            }
        }
        else if(title.equals("Alt")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(altX + altX/5, altY + altY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(altXDiger + altXDiger/5, altYDiger + altYDiger/5, 25, paint);
            }
        }
        else if(title.equals("Mayo (Alt)")){
            if(isBenClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(botMayoX + botMayoX/5, botMayoY + botMayoY/5, 25, paint);
            }
            else if(isDigerClicked){
                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(botMayoXDiger + botMayoXDiger/5, botMayoYDiger + botMayoYDiger/5, 25, paint);
            }
        }
        imageView.setAdjustViewBounds(true);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(mutableBitmap[0]);


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(title.equals("Mont")){
                    if(isBenClicked){
                        montX = event.getX();
                        montY = event.getY();
                    }
                    else{
                        montXDiger = event.getX();
                        montYDiger = event.getY();
                    }
                }
                else if(title.equals("Elbise")){
                    if(isBenClicked){
                        elbiseX = event.getX();
                        elbiseY = event.getY();
                    }
                    else{
                        elbiseDigerX = event.getX();
                        elbiseDigerY = event.getY();
                    }
                }
                else if(title.equals("Üst")){
                    if(isBenClicked){
                        ustX = event.getX();
                        ustY = event.getY();
                    }
                    else{
                        ustXDiger = event.getX();
                        ustYDiger = event.getY();
                    }
                }
                else if(title.equals("Mayo")){
                    if(isBenClicked){
                        midMayoX = event.getX();
                        midMayoY = event.getY();
                    }
                    else{
                        midMayoXDiger = event.getX();
                        midMayoYDiger = event.getY();
                    }
                }
                else if(title.equals("Ayakkabı")){
                    if(isBenClicked){
                        ayakkabiX = event.getX();
                        ayakkabiY = event.getY();
                    }
                    else{
                        ayakkabiXDiger = event.getX();
                        ayakkabiYDiger = event.getY();
                    }
                }
                else if(title.equals("Alt")){
                    if(isBenClicked){
                        altX = event.getX();
                        altY = event.getY();
                    }
                    else{
                        altXDiger = event.getX();
                        altYDiger = event.getY();
                    }
                }
                else if(title.equals("Mayo (Alt)")){
                    if(isBenClicked){
                        botMayoX = event.getX();
                        botMayoY = event.getY();
                    }
                    else{
                        botMayoXDiger = event.getX();
                        botMayoYDiger = event.getY();
                    }
                }

                mutableBitmap[0] = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

                Canvas canvas = new Canvas(mutableBitmap[0]);
                canvas.drawCircle(event.getX(), event.getY(), 25, paint);
//
                imageView.setAdjustViewBounds(true);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(mutableBitmap[0]);

                return false;
            }
        });

//        Canvas canvas = new Canvas(mutableBitmap);
//        canvas.drawCircle(10, 10, 25, paint);
//        canvas.drawCircle(100, 120, 25, paint);
////
//        imageView.setAdjustViewBounds(true);
//        imageView.setImageBitmap(mutableBitmap);

        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }



    public void clickVAR(View view) {
        builder.dismiss();
    }

    public void clickYOK(View view) {
        builder.dismiss();
    }

    public void clickTarih(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityCategory2_Detail2.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String d = ""+day;
                        if(d.length()==1){
                            d="0"+d;
                        }
                        String m = ""+(month+1);
                        if(m.length()==1){
                            m = "0"+m;
                        }
                        tarih = d+"."+m+"."+year;
                        r_tarih.setText(tarih);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void clickBitir(View view) {
        // save

        if(e_title.getText().toString().length()==0){
            Toast.makeText(this, "Lütfen bu aramaya bir başlık giriniz", Toast.LENGTH_SHORT).show();
        }
        else{
            if(wantedID.equals("-1"))
                insertUserInfo();
            else
                updateUserInfo();
        }

    }

    private void insertUserInfo() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String top = "shapka="+shapkaEsharp+",gozluk="+gozluk+",kupe="+kupe+",maske="+maske+",ruj="+ruj+",sach="+sach+",goz="+goz+",atki="+atki+",kravat="+kravat;
        final String middle = "mont="+mont+",ust="+ust+",eldiven="+eldiven+",dovme="+dovme+",mayo="+mayo+",saat="+saat;
        final String bottom = "ayakkabi="+ayakkabi+",alt="+alt+",mayo="+mayo+",dovme="+altDovme;

        final String topDiger = "shapka="+shapkaEsharpDiger+",gozluk="+gozlukDiger+",kupe="+kupeDiger+",maske="+maskeDiger+",ruj="+rujDiger+",sach="+sachDiger+",goz="+gozDiger+",atki="+atkiDiger+",kravat="+kravatDiger;
        final String middleDiger = "mont="+montDiger+",ust="+ustDiger+",eldiven="+eldivenDiger+",dovme="+dovmeDiger+",mayo="+mayoDiger+",saat="+saatDiger;
        final String bottomDiger = "ayakkabi="+ayakkabiDiger+",alt="+altDovme+",mayo="+mayoDiger+",dovme="+altDovmeDiger;

        final String url = "http://mobiloby.com/_filter/insert_wanted_user_info.php";

        String questions = "-";
        if(questionNumber>=1 && e_q1.getText().toString().length()!=0){
            questions += "Soru:"+e_q1.getText().toString() + " Cevap1:" + e_a11.getText().toString() + " Cevap2:" + e_a12.getText().toString() + " Cevap3:" + e_a13.getText().toString();
        }
        if(questionNumber>=2 && e_q2.getText().toString().length()!=0){
            questions += "Soru:"+e_q2.getText().toString() + " Cevap1:" + e_a21.getText().toString() + " Cevap2:" + e_a22.getText().toString() + " Cevap3:" + e_a23.getText().toString();
        }
        if(questionNumber>=3 && e_q3.getText().toString().length()!=0){
            questions += "Soru:"+e_q3.getText().toString() + " Cevap1:" + e_a31.getText().toString() + " Cevap2:" + e_a32.getText().toString() + " Cevap3:" + e_a33.getText().toString();
        }
        if(questionNumber>=4 && e_q4.getText().toString().length()!=0){
            questions += "Soru:"+e_q4.getText().toString() + " Cevap1:" + e_a41.getText().toString() + " Cevap2:" + e_a42.getText().toString() + " Cevap3:" + e_a43.getText().toString();
        }
        if(questionNumber>=5 && e_q5.getText().toString().length()!=0){
            questions += "Soru:"+e_q5.getText().toString() + " Cevap1:" + e_a51.getText().toString() + " Cevap2:" + e_a52.getText().toString() + " Cevap3:" + e_a53.getText().toString();
        }

        String finalQuestions = questions;
        String lben = "-", ldiger="-", aben="-", adiger="-";
        if(spinnerYerBen.getSelectedItemPosition()>0)
            lben = spinnerYerBen.getSelectedItem().toString();
        if(spinnerYerDiger.getSelectedItemPosition()>0)
            ldiger = spinnerYerDiger.getSelectedItem().toString();

        if(spinnerEylemBen.getSelectedItemPosition()>0)
            aben = spinnerEylemBen.getSelectedItem().toString();
        if(spinnerEylemDiger.getSelectedItemPosition()>0)
            adiger = spinnerEylemDiger.getSelectedItem().toString();

        String finalAben = aben;
        String finalAdiger = adiger;
        String finalLben = lben;
        String finalLdiger = ldiger;
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("giyim_top", top);
                jsonData.put("giyim_middle", middle);
                jsonData.put("giyim_bottom", bottom);
                jsonData.put("giyim_top_diger", topDiger);
                jsonData.put("giyim_middle_diger", middleDiger);
                jsonData.put("giyim_bottom_diger", bottomDiger);
                jsonData.put("wanted_title", e_title.getText().toString());
                jsonData.put("wanted_user_name", "wanted_username");
                jsonData.put("wanted_question", finalQuestions);
                jsonData.put("location_ben", finalLben);
                jsonData.put("location_diger", finalLdiger);
                jsonData.put("gender_ben", cinsiyetBen);
                jsonData.put("gender_diger", cinsiyetDiger);
                jsonData.put("activity_ben", finalAben);
                jsonData.put("activity_diger", finalAdiger);
                jsonData.put("wanted_user_tarih", tarih);

                System.out.println("ben: " + cinsiyetBen + " diger: " + cinsiyetDiger);

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("-", ex.getMessage());
                    }
                }
                return String.valueOf(success);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
                    getLastUserInfo();
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    popup("insert");
                }

            }
        }.execute(null, null, null);
    }

    private void updateUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String top = "shapka="+shapkaEsharp+",gozluk="+gozluk+",kupe="+kupe+",maske="+maske+",ruj="+ruj+",sach="+sach+",goz="+goz+",atki="+atki+",kravat="+kravat;
        final String middle = "mont="+mont+",ust="+ust+",eldiven="+eldiven+",dovme="+dovme+",mayo="+mayo+",saat="+saat;
        final String bottom = "ayakkabi="+ayakkabi+",alt="+alt+",mayo="+mayo+",dovme="+altDovme;

        final String topDiger = "shapka="+shapkaEsharpDiger+",gozluk="+gozlukDiger+",kupe="+kupeDiger+",maske="+maskeDiger+",ruj="+rujDiger+",sach="+sachDiger+",goz="+gozDiger+",atki="+atkiDiger+",kravat="+kravatDiger;
        final String middleDiger = "mont="+montDiger+",ust="+ustDiger+",eldiven="+eldivenDiger+",dovme="+dovmeDiger+",mayo="+mayoDiger+",saat="+saatDiger;
        final String bottomDiger = "ayakkabi="+ayakkabiDiger+",alt="+altDovme+",mayo="+mayoDiger+",dovme="+altDovmeDiger;

        final String url = "http://mobiloby.com/_filter/update_wanted_info.php";

        String questions = "";
        if(questionNumber==1 && e_q1.getText().toString().length()!=0){
            questions += "Soru:"+e_q1.getText().toString() + " Cevap1:" + e_a11.getText().toString() + " Cevap2:" + e_a12.getText().toString() + " Cevap3:" + e_a13.getText().toString();
        }
        if(questionNumber==2 && e_q2.getText().toString().length()!=0){
            questions += "Soru:"+e_q2.getText().toString() + " Cevap1:" + e_a21.getText().toString() + " Cevap2:" + e_a22.getText().toString() + " Cevap3:" + e_a23.getText().toString();
        }
        if(questionNumber==3 && e_q3.getText().toString().length()!=0){
            questions += "Soru:"+e_q3.getText().toString() + " Cevap1:" + e_a31.getText().toString() + " Cevap2:" + e_a32.getText().toString() + " Cevap3:" + e_a33.getText().toString();
        }
        if(questionNumber==4 && e_q4.getText().toString().length()!=0){
            questions += "Soru:"+e_q4.getText().toString() + " Cevap1:" + e_a41.getText().toString() + " Cevap2:" + e_a42.getText().toString() + " Cevap3:" + e_a43.getText().toString();
        }
        if(questionNumber==5 && e_q5.getText().toString().length()!=0){
            questions += "Soru:"+e_q5.getText().toString() + " Cevap1:" + e_a51.getText().toString() + " Cevap2:" + e_a52.getText().toString() + " Cevap3:" + e_a53.getText().toString();
        }

        String finalQuestions = questions;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
                jsonData.put("user_name", username);
                jsonData.put("giyim_top", top);
                jsonData.put("giyim_middle", middle);
                jsonData.put("giyim_bottom", bottom);
                jsonData.put("giyim_top_diger", topDiger);
                jsonData.put("giyim_middle_diger", middleDiger);
                jsonData.put("giyim_bottom_diger", bottomDiger);
                jsonData.put("wanted_title", e_title.getText().toString());
                jsonData.put("wanted_user_name", "wanted_username");
                jsonData.put("wanted_question", finalQuestions);
                jsonData.put("location_ben", spinnerYerBen.getSelectedItem().toString());
                jsonData.put("location_diger", spinnerYerDiger.getSelectedItem().toString());
                jsonData.put("gender_ben", cinsiyetBen);
                jsonData.put("gender_diger", cinsiyetDiger);
                jsonData.put("activity_ben", spinnerEylemBen.getSelectedItem().toString());
                jsonData.put("activity_diger", spinnerEylemDiger.getSelectedItem().toString());
                jsonData.put("wanted_user_tarih", tarih);

                int success = 0;
                try {

                    jsonObject = new JSONObject(jsonParser.sendPostRequestForImage(url, jsonData));

                    success = jsonObject.getInt("success");

                } catch (Exception ex) {
                    if (ex.getMessage() != null) {
                        Log.e("-", ex.getMessage());
                    }
                }
                return String.valueOf(success);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(String res) {

                progressDialog.dismiss();

                if (res.equals("1")) {
//                    getUserInfo();
                }
                else{
                    popup("update");
                }

            }
        }.execute(null, null, null);
    }

    public void clickInformationDigerTamam(View view) {
        isTamamDiger = true;
        isInformationOpen = false;

        Toast.makeText(this, "clickInfoTamamDiger", Toast.LENGTH_SHORT).show();

            findViewById(R.id.r_image2).animate()
                    .translationXBy(+300)
                    .setDuration(1000);

            findViewById(R.id.r_informationDiger).animate()
                    .translationXBy(1650)
//                    .alpha(0f)
                    .setDuration(1000);

    }

    public void clickTopRuj(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Ruj/Makyaj var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_ruj.setTextColor(getResources().getColor(R.color.colorWhite));
                                    ruj = "var";
                                }
                                else{
                                    findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_rujDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    rujDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_ruj.setTextColor(getResources().getColor(R.color.colorWhite));
                                    ruj = "yok";
                                }
                                else{
                                    findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_rujDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    rujDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_ruj.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    ruj = "-";
                                }
                                else{
                                    findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_rujDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    rujDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopKravat(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Kravat/Papyon var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravat = "var";
                                }
                                else{
                                    findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravatDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravat = "yok";
                                }
                                else{
                                    findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravatDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_kravat.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    kravat = "-";
                                }
                                else{
                                    findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_kravatDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    kravatDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickMidSaat(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Saat var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saat = "var";
                                }
                                else{
                                    findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saatDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saat = "yok";
                                }
                                else{
                                    findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saatDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_saat.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    saat = "-";
                                }
                                else{
                                    findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saatDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickBotMayo(View view) {
        secilenTitle = "Mayo (Alt)";
        popupColorWheel("Mayo (Alt)");
    }

    public void clickBotDovme(View view) {

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Dövme var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        R.color.colorWhite,
                        R.color.colorPalet1,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovme = "var";
                                }
                                else{
                                    findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovmeDiger = "var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        R.color.colorWhite,
                        R.color.colorPalet2,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovme = "yok";
                                }
                                else{
                                    findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovmeDiger = "yok";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Sil",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(isBenClicked){
                                    findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_altDovme.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    altDovme = "-";
                                }
                                else{
                                    findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                                    altDovmeDiger = "-";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Vazgeç",
                        R.color.colorWhite,
                        R.color.colorDarkGray,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }

    public void clickTopLeft(View view) {
        if(secilenTitle.equals("Mont") && isBenClicked){
            mont = "topLeft";
        }
        else if(secilenTitle.equals("Mont") && isDigerClicked){
            montDiger = "topLeft";
        }
        else if(secilenTitle.equals("Üst") && isBenClicked){
            ust = "topLeft";
        }
        else if(secilenTitle.equals("Üst") && isDigerClicked){
            ustDiger = "topLeft";
        }
        else if(secilenTitle.equals("Mayo") && isBenClicked){
            mayo = "topLeft";
        }
        else if(secilenTitle.equals("Mayo") && isDigerClicked){
            mayoDiger = "topLeft";
        }
        else if(secilenTitle.equals("Ayakkabı") && isBenClicked){
            ayakkabi = "topLeft";
        }
        else if(secilenTitle.equals("Ayakkabı") && isDigerClicked){
            ayakkabiDiger = "topLeft";
        }
        else if(secilenTitle.equals("Alt") && isBenClicked){
            alt = "topLeft";
        }
        else if(secilenTitle.equals("Alt") && isDigerClicked){
            altDiger = "topLeft";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isBenClicked){
            altMayo = "topLeft";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isDigerClicked){
            altMayoDiger = "topLeft";
        }
    }

    public void clickTopRight(View view) {
        if(secilenTitle.equals("Mont") && isBenClicked){
            mont = "topRight";
        }
        else if(secilenTitle.equals("Mont") && isDigerClicked){
            montDiger = "topRight";
        }
        else if(secilenTitle.equals("Üst") && isBenClicked){
            ust = "topRight";
        }
        else if(secilenTitle.equals("Üst") && isDigerClicked){
            ustDiger = "topRight";
        }
        else if(secilenTitle.equals("Mayo") && isBenClicked){
            mayo = "topRight";
        }
        else if(secilenTitle.equals("Mayo") && isDigerClicked){
            mayoDiger = "topRight";
        }
        else if(secilenTitle.equals("Ayakkabı") && isBenClicked){
            ayakkabi = "topRight";
        }
        else if(secilenTitle.equals("Ayakkabı") && isDigerClicked){
            ayakkabiDiger = "topRight";
        }
        else if(secilenTitle.equals("Alt") && isBenClicked){
            alt = "topRight";
        }
        else if(secilenTitle.equals("Alt") && isDigerClicked){
            altDiger = "topRight";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isBenClicked){
            altMayo = "topRight";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isDigerClicked){
            altMayoDiger = "topRight";
        }
    }

    public void clickBottomLeft(View view) {
        if(secilenTitle.equals("Mont") && isBenClicked){
            mont = "bottomLeft";
        }
        else if(secilenTitle.equals("Mont") && isDigerClicked){
            montDiger = "bottomLeft";
        }
        else if(secilenTitle.equals("Üst") && isBenClicked){
            ust = "bottomLeft";
        }
        else if(secilenTitle.equals("Üst") && isDigerClicked){
            ustDiger = "bottomLeft";
        }
        else if(secilenTitle.equals("Mayo") && isBenClicked){
            mayo = "bottomLeft";
        }
        else if(secilenTitle.equals("Mayo") && isDigerClicked){
            mayoDiger = "bottomLeft";
        }
        else if(secilenTitle.equals("Ayakkabı") && isBenClicked){
            ayakkabi = "bottomLeft";
        }
        else if(secilenTitle.equals("Ayakkabı") && isDigerClicked){
            ayakkabiDiger = "bottomLeft";
        }
        else if(secilenTitle.equals("Alt") && isBenClicked){
            alt = "bottomLeft";
        }
        else if(secilenTitle.equals("Alt") && isDigerClicked){
            altDiger = "bottomLeft";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isBenClicked){
            altMayo = "bottomLeft";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isDigerClicked){
            altMayoDiger = "bottomLeft";
        }
    }

    public void clickBottomRight(View view) {
        if(secilenTitle.equals("Mont") && isBenClicked){
            mont = "bottomRight";
        }
        else if(secilenTitle.equals("Mont") && isDigerClicked){
            montDiger = "bottomRight";
        }
        else if(secilenTitle.equals("Üst") && isBenClicked){
            ust = "bottomRight";
        }
        else if(secilenTitle.equals("Üst") && isDigerClicked){
            ustDiger = "bottomRight";
        }
        else if(secilenTitle.equals("Mayo") && isBenClicked){
            mayo = "bottomRight";
        }
        else if(secilenTitle.equals("Mayo") && isDigerClicked){
            mayoDiger = "bottomRight";
        }
        else if(secilenTitle.equals("Ayakkabı") && isBenClicked){
            ayakkabi = "bottomRight";
        }
        else if(secilenTitle.equals("Ayakkabı") && isDigerClicked){
            ayakkabiDiger = "bottomRight";
        }
        else if(secilenTitle.equals("Alt") && isBenClicked){
            alt = "bottomRight";
        }
        else if(secilenTitle.equals("Alt") && isDigerClicked){
            altDiger = "bottomRight";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isBenClicked){
            altMayo = "bottomRight";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isDigerClicked){
            altMayoDiger = "bottomRight";
        }
    }

    public void clickTamam(View view) {
        builder.dismiss();
        Toast.makeText(this, "Renk kaydedildi", Toast.LENGTH_SHORT).show();

        if(secilenTitle.equals("Mont")){
            if(isBenClicked){
                findViewById(R.id.r_midMont).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_mont.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_midMontDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_montDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Elbise")){
            if(isBenClicked){
                findViewById(R.id.r_midElbise).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_elbise.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_midElbiseDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_elbiseDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Üst")){
            if(isBenClicked){
                findViewById(R.id.r_midUst).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_ust.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_midUstDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_ustDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Mayo")){
            if(isBenClicked){
                findViewById(R.id.r_midMayo).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_mayo.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_midMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_mayoDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Ayakkabı")){
            if(isBenClicked){
                findViewById(R.id.r_botAyakkabi).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_ayakkabi.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_botAyakkabiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_ayakkabiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Alt")){
            if(isBenClicked){
                findViewById(R.id.r_botAlt).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_alt.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_botAltDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_altDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        else if(secilenTitle.equals("Mayo (Alt)")){
            if(isBenClicked){
                findViewById(R.id.r_botMayo).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_altMayo.setTextColor(getResources().getColor(R.color.colorWhite));
            }
            else{
                findViewById(R.id.r_botMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                t_altMayoDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }

    }

    public void clickVazgec(View view) {
        builder.dismiss();
    }

    public void clickInsertQuestion(View view) {

//        View v;
//        EditText e_question, e_answer1, e_answer2, e_answer3;
//
//        int listLength = listView.getChildCount();
//        for (int i = 0; i < listLength; i++)
//        {
//            v = listView.getChildAt(i);
//            e_question = (EditText) v.findViewById(R.id.e_question);
//            e_answer1 = v.findViewById(R.id.e_answer1);
//            e_answer2 = v.findViewById(R.id.e_answer2);
//            e_answer3 = v.findViewById(R.id.e_answer3);
//            questionObjects.get(i).setQuestion(e_question.getText().toString());
//            questionObjects.get(i).setAnswer1(e_answer1.getText().toString());
//            questionObjects.get(i).setAnswer2(e_answer2.getText().toString());
//            questionObjects.get(i).setAnswer3(e_answer3.getText().toString());
//        }

        makeVisible();
    }

    private void makeVisible() {

        if(questionNumber<5){
            findViewById(R.id.i_2).setVisibility(View.GONE);
            findViewById(R.id.i_3).setVisibility(View.GONE);
            findViewById(R.id.i_4).setVisibility(View.GONE);
            findViewById(R.id.i_5).setVisibility(View.GONE);

            questionNumber++;
            if(questionNumber==2){
                findViewById(R.id.l_2).setVisibility(View.VISIBLE);
                findViewById(R.id.i_2).setVisibility(View.VISIBLE);
            }
            else if(questionNumber==3){
                findViewById(R.id.l_3).setVisibility(View.VISIBLE);
                findViewById(R.id.i_3).setVisibility(View.VISIBLE);
            }
            else if(questionNumber==4){
                findViewById(R.id.l_4).setVisibility(View.VISIBLE);
                findViewById(R.id.i_4).setVisibility(View.VISIBLE);
            }
            else if(questionNumber==5){
                findViewById(R.id.l_5).setVisibility(View.VISIBLE);
                findViewById(R.id.i_5).setVisibility(View.VISIBLE);
            }
        }
        else{
            Toast.makeText(this, "En fazla 5 soru ekleyebilirsiniz", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickInvisible(View view) {
        if(questionNumber==2){
            e_q2.setText("-");
            e_a21.setText("-");
            e_a22.setText("-");
            e_a23.setText("-");
            findViewById(R.id.l_2).setVisibility(View.INVISIBLE);
        }
        else if(questionNumber==3){
            e_q3.setText("-");
            e_a31.setText("-");
            e_a32.setText("-");
            e_a33.setText("-");
            findViewById(R.id.l_3).setVisibility(View.INVISIBLE);
        }
        else if(questionNumber==4){
            e_q4.setText("-");
            e_a41.setText("-");
            e_a42.setText("-");
            e_a43.setText("-");
            findViewById(R.id.l_4).setVisibility(View.INVISIBLE);
        }
        else if(questionNumber==5){
            e_q5.setText("-");
            e_a51.setText("-");
            e_a52.setText("-");
            e_a53.setText("-");
            findViewById(R.id.l_5).setVisibility(View.INVISIBLE);
        }
        questionNumber--;
    }

    public void clickSil(View view) {

        builder.dismiss();

        if(secilenTitle.equals("Mont")){
            if(isBenClicked){
                findViewById(R.id.r_midMont).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_mont.setTextColor(getResources().getColor(R.color.colorDarkGray));
                mont = "-";
                montX = -1;
                montY = -1;
            }
            else{
                findViewById(R.id.r_midMontDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_montDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                montDiger = "-";
                montXDiger = -1;
                montYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Elbise")){
            if(isBenClicked){
                findViewById(R.id.r_midElbise).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_elbise.setTextColor(getResources().getColor(R.color.colorDarkGray));
                elbise = "-";
                elbiseX = -1;
                elbiseY = -1;
            }
            else{
                findViewById(R.id.r_midElbiseDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_elbiseDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                montDiger = "-";
                montXDiger = -1;
                montYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Üst")){
            if(isBenClicked){
                findViewById(R.id.r_midUst).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_ust.setTextColor(getResources().getColor(R.color.colorDarkGray));
                ustX = -1;
                ustY = -1;
                ust = "-";
            }
            else{
                findViewById(R.id.r_midUstDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_ustDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                ustDiger = "-";
                ustXDiger = -1;
                ustYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Mayo")){
            if(isBenClicked){
                findViewById(R.id.r_midMayo).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_mayo.setTextColor(getResources().getColor(R.color.colorDarkGray));
                mayo = "-";
                midMayoX = -1;
                midMayoY = -1;
            }
            else{
                findViewById(R.id.r_midMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_mayoDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                mayoDiger = "-";
                midMayoXDiger = -1;
                midMayoYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Ayakkabı")){
            if(isBenClicked){
                findViewById(R.id.r_botAyakkabi).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_ayakkabi.setTextColor(getResources().getColor(R.color.colorDarkGray));
                ayakkabi = "-";
                ayakkabiX = -1;
                ayakkabiY = -1;
            }
            else{
                findViewById(R.id.r_botAyakkabiDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_ayakkabiDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                ayakkabiDiger = "-";
                ayakkabiXDiger = -1;
                ayakkabiYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Alt")){
            if(isBenClicked){
                findViewById(R.id.r_botAlt).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_alt.setTextColor(getResources().getColor(R.color.colorDarkGray));
                alt = "-";
                altX = -1;
                altY = -1;
            }
            else{
                findViewById(R.id.r_botAltDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_altDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                altDiger = "-";
                altXDiger = -1;
                altYDiger = -1;
            }
        }
        else if(secilenTitle.equals("Mayo (Alt)")){
            if(isBenClicked){
                findViewById(R.id.r_botMayo).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_altMayo.setTextColor(getResources().getColor(R.color.colorDarkGray));
                altMayo = "-";
                botMayoX = -1;
                botMayoY = -1;
            }
            else{
                findViewById(R.id.r_botMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background));
                t_altMayoDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
                altMayoDiger = "-";
                botMayoXDiger = -1;
                botMayoYDiger = -1;
            }
        }

    }

    public void clickCenter(View view) {
        if(secilenTitle.equals("Mont") && isBenClicked){
            mont = "center";
        }
        else if(secilenTitle.equals("Mont") && isDigerClicked){
            montDiger = "center";
        }
        else if(secilenTitle.equals("Üst") && isBenClicked){
            ust = "center";
        }
        else if(secilenTitle.equals("Üst") && isDigerClicked){
            ustDiger = "center";
        }
        else if(secilenTitle.equals("Mayo") && isBenClicked){
            mayo = "center";
        }
        else if(secilenTitle.equals("Mayo") && isDigerClicked){
            mayoDiger = "center";
        }
        else if(secilenTitle.equals("Ayakkabı") && isBenClicked){
            ayakkabi = "center";
        }
        else if(secilenTitle.equals("Ayakkabı") && isDigerClicked){
            ayakkabiDiger = "center";
        }
        else if(secilenTitle.equals("Alt") && isBenClicked){
            alt = "center";
        }
        else if(secilenTitle.equals("Alt") && isDigerClicked){
            altDiger = "center";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isBenClicked){
            altMayo = "center";
        }
        else if(secilenTitle.equals("Mayo (Alt)") && isDigerClicked){
            altMayoDiger = "center";
        }
    }


}