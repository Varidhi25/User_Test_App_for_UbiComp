package com.example.android.userapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BrandDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = BrandDbHelper.class.getSimpleName();


    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "Brand.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link CategoryDbHelper}.
     *
     * @param context of the app
     */
    public BrandDbHelper(@Nullable Context context) {
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
        String SQL_BRAND_CREATE_TABLE = "CREATE TABLE " + BrandContract.BrandEntry.TABLE_NAME + " (" +
                BrandContract.BrandEntry.COLUMN_B_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BrandContract.BrandEntry.COLUMN_B_NAME + " VARCHAR(30) NOT NULL );";


        //execute the create table query
        db.execSQL(SQL_BRAND_CREATE_TABLE);

        //insert statements for categories
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('cadbury')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('hershey')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('nestle')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('amul')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('brittania')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('harvestGold')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('sprite')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('thumbsUp')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('pepsi')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('cococola')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('mirinda')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('fanta')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('mountainDew')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('Frooti')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('tropicana')");
        db.execSQL("INSERT INTO " + BrandContract.BrandEntry.TABLE_NAME + " (  " + BrandContract.BrandEntry.COLUMN_B_NAME + " ) VALUES ('nandini')");
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + CategoryContract.CategoryEntry.TABLE_NAME + " ;";
        db.execSQL(SQL_DROP_TABLE);
    }

    /*
    *method to get the id of the category
     @param category
     * It returns the id of the passed category  if exists else returns -1;
    */
    public long getBID(String brand) {
        SQLiteDatabase db = this.getReadableDatabase();

        //query to get rid of the category
        Cursor cursor = db.rawQuery("SELECT " + BrandContract.BrandEntry.COLUMN_B_ID + " FROM " + BrandContract.BrandEntry.TABLE_NAME + " WHERE " +
                BrandContract.BrandEntry.COLUMN_B_NAME + " = ?", new String[]{brand});
        if (cursor.getCount() > 0) {
            String B_ID;
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    B_ID = cursor.getString(cursor.getColumnIndex(BrandContract.BrandEntry.COLUMN_B_ID));
                    cursor.moveToNext();
                    cursor.close();
                    return Long.parseLong(B_ID);
                }

            }

        } else return -1;
        return 0;
    }
}
