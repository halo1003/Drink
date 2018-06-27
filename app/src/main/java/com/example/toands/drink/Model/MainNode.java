package com.example.toands.drink.Model;

public class MainNode {
    int id;
    public String hour;
    public String minute;
    public String second;
    public String time_type;
    public String day;
    public String mounth;
    public String year;

    public MainNode() {
    }

    public MainNode(int id, String hour, String minute, String second, String time_type, String day, String mounth, String year) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.time_type = time_type;
        this.day = day;
        this.mounth = mounth;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
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
