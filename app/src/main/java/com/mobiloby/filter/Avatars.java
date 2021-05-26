package com.mobiloby.filter;

public class Avatars {
    String id;
    int img, color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Avatars(String id, int img, int color) {
        this.id = id;
        this.img = img;
        this.color = color;
    }
}
