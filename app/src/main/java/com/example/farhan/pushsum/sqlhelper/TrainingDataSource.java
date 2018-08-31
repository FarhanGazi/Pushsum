package com.example.farhan.pushsum.sqlhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.farhan.pushsum.entity.Week;

public class TrainingDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_MONDAY,
            MySQLiteHelper.COLUMN_TUESDAY,
            MySQLiteHelper.COLUMN_WEDNESDAY,
            MySQLiteHelper.COLUMN_THURSDAY,
            MySQLiteHelper.COLUMN_FRIDAY,
            MySQLiteHelper.COLUMN_SATURDAY,
            MySQLiteHelper.COLUMN_SUNDAY,
            MySQLiteHelper.COLUMN_OBBIETTIVO,
            MySQLiteHelper.COLUMN_MASSIMO};

    public TrainingDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Week week) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_MONDAY, week.getMonday());
        values.put(MySQLiteHelper.COLUMN_TUESDAY, week.getTuesday());
        values.put(MySQLiteHelper.COLUMN_WEDNESDAY, week.getWednesday());
        values.put(MySQLiteHelper.COLUMN_THURSDAY, week.getThursday());
        values.put(MySQLiteHelper.COLUMN_FRIDAY, week.getFriday());
        values.put(MySQLiteHelper.COLUMN_SATURDAY, week.getSaturday());
        values.put(MySQLiteHelper.COLUMN_SUNDAY, week.getSunday());
        values.put(MySQLiteHelper.COLUMN_OBBIETTIVO, week.getObbiettivo());
        values.put(MySQLiteHelper.COLUMN_MASSIMO, week.getMassimo());

        long insertId = database.insert(MySQLiteHelper.TABLE_WEEK, null, values);
    }

    public void delete(Week week) {
        String selection = MySQLiteHelper.COLUMN_ID + "=?";
        String[] selectionArgs = {week.getId() + ""};
        int deleteRows = database.delete(MySQLiteHelper.TABLE_WEEK, selection, selectionArgs);
    }

    public void deleteAll() {
        int deleteRows = database.delete(MySQLiteHelper.TABLE_WEEK, null, null);
    }

    private Week cursorToWeek(Cursor cursor) {
        Week week = new Week(cursor.getLong(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getInt(9));

        return week;
    }

    public Week getWeek() {
        Week ris;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WEEK,
                allColumns, null, null, null
                , null, null);
        cursor.moveToFirst();
        ris = cursorToWeek(cursor);
        cursor.close();
        return ris;
    }

}