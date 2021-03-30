package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppDB";

    public static final String TABLE_1 = "Механическая_работа";
    public static final String TABLE_1_KEY_ID = "_id";
    public static final String TABLE_1_row1 = "участок";
    public static final String TABLE_1_row2 = "дата";
    public static final String TABLE_1_row3 = "деятельность";
    public static final String TABLE_1_row4 = "исполнитель";
    public static final String TABLE_1_row5 = "отв_лицо";
    public static final String TABLE_1_row6 = "расход";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_1 + "("
                + TABLE_1_KEY_ID + " integer primary key, "
                + TABLE_1_row1 +" text, "+ TABLE_1_row2 +" text, "
                + TABLE_1_row3 +" text, "+ TABLE_1_row4 +" text, "
                + TABLE_1_row5 +" text, " + TABLE_1_row6 +" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_1);
        onCreate(db);
    }
}
