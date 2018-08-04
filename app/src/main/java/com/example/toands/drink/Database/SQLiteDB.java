package com.example.toands.drink.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.toands.drink.Model.MainNode;
import com.example.toands.drink.Model.Version;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper{

    static String DBname = "Schedule";
    public static String TBname = "Table_time_and_day";
    public static String colHour = "hour";
    public static String colMinute = "minute";
    public static String colSecond = "second";
    public static String colTimeType = "time_type";
    public static String colDay = "day";
    public static String colMonth = "month";
    public static String colYear = "year";

    public static String TB_version_name = "Table_version";
    public static String colVer = "version";

    MainNode mainNode;
    String TAG = "SQLiteDB";

    public SQLiteDB(Context context) {
        super(context, DBname, null, 1);
    }

    public SQLiteDB(Context context, MainNode mainNode) {
        super(context, DBname, null, 1);
        this.mainNode = mainNode;
    }

    public void Create_table_ver(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TB_version_name+"("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +colVer+" DOUBLE,"
                +colDay+" STRING"
                +")";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        Log.e(TAG,"Create table version success");
    }

    public void addNodeVersion(Version version){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(colVer, version.getVer());
        values.put(colDay, version.getDay());

        db.insert(TB_version_name,null,values);
        Log.e(TAG,"Insert success: ["+version.getVer()+"]");
        db.close();
    }

    public void Create_table(){
        String SQL = "CREATE TABLE IF NOT EXISTS "+ TBname+"("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +colHour+" INT,"
                +colMinute+" INT,"
                +colSecond+" INT,"
                +colTimeType+" STRING,"
                +colDay+" INT,"
                +colMonth+" INT,"
                +colYear+" INT)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        Log.e(TAG,"Create table success");
    }

    public long getNodesCount(String TBname) {
        String countQuery = "SELECT  COUNT(*) FROM " + TBname;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        long c  = cursor.getInt(0);
        cursor.close();
        return c;
    }

    public long getNodesCountCondition(String TBname,int month, int year) {
        String countQuery = "SELECT  COUNT(*) FROM " + TBname +" WHERE "
                + colMonth +" = " + month
                + " AND "
                + colYear + " = " + year;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        long c  = cursor.getInt(0);
        cursor.close();
        return c;
    }

    public void addNode(MainNode mainNode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(colHour, mainNode.getHour());
        values.put(colMinute,mainNode.getMinute());
        values.put(colSecond,mainNode.getSecond());
        values.put(colTimeType,mainNode.getTime_type());
        values.put(colDay,mainNode.getDay());
        values.put(colMonth,mainNode.getMounth());
        values.put(colYear,mainNode.getYear());

        db.insert(TBname,null,values);
        Log.e(TAG,"Insert success: ["+mainNode.getHour()+":"+mainNode.getMinute()+"] "
                +mainNode.getDay()+"/"+mainNode.getMounth()+"/"+mainNode.getYear());
        db.close();
    }

    public MainNode getNode(int i){
        String sql = "SELECT * FROM "+ TBname+ " WHERE id ="+i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                MainNode mainNode = new MainNode();
                mainNode.setId(cursor.getInt(0));
                mainNode.setHour(cursor.getString(1));
                mainNode.setMinute(cursor.getString(2));
                mainNode.setSecond(cursor.getString(3));
                mainNode.setTime_type(cursor.getString(4));
                mainNode.setDay(cursor.getString(5));
                mainNode.setMounth(cursor.getString(6));
                mainNode.setYear(cursor.getString(7));
                return mainNode;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }

    public List<Integer> getNodeMonth(){
        List<Integer> li = new ArrayList<Integer>();
        String sql = "SELECT * FROM "+ TBname;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            do {
                MainNode mainNode = new MainNode();
                mainNode.setId(cursor.getInt(0));
                mainNode.setHour(cursor.getString(1));
                mainNode.setMinute(cursor.getString(2));
                mainNode.setSecond(cursor.getString(3));
                mainNode.setTime_type(cursor.getString(4));
                mainNode.setDay(cursor.getString(5));
                mainNode.setMounth(cursor.getString(6));
                mainNode.setYear(cursor.getString(7));
                li.add(Integer.valueOf(mainNode.getMounth()));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return li;
    }

    public List<Integer> getNodeYear(){
        List<Integer> li = new ArrayList<Integer>();
        String sql = "SELECT * FROM "+ TBname;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            do {
                MainNode mainNode = new MainNode();
                mainNode.setId(cursor.getInt(0));
                mainNode.setHour(cursor.getString(1));
                mainNode.setMinute(cursor.getString(2));
                mainNode.setSecond(cursor.getString(3));
                mainNode.setTime_type(cursor.getString(4));
                mainNode.setDay(cursor.getString(5));
                mainNode.setMounth(cursor.getString(6));
                mainNode.setYear(cursor.getString(7));
                li.add(Integer.valueOf(mainNode.getYear()));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return li;
    }

    public void UpdateTB(String TBname,String hour,String minute,String second,String type){
        String sql = "UPDATE "+TBname
                +" SET "+colHour+" = '"+hour+"', "
                +colMinute+" = '"+minute+"', "
                +colSecond+" = '"+second+"', "
                +colTimeType+" = '"+type+"' "
                +" WHERE id = ";
        String sql2 = "(SELECT id FROM "+TBname+" ORDER BY ID DESC LIMIT 1)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql+sql2);
    }

    public Version getNewVersion(){
        String sql = "SELECT * FROM "+TB_version_name+" ORDER BY ID DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do{
                Version version = new Version();
                version.setVer(cursor.getDouble(1));
                version.setDay(cursor.getString(2));
                return version;
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return null;
    }

    public MainNode getNew(String TBname) {
        // Select All Query
        String selectQuery = "SELECT * FROM "+TBname+" ORDER BY ID DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MainNode mainNode = new MainNode();
                mainNode.setId(cursor.getInt(0));
                mainNode.setHour(cursor.getString(1));
                mainNode.setMinute(cursor.getString(2));
                mainNode.setSecond(cursor.getString(3));
                mainNode.setTime_type(cursor.getString(4));
                mainNode.setDay(cursor.getString(5));
                mainNode.setMounth(cursor.getString(6));
                mainNode.setYear(cursor.getString(7));
                return mainNode;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
