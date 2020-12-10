package com.example.group2_project3.Model;

public class ShoppingCart {
    private String vname, vdescription, vprice, vimage, vcategory, vpid, vdate, vtime;

    public ShoppingCart(String name, String description, String price, String image, String category, String pid, String date, String time) {
        this.vname = name;
        this.vdescription = description;
        this.vprice = price;
        this.vimage = image;
        this.vcategory = category;
        this.vpid = pid;
        this.vdate = date;
        this.vtime = time;
    }

    public String getName() {
        return vname;
    }

    public String getDescription() {
        return vdescription;
    }

    public String getPrice() {
        return vprice;
    }

    public String getImage() {
        return vimage;
    }

    public String getCategory() {
        return vcategory;
    }

    public String getPid() {
        return vpid;
    }

    public String getDate() {
        return vdate;
    }

    public String getTime() {
        return vtime;
    }

    public void setName(String name) {
        this.vname = name;
    }

    public void setDescription(String description) {
        this.vdescription = description;
    }

    public void setPrice(String price) {
        this.vprice = price;
    }

    public void setImage(String image) {
        this.vimage = image;
    }

    public void setCategory(String category) {
        this.vcategory = category;
    }

    public void setPid(String pid) {
        this.vpid = pid;
    }

    public void setDate(String date) {
        this.vdate = date;
    }

    public void setTime(String time) {
        this.vtime = time;
    }
}
