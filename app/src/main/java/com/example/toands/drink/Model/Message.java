package com.example.toands.drink.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Message {
    long _id;
    String title;
    String date;
    String time;
    String shortContain;
    String fullContain;

    public Message() {
    }

    public Message(long _id, String title, String date, String time, String shortContain, String fullContain) {
        this._id = _id;
        this.title = title;
        this.date = date;
        this.time = time;
        this.shortContain = shortContain;
        this.fullContain = fullContain;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getShortContain() {
        return shortContain;
    }

    public void setShortContain(String shortContain) {
        this.shortContain = shortContain;
    }

    public String getFullContain() {
        return fullContain;
    }

    public void setFullContain(String fullContain) {
        this.fullContain = fullContain;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("_id", _id);
        result.put("date", date);
        result.put("full-contain", fullContain);
        result.put("short-contain", shortContain);
        result.put("time", time);
        result.put("title", title);

        return result;
    }
}
