package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v4.database.CursorWindowCompat;

import com.example.android.userapp.model.details;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class  SensorObjectDbHelper extends SQLiteOpenHelper {
    CategoryDbHelper cdb;
    public static final String LOG_TAG = SensorObjectDbHelper.class.getSimpleName();


    /** Name of the database file */
    private static final String DATABASE_NAME = "SensorObject.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link SensorObjectDbHelper}.
     * @param context of the app
     */
    public SensorObjectDbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*This is create table statement for the Register Database
         * Get the constants created from the RegisterContract Class
         */
        String SQL_SENSOR_OBJECT_CREATE_TABLE="CREATE TABLE "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME +"( "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  , "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME+ " VARCHAR(20) NOT NULL, "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT+ " VARCHAR(10) NOT NULL, "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID+ " INT NOT NULL, " +
                SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY+ " DECIMAL(10,2) NOT NULL, "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE+ " VARCHAR(10) NOT NULL, "+
                "FOREIGN KEY ("+ SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID +") REFERENCES "+ CategoryContract.CategoryEntry.TABLE_NAME +"(" + CategoryContract.CategoryEntry.COLUMN_C_ID +") "+
                ");" ;
        db.execSQL(SQL_SENSOR_OBJECT_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     String SQL_DROP_TABLE="DROP TABLE IF EXISTS "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME;
     db.execSQL(SQL_DROP_TABLE);
    }

    public long itemExist(String itemName, String expDate){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + SensorObjectContract.SensorObjectEntry.COLUMN_S_ID + " FROM " + SensorObjectContract.SensorObjectEntry.TABLE_NAME + " WHERE " +
                SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME + " = ? AND "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE+"=?",  new String[]{itemName,expDate});
        //item exists already with same exp date
        if (cursor.getCount()>0) {
            String S_ID;
            if (cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    S_ID = cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_ID));
                    cursor.moveToNext();
                    cursor.close();
                    return Long.parseLong(S_ID);
                }

            }

        }
        //item doesn't exist with same expiry date
        else return -1;
        return -1;
    }

    public void insertObject(String itemName, String category, long C_ID, int quantity, int year, int month, int day){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
      String expDate=year+"-"+month+"-"+day;
        // These create an object with column rows and associated values
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME,itemName);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT,category);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID,C_ID);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY,quantity);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE, expDate);
        db.insert(SensorObjectContract.SensorObjectEntry.TABLE_NAME,null,contentValues);

      }

    /**
     * method used to update the quantity of already existing elements
     * @param S_ID
     * @param itemName
     * @param category
     * @param C_ID
     * @param quantity
     * @param expDate
     */
      public void updateQuantity(long S_ID,String itemName, String category, long C_ID, int quantity, String expDate){
          SQLiteDatabase db=this.getWritableDatabase();
          ContentValues contentValues = new ContentValues();
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_ID,S_ID);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME,itemName);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT,category);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID,C_ID);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY,quantity);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE,expDate);
          String ID= toString().valueOf(S_ID);
          db.update(SensorObjectContract.SensorObjectEntry.TABLE_NAME,contentValues, SensorObjectContract.SensorObjectEntry.COLUMN_S_ID+" =?",new String[]{ID});
      }

      public ArrayList<details> getAllData(){
          String category="vegetable";
          ArrayList<details> detailsArrayList=new ArrayList<>();
          SQLiteDatabase db=this.getReadableDatabase();
          Cursor cursor=db.rawQuery("SELECT "+SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME +", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE +" FROM "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME+";",null);
           while(cursor.moveToNext()){
               String itemName=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME));
               long C_ID =cursor.getLong(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID));
               int quantity=cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY));
               String expDate=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE));
               int ID=Integer.parseInt(toString().valueOf(C_ID));
               switch (ID){
                   case 1:category="Vegetable";
                           break;
                   case 2:category="Fruit";
                           break;
                   case 3:category="Cake";
                       break;
                   case 4:category="Chocolate";
                          break;
                   case 5:category="Bread";
                       break;
                   case 6:category="Soft Drink";
                       break;
                   case 7:category="Cold Drink";
                       break;
                   case 8:category="Juice";
                       break;
                   case 9:category="Milkshake";
                       break;
                   case 10:category="Water";
                       break;
                   case 11:category="Milk";
                            break;
                   case 12:category="Pudding";
                       break;
                   case 13:category="Ice-cream";
                       break;
                   case 14:category="Sauce";
                       break;
                   case 15:category="Jam";
                       break;
                   case 16:category="Butter";
                       break;
               }
               details details=new details(itemName,category,quantity,expDate);
               detailsArrayList.add(details);
           }
           return detailsArrayList;
      }
}
