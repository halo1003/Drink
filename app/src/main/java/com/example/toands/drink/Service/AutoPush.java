package com.example.toands.drink.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Functions;
import com.example.toands.drink.MainActivity;
import com.example.toands.drink.Model.Day;
import com.example.toands.drink.Model.MainNode;
import com.example.toands.drink.Model.Message;
import com.example.toands.drink.Model.Time;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import static com.example.toands.drink.Database.SQLiteDB.TBname;

public class AutoPush extends Service {
    ContainValue containValue = new ContainValue();
    Functions functions = new Functions();
    long vitualcount;
    long realcount;

    private static final String TAG = "AutoPush";

    public AutoPush() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getDay(String d,String m,String y){
        m = m.replaceAll("\\D+","");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {

            date = newDateFormat.parse(d+"/"+m+"/"+y);
            newDateFormat.applyPattern("EEEE, d MMMM yyyy");
            return newDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDayOnly(String d,String m,String y){
        m = m.replaceAll("\\D+","");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {

            date = newDateFormat.parse(d+"/"+m+"/"+y);
            newDateFormat.applyPattern("d, MMMM yyyy");
            return newDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getHour(String h,String m,String s,String a){
        SimpleDateFormat newDateFormat = new SimpleDateFormat("hh/mm/ss/a");
        Date date = null;
        try {

            date = newDateFormat.parse(h+"/"+m+"/"+s+"/"+a);
            newDateFormat.applyPattern("hh:mm:ss a, ");
            return newDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getHourOnly(String h,String m,String s,String a){
        SimpleDateFormat newDateFormat = new SimpleDateFormat("hh/mm/ss/a");
        Date date = null;
        try {

            date = newDateFormat.parse(h+"/"+m+"/"+s+"/"+a);
            newDateFormat.applyPattern("hh:mm a");
            return newDateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Time getHourReturn(String str){

        StringTokenizer tokens = new StringTokenizer(str, ",");
        String time_temp = tokens.nextToken();

        tokens = new StringTokenizer(time_temp, ":");
        String h = tokens.nextToken();
        String m = tokens.nextToken();
        String s_temp = tokens.nextToken();

        tokens = new StringTokenizer(s_temp," ");
        String s = tokens.nextToken();
        String type = tokens.nextToken();

        Time time = new Time();
        time.setHour(h);
        time.setMinute(m);
        time.setSecond(s);
        time.setType(type);

        return time;
    }

    public Day getDayReturn(String str){

        StringTokenizer tokenizer = new StringTokenizer(str,",");
        String d = tokenizer.nextToken();
        String temp = tokenizer.nextToken();

        tokenizer = new StringTokenizer(temp," ");
        tokenizer.nextToken();
        String m = tokenizer.nextToken();
        String y = tokenizer.nextToken();
        Log.e(TAG,d+"-"+m+"-"+y);
        return new Day(d,m,y);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        functions.loge(TAG,"start");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        functions.loge(TAG,"stop");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        makeAlert();
        final SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
        realcount = sqLiteDB.getNodesCount(TBname);
        containValue._Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vitualcount = dataSnapshot.getChildrenCount();
                if (realcount-vitualcount>=0){
                    //Toast.makeText(getApplicationContext(),"Server Synchronous",Toast.LENGTH_SHORT).show();
                    //new GlideToast.makeToast((,"Server Synchronous",GlideToast.LENGTHLONG,GlideToast.SUCCESSTOAST,GlideToast.TOP).show();

                    while (vitualcount<=realcount){

                        MainNode mainNode = sqLiteDB.getNode((int) vitualcount);

                        Intent mIntent = new Intent(getApplicationContext(), FirebaseService.class);

                        String fullday = getDay(mainNode.day,mainNode.getMounth(),mainNode.getYear());
                        String shortday = getDayOnly(mainNode.day,mainNode.getMounth(),mainNode.getYear());

                        String fullhour = getHour(mainNode.getHour(),mainNode.getMinute(),
                                mainNode.getSecond(),mainNode.getTime_type());
                        String shorthour = getHourOnly(mainNode.getHour(),mainNode.getMinute(),
                                mainNode.getSecond(),mainNode.getTime_type());

                        mIntent.putExtra("ID", vitualcount -1);

                        Log.e(TAG,fullday);
                        mIntent.putExtra(containValue.HOUR,fullhour);
                        mIntent.putExtra(containValue.MINUTE,shorthour);

                        mIntent.putExtra(containValue.DAY,fullday);
                        mIntent.putExtra(containValue.MONTH,shortday);

                        startService(mIntent);

                        vitualcount++;
                    }
                }else{
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        Message node = data.getValue(Message.class);

                        Time time = getHourReturn(node.getTime());
                        Day day = getDayReturn(node.getSortDay());

                        MainNode mainNode = new MainNode();
                        mainNode.setHour(time.getHour());
                        mainNode.setMinute(time.getMinute());
                        mainNode.setSecond(time.getSecond());
                        mainNode.setTime_type(time.getType());
                        mainNode.setDay(day.getDay());
                        mainNode.setMounth(day.getMounth());
                        mainNode.setYear(day.getYear());

                        sqLiteDB.addNode(mainNode);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        stopSelf();
    }

    private void makeAlert() {
        Intent intent = new Intent(containValue.SIGNAL);
        // add data
        intent.putExtra(containValue.SIGNAL, "on");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
