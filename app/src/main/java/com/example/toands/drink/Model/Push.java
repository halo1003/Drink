package com.example.toands.drink.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Push {
    String allow;
    int count;
    String token;

    public Push() {
    }

    public Push(String allow, int count, String token) {
        this.allow = allow;
        this.count = count;
        this.token = token;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("allow", allow);
        result.put("count", count);
        result.put("token", token);

        return result;
    }
}
