package com.example.infilectcustomcamerainternshipassignment;

public class UserModel {

    private String stringImageUrl, stringHour,stringTime;

    public UserModel(String stringImageUrl, String stringHour, String stringTime) {
        this.stringImageUrl = stringImageUrl;
        this.stringHour = stringHour;
        this.stringTime = stringTime;
    }

    public UserModel() {
    }

    public String getStringHour() {
        return stringHour;
    }

    public String getStringTime() {
        return stringTime;
    }

    public String getStringImageUrl() {
        return stringImageUrl;
    }

    public void setStringImageUrl(String stringImageUrl) {
        this.stringImageUrl = stringImageUrl;
    }

    public void setStringHour(String stringHour) {
        this.stringHour = stringHour;
    }

    public void setStringTime(String stringTime) {
        this.stringTime = stringTime;
    }
}
