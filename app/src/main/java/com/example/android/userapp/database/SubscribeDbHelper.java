package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.Subscribe;

public class SubscribeDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = SubscribeDbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "Subscribe.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link SubscribeDbHelper}.
     * @param context of the app
     */
    public SubscribeDbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         *This is create table statement for the Subscribe Database
         * Get the constants created from the SubscribeContract Class
         */
        String SQL_SUBSCRIBE_CREATE_TABLE ="CREATE TABLE "+ SubscribeContract.SubscribeEntry.TABLE_NAME +" ( "+
                SubscribeContract.SubscribeEntry.COLUMN_R_ID+ " INT NOT NULL, "+
                SubscribeContract.SubscribeEntry.COLUMN_C_ID + " INT NOT NULL, "+
                "PRIMARY KEY ( "+SubscribeContract.SubscribeEntry.COLUMN_R_ID +"," +SubscribeContract.SubscribeEntry.COLUMN_C_ID +")," +
                "FOREIGN KEY ("+SubscribeContract.SubscribeEntry.COLUMN_R_ID +") REFERENCES "+ RegisterContract.RegisterEntry.TABLE_NAME+"(" + RegisterContract.RegisterEntry.COLUMN_R_ID +"),"+
                "FOREIGN KEY ("+SubscribeContract.SubscribeEntry.COLUMN_C_ID +") REFERENCES "+ CategoryContract.CategoryEntry.TABLE_NAME +"(" + CategoryContract.CategoryEntry.COLUMN_C_ID +") " +
                ");" ;

        //execute the sql statement
     db.execSQL(SQL_SUBSCRIBE_CREATE_TABLE);

    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE ="DROP TABLE IF EXISTS "+ SubscribeContract.SubscribeEntry.TABLE_NAME +" ;";
        db.execSQL(SQL_DROP_TABLE);
    }

    /**
     * This method is called to insert an item into the database {@link SubscribeDbHelper}
     * @param R_ID
     * @param C_ID
     */
    public void insertItem(long R_ID, long C_ID){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubscribeContract.SubscribeEntry.COLUMN_R_ID,R_ID);
        contentValues.put(SubscribeContract.SubscribeEntry.COLUMN_C_ID,C_ID);

        //insert the values into subscribe database
        long insert= db.insert(SubscribeContract.SubscribeEntry.TABLE_NAME,null,contentValues);
    }

}
