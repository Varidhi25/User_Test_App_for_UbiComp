package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class Temp1DbHelper  extends SQLiteOpenHelper {
    public static final String LOG_TAG = Temp1DbHelper.class.getSimpleName();


    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "Temp1.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link SensorObjectDbHelper}.
     *
     * @param context of the app
     */
    public Temp1DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TEMP1_CREATE_TABLE = "CREATE TABLE " + Temp1Contract.Temp1Entry.TABLE_NAME + "( " +
                Temp1Contract.Temp1Entry.COLUMN_T1_NAME + " VARCHAR(20) NOT NULL, " +
                Temp1Contract.Temp1Entry.COLUMN_T1_MCAT + " VARCHAR(20) NOT NULL, " +
                Temp1Contract.Temp1Entry.COLUMN_T1_CAT + " VARCHAR(20)  NOT NULL, " +
                Temp1Contract.Temp1Entry.COLUMN_T1_BRAND + " VARCHAR(20) NOT NULL, " +
                Temp1Contract.Temp1Entry.COLUMN_T1_QUANTITY + " DECIMAL(10,2) NOT NULL, " +
                Temp1Contract.Temp1Entry.COLUMN_T1_EXP_DATE + " VARCHAR(10) NOT NULL, " +
                "PRIMARY KEY (" + Temp1Contract.Temp1Entry.COLUMN_T1_NAME + ", " + Temp1Contract.Temp1Entry.COLUMN_T1_BRAND + ", " + Temp1Contract.Temp1Entry.COLUMN_T1_EXP_DATE + ")" +
                ");";
        db.execSQL(SQL_TEMP1_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertObject(String itemName, String mainCategory, String category, String brand, int quantity, String expDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // These create an object with column rows and associated values
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_NAME, itemName);
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_MCAT, mainCategory);
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_CAT, category);
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_BRAND, brand);
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_QUANTITY, quantity);
        contentValues.put(Temp1Contract.Temp1Entry.COLUMN_T1_EXP_DATE, expDate);
        db.insert(Temp1Contract.Temp1Entry.TABLE_NAME, null, contentValues);
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Temp1Contract.Temp1Entry.TABLE_NAME, new String[]{});
        if(cursor.getCount()>0)
            return true ;
        else  return false;
    }

    public ArrayList<details> CategoryBased(String category) {
        ArrayList<details> detailsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Temp1Contract.Temp1Entry.TABLE_NAME + " WHERE " +
                Temp1Contract.Temp1Entry.COLUMN_T1_CAT + " =?", new String[]{category});
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(Temp1Contract.Temp1Entry.COLUMN_T1_NAME));
            String mainCategory = cursor.getString(cursor.getColumnIndex(Temp1Contract.Temp1Entry.COLUMN_T1_MCAT));
            String brand = cursor.getString(cursor.getColumnIndex(Temp1Contract.Temp1Entry.COLUMN_T1_BRAND));
            int quantity = cursor.getInt(cursor.getColumnIndex(Temp1Contract.Temp1Entry.COLUMN_T1_QUANTITY));
            String expDate = cursor.getString(cursor.getColumnIndex(Temp1Contract.Temp1Entry.COLUMN_T1_EXP_DATE));
            details d = new details(itemName, mainCategory, category, brand, quantity, expDate);
            detailsArrayList.add(d);
        }

        return detailsArrayList;
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Temp1Contract.Temp1Entry.TABLE_NAME);
    }
}