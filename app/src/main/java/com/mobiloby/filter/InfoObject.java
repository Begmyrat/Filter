package com.mobiloby.filter;

public class InfoObject {
    String category, info;

    public InfoObject(String category, String info) {
        this.category = category;
        this.info = info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
