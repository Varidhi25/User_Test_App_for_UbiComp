package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.model.details;

import java.util.ArrayList;

public class Temp2DbHelper  extends SQLiteOpenHelper {
    public static final String LOG_TAG = Temp2DbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "Temp2.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link SensorObjectDbHelper}.
     * @param context of the app
     */
    public Temp2DbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TEMP2_CREATE_TABLE="CREATE TABLE "+ Temp2Contract.Temp2Entry.TABLE_NAME +"( "+
                Temp2Contract.Temp2Entry.COLUMN_T2_NAME + " VARCHAR(20) NOT NULL, "+
                Temp2Contract.Temp2Entry.COLUMN_T2_MCAT + " VARCHAR(20) NOT NULL, "+
                Temp2Contract.Temp2Entry.COLUMN_T2_CAT + " VARCHAR(20)  NOT NULL, " +
                Temp2Contract.Temp2Entry.COLUMN_T2_BRAND+ " VARCHAR(20) NOT NULL, " +
                Temp2Contract.Temp2Entry.COLUMN_T2_QUANTITY + " DECIMAL(10,2) NOT NULL, "+
                Temp2Contract.Temp2Entry.COLUMN_T2_EXP_DATE+ " VARCHAR(10) NOT NULL, "+
                "PRIMARY KEY ("+ Temp2Contract.Temp2Entry.COLUMN_T2_NAME +", "+ Temp2Contract.Temp2Entry.COLUMN_T2_BRAND +", "+ Temp2Contract.Temp2Entry.COLUMN_T2_EXP_DATE+ ")"+
                ");" ;
        db.execSQL(SQL_TEMP2_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertObject(String itemName, String mainCategory,String category,String brand, int quantity, String expDate){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // These create an object with column rows and associated values
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_NAME ,itemName);
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_MCAT,mainCategory);
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_CAT,category);
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_BRAND,brand);
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_QUANTITY,quantity);
        contentValues.put(Temp2Contract.Temp2Entry.COLUMN_T2_EXP_DATE, expDate);
        db.insert(Temp2Contract.Temp2Entry.TABLE_NAME,null,contentValues);
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Temp2Contract.Temp2Entry.TABLE_NAME, new String[]{});
        if(cursor.getCount()>0)
            return true ;
        else  return false;
    }

    public ArrayList<details> BrandBased(String brand) {
        ArrayList<details> detailsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Temp2Contract.Temp2Entry.TABLE_NAME + " WHERE " +
                Temp2Contract.Temp2Entry.COLUMN_T2_BRAND + " =?", new String[]{brand});
        while (cursor.moveToNext()) {
            String itemName = cursor.getString(cursor.getColumnIndex(Temp2Contract.Temp2Entry.COLUMN_T2_NAME));
            String mainCategory = cursor.getString(cursor.getColumnIndex(Temp2Contract.Temp2Entry.COLUMN_T2_MCAT));
            String category = cursor.getString(cursor.getColumnIndex(Temp2Contract.Temp2Entry.COLUMN_T2_CAT));
            int quantity = cursor.getInt(cursor.getColumnIndex(Temp2Contract.Temp2Entry.COLUMN_T2_QUANTITY));
            String expDate = cursor.getString(cursor.getColumnIndex(Temp2Contract.Temp2Entry.COLUMN_T2_EXP_DATE));
            details d = new details(itemName, mainCategory, category, brand, quantity, expDate);
            detailsArrayList.add(d);
        }
        return detailsArrayList;
    }

    public void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Temp2Contract.Temp2Entry.TABLE_NAME);
    }
}
