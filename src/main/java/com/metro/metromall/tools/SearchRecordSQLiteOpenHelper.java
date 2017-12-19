package com.metro.metromall.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guhf on 2017/11/20.
 */

public class SearchRecordSQLiteOpenHelper extends SQLiteOpenHelper {
    private static String name = "MetroMallSearch.db";
    private static Integer version = 1;

    public SearchRecordSQLiteOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table person(_id integer primary key autoincrement,name char(20),salary char(20),phone char(20))");
        db.execSQL("create table SearchHistory(id integer primary key autoincrement,Name varchar(200),logDate datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
