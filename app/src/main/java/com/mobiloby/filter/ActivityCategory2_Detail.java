package com.mobiloby.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityCategory2_Detail extends AppCompatActivity implements MyWantedRecycleListAdapter.ItemClickListener {

    RecyclerView recyclerView;
    MyWantedRecycleListAdapter recycleListAdapter;
    JSONParser jsonParser;
    JSONObject jsonObject;
    String username="", soru1, soru2, soru3;
    String top_goz="-", top_esharf="-", top_kolye="-", top_shapka="-", top_gozluk="-", top_kupe="-", top_kulaklik="-", top_dovme="-";
    String mid_tshirt="-", mid_mont="-", mid_dovme="-", mid_kazak="-", mid_kolSaat="-", mid_palto="-", mid_gomlek="-", mid_bileklik="-";
    String bot_jeans="-", bot_pantalon="-", bot_etek="-", bot_bot="-", bot_ayakkabi="-";
    String top_gozDiger="-", top_esharfDiger="-", top_kolyeDiger="-", top_shapkaDiger="-", top_gozlukDiger="-", top_kupeDiger="-", top_kulaklikDiger="-", top_dovmeDiger="-";
    String mid_tshirtDiger="-", mid_montDiger="-", mid_dovmeDiger="-", mid_kazakDiger="-", mid_kolSaatDiger="-", mid_paltoDiger="-", mid_gomlekDiger="-", mid_bileklikDiger="-";
    String bot_jeansDiger="-", bot_pantalonDiger="-", bot_etekDiger="-", bot_botDiger="-", bot_ayakkabiDiger="-";
    String wanted_boy = "", wanted_username = "", wanted_title = "";
    RelativeLayout r_topGoz, r_topEsharf, r_topKolye, r_topShapka, r_topGozluk, r_topKupe, r_topKulaklik, r_topDovme, r_midTshirt, r_midMont, r_midDovme, r_midKazak, r_midKolSati, r_midPalto, r_midGomlek, r_midBileklik;
    RelativeLayout r_botJeans, r_botPantalon, r_botEtek, r_botBot, r_botAyakkabi;
    RelativeLayout r_topGozDiger, r_topEsharfDiger, r_topKolyeDiger, r_topShapkaDiger, r_topGozlukDiger, r_topKupeDiger, r_topKulaklikDiger, r_topDovmeDiger;
    RelativeLayout r_midTshirtDiger, r_midMontDiger, r_midDovmeDiger, r_midKazakDiger, r_midKolSatiDiger, r_midPaltoDiger, r_midGomlekDiger, r_midBileklikDiger;
    RelativeLayout r_botJeansDiger, r_botPantalonDiger, r_botEtekDiger, r_botBotDiger, r_botAyakkabiDiger;
    TextView t_topGoz, t_topEsharf, t_topKolye, t_topShapka, t_topGozluk, t_topKupe, t_topKulaklik, t_topDovme, t_midTshirt, t_midMont, t_midDovme, t_midKazak, t_midKolSati, t_midPalto, t_midGomlek, t_midBileklik;
    TextView t_botJeans, t_botPantalon, t_botEtek, t_botBot, t_botAyakkabi;
    TextView t_topGozDiger, t_topEsharfDiger, t_topKolyeDiger, t_topShapkaDiger, t_topGozlukDiger, t_topKupeDiger, t_topKulaklikDiger, t_topDovmeDiger, t_midTshirtDiger, t_midMontDiger, t_midDovmeDiger, t_midKazakDiger, t_midKolSatiDiger, t_midPaltoDiger, t_midGomlekDiger, t_midBileklikDiger;
    TextView t_botJeansDiger, t_botPantalonDiger, t_botEtekDiger, t_botBotDiger, t_botAyakkabiDiger;
    Bundle extras;
    RecyclerView.LayoutManager layoutManager;
    MyInfoListAdapter adapter;
    ArrayList<InfoObject> infoList, infoListCopy;
    FlowLayout f_bash, f_govde, f_bacak;
    String category = "giyimTop", hint="Örnek: Siyah şapka, mavi göz, sarı saç...", info="", isUpdate="", wantedID="";
    ArrayList<String> categories;
    Dialog builder;
    EditText e_info, e_adSoyad, e_boy, e_title, e_soru1, e_soru2, e_soru3;
    Boolean isDigerClicked = false, isBenClicked = true, isProfileExist = false, isSoruClicked = false;
    Boolean isTGoz = true, isTSharp = true, isTKolye = false, isTShapka = true, isTGozluk = false, isTKupe = false, isTKulaklik = false, isTDovme = false;
    Boolean isMTshirt = true, isMMont = true, isMDovme = false, isMKazak = true, isMKolSaati = false, isMPalto = true, isMGomlek = true, isMBileklik = false;
    Boolean isBJeans = true, isBPantalon = true, isBEtek = true, isBBot = true, isBAyakkabi = true;
    Boolean isTGozDiger = true, isTSharpDiger = true, isTKolyeDiger = false, isTShapkaDiger = true, isTGozlukDiger = false, isTKupeDiger = false, isTKulaklikDiger = false, isTDovmeDiger = false;
    Boolean isMTshirtDiger = true, isMMontDiger = true, isMDovmeDiger = false, isMKazakDiger = true, isMKolSaatiDiger = false, isMPaltoDiger = true, isMGomlekDiger = true, isMBileklikDiger = false;
    Boolean isBJeansDiger = true, isBPantalonDiger = true, isBEtekDiger = true, isBBotDiger = true, isBAyakkabiDiger = true;
    TextView t_diger, t_ben, t_soru;
    String type="", color="";
    View viewPopup;
    int selectedColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2__detail);

        prepareMe();

        if(isUpdate.equals("true")){
            wantedID = extras.getString("wanted_id");
            getUserInfo();
        }

    }

    private void prepareMe() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorBackground));// set status background white
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViewById(R.id.r_main).getBackground().setTint(getResources().getColor(R.color.colorBackground));

        recyclerView = findViewById(R.id.recycleView);
        e_adSoyad = findViewById(R.id.e_adSoyad);
        e_boy = findViewById(R.id.e_boy);
        e_title = findViewById(R.id.e_title);
        e_soru1 = findViewById(R.id.e_soru1);
        e_soru2 = findViewById(R.id.e_soru2);
        e_soru3 = findViewById(R.id.e_soru3);
        infoList = new ArrayList<>();
        infoListCopy = new ArrayList<>();
        categories = new ArrayList<>();
        categories.add("Baş");
        categories.add("Gövde");
        categories.add("Bacak");
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recycleListAdapter = new MyWantedRecycleListAdapter(this, categories);
        recyclerView.setAdapter(recycleListAdapter);
        adapter = new MyInfoListAdapter(this, infoList);
        recycleListAdapter.notifyDataSetChanged();
        recycleListAdapter.setClickListener(this);
        t_ben = findViewById(R.id.t_ben);
        t_diger = findViewById(R.id.t_diger);
        t_soru = findViewById(R.id.t_soru);
        f_bash = findViewById(R.id.f_bash);
        f_govde = findViewById(R.id.f_govde);
        f_bacak = findViewById(R.id.f_bacak);

        r_topDovme = findViewById(R.id.r_topDovme);
        r_topEsharf = findViewById(R.id.r_topEsharf);
        r_topGoz = findViewById(R.id.r_topGoz);
        r_topGozluk = findViewById(R.id.r_topGozluk);
        r_topKolye = findViewById(R.id.r_topKolye);
        r_topKulaklik = findViewById(R.id.r_topKulaklik);
        r_topKupe = findViewById(R.id.r_topKupe);
        r_topShapka = findViewById(R.id.r_topShapka);

        r_midBileklik = findViewById(R.id.r_midBileklik);
        r_midDovme = findViewById(R.id.r_midDovme);
        r_midGomlek = findViewById(R.id.r_midGomlek);
        r_midKazak = findViewById(R.id.r_midKazak);
        r_midKolSati = findViewById(R.id.r_midKolSaati);
        r_midMont = findViewById(R.id.r_midMont);
        r_midPalto = findViewById(R.id.r_midPalto);
        r_midTshirt = findViewById(R.id.r_midTshirt);

        r_botAyakkabi = findViewById(R.id.r_botAyakkabi);
        r_botBot = findViewById(R.id.r_botBot);
        r_botEtek = findViewById(R.id.r_botEtek);
        r_botJeans = findViewById(R.id.r_botJeans);
        r_botPantalon = findViewById(R.id.r_botPantalon);


        t_topDovme = findViewById(R.id.t_topDovme);
        t_topEsharf = findViewById(R.id.t_topEsharf);
        t_topGoz = findViewById(R.id.t_topGoz);
        t_topGozluk = findViewById(R.id.t_topGozluk);
        t_topKolye = findViewById(R.id.t_topKolye);
        t_topKulaklik = findViewById(R.id.t_topKulaklik);
        t_topKupe = findViewById(R.id.t_topKupe);
        t_topShapka = findViewById(R.id.t_topShapka);

        t_midBileklik = findViewById(R.id.t_midBileklik);
        t_midDovme = findViewById(R.id.t_midDovme);
        t_midGomlek = findViewById(R.id.t_midGomlek);
        t_midKazak = findViewById(R.id.t_midKazak);
        t_midKolSati = findViewById(R.id.t_midKolSaati);
        t_midMont = findViewById(R.id.t_midMont);
        t_midPalto = findViewById(R.id.t_midPalto);
        t_midTshirt = findViewById(R.id.t_midTshirt);

        t_botAyakkabi = findViewById(R.id.t_botAyakkabi);
        t_botBot = findViewById(R.id.t_botBot);
        t_botEtek = findViewById(R.id.t_botEtek);
        t_botJeans = findViewById(R.id.t_botJeans);
        t_botPantalon = findViewById(R.id.t_botPantalon);

        //ds

        r_topDovmeDiger = findViewById(R.id.r_topDovme_diger);
        r_topEsharfDiger = findViewById(R.id.r_topEsharf_diger);
        r_topGozDiger = findViewById(R.id.r_topGoz_diger);
        r_topGozlukDiger = findViewById(R.id.r_topGozluk_diger);
        r_topKolyeDiger = findViewById(R.id.r_topKolye_diger);
        r_topKulaklikDiger = findViewById(R.id.r_topKulaklik_diger);
        r_topKupeDiger = findViewById(R.id.r_topKupe_diger);
        r_topShapkaDiger = findViewById(R.id.r_topShapka_diger);

        r_midBileklikDiger = findViewById(R.id.r_midBileklik_diger);
        r_midDovmeDiger = findViewById(R.id.r_midDovme_diger);
        r_midGomlekDiger = findViewById(R.id.r_midGomlek_diger);
        r_midKazakDiger = findViewById(R.id.r_midKazak_diger);
        r_midKolSatiDiger = findViewById(R.id.r_midKolSaati_diger);
        r_midMontDiger = findViewById(R.id.r_midMont_diger);
        r_midPaltoDiger = findViewById(R.id.r_midPalto_diger);
        r_midTshirtDiger = findViewById(R.id.r_midTshirt_diger);

        r_botAyakkabiDiger = findViewById(R.id.r_botAyakkabi_diger);
        r_botBotDiger = findViewById(R.id.r_botBot_diger);
        r_botEtekDiger = findViewById(R.id.r_botEtek_diger);
        r_botJeansDiger = findViewById(R.id.r_botJeans_diger);
        r_botPantalonDiger = findViewById(R.id.r_botPantalon_diger);


        t_topDovmeDiger = findViewById(R.id.t_topDovme_diger);
        t_topEsharfDiger = findViewById(R.id.t_topEsharf_diger);
        t_topGozDiger = findViewById(R.id.t_topGoz_diger);
        t_topGozlukDiger = findViewById(R.id.t_topGozluk_diger);
        t_topKolyeDiger = findViewById(R.id.t_topKolye_diger);
        t_topKulaklikDiger = findViewById(R.id.t_topKulaklik_diger);
        t_topKupeDiger = findViewById(R.id.t_topKupe_diger);
        t_topShapkaDiger = findViewById(R.id.t_topShapka_diger);

        t_midBileklikDiger = findViewById(R.id.t_midBileklik_diger);
        t_midDovmeDiger = findViewById(R.id.t_midDovme_diger);
        t_midGomlekDiger = findViewById(R.id.t_midGomlek_diger);
        t_midKazakDiger = findViewById(R.id.t_midKazak_diger);
        t_midKolSatiDiger = findViewById(R.id.t_midKolSaati_diger);
        t_midMontDiger = findViewById(R.id.t_midMont_diger);
        t_midPaltoDiger = findViewById(R.id.t_midPalto_diger);
        t_midTshirtDiger = findViewById(R.id.t_midTshirt_diger);

        t_botAyakkabiDiger = findViewById(R.id.t_botAyakkabi_diger);
        t_botBotDiger = findViewById(R.id.t_botBot_diger);
        t_botEtekDiger = findViewById(R.id.t_botEtek_diger);
        t_botJeansDiger = findViewById(R.id.t_botJeans_diger);
        t_botPantalonDiger = findViewById(R.id.t_botPantalon_diger);

        extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
            isUpdate = extras.getString("isUpdate");
        }

        MyWantedRecycleListAdapter.row_index = 0;
        recycleListAdapter.notifyDataSetChanged();
    }

    public void clickBack(View view) {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {

//        view.setBackground(getResources().getDrawable(R.drawable.recycle_background_clicked));

        if(position==0 && isBenClicked){
            makeInvisible();
            f_bash.setVisibility(View.VISIBLE);
        }
        else if(position==0 && !isBenClicked){
            makeInvisible();
            findViewById(R.id.f_bash_diger).setVisibility(View.VISIBLE);
        }
        else if(position==1 && isBenClicked){
            makeInvisible();
            f_govde.setVisibility(View.VISIBLE);
        }
        else if(position==1 && !isBenClicked){
            makeInvisible();
            findViewById(R.id.f_govde_diger).setVisibility(View.VISIBLE);
        }
        else if(position==2 && isBenClicked){
            makeInvisible();
            f_bacak.setVisibility(View.VISIBLE);
        }
        else if(position==2 && !isBenClicked){
            makeInvisible();
            findViewById(R.id.f_bacak_diger).setVisibility(View.VISIBLE);
        }

        MyWantedRecycleListAdapter.row_index=position;
        recycleListAdapter.notifyDataSetChanged();

    }

    public void makeInvisible(){
        f_bash.setVisibility(View.GONE);
        f_govde.setVisibility(View.GONE);
        f_bacak.setVisibility(View.GONE);
        findViewById(R.id.f_bash_diger).setVisibility(View.GONE);
        findViewById(R.id.f_govde_diger).setVisibility(View.GONE);
        findViewById(R.id.f_bacak_diger).setVisibility(View.GONE);
    }

//    public void clickInsertInfo(View view) {
//        popup();
//    }

    public void popup(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_uyari, null);
        TextView t_title = view.findViewById(R.id.e_info);
        t_title.setText(title);
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }



