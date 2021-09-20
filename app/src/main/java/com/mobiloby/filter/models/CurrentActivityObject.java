package com.mobiloby.filter.models;

import java.util.ArrayList;

public class CurrentActivityObject {
    ArrayList<UserObject> recommendedUsers;
    CategoryObject categoryObject;
    int view_type;

    public CurrentActivityObject(ArrayList<UserObject> recommendedUsers, int viewType) {
        this.recommendedUsers = recommendedUsers;
        this.view_type = viewType;
    }

    public CurrentActivityObject(CategoryObject categoryObject, int viewType) {
        this.categoryObject = categoryObject;
        this.view_type = viewType;
    }

    public CurrentActivityObject(){

    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public ArrayList<UserObject> getRecommendedUsers() {
        return recommendedUsers;
    }

    public void setRecommendedUsers(ArrayList<UserObject> recommendedUsers) {
        this.recommendedUsers = recommendedUsers;
    }

    public CategoryObject getCategoryObject() {
        return categoryObject;
    }

    public void setCategoryObject(CategoryObject categoryObject) {
        this.categoryObject = categoryObject;
    }
}
