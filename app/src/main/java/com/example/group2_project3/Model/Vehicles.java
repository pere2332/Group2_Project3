package com.example.group2_project3.Model;

public class Vehicles {
private String category, date, description,
        image, mileage, price, time, vName, vid, year;

public Vehicles(){}

    public Vehicles(String category, String date, String description,
                    String image, String mileage, String price, String time,
                    String vName, String vid, String year) {
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = image;
        this.mileage = mileage;
        this.price = price;
        this.time = time;
        this.vName = vName;
        this.vid = vid;
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}



