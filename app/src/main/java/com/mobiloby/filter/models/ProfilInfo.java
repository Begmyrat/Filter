package com.mobiloby.filter.models;

public class ProfilInfo {
    String title, info, avatarID;

    public ProfilInfo(){}

    public ProfilInfo(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(String avatarID) {
        this.avatarID = avatarID;
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
