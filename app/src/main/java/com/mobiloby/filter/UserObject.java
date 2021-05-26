package com.mobiloby.filter;

public class UserObject {
    String username, address, date, id, okul1, okul2, universite, takim, club, spor, adSoyad, username_visible, userPassword, similarity, profil_gizlilik;
    String wanted_id;
    String soru1, soru, cevap;
    String soru2;
    String soru3;
    String answer1;

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getCevap() {
        return cevap;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }

    String answer2;
    String avatar_id;
    String fromWhere;

    public String getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(String avatar_id) {
        this.avatar_id = avatar_id;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getSoru1() {
        return soru1;
    }

    public void setSoru1(String soru1) {
        this.soru1 = soru1;
    }

    public String getSoru2() {
        return soru2;
    }

    public void setSoru2(String soru2) {
        this.soru2 = soru2;
    }

    public String getSoru3() {
        return soru3;
    }

    public void setSoru3(String soru3) {
        this.soru3 = soru3;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    String answer3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOkul1() {
        return okul1;
    }

    public String getWanted_id() {
        return wanted_id;
    }

    public void setWanted_id(String wanted_id) {
        this.wanted_id = wanted_id;
    }

    public String getSimilarity() {
        return similarity;
    }

    public String getProfil_gizlilik() {
        return profil_gizlilik;
    }

    public void setProfil_gizlilik(String profil_gizlilik) {
        this.profil_gizlilik = profil_gizlilik;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public void setOkul1(String okul1) {
        this.okul1 = okul1;
    }

    public String getOkul2() {
        return okul2;
    }

    public void setOkul2(String okul2) {
        this.okul2 = okul2;
    }

    public String getUniversite() {
        return universite;
    }

    public String getUsername_visible() {
        return username_visible;
    }

    public void setUsername_visible(String username_visible) {
        this.username_visible = username_visible;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getTakim() {
        return takim;
    }

    public void setTakim(String takim) {
        this.takim = takim;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getSpor() {
        return spor;
    }

    public void setSpor(String spor) {
        this.spor = spor;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public UserObject(String id, String username, String date, String adSyoad, String okul1, String okul2, String universite, String takim, String club, String spor) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.okul1 = okul1;
        this.okul2 = okul2;
        this.universite = universite;
        this.takim = takim;
        this.club = club;
        this.adSoyad = adSyoad;
        this.spor = spor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
