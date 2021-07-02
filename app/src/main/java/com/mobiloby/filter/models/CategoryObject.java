package com.mobiloby.filter.models;

public class CategoryObject {
    String title, subtitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public CategoryObject(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }
}
