package com.example.toands.drink;

import android.os.Build;
import android.util.Log;

import com.example.toands.drink.Model.Timeline;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Functions {

    public String getTime(){
        DateFormat df = new SimpleDateFormat("hh:mm:ss a, zzzz");
        String time = df.format(Calendar.getInstance().getTime());
        return time;
    }

    public String getTimeZ(){
        DateFormat df = new SimpleDateFormat("zzzz");
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

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public String chuanHoa(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }

    public String standaraze(String str) {
        str = chuanHoa(str);
        String temp[] = str.split(" ");
        str = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) // ? ^-^
                str += " ";
        }
        return str;
    }

    public String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
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

    public List<Integer> detectSectionEnd(List<Timeline> list){
        List<Integer> integerList1 = new ArrayList<Integer>();
        List<Integer> integerList2 = new ArrayList<Integer>();

        for(Timeline li : list){
            String s[] = li.month.split(" ");
            integerList1.add(Integer.valueOf(s[2]));
        }

        int i = 0;
        while (integerList1.size()>i){
            int temp  = recursive(integerList1,i);
            i = i+temp;
            integerList2.add(i);
        }
        return integerList2;
    }

    public int recursive(List<Integer> integerList,int i){
        int count = 0;
        int start = integerList.get(i);

        for (int j: integerList){
            if (j == start) count++;
        }

        return count;
    }

    public void loge(String x1,String x2){
        Log.e(x1,x2);
    }

    public void logi(String x1,String x2){
        Log.i(x1,x2);
    }
}

