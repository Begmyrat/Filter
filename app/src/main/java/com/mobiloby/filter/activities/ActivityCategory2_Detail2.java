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
import android.graphics.Color;
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
import com.mobiloby.filter.models.QuestionObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActivityCategory2_Detail2 extends AppCompatActivity{

    RelativeLayout r_information;
    SharedPreferences preferences;
    boolean isBenClicked=true, isDigerClicked=false, isSoruClicked=false, isProfileExist=false;

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
    Boolean isTamam = false, isTamamDiger = false, isInformationOpen = false, isEditable=true;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username="-", secilenTitle="-", wantedID="-1";
    Bundle extras;
    ImageView imageView;
    View popupView;
    float montX=-1, montY=-1, montXDiger=-1, montYDiger=-1, elbiseX=-1, elbiseY=-1, elbiseDigerX=-1, elbiseDigerY=-1, ustX=-1, ustXDiger=1, ustY=-1, ustYDiger=-1, midMayoX=-1, midMayoXDiger=-1, midMayoY=-1, midMayoYDiger=-1, ayakkabiX=-1, ayakkabiXDiger=-1, ayakkabiY=-1, ayakkabiYDiger=-1, altX=-1, altXDiger=-1, altY=-1, altYDiger=-1, botMayoX=-1, botMayoXDiger=-1, botMayoY=-1, botMayoYDiger=-1;
    EditText e_title, e_question, e_answer1, e_answer2, e_answer3;
    Spinner spinnerYerBen, spinnerYerDiger, spinnerEylemBen, spinnerEylemDiger, spinnerSehirBen;
    ImageView i_body;
    RadioButton rb_erkekBen, rb_kizBen, rb_erkekDiger, rb_kizDiger, rb_bugun, rb_dun, rb_tarihSec;
    ArrayList<QuestionObject> questionObjects = new ArrayList<>();
    int questionIndex = 0;
    int colorP = R.color.pdlg_color_gray;
    int colorN = R.color.pdlg_color_gray;
    int textP = R.color.black;
    int textN = R.color.black;

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
                    cinsiyetBen = "Kiz";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));
                }
                else {
                    cinsiyetBen = "Erkek";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
                }
            }
        });

        rg_genderDiger.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.r_kizDiger) {
                    cinsiyetDiger = "Kiz";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));
                }
                else {
                    cinsiyetDiger = "Erkek";
                    i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
                }
            }
        });

        if(!wantedID.equals("-1")){
            findViewById(R.id.r_bitir).setVisibility(View.GONE);
            getUserInfo();
        }
        else{
            findViewById(R.id.r_bitir).setVisibility(View.VISIBLE);
        }
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

        rb_erkekBen = findViewById(R.id.r_oglanBen);
        rb_erkekDiger = findViewById(R.id.r_oglanDiger);
        rb_kizBen = findViewById(R.id.r_kizBen);
        rb_kizDiger = findViewById(R.id.r_kizDiger);
        rb_bugun = findViewById(R.id.r_bugun);
        rb_dun = findViewById(R.id.r_dun);
        rb_tarihSec = findViewById(R.id.r_tarihsec);

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

            try {
                wantedID = extras.getString("wanted_id");
                if(!wantedID.equals("-1")){
                    isEditable = false;
                }
                findViewById(R.id.r_bitir).setVisibility(View.GONE);
            }catch (Exception e){
                wantedID = "-1";
            }
        }

        e_title = findViewById(R.id.e_title);
        spinnerYerBen = findViewById(R.id.spinner_locationBen);
        spinnerYerDiger = findViewById(R.id.spinner_locationDiger);
        spinnerEylemBen = findViewById(R.id.spinner_activityBen);
        spinnerEylemDiger = findViewById(R.id.spinner_activityDiger);
        spinnerSehirBen = findViewById(R.id.spinner_sehirBen);

        questionObjects.add(new QuestionObject("","","",""));
        questionObjects.add(new QuestionObject("","","",""));
        questionObjects.add(new QuestionObject("","","",""));

    }

    HashMap<String, String> dataMap = new HashMap<>();

    public void initSelectedButtonColors(){
        colorP = R.color.pdlg_color_gray;
        colorN = R.color.pdlg_color_gray;
        textP = R.color.black;
        textN = R.color.black;
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

            if(cinsiyetDiger.equals("erkek"))
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
            else
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));

            t_ben.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_diger.setTextColor(getResources().getColor(R.color.colorWhite));
            t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

            findViewById(R.id.scrollview_beno).setVisibility(View.VISIBLE);
            findViewById(R.id.scrollView_questions).setVisibility(View.INVISIBLE);

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

            if(cinsiyetBen.equals("erkek"))
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.fred));
            else
                i_body.setImageDrawable(getResources().getDrawable(R.drawable.dafne));

            t_ben.setTextColor(getResources().getColor(R.color.colorWhite));
            t_diger.setTextColor(getResources().getColor(R.color.colorDarkGray));
            t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

            findViewById(R.id.scrollview_beno).setVisibility(View.VISIBLE);
            findViewById(R.id.scrollView_questions).setVisibility(View.INVISIBLE);

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

        initSelectedButtonColors();

        if(isBenClicked && goz.equals("Renkli")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && goz.equals("Renksiz")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && gozDiger.equals("Renkli")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && gozDiger.equals("Renksiz")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }


        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Göz bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Renkli",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    Toast.makeText(ActivityCategory2_Detail2.this, "clicked BEN", Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_goz.setTextColor(getResources().getColor(R.color.colorWhite));
                                    goz = "Renkli";
                                }
                                else{
                                    Toast.makeText(ActivityCategory2_Detail2.this, "clicked O", Toast.LENGTH_SHORT).show();
                                    findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozDiger = "Renkli";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Renksiz",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_goz.setTextColor(getResources().getColor(R.color.colorWhite));
                                    goz = "Renksiz";
                                }
                                else{
                                    findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozDiger = "Renksiz";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && shapkaEsharp.equals("Var")){
            colorP = R.color.colorPalet2;
            textP = R.color.colorWhite;
        }
        else if(isBenClicked && shapkaEsharp.equals("Yok")){
            colorN = R.color.colorPalet2;
            textN = R.color.colorWhite;
        }
        else if(isDigerClicked && shapkaEsharpDiger.equals("Var")){
            colorP = R.color.colorPalet2;
            textP = R.color.colorWhite;
        }
        else if(isDigerClicked && shapkaEsharpDiger.equals("Yok")){
            colorN = R.color.colorPalet2;
            textN = R.color.colorWhite;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Şapka var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharp = "Var";
                                }
                                else{
                                    findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharpDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharp = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    shapkaEsharpDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && gozluk.equals("Numaralı gözlük")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && gozluk.equals("Güneş gözlüğü")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && gozlukDiger.equals("Numaralı gözlük")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && gozlukDiger.equals("Güneş gözlüğü")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Gözlük var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Numaralı gözlük",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozluk.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozluk = "Numaralı gözlük";
                                }
                                else{
                                    findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozlukDiger = "Numaralı gözlük";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Güneş gözlüğü",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozluk.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozluk = "Güneş gözlüğü";
                                }
                                else{
                                    findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    gozlukDiger = "Güneş gözlüğü";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && maske.equals("Renkli")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && maske.equals("Beyaz")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && maskeDiger.equals("Renkli")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && maskeDiger.equals("Beyaz")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Maske bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Renkli",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maske.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maske = "Renkli";
                                }
                                else{
                                    findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maskeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maskeDiger = "Renkli";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Beyaz",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maske.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maske = "Beyaz";
                                }
                                else{
                                    findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_maskeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    maskeDiger = "Beyaz";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && sach.equals("Uzun")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && sach.equals("Kısa")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && sachDiger.equals("Uzun")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && sachDiger.equals("Kısa")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Lütfen saç bilgilerini giriniz")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Uzun",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sach.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sach = "Uzun";
                                }
                                else{
                                    findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sachDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sachDiger = "Uzun";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Kısa",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sach.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sach = "Kısa";
                                }
                                else{
                                    findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_sachDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    sachDiger = "Kısa";
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
                                if(!isEditable) return;
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

        final String url = "https://mobiloby.com/_filter/get_last_wanted_info.php";

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

        initSelectedButtonColors();

        if(isBenClicked && atki.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && atki.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && atkiDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && atkiDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Atkı var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atki.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atki = "Var";
                                }
                                else{
                                    findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atkiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atkiDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atki.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atki = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_atkiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    atkiDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && eldiven.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && eldiven.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && eldivenDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && eldivenDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Eldiven var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldiven.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldiven = "Var";
                                }
                                else{
                                    findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldivenDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldiven.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldiven = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    eldivenDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && dovme.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && dovme.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && dovmeDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && dovmeDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Dövme var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovme = "Var";
                                }
                                else{
                                    findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovmeDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovme = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    dovmeDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && kupe.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && kupe.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && kupeDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && kupeDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Küpe var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupe.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupe = "Var";
                                }
                                else{
                                    findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupeDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupe.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupe = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kupeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kupeDiger = "Yok";
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
                                if(!isEditable) return;
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
//            else
//                updateUserInfo();
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
        final String middle = "mont="+mont+",ust="+ust+",eldiven="+eldiven+",dovme="+dovme+",mayo="+mayo+",saat="+saat + ",elbise="+elbise;
        final String bottom = "ayakkabi="+ayakkabi+",alt="+alt+",mayo="+mayo+",dovme="+altDovme;

        final String topDiger = "shapka="+shapkaEsharpDiger+",gozluk="+gozlukDiger+",kupe="+kupeDiger+",maske="+maskeDiger+",ruj="+rujDiger+",sach="+sachDiger+",goz="+gozDiger+",atki="+atkiDiger+",kravat="+kravatDiger;
        final String middleDiger = "mont="+montDiger+",ust="+ustDiger+",eldiven="+eldivenDiger+",dovme="+dovmeDiger+",mayo="+mayoDiger+",saat="+saatDiger +",elbise="+elbiseDiger;
        final String bottomDiger = "ayakkabi="+ayakkabiDiger+",alt="+altDovme+",mayo="+mayoDiger+",dovme="+altDovmeDiger;

        final String url = "https://mobiloby.com/_filter/insert_wanted_user_info.php";

        String questions = "";

        for(int i=0;i<questionObjects.size();i++){
            if(questionObjects.get(i).getQuestion().length()>0){
                questions += "SoruMobiloby:" + questionObjects.get(i).getQuestion() + "CevapMobiloby:" + questionObjects.get(i).getAnswer1() + "CevapMobiloby:" + questionObjects.get(i).getAnswer2() + "CevapMobiloby:" + questionObjects.get(i).getAnswer3();
            }
        }

        String finalQuestions = questions;
        String lben = "-", ldiger="-", aben="-", adiger="-", cben="-", cdiger="-";
        if(spinnerYerBen.getSelectedItemPosition()>0)
            lben = spinnerYerBen.getSelectedItem().toString();
        if(spinnerYerDiger.getSelectedItemPosition()>0)
            ldiger = spinnerYerDiger.getSelectedItem().toString();

        if(spinnerEylemBen.getSelectedItemPosition()>0)
            aben = spinnerEylemBen.getSelectedItem().toString();
        if(spinnerEylemDiger.getSelectedItemPosition()>0)
            adiger = spinnerEylemDiger.getSelectedItem().toString();

        if(spinnerSehirBen.getSelectedItemPosition()>0)
            cben = spinnerSehirBen.getSelectedItem().toString();

        String finalAben = aben;
        String finalAdiger = adiger;
        String finalLben = lben;
        String finalLdiger = ldiger;
        String finalSehir = cben;
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
                jsonData.put("sehir", finalSehir);
                jsonData.put("location_diger", finalLdiger);
                jsonData.put("gender_ben", cinsiyetBen);
                jsonData.put("gender_diger", cinsiyetDiger);
                jsonData.put("activity_ben", finalAben);
                jsonData.put("activity_diger", finalAdiger);
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

        final String url = "https://mobiloby.com/_filter/update_wanted_info.php";


        final String finalQuestions="";

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
//                    popup("update");
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

        initSelectedButtonColors();

        if(isBenClicked && ruj.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && ruj.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && rujDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && rujDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Ruj/Makyaj var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_ruj.setTextColor(getResources().getColor(R.color.colorWhite));
                                    ruj = "Var";
                                }
                                else{
                                    findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_rujDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    rujDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_ruj.setTextColor(getResources().getColor(R.color.colorWhite));
                                    ruj = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_rujDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    rujDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && kravat.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && kravat.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && kravatDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && kravatDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Kravat/Papyon var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravat = "Var";
                                }
                                else{
                                    findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravatDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravat = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_kravatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    kravatDiger = "Yok";
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

        initSelectedButtonColors();
        
        if(isBenClicked && saat.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && saat.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && saatDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && saatDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Saat var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saat = "Var";
                                }
                                else{
                                    findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saatDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saat.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saat = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    saatDiger = "Yok";
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
                                if(!isEditable) return;
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

        initSelectedButtonColors();

        if(isBenClicked && altDovme.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isBenClicked && altDovme.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }
        else if(isDigerClicked && altDovmeDiger.equals("Var")){
            textP = R.color.colorWhite;
            colorP = R.color.colorPalet2;
        }
        else if(isDigerClicked && altDovmeDiger.equals("Yok")){
            textN = R.color.colorWhite;
            colorN = R.color.colorPalet2;
        }

        PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Filter")
                .setMessage("Dövme var mıydı?")
                .setIcon(R.drawable.ic_f_char)
                .addButton(
                        "Var",
                        textP,
                        colorP,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovme = "Var";
                                }
                                else{
                                    findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovmeDiger = "Var";
                                }
                                prettyDialog.dismiss();
                            }
                        }
                )
                .addButton(
                        "Yok",
                        textN,
                        colorN,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                if(!isEditable) return;
                                if(isBenClicked){
                                    findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_altDovme.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovme = "Yok";
                                }
                                else{
                                    findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
                                    t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
                                    altDovmeDiger = "Yok";
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
                                if(!isEditable) return;
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
        if(!isEditable) return;
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

    public void clickSil(View view) {
        if(!isEditable) return;
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

    public void  getUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "https://mobiloby.com/_filter/get_wanted_user_info.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);

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

                        isProfileExist = true;

                        for(int i=0;i<pro.length();i++){
                            JSONObject c = pro.getJSONObject(i);
                            String giyim_top = c.getString("giyim_top");
                            String giyim_middle = c.getString("giyim_middle");
                            String giyim_bottom = c.getString("giyim_bottom");
                            String giyim_top_diger = c.getString("giyim_top_diger");
                            String giyim_middle_diger = c.getString("giyim_middle_diger");
                            String giyim_bottom_diger = c.getString("giyim_bottom_diger");
                            String title = c.getString("wanted_title");
                            String adSoyad = c.getString("wanted_user_name");
                            String tarih = c.getString("wanted_tarih");
                            String gender_ben  = c.getString("gender_ben");
                            String gender_diger  = c.getString("gender_diger");
                            String location_ben = c.getString("location_ben");
                            String location_diger = c.getString("location_diger");
                            String activity_ben = c.getString("activity_ben");
                            String activity_diger = c.getString("activity_diger");
                            String wanted_user_tarih = c.getString("wanted_user_tarih");
                            String wanted_question = c.getString("wanted_question");

                            String[] questions = wanted_question.split("SoruMobiloby:");
                            for(int j=1;j<questions.length;j++){
                                String[] elements = questions[j].split("CevapMobiloby:");
                                questionObjects.get(j-1).setQuestion(elements[0]);
                                questionObjects.get(j-1).setAnswer1(elements[1]);
                                questionObjects.get(j-1).setAnswer2(elements[2]);
                                questionObjects.get(j-1).setAnswer3(elements[3]);
                            }

                            if(gender_ben.equals("Erkek")){
                                rb_erkekBen.setChecked(true);
                            }
                            else if(gender_ben.equals("Kiz")){
                                rb_kizBen.setChecked(true);
                            }

                            if(gender_diger.equals("Erkek")){
                                rb_erkekDiger.setChecked(true);
                            }
                            else if(gender_diger.equals("Kiz")){
                                rb_kizDiger.setChecked(true);
                            }

                            if(wanted_user_tarih.equals("dun")){
                                rb_dun.setChecked(true);
                            }
                            else if(wanted_user_tarih.equals("bugun")){
                                rb_bugun.setChecked(true);
                            }
                            else if(wanted_user_tarih.length()>2){
                                rb_tarihSec.setChecked(true);
                                rb_tarihSec.setText(wanted_user_tarih);
                            }


                            wantedID = c.getString("wanted_id");
                            e_title.setText(title);

                            String[] gt = giyim_top.split(",");
                            String[] gm = giyim_middle.split(",");
                            String[] gb = giyim_bottom.split(",");

                            String[] gtd = giyim_top_diger.split(",");
                            String[] gmd = giyim_middle_diger.split(",");
                            String[] gbd = giyim_bottom_diger.split(",");

                            shapkaEsharp = gt[0].substring(gt[0].indexOf("=")+1);
                            gozluk = gt[1].substring(gt[1].indexOf("=")+1);
                            kupe = gt[2].substring(gt[2].indexOf("=")+1);
                            maske = gt[3].substring(gt[3].indexOf("=")+1);
                            ruj = gt[4].substring(gt[4].indexOf("=")+1);
                            sach = gt[5].substring(gt[5].indexOf("=")+1);
                            goz = gt[6].substring(gt[6].indexOf("=")+1);
                            atki = gt[7].substring(gt[7].indexOf("=")+1);
                            kravat = gt[8].substring(gt[8].indexOf("=")+1);

                            shapkaEsharpDiger = gtd[0].substring(gt[0].indexOf("=")+1);
                            gozlukDiger = gtd[1].substring(gt[1].indexOf("=")+1);
                            kupeDiger = gtd[2].substring(gt[2].indexOf("=")+1);
                            maskeDiger = gtd[3].substring(gt[3].indexOf("=")+1);
                            rujDiger = gtd[4].substring(gt[4].indexOf("=")+1);
                            sachDiger = gtd[5].substring(gt[5].indexOf("=")+1);
                            gozDiger = gtd[6].substring(gt[6].indexOf("=")+1);
                            atkiDiger = gtd[7].substring(gt[7].indexOf("=")+1);
                            kravatDiger = gtd[8].substring(gt[8].indexOf("=")+1);

                            mont = gm[0].substring(gm[0].indexOf("=")+1);
                            ust = gm[1].substring(gm[1].indexOf("=")+1);
                            eldiven = gm[2].substring(gm[2].indexOf("=")+1);
                            dovme = gm[3].substring(gm[3].indexOf("=")+1);
                            mayo = gm[4].substring(gm[4].indexOf("=")+1);
                            saat = gm[5].substring(gm[5].indexOf("=")+1);

                            montDiger = gmd[0].substring(gm[0].indexOf("=")+1);
                            ustDiger = gmd[1].substring(gm[1].indexOf("=")+1);
                            eldivenDiger = gmd[2].substring(gm[2].indexOf("=")+1);
                            dovmeDiger = gmd[3].substring(gm[3].indexOf("=")+1);
                            mayoDiger = gmd[4].substring(gm[4].indexOf("=")+1);
                            saatDiger = gmd[5].substring(gm[5].indexOf("=")+1);

                            ayakkabi = gb[0].substring(gb[0].indexOf("=")+1);
                            alt = gb[1].substring(gb[1].indexOf("=")+1);
                            mayo = gb[2].substring(gb[2].indexOf("=")+1);
                            dovme = gb[3].substring(gb[3].indexOf("=")+1);

                            ayakkabiDiger = gbd[0].substring(gb[0].indexOf("=")+1);
                            altDiger = gbd[1].substring(gb[1].indexOf("=")+1);
                            mayoDiger = gbd[2].substring(gb[2].indexOf("=")+1);
                            dovmeDiger = gbd[3].substring(gb[3].indexOf("=")+1);
                        }

                        checkData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_Detail2.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
//                    popup("get "+wantedID);
                }

            }
        }.execute(null, null, null);
    }

    public void checkData(){
        if(!goz.contains("-"))  { findViewById(R.id.r_topGoz).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_goz.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!gozDiger.contains("-")) { findViewById(R.id.r_topGozDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_gozDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!ruj.contains("-"))  { findViewById(R.id.r_topRuj).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_ruj.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!rujDiger.contains("-")) { findViewById(R.id.r_topRujDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_rujDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!kravat.contains("-"))   { findViewById(R.id.r_topKravat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_kravat.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!kravatDiger.contains("-"))  { findViewById(R.id.r_topKravatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_kravatDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!shapkaEsharp.contains("-"))        { findViewById(R.id.r_topEsharf).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_shapkaEsharp.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!shapkaEsharpDiger.contains("-"))   { findViewById(R.id.r_topEsharfDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_shapkaEsharpDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!maske.contains("-"))        { findViewById(R.id.r_topMaske).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_maske.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!maskeDiger.contains("-"))   { findViewById(R.id.r_topMaskeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_maskeDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!sach.contains("-"))          { findViewById(R.id.r_topSac).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_sach.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!sachDiger.contains("-"))     { findViewById(R.id.r_topSacDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_sachDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!gozluk.contains("-"))      { findViewById(R.id.r_topGozluk).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_gozluk.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!gozlukDiger.contains("-")) { findViewById(R.id.r_topGozlukDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_gozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!atki.contains("-"))        { findViewById(R.id.r_topAtki).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_atki.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!atkiDiger.contains("-"))   { findViewById(R.id.r_topAtkiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_atkiDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!kupe.contains("-"))        { findViewById(R.id.r_topKupe).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_kupe.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!kupeDiger.contains("-"))   { findViewById(R.id.r_topKupeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_kupeDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }

        if(!mont.contains("-"))        { findViewById(R.id.r_midMont).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_mont.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!montDiger.contains("-"))   { findViewById(R.id.r_midMontDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_montDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!elbise.contains("-"))          { findViewById(R.id.r_midElbise).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_elbise.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!elbiseDiger.contains("-"))     { findViewById(R.id.r_midElbiseDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_elbiseDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!ust.contains("-"))         { findViewById(R.id.r_midUst).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_ust.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!ustDiger.contains("-"))    { findViewById(R.id.r_midUstDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_ustDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!eldiven.contains("-"))         { findViewById(R.id.r_midEldiven).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_eldiven.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!eldivenDiger.contains("-"))    { findViewById(R.id.r_midEldivenDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_eldivenDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!dovme.contains("-"))       { findViewById(R.id.r_midDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_dovme.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!dovmeDiger.contains("-"))  { findViewById(R.id.r_midDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_dovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!mayo.contains("-"))         { findViewById(R.id.r_midMayo).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_mayo.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!mayoDiger.contains("-"))   { findViewById(R.id.r_midMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_mayoDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!saat.contains("-"))        { findViewById(R.id.r_midSaat).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_saat.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!saatDiger.contains("-"))   { findViewById(R.id.r_midSaatDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_saatDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }

        if(!ayakkabi.contains("-"))         { findViewById(R.id.r_botAyakkabi).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_ayakkabi.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!ayakkabiDiger.contains("-"))    { findViewById(R.id.r_botAyakkabiDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_ayakkabiDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!alt.contains("-"))      { findViewById(R.id.r_botAlt).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_alt.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!altDiger.contains("-")) { findViewById(R.id.r_botAltDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_altDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!altMayo.contains("-"))      { findViewById(R.id.r_botMayo).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_altMayo.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!altMayoDiger.contains("-")) { findViewById(R.id.r_botMayoDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_altMayoDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!altDovme.contains("-"))          { findViewById(R.id.r_botDovme).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_altDovme.setTextColor(getResources().getColor(R.color.colorWhite)); }
        if(!altDovmeDiger.contains("-"))     { findViewById(R.id.r_botDovmeDiger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));t_altDovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite)); }

    }

    public void clickFirstQuestion(View view) {
        questionIndex = 0;
        popupQuestion("İlk Soruyu Giriniz");
    }

    public void clickSecondQuestion(View view) {
        questionIndex = 1;
        popupQuestion("İkinci Soruyu Giriniz");
    }

    public void clickThirdQuestion(View view) {
        questionIndex = 2;
        popupQuestion("Üçüncü Soruyu Giriniz");
    }

    public void popupQuestion(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_question, null);

        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(title);
        e_question = view.findViewById(R.id.e_question);
        e_answer1 = view.findViewById(R.id.e_answer1);
        e_answer2 = view.findViewById(R.id.e_answer2);
        e_answer3 = view.findViewById(R.id.e_answer3);

        if(questionObjects.get(questionIndex).getQuestion().length()>0){
            e_question.setText(questionObjects.get(questionIndex).getQuestion());
        }
        else{
            e_question.setHint("Sorunuzu buraya yazınız");
        }

        if(questionObjects.get(questionIndex).getAnswer1().length()>0){
            e_answer1.setText(questionObjects.get(questionIndex).getAnswer1());
        }
        else{
            e_answer1.setHint("Cevap 1");
        }

        if(questionObjects.get(questionIndex).getAnswer2().length()>0){
            e_answer2.setText(questionObjects.get(questionIndex).getAnswer2());
        }
        else{
            e_answer2.setHint("Cevap 2");
        }

        if(questionObjects.get(questionIndex).getAnswer3().length()>0){
            e_answer3.setText(questionObjects.get(questionIndex).getAnswer3());
        }
        else{
            e_answer3.setHint("Cevap 3");
        }


        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickInsertQuestion(View view) {
        builder.dismiss();
        questionObjects.get(questionIndex).setQuestion(e_question.getText().toString());
        questionObjects.get(questionIndex).setAnswer1(e_answer1.getText().toString());
        questionObjects.get(questionIndex).setAnswer2(e_answer2.getText().toString());
        questionObjects.get(questionIndex).setAnswer3(e_answer3.getText().toString());
    }
}