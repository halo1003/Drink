package com.example.toands.drink.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.toands.drink.ContainValue;
import com.example.toands.drink.Model.MainNode;

public class SQLiteDB extends SQLiteOpenHelper{

    static String DBname = "Schedule";
    public static String TBname = "Table_time_and_day";
    static String colHour = "hour";
    static String colMinute = "minute";
    static String colSecond = "second";
    static String colTimeType = "time_type";
    static String colDay = "day";
    static String colMonth = "month";
    static String colYear = "year";

    MainNode mainNode;
    String TAG = "SQLiteDB";

    public SQLiteDB(Context context) {
        super(context, DBname, null, 1);
    }

    public SQLiteDB(Context context, MainNode mainNode) {
        super(context, DBname, null, 1);
        this.mainNode = mainNode;
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
        Log.e(TAG,"Insert success");
        db.close();
    }

    public MainNode getNew(String TBname) {
        // Select All Query
        String selectQuery = "SELECT * FROM "+TBname+" ORDER BY ID DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                MainNode mainNode = new MainNode();
                mainNode.setDay(cursor.getString(5));

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
