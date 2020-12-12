package com.example.group2_project3.Model;

public class ShoppingCart {
    private String date, time, vName, vPrice, vid, vDescription;

    public ShoppingCart(String date, String time, String vName, String vPrice, String vid, String vDescription) {
        this.vDescription = vDescription;
        this.date = date;
        this.time = time;
        this.vName = vName;
        this.vPrice = vPrice;
        this.vid = vid;
    }

    public String getVdescription() {
        return vDescription;
    }

    public void setVdescrpition(String vDescription) {
        this.vDescription = vDescription;
    }

    public String getName() {
        return vName;
    }

    public void setName(String vName) {
        this.vName = vName;
    }

    public String getPrice() {
        return vPrice;
    }

    public void setPrice(String vPrice) {
        this.vPrice = vPrice;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
