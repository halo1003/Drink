package com.example.toands.drink.Model;

public class Day {
    String day;
    String mounth;
    String year;

    public Day() {
    }

    public Day(String day, String mounth, String year) {
        this.day = day;
        this.mounth = mounth;
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMounth() {
        return mounth;
    }

    public void setMounth(String mounth) {
        this.mounth = mounth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
