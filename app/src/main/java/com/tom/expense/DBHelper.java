package com.tom.expense;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2015/9/21.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE expense ( _id INTEGER PRIMARY KEY,
        // udate DATETIME,
        // name TEXT,
        // amount INTEGER )
        db.execSQL("CREATE TABLE expense ( _id INTEGER PRIMARY KEY, " +
                "udate DATETIME, " +
                "name TEXT, " +
                "amount INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
