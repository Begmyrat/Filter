package com.mobiloby.filter.models;

public class TodoObject {
    String username, todoDescription, todoID, time, userProfileUrl, feeling, location;
    int view_type;

    public TodoObject(){}

    public TodoObject(int view_type){
        this.view_type = view_type;
    }

    public TodoObject(String todoID, String username, String todoDescription) {
        this.todoID = todoID;
        this.username = username;
        this.todoDescription = todoDescription;
    }

    public TodoObject(String todoID, String username, String todoDescription, String feeling, String location) {
        this.todoID = todoID;
        this.username = username;
        this.todoDescription = todoDescription;
        this.feeling = feeling;
        this.location = location;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getTodoID() {
        return todoID;
    }

    public void setTodoID(String todoID) {
        this.todoID = todoID;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }
}
