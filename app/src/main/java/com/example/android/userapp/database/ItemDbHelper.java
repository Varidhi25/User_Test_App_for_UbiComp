package com.example.android.userapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.Item;

import java.util.ArrayList;

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "Item.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    public ItemDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String SQL_ITEM_CREATE_TABLE="CREATE TABLE "+ ItemContract.ItemtEntry.TABLE_NAME +"( "+
                ItemContract.ItemtEntry.COLUMN_I_NAME + " VARCHAR(20) NOT NULL, "+
                ItemContract.ItemtEntry.COLUMN_I_MCAT + " VARCHAR(20) NOT NULL, "+
                ItemContract.ItemtEntry.COLUMN_I_CAT + " VARCHAR(20) NOT NULL, " +
                ItemContract.ItemtEntry.COLUMN_I_BRAND + " VARCHAR(20) NOT NULL, " +
                "PRIMARY KEY( "+ItemContract.ItemtEntry.COLUMN_I_NAME +","+ItemContract.ItemtEntry.COLUMN_I_BRAND+") );";
        db.execSQL(SQL_ITEM_CREATE_TABLE);
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('coke','liquid','softDrink','cococola')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('coke','liquid','softDrink','pepsi')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('frooti','liquid','softDrink','tropicana')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME +  "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('frooti','liquid','softDrink','amul')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('mango','liquid','juice','tropicana')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('mango','liquid','juice','amul')");
        db.execSQL("INSERT INTO "+ItemContract.ItemtEntry.TABLE_NAME + "("+ItemContract.ItemtEntry.COLUMN_I_NAME+","+ItemContract.ItemtEntry.COLUMN_I_MCAT+","+ItemContract.ItemtEntry.COLUMN_I_CAT+","+ItemContract.ItemtEntry.COLUMN_I_BRAND+")VALUES('mango','liquid','juice','none')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Item> getDetails(String itemName){
        ArrayList<Item> itemArrayList =new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ ItemContract.ItemtEntry.TABLE_NAME +" WHERE "+ ItemContract.ItemtEntry.COLUMN_I_NAME+"=?",new String[]{itemName});
        while(cursor.moveToNext()){
            String Name=cursor.getString(cursor.getColumnIndex(ItemContract.ItemtEntry.COLUMN_I_NAME));
            String mainCategory=cursor.getString(cursor.getColumnIndex(ItemContract.ItemtEntry.COLUMN_I_MCAT));
            String category=cursor.getString(cursor.getColumnIndex(ItemContract.ItemtEntry.COLUMN_I_CAT));
            String brand=cursor.getString(cursor.getColumnIndex(ItemContract.ItemtEntry.COLUMN_I_BRAND));
            Item i=new Item(Name,mainCategory,category,brand);
            itemArrayList.add(i);
        }
        return itemArrayList;
    }
}
