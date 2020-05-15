package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class Temp3DbHelper  extends SQLiteOpenHelper {
    public static final String LOG_TAG = Temp3DbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "Temp3.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link SensorObjectDbHelper}.
     * @param context of the app
     */
    public Temp3DbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TEMP3_CREATE_TABLE="CREATE TABLE "+ Temp3Contract.Temp3Entry.TABLE_NAME +"( "+
                Temp3Contract.Temp3Entry.COLUMN_T3_NAME + " VARCHAR(20) NOT NULL, "+
                Temp3Contract.Temp3Entry.COLUMN_T3_MCAT + " VARCHAR(20) NOT NULL, "+
                Temp3Contract.Temp3Entry.COLUMN_T3_CAT + " VARCHAR(20)  NOT NULL, " +
                Temp3Contract.Temp3Entry.COLUMN_T3_BRAND+ " VARCHAR(20) NOT NULL, " +
                Temp3Contract.Temp3Entry.COLUMN_T3_QUANTITY + " DECIMAL(10,2) NOT NULL, "+
                Temp3Contract.Temp3Entry.COLUMN_T3_EXP_DATE+ " VARCHAR(10) NOT NULL, "+
                "PRIMARY KEY ("+ Temp3Contract.Temp3Entry.COLUMN_T3_NAME +", "+ Temp3Contract.Temp3Entry.COLUMN_T3_BRAND +", "+ Temp3Contract.Temp3Entry.COLUMN_T3_EXP_DATE+ ")"+
                ");" ;
        db.execSQL(SQL_TEMP3_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertObject(String itemName, String mainCategory,String category,String brand, int quantity, String expDate){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // These create an object with column rows and associated values
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_NAME ,itemName);
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_MCAT,mainCategory);
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_CAT,category);
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_BRAND,brand);
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_QUANTITY,quantity);
        contentValues.put(Temp3Contract.Temp3Entry.COLUMN_T3_EXP_DATE, expDate);
        db.insert(Temp3Contract.Temp3Entry.TABLE_NAME,null,contentValues);

    }
    public ArrayList<details> getData() {
        ArrayList<details> detailsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Temp3Contract.Temp3Entry.TABLE_NAME ,new String[]{});
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_NAME));
            String mainCategory = cursor.getString(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_MCAT));
            String category = cursor.getString(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_CAT));
            String brand=cursor.getString(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_BRAND));
            int quantity = cursor.getInt(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_QUANTITY));
            String expDate = cursor.getString(cursor.getColumnIndex(Temp3Contract.Temp3Entry.COLUMN_T3_EXP_DATE));
            details d = new details(itemName, mainCategory, category, brand, quantity, expDate);
            detailsArrayList.add(d);
        }
        return detailsArrayList;
    }
    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Temp3Contract.Temp3Entry.TABLE_NAME);
    }
}
