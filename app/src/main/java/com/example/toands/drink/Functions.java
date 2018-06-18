package com.example.toands.drink;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Functions {

    public String getTime(){
        DateFormat df = new SimpleDateFormat("hh:mm:ss a, zzzz");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getHour(){
        DateFormat df = new SimpleDateFormat("hh");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getMinute(){
        DateFormat df = new SimpleDateFormat("mm");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getSecond(){
        DateFormat df = new SimpleDateFormat("ss");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getType(){
        DateFormat df = new SimpleDateFormat("a");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getTimeOnly(){
        DateFormat df = new SimpleDateFormat("hh:mm a");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getDayOnly(){
        DateFormat df = new SimpleDateFormat("dd, MMMM yyyy");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getDay(){
        DateFormat df = new SimpleDateFormat("dd");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getMouth(){
        DateFormat df = new SimpleDateFormat("MMMM");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getYear(){
        DateFormat df = new SimpleDateFormat("yyyy");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getDate(){
        DateFormat df = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public void loge(String x1,String x2){
        Log.e(x1,x2);
    }

    public void logi(String x1,String x2){
        Log.i(x1,x2);
    }
}

