package com.mobiloby.filter.models;

public class WantedObject {
    String wantedID, wantedDate, soru;
    String similarity = "", userProfileUrl;
    Double doluluk, similarityDouble;

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public WantedObject(){}

    public Double getDoluluk() {
        return doluluk;
    }

    public void setDoluluk(Double doluluk) {
        this.doluluk = doluluk;
    }

    public Double getSimilarityDouble() {
        return similarityDouble;
    }

    public void setSimilarityDouble(Double similarityDouble) {
        this.similarityDouble = similarityDouble;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getWantedID() {
        return wantedID;
    }

    public void setWantedID(String wantedID) {
        this.wantedID = wantedID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWantedUserName() {
        return wantedUserName;
    }

    public String getWantedDate() {
        return wantedDate;
    }

    public void setWantedDate(String wantedDate) {
        this.wantedDate = wantedDate;
    }

    public void setWantedUserName(String wantedUserName) {
        this.wantedUserName = wantedUserName;
    }

    public String getGiyimTop() {
        return giyimTop;
    }

    public void setGiyimTop(String giyimTop) {
        this.giyimTop = giyimTop;
    }

    public String getGiyimMiddle() {
        return giyimMiddle;
    }

    public void setGiyimMiddle(String giyimMiddle) {
        this.giyimMiddle = giyimMiddle;
    }

    public String getGiyimBottom() {
        return giyimBottom;
    }

    public void setGiyimBottom(String giyimBottom) {
        this.giyimBottom = giyimBottom;
    }

    public String getGiyimAyakkabi() {
        return giyimAyakkabi;
    }

    public void setGiyimAyakkabi(String giyimAyakkabi) {
        this.giyimAyakkabi = giyimAyakkabi;
    }

    public String getWantedBoy() {
        return wantedBoy;
    }

    public void setWantedBoy(String wantedBoy) {
        this.wantedBoy = wantedBoy;
    }

    public String getWantedTitle() {
        return wantedTitle;
    }

    public void setWantedTitle(String wantedTitle) {
        this.wantedTitle = wantedTitle;
    }

    public String getGiyimTopDiger() {
        return giyimTopDiger;
    }

    public void setGiyimTopDiger(String giyimTopDiger) {
        this.giyimTopDiger = giyimTopDiger;
    }

    public String getGiyimMiddleDiger() {
        return giyimMiddleDiger;
    }

    public void setGiyimMiddleDiger(String giyimMiddleDiger) {
        this.giyimMiddleDiger = giyimMiddleDiger;
    }

    public String getGiyimBottomDiger() {
        return giyimBottomDiger;
    }

    public void setGiyimBottomDiger(String giyimBottomDiger) {
        this.giyimBottomDiger = giyimBottomDiger;
    }

    public WantedObject(String wantedID, String userName, String wantedUserName, String wantedTitle, String giyimTop, String giyimMiddle, String giyimBottom, String giyimAyakkabi, String wantedBoy, String wantedDate) {
        this.wantedID = wantedID;
        this.userName = userName;
        this.wantedUserName = wantedUserName;
        this.giyimTop = giyimTop;
        this.giyimMiddle = giyimMiddle;
        this.giyimBottom = giyimBottom;
        this.giyimAyakkabi = giyimAyakkabi;
        this.wantedBoy = wantedBoy;
        this.wantedTitle = wantedTitle;
        this.wantedDate = wantedDate;
    }

    String userName;
    String wantedUserName;
    String giyimTop, giyimTopDiger;
    String giyimMiddle, giyimMiddleDiger;
    String giyimBottom, giyimBottomDiger;
    String giyimAyakkabi;
    String wantedBoy;
    String wantedTitle;
}
