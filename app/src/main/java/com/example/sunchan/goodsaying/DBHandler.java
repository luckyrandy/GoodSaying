package com.example.sunchan.goodsaying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String TAG = "DEBUG - DBHandler";

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "GOODSAYING";

    // table name
    private static final String table_name = "GOODSAYING";
    // colunm name
    private static final String col_ID = "ID";
    private static final String col_TEXT = "TEXT";

    private static final String[] COLUMNS = { col_ID, col_TEXT };


    // table name for Alarm
    private static final String alarm_table_name = "SET_ALARM";
    // colunm name for Alarm
    private static final String alarm_col_FLAG = "FLAG";
    private static final String alarm_col_HOUR = "HOUR";
    private static final String alarm_col_MIN = "MINUTE";

    private static final String[] ALARM_COLUMNS = { alarm_col_FLAG, alarm_col_HOUR, alarm_col_MIN };


    public DBHandler(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create main table
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS GOODSAYING ( "
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TEXT TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public void alarmDBCreate(SQLiteDatabase db) {
        String CREATE_ALARM_TABLE = "CREATE TABLE IF NOT EXISTS SET_ALARM ( "
                + "FLAG INTEGER, "
                + "HOUR INTEGER, "
                + "MINUTE INTEGER)";
        db.execSQL(CREATE_ALARM_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop GOODSAYING table if already exists
        db.execSQL("DROP TABLE IF EXISTS GOODSAYING");
        this.onCreate(db);

        // drop SET_ALARM table if already exists
        db.execSQL("DROP TABLE IF EXISTS SET_ALARM");
        this.alarmDBCreate(db);
    }

    public long insertItem(Item item) {
        long lResult = 0;

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "Text : " + item.getText());

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(col_TEXT, item.getText());

        // insert anniversary
        lResult = db.insert(table_name, null, values);

        Log.d(TAG, "Insert result : " + lResult);

        // close database transaction
        db.close();

        return lResult;
    }

    public Item readItem(int id) {
        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getReadableDatabase();

        // get item query
        Cursor cursor = db.query(table_name, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setText(cursor.getString(1));

        return item;
    }

    public ArrayList getAllItems() {
        ArrayList items = new ArrayList();

        // select book query
        String query = "SELECT  * FROM " + table_name;

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setText(cursor.getString(1));

                // Add anniversary to items
                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public int updateItem(int id, String text) {

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(col_TEXT, text);

        // update
        int i = db.update(table_name, values, col_ID + " = ?", new String[] { String.valueOf(id) });

        db.close();
        return i;
    }

    // Deleting single item
    public void deleteItem(int id) {

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete book
        db.delete(table_name, col_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // for SET_ALARM table
    ///////////////////////////////////////////////////////////////////////////////////

    public long insertAlarmItem(AlarmItem item) {
        long lResult = 0;

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "FLAG : " + item.getFlag());
        Log.d(TAG, "HOUR : " + item.getHour());
        Log.d(TAG, "MINUTE : " + item.getMin());

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(alarm_col_FLAG, item.getFlag());
        values.put(alarm_col_HOUR, item.getHour());
        values.put(alarm_col_MIN, item.getMin());

        // insert anniversary
        lResult = db.insert(alarm_table_name, null, values);

        Log.d(TAG, "Insert result : " + lResult);

        // close database transaction
        db.close();

        return lResult;
    }


    public ArrayList getAllAlarmItem() {
        ArrayList items = new ArrayList();

        // select book query
        String query = "SELECT  * FROM " + alarm_table_name;

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        AlarmItem item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new AlarmItem();
                item.setFlag(Integer.parseInt(cursor.getString(0)));
                item.setHour(Integer.parseInt(cursor.getString(1)));
                item.setMin(Integer.parseInt(cursor.getString(2)));

                // Add anniversary to items
                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }


    // Deleting single item
    public void deleteAlarmItem() {

        // get reference of the GOODSAYING database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete alarmitem
        String query = "DELETE FROM " + alarm_table_name;
        Log.d(TAG, query);
        db.execSQL(query);
        db.close();
    }
}