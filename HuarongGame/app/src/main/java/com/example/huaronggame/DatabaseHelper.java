package com.example.huaronggame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_RECORD = "records";
    private static final String RECORDS_CREATE_TABLE_SQL = "create table " +
            TABLE_RECORD +
            "(id integer primary key autoincrement," +
            "type varchar(20) not null," +
            "name varchar(20) not null," +
            "step integer not null" +
            ")" ;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RECORDS_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
