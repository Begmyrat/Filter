package com.mobiloby.filter.models;

public class ProfilInfo {
    String title, info;

    public ProfilInfo(){}

    public ProfilInfo(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public ProfilInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