//    public void clickClose(View view) {
//        builder.dismiss();
//    }

//    public void clickInsertTodo(View view) {
//        info = e_info.getText().toString();
//        builder.dismiss();
//        insertInfo();
//    }

//    private void insertInfo() {
//        infoListCopy.add(new InfoObject(category, info));
//        infoList.clear();
//        for(int i=0;i<infoListCopy.size();i++){
//            if(infoListCopy.get(i).getCategory().equals(category)){
//                infoList.add(infoListCopy.get(i));
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }

    public void clickTemizle(View view) {
        e_adSoyad.setText("");
    }

    public void clickDiger(View view) {
        isDigerClicked = true;
        isBenClicked = false;
        isSoruClicked = false;

        findViewById(R.id.f_bash).setVisibility(View.GONE);
        findViewById(R.id.f_govde).setVisibility(View.GONE);
        findViewById(R.id.f_bacak).setVisibility(View.GONE);
        findViewById(R.id.f_bash_diger).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView_questions).setVisibility(View.GONE);

        findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
        t_diger.setTextColor(getResources().getColor(R.color.colorWhite));
        findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_ben.setTextColor(getResources().getColor(R.color.colorDarkGray));
        findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

        MyWantedRecycleListAdapter.row_index = 0;
        recycleListAdapter.notifyDataSetChanged();
    }

    public void clickSoru(View view) {
        isDigerClicked = false;
        isBenClicked = false;
        isSoruClicked = true;

        findViewById(R.id.f_bash).setVisibility(View.GONE);
        findViewById(R.id.f_govde).setVisibility(View.GONE);
        findViewById(R.id.f_bacak).setVisibility(View.GONE);
        findViewById(R.id.f_bash_diger).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView).setVisibility(View.GONE);
        findViewById(R.id.scrollView_questions).setVisibility(View.VISIBLE);

        findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_diger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_ben.setTextColor(getResources().getColor(R.color.colorDarkGray));
        findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
        t_soru.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    public void clickBen(View view) {
        isDigerClicked = false;
        isBenClicked = true;
        isSoruClicked = false;

        findViewById(R.id.f_bash).setVisibility(View.VISIBLE);
        findViewById(R.id.f_bash_diger).setVisibility(View.GONE);
        findViewById(R.id.f_govde_diger).setVisibility(View.GONE);
        findViewById(R.id.f_bacak_diger).setVisibility(View.GONE);
        findViewById(R.id.scrollView).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView_questions).setVisibility(View.GONE);

        findViewById(R.id.r_diger).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_diger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        findViewById(R.id.r_ben).setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
        t_ben.setTextColor(getResources().getColor(R.color.colorWhite));
        findViewById(R.id.r_soru).setBackground(getResources().getDrawable(R.drawable.back_background));
        t_soru.setTextColor(getResources().getColor(R.color.colorDarkGray));

        MyWantedRecycleListAdapter.row_index = 0;
        recycleListAdapter.notifyDataSetChanged();
    }

    public void clickSave(View view) {

        wanted_boy = e_boy.getText().toString();
        wanted_title = e_title.getText().toString();
        wanted_username = e_adSoyad.getText().toString();
        soru1 = e_soru1.getText().toString();
        soru2 = e_soru2.getText().toString();
        soru3 = e_soru3.getText().toString();

        if(soru1.length()>0 && soru2.length()>0 && soru3.length()>0) {
            if(wanted_title.length()>0) {
                if (isProfileExist) {
                    updateUserInfo();
                } else {
                    insertUserInfo();
                }
            }
            else{
                popup("title");
            }
        }
        else{
            popup("Sorular eklenmedi");
        }
    }

    public void clickBoyTemizle(View view) {
        e_boy.setText("");
    }

    public void  getUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_wanted_user_info.php";

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
                            String boy = c.getString("wanted_boy");
                            String tarih = c.getString("wanted_tarih");
                            soru1 = c.getString("wanted_soru_1");
                            soru2 = c.getString("wanted_soru_2");
                            soru3 = c.getString("wanted_soru_3");

                            e_adSoyad.setText(adSoyad);
                            e_boy.setText(boy);
                            wantedID = c.getString("wanted_id");
                            e_title.setText(title);
                            wanted_title = title;
                            wanted_boy = boy;
                            wanted_username = adSoyad;
                            e_soru1.setText(soru1);
                            e_soru2.setText(soru2);
                            e_soru3.setText(soru3);

                            String[] gt = giyim_top.split(",");
                            String[] gm = giyim_middle.split(",");
                            String[] gb = giyim_bottom.split(",");

                            String[] gtd = giyim_top_diger.split(",");
                            String[] gmd = giyim_middle_diger.split(",");
                            String[] gbd = giyim_bottom_diger.split(",");
                            top_goz = gt[0].substring(gt[0].indexOf("=")+1);
                            top_esharf = gt[1].substring(gt[1].indexOf("=")+1);
                            top_kolye = gt[2].substring(gt[2].indexOf("=")+1);
                            top_shapka = gt[3].substring(gt[3].indexOf("=")+1);
                            top_gozluk = gt[4].substring(gt[4].indexOf("=")+1);
                            top_kupe = gt[5].substring(gt[5].indexOf("=")+1);
                            top_kulaklik = gt[6].substring(gt[6].indexOf("=")+1);
                            top_dovme = gt[7].substring(gt[7].indexOf("=")+1);

                            mid_tshirt = gm[0].substring(gm[0].indexOf("=")+1);
                            mid_mont = gm[1].substring(gm[1].indexOf("=")+1);
                            mid_dovme = gm[2].substring(gm[2].indexOf("=")+1);
                            mid_kazak = gm[3].substring(gm[3].indexOf("=")+1);
                            mid_kolSaat = gm[4].substring(gm[4].indexOf("=")+1);
                            mid_palto = gm[5].substring(gm[5].indexOf("=")+1);
                            mid_gomlek = gm[6].substring(gm[6].indexOf("=")+1);
                            mid_bileklik = gm[7].substring(gm[7].indexOf("=")+1);

                            bot_jeans = gb[0].substring(gb[0].indexOf("=")+1);
                            bot_pantalon = gb[1].substring(gb[1].indexOf("=")+1);
                            bot_etek = gb[2].substring(gb[2].indexOf("=")+1);
                            bot_bot = gb[3].substring(gb[3].indexOf("=")+1);
                            bot_ayakkabi = gb[4].substring(gb[4].indexOf("=")+1);

                            top_gozDiger = gtd[0].substring(gtd[0].indexOf("=")+1);
                            top_esharfDiger = gtd[1].substring(gtd[1].indexOf("=")+1);
                            top_kolyeDiger = gtd[2].substring(gtd[2].indexOf("=")+1);
                            top_shapkaDiger = gtd[3].substring(gtd[3].indexOf("=")+1);
                            top_gozlukDiger = gtd[4].substring(gtd[4].indexOf("=")+1);
                            top_kupeDiger = gtd[5].substring(gtd[5].indexOf("=")+1);
                            top_kulaklikDiger = gtd[6].substring(gtd[6].indexOf("=")+1);
                            top_dovmeDiger = gtd[7].substring(gtd[7].indexOf("=")+1);

                            mid_tshirtDiger = gmd[0].substring(gmd[0].indexOf("=")+1);
                            mid_montDiger = gmd[1].substring(gmd[1].indexOf("=")+1);
                            mid_dovmeDiger = gmd[2].substring(gmd[2].indexOf("=")+1);
                            mid_kazakDiger = gmd[3].substring(gmd[3].indexOf("=")+1);
                            mid_kolSaatDiger = gmd[4].substring(gmd[4].indexOf("=")+1);
                            mid_paltoDiger = gmd[5].substring(gmd[5].indexOf("=")+1);
                            mid_gomlekDiger = gmd[6].substring(gmd[6].indexOf("=")+1);
                            mid_bileklikDiger = gmd[7].substring(gmd[7].indexOf("=")+1);

                            bot_jeansDiger = gbd[0].substring(gbd[0].indexOf("=")+1);
                            bot_pantalonDiger = gbd[1].substring(gbd[1].indexOf("=")+1);
                            bot_etekDiger = gbd[2].substring(gbd[2].indexOf("=")+1);
                            bot_botDiger = gbd[3].substring(gbd[3].indexOf("=")+1);
                            bot_ayakkabiDiger = gbd[4].substring(gbd[4].indexOf("=")+1);
                        }

                        checkData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_Detail.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    popup("get "+wantedID);
                }

            }
        }.execute(null, null, null);
    }

    public void  getLastUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/get_last_wanted_info.php";

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

                            wantedID = c.getString("wanted_id");
                        }

                        checkData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivityCategory2_Detail.this, "error jiim", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
