package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class RegisterDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = RegisterDbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "Register.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link RegisterDbHelper}.
     * @param context of the app
     */
    public RegisterDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        /*This is create table statement for the Register Database
         * Get the constants created from the RegisterContract Class
         */
        String SQL_REGISTER_CREATE_TABLE="CREATE TABLE "+ RegisterContract.RegisterEntry.TABLE_NAME + " (" +
                RegisterContract.RegisterEntry.COLUMN_R_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RegisterContract.RegisterEntry.COLUMN_R_NAME + " VARCHAR(30) NOT NULL, " +
                RegisterContract.RegisterEntry.COLUMN_R_EMAIL + " VARCHAR(20) NOT NULL, " +
                RegisterContract.RegisterEntry.COLUMN_R_PASSWORD + " VARCHAR(10) NOT NULL, "+
                RegisterContract.RegisterEntry.COLUMN_R_PHONE + " INTEGER NOT NULL); " ;


        //execute the create table query
        db.execSQL(SQL_REGISTER_CREATE_TABLE);
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       String SQL_DROP_TABLE ="DROP TABLE IF EXISTS "+ RegisterContract.RegisterEntry.TABLE_NAME +" ;";
    }

    /*
     insert() Method  to insert the data into the Register table of the database
     param(Name, Email, PhoneNo, Password)
     returns true/false
     */
    public boolean insertUser(String Name, String Email, String Password, int PhoneNo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // These create an object with column rows and associated values
        contentValues.put(RegisterContract.RegisterEntry.COLUMN_R_NAME ,Name);
        contentValues.put(RegisterContract.RegisterEntry.COLUMN_R_EMAIL ,Email);
        contentValues.put(RegisterContract.RegisterEntry.COLUMN_R_PASSWORD ,Password);
        contentValues.put(RegisterContract.RegisterEntry.COLUMN_R_PHONE ,PhoneNo);

        long insert= db.insert(RegisterContract.RegisterEntry.TABLE_NAME,null,contentValues);
        //if insertion isn't successful
        if(insert== -1) return false;

        //if insertion is  successful
        else return true;

    }

    /* method checkEmail
     this method is called to check whether the user already exists
     */

    public boolean checkEmail(String Email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ RegisterContract.RegisterEntry.TABLE_NAME+" where "+ RegisterContract.RegisterEntry.COLUMN_R_EMAIL +"=?", new String[]{Email});

        //the parameter passed already exists
        if(cursor.getCount()>0) return false;

        //user doesn't exist
        else return true;
    }

    //To check whether email and passwords are correct
    public Boolean emailPasswordCheck(String Email, String Password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ RegisterContract.RegisterEntry.TABLE_NAME+ " where "+
                RegisterContract.RegisterEntry.COLUMN_R_EMAIL+ " =? AND "+
                RegisterContract.RegisterEntry.COLUMN_R_PASSWORD+" =?",new String[]{Email,Password});

        //if both email and password are correct
        if(cursor.getCount()>0) return true;

        //if one of them is incorrect
        else return false;
    }
}
