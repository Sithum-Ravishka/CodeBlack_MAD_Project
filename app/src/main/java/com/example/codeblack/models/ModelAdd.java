package com.example.codeblack.models;

public class ModelAdd {

    //parameters
    String pId, pTime, pDesPrice, pBathroom, pKitchen, pTitle, pImage, pState, uid, pHostPhoneNumber, pBedroom,pAdult,pChildern,pDescribePlace,uEmail, uDp, uName;

    //default constructor
    public ModelAdd() {
    }

    public ModelAdd(String pId, String pTime, String pDesPrice, String pBathroom, String pKitchen, String pTitle, String pImage, String pState, String uid, String pHostPhoneNumber, String pBedroom, String pAdult, String pChildern, String pDescribePlace, String uEmail, String uDp, String uName) {
        this.pId = pId;
        this.pTime = pTime;
        this.pDesPrice = pDesPrice;
        this.pBathroom = pBathroom;
        this.pKitchen = pKitchen;
        this.pTitle = pTitle;
        this.pImage = pImage;
        this.pState = pState;
        this.uid = uid;
        this.pHostPhoneNumber = pHostPhoneNumber;
        this.pBedroom = pBedroom;
        this.pAdult = pAdult;
        this.pChildern = pChildern;
        this.pDescribePlace = pDescribePlace;
        this.uEmail = uEmail;
        this.uDp = uDp;
        this.uName = uName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

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

    public String getpHostPhoneNumber() {
        return pHostPhoneNumber;
    }

    public void setpHostPhoneNumber(String pHostPhoneNumber) {
        this.pHostPhoneNumber = pHostPhoneNumber;
    }

    public String getpBedroom() {
        return pBedroom;
    }

    public void setpBedroom(String pBedroom) {
        this.pBedroom = pBedroom;
    }

    public String getpAdult() {
        return pAdult;
    }

    public void setpAdult(String pAdult) {
        this.pAdult = pAdult;
    }

    public String getpChildern() {
        return pChildern;
    }

    public void setpChildern(String pChildern) {
        this.pChildern = pChildern;
    }

    public String getpDescribePlace() {
        return pDescribePlace;
    }

    public void setpDescribePlace(String pDescribePlace) {
        this.pDescribePlace = pDescribePlace;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}