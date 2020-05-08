package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class CategoryDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = CategoryDbHelper.class.getSimpleName();


    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "Category.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link CategoryDbHelper}.
     *
     * @param context of the app
     */
    public CategoryDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*This is create table statement for the Category Database
         * Get the constants created from the CategoryContract Class
         */
        String SQL_CATEGORY_CREATE_TABLE = "CREATE TABLE " + CategoryContract.CategoryEntry.TABLE_NAME + " (" +
                CategoryContract.CategoryEntry.COLUMN_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryContract.CategoryEntry.COLUMN_C_NAME + " VARCHAR(30) NOT NULL );";


        //execute the create table query
        db.execSQL(SQL_CATEGORY_CREATE_TABLE);

        //insert statements for categories
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('vegetable')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('fruit')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('cake')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('chocolate')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('bread')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('softDrink')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('coldDrink')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('juice')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('milkshake')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('water')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('milk')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('pudding')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('iceCream')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('sauce')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('jam')");
        db.execSQL("INSERT INTO " + CategoryContract.CategoryEntry.TABLE_NAME + " (  " + CategoryContract.CategoryEntry.COLUMN_C_NAME + " ) VALUES ('butter')");
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + CategoryContract.CategoryEntry.TABLE_NAME + " ;";
        db.execSQL(SQL_DROP_TABLE);
    }

    /*method to check if the category exists or not
    returns  true if exists else false
     */

    public Boolean categoryCheck(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoryContract.CategoryEntry.TABLE_NAME + " where " +
                CategoryContract.CategoryEntry.COLUMN_C_NAME + " =?", new String[]{category});

        if (cursor.getCount() > 0) return true;
        else return false;
    }

    /*
     *method to get the id of the category
      @param category
      * It returns the id of the passed category  if exists else returns -1;
     */
    public long getCID(String category) {
        SQLiteDatabase db = this.getReadableDatabase();

        //query to get rid of the category
        Cursor cursor = db.rawQuery("SELECT " + CategoryContract.CategoryEntry.COLUMN_C_ID + " FROM " + CategoryContract.CategoryEntry.TABLE_NAME + " WHERE " +
                CategoryContract.CategoryEntry.COLUMN_C_NAME + " = ?", new String[]{category});
        if (cursor.getCount() > 0) {
            String C_ID;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    C_ID = cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_C_ID));
                    cursor.moveToNext();
                    cursor.close();
                    return Long.parseLong(C_ID);
                }

            }

        } else return -1;
        return 0;
    }

    public String getCategory(long C_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String ID = toString().valueOf(C_ID);
        Cursor cursor = db.rawQuery("SELECT " + CategoryContract.CategoryEntry.COLUMN_C_NAME + "FROM " + CategoryContract.CategoryEntry.TABLE_NAME +
                " WHERE " + CategoryContract.CategoryEntry.COLUMN_C_ID + "=?", new String[]{ID});
        String category=null;
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    category = cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_C_NAME));
                    cursor.moveToNext();
                    cursor.close();
                    return category;
                }

            }
        }
        return category;
    }
}
