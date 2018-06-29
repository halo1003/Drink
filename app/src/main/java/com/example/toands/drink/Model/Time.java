package com.example.toands.drink.Model;

public class Time {
    String hour;
    String minute;
    String second;
    String type;

    public Time() {
    }

    public Time(String hour, String minute, String second, String type) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
