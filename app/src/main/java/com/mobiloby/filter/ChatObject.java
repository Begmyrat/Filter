package com.mobiloby.filter;

public class ChatObject {
    String id;
    String username_unique;
    String friend_username_unique;
    String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername_unique() {
        return username_unique;
    }

    public void setUsername_unique(String username_unique) {
        this.username_unique = username_unique;
    }

    public String getFriend_username_unique() {
        return friend_username_unique;
    }

    public void setFriend_username_unique(String friend_username_unique) {
        this.friend_username_unique = friend_username_unique;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKimdenKime() {
        return kimdenKime;
    }

    public void setKimdenKime(String kimdenKime) {
        this.kimdenKime = kimdenKime;
    }

    public ChatObject(String id, String username_unique, String friend_username_unique, String message, String date, String kimdenKime) {
        this.id = id;
        this.username_unique = username_unique;
        this.friend_username_unique = friend_username_unique;
        this.message = message;
        this.date = date;
        this.kimdenKime = kimdenKime;
    }

    String date;
    String kimdenKime;
}
