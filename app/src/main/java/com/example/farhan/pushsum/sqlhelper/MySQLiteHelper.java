package com.example.farhan.pushsum.sqlhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_WEEK = "week";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MONDAY = "monday";
    public static final String COLUMN_TUESDAY = "tuesday";
    public static final String COLUMN_WEDNESDAY = "wednesday";
    public static final String COLUMN_THURSDAY = "thursday";
    public static final String COLUMN_FRIDAY = "friday";
    public static final String COLUMN_SATURDAY = "saturday";
    public static final String COLUMN_SUNDAY = "sunday";
    public static final String COLUMN_OBBIETTIVO = "obbiettivo";
    public static final String COLUMN_MASSIMO = "massimo";

    private static final String DATABASE_NAME = "week.db";
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TABLE_WEEK + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MONDAY + " INTEGER NOT NULL, " +
            COLUMN_TUESDAY + " INTEGER NOT NULL, " +
            COLUMN_WEDNESDAY + " INTEGER NOT NULL, " +
            COLUMN_THURSDAY + " INTEGER NOT NULL, " +
            COLUMN_FRIDAY + " INTEGER NOT NULL, " +
            COLUMN_SATURDAY + " INTEGER NOT NULL, " +
            COLUMN_SUNDAY + " INTEGER NOT NULL, " +
            COLUMN_OBBIETTIVO + " INTEGER NOT NULL, " +
            COLUMN_MASSIMO + " INTEGER NOT NULL" +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEEK);
        onCreate(sqLiteDatabase);
    }
}
