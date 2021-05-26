package com.mobiloby.filter;

public class SocialObject {

    String id, tarih;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_other() {
        return username_other;
    }

    public void setUsername_other(String username_other) {
        this.username_other = username_other;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public SocialObject(String id, String type, String username, String username_other, String tarih) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.username_other = username_other;
        this.tarih = tarih;
    }

    String type;
    String username;
    String username_other;


}
