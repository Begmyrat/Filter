package com.mobiloby.filter.models;

public class SocialMediaObject {

    String username, social_username, social_username_other, social_type, id, date;

    public SocialMediaObject() {}

    public SocialMediaObject(String id, String username, String social_username, String social_username_other, String social_type, String date) {
        this.id = id;
        this.username = username;
        this.social_username = social_username;
        this.social_username_other = social_username_other;
        this.social_type = social_type;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setSocial_type(String social_type) {
        this.social_type = social_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSocial_username() {
        return social_username;
    }

    public void setSocial_username(String social_username) {
        this.social_username = social_username;
    }

    public String getSocial_username_other() {
        return social_username_other;
    }

    public void setSocial_username_other(String social_username_other) {
        this.social_username_other = social_username_other;
    }
}
