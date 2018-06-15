package com.example.toands.drink;

import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Functions {

    public String getTime(){
        DateFormat df = new SimpleDateFormat("hh:mm:ss a, zzzz");
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

