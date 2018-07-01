package com.example.toands.drink.Model;

public class Version {
    double ver;
    String day;

    public Version(double ver, String day) {
        this.ver = ver;
        this.day = day;
    }

    public Version() {
    }

    public double getVer() {
        return ver;
    }

    public void setVer(double ver) {
        this.ver = ver;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
