package com.example.toands.drink.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Database.SQLiteDB;
import com.example.toands.drink.Functions;
import com.example.toands.drink.Model.MainNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        final SQLiteDB sqLiteDB = new SQLiteDB(getApplicationContext());
        realcount = sqLiteDB.getNodesCount(TBname);
        containValue._Messages.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vitualcount = dataSnapshot.getChildrenCount();
                if (realcount-vitualcount>=0){
                    Toast.makeText(getApplicationContext(),"Server Synchronous",Toast.LENGTH_SHORT).show();
                    while (vitualcount<=realcount){
                        vitualcount++;
                        if (vitualcount>realcount) break;

                            Log.e(TAG,vitualcount+"/"+realcount);

                        MainNode mainNode = sqLiteDB.getNode((int) vitualcount);

                        Intent mIntent = new Intent(getApplicationContext(), FirebaseService.class);

                        String fullday = getDay(mainNode.day,mainNode.getMounth(),mainNode.getYear());
                        String shortday = getDayOnly(mainNode.day,mainNode.getMounth(),mainNode.getYear());

                        String fullhour = getHour(mainNode.getHour(),mainNode.getMinute(),
                                mainNode.getSecond(),mainNode.getTime_type());
                        String shorthour = getHourOnly(mainNode.getHour(),mainNode.getMinute(),
                                mainNode.getSecond(),mainNode.getTime_type());

                        mIntent.putExtra("ID", vitualcount -1);

                        mIntent.putExtra(containValue.HOUR,fullhour);
                        mIntent.putExtra(containValue.MINUTE,shorthour);

                        mIntent.putExtra(containValue.DAY,fullday);
                        mIntent.putExtra(containValue.MONTH,shortday);

                        startService(mIntent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        stopSelf();
    }
}