//                    makeAlert.uyarıVer("E-Mobil Saglyk", "Bir hata oldu. Lütfen tekrar deneyiniz.", ActivityCategory3.this, true);
                    popup("getLast "+username);
                }

            }
        }.execute(null, null, null);
    }

    public void insertUserInfo() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Filter");
        progressDialog.setMessage("İşleminiz gerçekleştiriliyor...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.show();

        final String giyimTop = "goz="+top_goz+",sharp="+top_esharf+",kolye="+top_kolye+",shapka="+top_shapka+",gozluk="+top_gozluk+",kupe="+top_kupe+",kulaklik="+top_kulaklik+",dovme="+top_dovme;
        final String giyimMiddle = "tshirt="+mid_tshirt+",mont="+mid_mont+",dovme="+mid_dovme+",kazak="+mid_kazak+",saat="+mid_kolSaat+",palto="+mid_palto+",gomlek="+mid_gomlek+",bileklik="+mid_bileklik;
        final String giyimBottom = "jeans="+bot_jeans+",pantalon="+bot_pantalon+",etek="+bot_etek+",bot="+bot_bot+",ayakkabi="+bot_ayakkabi;

        final String giyimTopDiger = "goz="+top_gozDiger+",sharp="+top_esharfDiger+",kolye="+top_kolyeDiger+",shapka="+top_shapkaDiger+",gozluk="+top_gozlukDiger+",kupe="+top_kupeDiger+",kulaklik="+top_kulaklikDiger+",dovme="+top_dovmeDiger;
        final String giyimMiddleDiger = "tshirt="+mid_tshirtDiger+",mont="+mid_montDiger+",dovme="+mid_dovmeDiger+",kazak="+mid_kazakDiger+",saat="+mid_kolSaatDiger+",palto="+mid_paltoDiger+",gomlek="+mid_gomlekDiger+",bileklik="+mid_bileklikDiger;
        final String giyimBottomDiger = "jeans="+bot_jeansDiger+",pantalon="+bot_pantalonDiger+",etek="+bot_etekDiger+",bot="+bot_botDiger+",ayakkabi="+bot_ayakkabiDiger;

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/insert_wanted_user_info.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("user_name", username);
                jsonData.put("giyim_top", giyimTop);
                jsonData.put("giyim_middle", giyimMiddle);
                jsonData.put("giyim_bottom", giyimBottom);
                jsonData.put("giyim_top_diger", giyimTopDiger);
                jsonData.put("giyim_middle_diger", giyimMiddleDiger);
                jsonData.put("giyim_bottom_diger", giyimBottomDiger);
                jsonData.put("wanted_title", wanted_title);
                jsonData.put("wanted_boy", wanted_boy);
                jsonData.put("wanted_user_name", wanted_username);
                jsonData.put("wanted_soru_1", soru1);
                jsonData.put("wanted_soru_2", soru2);
                jsonData.put("wanted_soru_3", soru3);

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

        final String giyimTop = "goz="+top_goz+",sharp="+top_esharf+",kolye="+top_kolye+",shapka="+top_shapka+",gozluk="+top_gozluk+",kupe="+top_kupe+",kulaklik="+top_kulaklik+",dovme="+top_dovme;
        final String giyimMiddle = "tshirt="+mid_tshirt+",mont="+mid_mont+",dovme="+mid_dovme+",kazak="+mid_kazak+",saat="+mid_kolSaat+",palto="+mid_palto+",gomlek="+mid_gomlek+",bileklik="+mid_bileklik;
        final String giyimBottom = "jeans="+bot_jeans+",pantalon="+bot_pantalon+",etek="+bot_etek+",bot="+bot_bot+",ayakkabi="+bot_ayakkabi;

        final String giyimTopDiger = "goz="+top_gozDiger+",sharp="+top_esharfDiger+",kolye="+top_kolyeDiger+",shapka="+top_shapkaDiger+",gozluk="+top_gozlukDiger+",kupe="+top_kupeDiger+",kulaklik="+top_kulaklikDiger+",dovme="+top_dovmeDiger;
        final String giyimMiddleDiger = "tshirt="+mid_tshirtDiger+",mont="+mid_montDiger+",dovme="+mid_dovmeDiger+",kazak="+mid_kazakDiger+",saat="+mid_kolSaatDiger+",palto="+mid_paltoDiger+",gomlek="+mid_gomlekDiger+",bileklik="+mid_bileklikDiger;
        final String giyimBottomDiger = "jeans="+bot_jeansDiger+",pantalon="+bot_pantalonDiger+",etek="+bot_etekDiger+",bot="+bot_botDiger+",ayakkabi="+bot_ayakkabiDiger;

        final String url = "http://anneligehazirlaniyorum.com/FilterMobil/update_wanted_info.php";

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                jsonParser = new JSONParser();

                HashMap<String, String> jsonData = new HashMap<>();

                jsonData.put("wanted_id", wantedID);
                jsonData.put("giyim_top", giyimTop);
                jsonData.put("giyim_middle", giyimMiddle);
                jsonData.put("giyim_bottom", giyimBottom);
                jsonData.put("giyim_top_diger", giyimTopDiger);
                jsonData.put("giyim_middle_diger", giyimMiddleDiger);
                jsonData.put("giyim_bottom_diger", giyimBottomDiger);
                jsonData.put("wanted_title", wanted_title);
                jsonData.put("wanted_boy", wanted_boy);
                jsonData.put("wanted_user_name", wanted_username);
                jsonData.put("wanted_soru_1", soru1);
                jsonData.put("wanted_soru_2", soru2);
                jsonData.put("wanted_soru_3", soru3);

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
                    getUserInfo();
                }
                else{
                    popup("update");
                }

            }
        }.execute(null, null, null);
    }

    public void clickTopGoz(View view) {
//        isTGoz = !isTGoz;
        if(isTGoz){
//            r_topGoz.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topGoz.setTextColor(getResources().getColor(R.color.colorWhite));
//            top_goz = "renk";
            popupRenk("top_goz");
        }
        else{
            top_goz = "";
            r_topGoz.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topGoz.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }

    }

    public void clickTopEsharf(View view) {
//        isTSharp = !isTSharp;
        if(isTSharp){
//            top_esharf = "renk";
//            r_topEsharf.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topEsharf.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("top_esharf");
        }
        else{
            top_esharf = "";
            r_topEsharf.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topEsharf.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }

    }

    public void clickTopKolye(View view) {
        isTKolye = !isTKolye;
        if(isTKolye){
            top_kolye = "var";
            r_topKolye.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKolye.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKolye.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kolye = "";
            r_topKolye.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKolye.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopShapka(View view) {
//        isTShapka = !isTShapka;
        if(isTShapka){
//            top_shapka = "renk";
//            r_topShapka.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topShapka.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("top_shapka");
        }
        else{
            top_shapka = "";
            r_topShapka.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topShapka.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopGozlukk(View view) {
        isTGozluk = !isTGozluk;
        if(isTGozluk){
            top_gozluk = "var";
            r_topGozluk.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topGozluk.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topGozluk.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_gozluk = "";
            r_topGozluk.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topGozluk.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopKupe(View view) {
        isTKupe = !isTKupe;
        if(isTKupe){
            top_kupe = "var";
            r_topKupe.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKupe.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKupe.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kupe = "";
            r_topKupe.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKupe.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopKulaklik(View view) {
        isTKulaklik = !isTKulaklik;
        if(isTKulaklik){
            top_kulaklik = "var";
            r_topKulaklik.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKulaklik.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKulaklik.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kulaklik = "";
            r_topKulaklik.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKulaklik.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopDovme(View view) {
        isTDovme = !isTDovme;
        if(isTDovme){
            top_dovme = "var";
            r_topDovme.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topDovme.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topDovme.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_dovme = "";
            r_topDovme.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topDovme.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidTshirt(View view) {
//        isMTshirt = !isMTshirt;
        if(isMTshirt){
//            mid_tshirt = "renk";
//            r_midTshirt.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midTshirt.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_tshirt");
        }
        else{
            mid_tshirt = "";
            r_midTshirt.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midTshirt.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidMont(View view) {
//        isMMont = !isMMont;
        if(isMMont){
//            mid_mont = "renk";
//            r_midMont.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midMont.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_mont");
        }
        else{
            mid_mont = "";
            r_midMont.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midMont.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidDovme(View view) {
        isMDovme = !isMDovme;
        if(isMDovme){
            mid_dovme = "var";
            r_midDovme.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midDovme.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midDovme.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            mid_dovme = "";
            r_midDovme.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midDovme.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidKazak(View view) {
//        isMKazak = !isMKazak;
        if(isMKazak){
//            mid_kazak = "renk";
//            r_midKazak.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midKazak.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_kazak");
        }
        else{
            mid_kazak = "";
            r_midKazak.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midKazak.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidKolSaati(View view) {
        isMKolSaati = !isMKolSaati;
        if(isMKolSaati){
            mid_kolSaat = "var";
            r_midKolSati.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midKolSati.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midKolSati.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else {
            mid_kolSaat = "";
            r_midKolSati.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midKolSati.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidPalto(View view) {
//        isMPalto = !isMPalto;
        if(isMPalto){
//            mid_palto = "renk";
//            r_midPalto.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midPalto.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_palto");
        }
        else{
            mid_palto = "";
            r_midPalto.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midPalto.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidGomlek(View view) {
//        isMGomlek = !isMGomlek;
        if(isMGomlek){
//            mid_gomlek = "renk";
//            r_midGomlek.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midGomlek.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_gomlek");
        }
        else{
            mid_gomlek = "";
            r_midGomlek.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midGomlek.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidBileklik(View view) {
        isMBileklik = !isMBileklik;
        if(isMBileklik){
            mid_bileklik = "var";
            r_midBileklik.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midBileklik.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midBileklik.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            mid_bileklik = "";
            r_midBileklik.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midBileklik.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotJeans(View view) {
//        isBJeans = !isBJeans;
        if(isBJeans){
//            r_botJeans.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botJeans.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_jeans");
        }
        else{
            bot_jeans = "";
            r_botJeans.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botJeans.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotPantalon(View view) {
//        isBPantalon = !isBPantalon;
        if(isBPantalon){
//            r_botPantalon.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botPantalon.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_pantalon");
        }
        else{
            bot_pantalon = "";
            r_botPantalon.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botPantalon.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotEtek(View view) {
//        isBEtek = !isBEtek;
        if(isBEtek){
//            r_botEtek.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botEtek.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_etek");
        }
        else{
            bot_etek = "";
            r_botEtek.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botEtek.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotBot(View view) {
//        isBBot = !isBBot;
        if(isBBot){
//            r_botBot.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botBot.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_bot");
        }
        else{
            bot_bot = "";
            r_botBot.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botBot.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotAyakkabi(View view) {
//        isBAyakkabi = !isBAyakkabi;
        if (isBAyakkabi){
//            r_botAyakkabi.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botAyakkabi.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_ayakkabi");
        }
        else{
            bot_ayakkabi = "";
            r_botAyakkabi.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botAyakkabi.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopGozDiger(View view) {
//        isTGozDiger = !isTGozDiger;
        if(isTGozDiger){
//            r_topGozDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topGozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("top_gozDiger");
        }
        else{
            top_gozDiger = "";
            r_topGozDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topGozDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopEsharfDiger(View view) {
//        isTSharpDiger = !isTSharpDiger;
        if(isTSharpDiger){
//            r_topEsharfDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topEsharfDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("top_esharfDiger");
        }
        else{
            top_esharfDiger = "";
            r_topEsharfDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topEsharfDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopKolyeDiger(View view) {
        isTKolyeDiger = !isTKolyeDiger;
        if(isTKolyeDiger){
            top_kolyeDiger = "var";
            r_topKolyeDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKolyeDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKolyeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kolyeDiger = "";
            r_topKolyeDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKolyeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopShapkaDiger(View view) {
//        isTShapkaDiger = !isTShapkaDiger;
        if(isTShapkaDiger){
//            r_topShapkaDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_topShapkaDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("top_shapkaDiger");
        }
        else{
            top_shapkaDiger = "";
            r_topShapkaDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topShapkaDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopGozlukDiger(View view) {
        isTGozlukDiger = !isTGozlukDiger;
        if(isTGozlukDiger){
            top_gozlukDiger = "var";
            r_topGozlukDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topGozlukDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topGozlukDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_gozlukDiger = "";
            r_topGozlukDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topGozlukDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopKupeDiger(View view) {
        isTKupeDiger = !isTKupeDiger;
        if(isTKupeDiger){
            top_kupeDiger = "var";
            r_topKupeDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKupeDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKupeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kupeDiger = "";
            r_topKupeDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKupeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopKulaklikDiger(View view) {
        isTKulaklikDiger = !isTKulaklikDiger;
        if(isTKulaklikDiger){
            top_kulaklikDiger = "var";
            r_topKulaklikDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topKulaklikDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topKulaklikDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_kulaklikDiger = "";
            r_topKulaklikDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topKulaklikDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTopDovmeDiger(View view) {
        isTDovmeDiger = !isTDovmeDiger;
        if(isTDovmeDiger){
            top_dovmeDiger = "var";
            r_topDovmeDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topDovmeDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_topDovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            top_dovmeDiger = "";
            r_topDovmeDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_topDovmeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidTshirtDiger(View view) {
//        isMTshirtDiger = !isMTshirtDiger;
        if(isMTshirtDiger){
//            r_midTshirtDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midTshirtDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_tshirtDiger");
        }
        else{
            mid_tshirtDiger = "";
            r_midTshirtDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midTshirtDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidMontDiger(View view) {
//        isMMontDiger = !isMMontDiger;
        if(isMMontDiger){
//            r_midMontDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midMontDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_montDiger");
        }
        else{
            mid_montDiger = "";
            r_midMontDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midMontDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidDovmeDiger(View view) {
        isMDovmeDiger = !isMDovmeDiger;
        if(isMDovmeDiger){
            mid_dovmeDiger = "var";
            r_midDovmeDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midDovmeDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midDovmeDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            mid_dovmeDiger = "";
            r_midDovmeDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midDovmeDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidKazakDiger(View view) {
//        isMKazakDiger = !isMKazakDiger;
        if(isMKazakDiger){
//            r_midKazakDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midKazakDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_kazakDiger");
        }
        else{
            mid_kazakDiger = "";
            r_midKazakDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midKazakDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidKolSaatiDiger(View view) {
        isMKolSaatiDiger = !isMKolSaatiDiger;
        if(isMKolSaatiDiger){
            mid_kolSaatDiger = "var";
            r_midKolSatiDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midKolSatiDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midKolSatiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            mid_kolSaatDiger = "";
            r_midKolSatiDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midKolSatiDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidPaltoDiger(View view) {
//        isMPaltoDiger = !isMPaltoDiger;
        if(isMPaltoDiger){
//            r_midPaltoDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midPaltoDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_paltoDiger");
        }
        else{
            mid_paltoDiger = "";
            r_midPaltoDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midPaltoDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidGomlekDiger(View view) {
//        isMGomlekDiger = !isMGomlekDiger;
        if(isMGomlekDiger){
//            r_midGomlekDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_midGomlekDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("mid_gomlekDiger");
        }
        else{
            mid_gomlekDiger = "";
            r_midGomlekDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midGomlekDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickMidBileklikDiger(View view) {
        isMBileklikDiger = !isMBileklikDiger;
        if(isMBileklikDiger){
            mid_bileklikDiger = "var";
            r_midBileklikDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midBileklikDiger.getBackground().setTint(getResources().getColor(R.color.colorDarkGray));
            t_midBileklikDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else{
            mid_bileklikDiger = "";
            r_midBileklikDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_midBileklikDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotJeansDiger(View view) {
//        isBJeansDiger = !isBJeansDiger;
        if(isBJeansDiger){
//            r_botJeansDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botJeansDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_jeansDiger");
        }
        else{
            bot_jeansDiger = "";
            r_botJeansDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botJeansDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotPantalonDiger(View view) {
//        isBPantalonDiger = !isBPantalonDiger;
        if(isBPantalonDiger){
//            r_botPantalonDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botPantalonDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_pantalonDiger");
        }
        else{
            bot_pantalonDiger = "";
            r_botPantalonDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botPantalonDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotEtekDiger(View view) {
//        isBEtekDiger = !isBEtekDiger;
        if(isBEtekDiger){
//            r_botEtekDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botEtekDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_etekDiger");
        }
        else{
            bot_etekDiger = "";
            r_botEtekDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botEtekDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotBotDiger(View view) {
//        isBBotDiger = !isBBotDiger;
        if(isBBotDiger){
//            r_botBotDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botBotDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_botDiger");
        }
        else{
            bot_botDiger = "";
            r_botBotDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botBotDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickBotAyakkabiDiger(View view) {
//        isBAyakkabiDiger = !isBAyakkabiDiger;
        if(isBAyakkabiDiger){
//            r_botAyakkabiDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
//            t_botAyakkabiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
            popupRenk("bot_ayakkabiDiger");
        }
        else{
            bot_ayakkabiDiger = "";
            r_botAyakkabiDiger.setBackground(getResources().getDrawable(R.drawable.back_background));
            t_botAyakkabiDiger.setTextColor(getResources().getColor(R.color.colorDarkGray));
        }
    }

    public void clickTamam(View view) {
        builder.dismiss();
    }

    public void checkData(){
        if(!top_goz.contains("-"))           r_topGoz.performClick();
        if(!top_gozDiger.contains("-"))      r_topGozDiger.performClick();
        if(!top_esharf.contains("-"))        r_topEsharf.performClick();
        if(!top_esharfDiger.contains("-"))   r_topEsharfDiger.performClick();
        if(!top_kolye.contains("-"))         r_topKolye.performClick();
        if(!top_kolyeDiger.contains("-"))    r_topKolyeDiger.performClick();
        if(!top_shapka.contains("-"))        r_topShapka.performClick();
        if(!top_shapkaDiger.contains("-"))   r_topShapkaDiger.performClick();
        if(!top_gozluk.contains("-"))        r_topGozluk.performClick();
        if(!top_gozlukDiger.contains("-"))   r_topGozlukDiger.performClick();
        if(!top_kupe.contains("-"))          r_topKupe.performClick();
        if(!top_kupeDiger.contains("-"))     r_topKupeDiger.performClick();
        if(!top_kulaklik.contains("-"))      r_topKulaklik.performClick();
        if(!top_kulaklikDiger.contains("-")) r_topKulaklikDiger.performClick();
        if(!top_dovme.contains("-"))         r_topDovme.performClick();
        if(!top_dovmeDiger.contains("-"))    r_topDovmeDiger.performClick();

        if(!mid_tshirt.contains("-"))        r_midTshirt.performClick();
        if(!mid_tshirtDiger.contains("-"))   r_midTshirtDiger.performClick();
        if(!mid_mont.contains("-"))          r_midMont.performClick();
        if(!mid_montDiger.contains("-"))     r_midMontDiger.performClick();
        if(!mid_dovme.contains("-"))         r_midDovme.performClick();
        if(!mid_dovmeDiger.contains("-"))    r_midDovme.performClick();
        if(!mid_kazak.contains("-"))         r_midKazak.performClick();
        if(!mid_kazakDiger.contains("-"))    r_midKazakDiger.performClick();
        if(!mid_kolSaat.contains("-"))       r_midKolSati.performClick();
        if(!mid_kolSaatDiger.contains("-"))  r_midKolSatiDiger.performClick();
        if(!mid_palto.contains("-"))         r_midKolSati.performClick();
        if(!mid_paltoDiger.contains("-"))    r_midPaltoDiger.performClick();
        if(!mid_gomlek.contains("-"))        r_midGomlek.performClick();
        if(!mid_gomlekDiger.contains("-"))   r_topGozlukDiger.performClick();
        if(!mid_bileklik.contains("-"))      r_midBileklik.performClick();
        if(!mid_bileklikDiger.contains("-")) r_midBileklikDiger.performClick();

        if(!bot_jeans.contains("-"))         r_botJeans.performClick();
        if(!bot_jeansDiger.contains("-"))    r_botJeansDiger.performClick();
        if(!bot_pantalon.contains("-"))      r_botPantalon.performClick();
        if(!bot_pantalonDiger.contains("-")) r_botPantalonDiger.performClick();
        if(!bot_ayakkabi.contains("-"))      r_botAyakkabi.performClick();
        if(!bot_ayakkabiDiger.contains("-")) r_botAyakkabiDiger.performClick();
        if(!bot_etek.contains("-"))          r_botEtek.performClick();
        if(!bot_etekDiger.contains("-"))     r_botEtekDiger.performClick();
        if(!bot_bot.contains("-"))           r_botBot.performClick();
        if(!bot_botDiger.contains("-"))      r_botBotDiger.performClick();
    }


    public void clickTitleTemizle(View view) {
        e_title.setText("");
    }

    public void initBoxColors(){
        viewPopup.findViewById(R.id.r_blue).setBackground(getResources().getDrawable(R.drawable.blue_background));
        viewPopup.findViewById(R.id.r_red).setBackground(getResources().getDrawable(R.drawable.red_background));
        viewPopup.findViewById(R.id.r_green).setBackground(getResources().getDrawable(R.drawable.green_background));
        viewPopup.findViewById(R.id.r_orange).setBackground(getResources().getDrawable(R.drawable.orange_background));
        viewPopup.findViewById(R.id.r_black).setBackground(getResources().getDrawable(R.drawable.black_background));
        viewPopup.findViewById(R.id.r_yellow).setBackground(getResources().getDrawable(R.drawable.yellow_background));
    }

    public void clickColorBlue(View view) {
        color = "blue";
        selectedColor = (R.color.colorBlue);
        initBoxColors();
        viewPopup.findViewById(R.id.r_blue).setBackground(getResources().getDrawable(R.drawable.blue_background_selected));
    }

    public void clickColorRed(View view) {
        color = "red";
        selectedColor = (R.color.colorRed);
        initBoxColors();
        viewPopup.findViewById(R.id.r_red).setBackground(getResources().getDrawable(R.drawable.red_background_selected));
    }

    public void clickColorGreen(View view) {
        color = "green";
        selectedColor = R.color.colorGreen2;
        initBoxColors();
        viewPopup.findViewById(R.id.r_green).setBackground(getResources().getDrawable(R.drawable.green_background_selected));
    }

    public void clickColorOrange(View view) {
        color = "orange";
        selectedColor = R.color.colorOrange;
        initBoxColors();
        viewPopup.findViewById(R.id.r_orange).setBackground(getResources().getDrawable(R.drawable.orange_background_selected));
    }

    public void clickColorGray(View view) {
        color = "gray";
        selectedColor = R.color.colorDarkGray;
        initBoxColors();
        viewPopup.findViewById(R.id.r_black).setBackground(getResources().getDrawable(R.drawable.black_background_selected));
    }

    public void clickColorYellow(View view) {
        color = "yellow";
        selectedColor = R.color.colorYellow;
        initBoxColors();
        viewPopup.findViewById(R.id.r_yellow).setBackground(getResources().getDrawable(R.drawable.yellow_background_selected));
    }

    public void clickRenkSec(View view) {
        builder.dismiss();

        if(type.equals("top_goz")) {
            isTGoz = true;
            top_goz = color;
            r_topGoz.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topGoz.getBackground().setTint(getResources().getColor(selectedColor));
            t_topGoz.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("top_gozDiger")) {
            isTGozDiger = true;
            top_gozDiger = color;
            r_topGozDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topGozDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_topGozDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("top_esharf")) {
            isTSharp = true;
            top_esharf = color;
            r_topEsharf.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topEsharf.getBackground().setTint(getResources().getColor(selectedColor));
            t_topEsharf.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("top_esharfDiger")) {
            isTSharpDiger = true;
            top_esharfDiger = color;
            r_topEsharfDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topEsharfDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_topEsharfDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("top_shapka")) {
            isTShapka = true;
            top_shapka = color;
            r_topShapka.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topShapka.getBackground().setTint(getResources().getColor(selectedColor));
            t_topShapka.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("top_shapkaDiger")) {
            isTShapkaDiger = true;
            top_shapkaDiger = color;
            r_topShapkaDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_topShapkaDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_topShapkaDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("mid_tshirt")) {
            isMTshirt = true;
            mid_tshirt = color;
            r_midTshirt.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midTshirt.getBackground().setTint(getResources().getColor(selectedColor));
            t_midTshirt.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("mid_tshirtDiger")) {
            isMTshirtDiger = true;
            mid_tshirtDiger = color;
            r_midTshirtDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midTshirtDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_midTshirtDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("mid_mont")) {
            isMMont = true;
            mid_mont = color;
            r_midMont.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midMont.getBackground().setTint(getResources().getColor(selectedColor));
            t_midMont.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("mid_montDiger")) {
            isMMontDiger = true;
            mid_montDiger = color;
            r_midMontDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midMontDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_midMontDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("mid_kazak")) {
            isMKazak = true;
            mid_kazak = color;
            r_midKazak.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midKazak.getBackground().setTint(getResources().getColor(selectedColor));
            t_midKazak.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("mid_kazakDiger")) {
            isMKazakDiger = true;
            mid_kazakDiger = color;
            r_midKazakDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midKazakDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_midKazakDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("mid_palto")) {
            isMPalto = true;
            mid_palto = color;
            r_midPalto.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midPalto.getBackground().setTint(getResources().getColor(selectedColor));
            t_midPalto.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("mid_paltoDiger")) {
            isMPaltoDiger = true;
            mid_paltoDiger = color;
            r_midPaltoDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midPaltoDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_midPaltoDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("mid_gomlek")) {
            isMGomlek = true;
            mid_gomlek = color;
            r_midGomlek.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midGomlek.getBackground().setTint(getResources().getColor(selectedColor));
            t_midGomlek.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("mid_gomlekDiger")) {
            isMGomlekDiger = true;
            mid_gomlekDiger = color;
            r_midGomlekDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_midGomlekDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_midGomlekDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("bot_jeans")) {
            isBJeans = true;
            bot_jeans = color;
            r_botJeans.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botJeans.getBackground().setTint(getResources().getColor(selectedColor));
            t_botJeans.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("bot_jeansDiger")) {
            isBJeansDiger = true;
            bot_jeansDiger = color;
            r_botJeansDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botJeansDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_botJeansDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("bot_pantalon")) {
            isBPantalon = true;
            bot_pantalon = color;
            r_botPantalon.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botPantalon.getBackground().setTint(getResources().getColor(selectedColor));
            t_botPantalon.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("bot_pantalonDiger")) {
            isBPantalonDiger = true;
            bot_pantalonDiger = color;
            r_botPantalonDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botPantalonDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_botPantalonDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("bot_etek")) {
            isBEtek = true;
            bot_etek = color;
            r_botEtek.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botEtek.getBackground().setTint(getResources().getColor(selectedColor));
            t_botEtek.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("bot_etekDiger")) {
            isBEtekDiger = true;
            bot_etekDiger = color;
            r_botEtekDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botEtekDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_botEtekDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("bot_bot")) {
            isBBot = true;
            bot_bot = color;
            r_botBot.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botBot.getBackground().setTint(getResources().getColor(selectedColor));
            t_botBot.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("bot_botDiger")) {
            isBBotDiger = true;
            bot_botDiger = color;
            r_botBotDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botBotDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_botBotDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

        else if(type.equals("bot_ayakkabi")) {
            isBAyakkabi = true;
            bot_ayakkabi = color;
            r_botAyakkabi.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botAyakkabi.getBackground().setTint(getResources().getColor(selectedColor));
            t_botAyakkabi.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if(type.equals("bot_ayakkabiDiger")) {
            isBAyakkabiDiger = true;
            bot_ayakkabiDiger = color;
            r_botAyakkabiDiger.setBackground(getResources().getDrawable(R.drawable.back_background_clicked));
            r_botAyakkabiDiger.getBackground().setTint(getResources().getColor(selectedColor));
            t_botAyakkabiDiger.setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    public void popupRenk(String title){
        builder = new Dialog(this, R.style.AlertDialogCustom);
        View view;
        view = LayoutInflater.from(this).inflate(R.layout.popup_renk, null);
        viewPopup = view;
        TextView t_title = view.findViewById(R.id.t_title);
        t_title.setText(title);
        type = title;
        builder.setCancelable(true);
        builder.setContentView(view);
        builder.show();
    }

    public void clickVazgec(View view) {
        builder.dismiss();
    }
}