package com.example.codeblack.models;

public class ModelAdd {

    //parameters
    String pTime,pDesPrice,pBathroom,pKitchen,pTitle,pImage,pState,uid;

    //default constructor
    public ModelAdd() {
    }

    //overloaded constructor
    public ModelAdd(String pTime, String pDesPrice, String pBathroom, String pKitchen, String pTitle, String pImage, String pState, String uid) {
        this.pTime = pTime;
        this.pDesPrice = pDesPrice;
        this.pBathroom = pBathroom;
        this.pKitchen = pKitchen;
        this.pTitle = pTitle;
        this.pImage = pImage;
        this.pState  = pState;
        this.uid = uid;


    }

    //Getter and setters
    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getpDesPrice() {
        return pDesPrice;
    }

    public void setpDesPrice(String pDesPrice) {
        this.pDesPrice = pDesPrice;
    }

    public String getpBathroom() {
        return pBathroom;
    }

    public void setpBathroom(String pBathroom) {
        this.pBathroom = pBathroom;
    }

    public String getpKitchen() {
        return pKitchen;
    }

    public void setpKitchen(String pKitchen) {
        this.pKitchen = pKitchen;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
