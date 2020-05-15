package com.example.android.userapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.android.userapp.model.details;

import java.util.ArrayList;

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
                SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID+ " INT NOT NULL, " +
                SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY+ " DECIMAL(10,2) NOT NULL, "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE+ " VARCHAR(10) NOT NULL, "+
                "FOREIGN KEY ("+ SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID +") REFERENCES "+ BrandContract.BrandEntry.TABLE_NAME +"(" + BrandContract.BrandEntry.COLUMN_B_ID +"), "+
                "FOREIGN KEY ("+ SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID +") REFERENCES "+ CategoryContract.CategoryEntry.TABLE_NAME +"(" + CategoryContract.CategoryEntry.COLUMN_C_ID +") "+
                ");" ;
        db.execSQL(SQL_SENSOR_OBJECT_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     String SQL_DROP_TABLE="DROP TABLE IF EXISTS "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME;
     db.execSQL(SQL_DROP_TABLE);
    }

    /**
     * Method to check if the item already exists or not
     * @param itemName
     * @param C_ID
     * @param expDate
     * @return S_ID of the parameter passed
     */
    public long itemExist(String itemName,long C_ID,long B_ID, String expDate){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + SensorObjectContract.SensorObjectEntry.COLUMN_S_ID + " FROM " + SensorObjectContract.SensorObjectEntry.TABLE_NAME + " WHERE " +
                SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME + " = ? AND "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE+"=? AND "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID +"=? AND "+
                SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID +"=?",  new String[]{itemName,expDate,toString().valueOf(C_ID),toString().valueOf(B_ID)});
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

    /**
     * Method used to insert the object/item
     * @param itemName
     * @param category
     * @param C_ID
     * @param quantity
     * @param year
     * @param month
     * @param day
     */
    public void insertObject(String itemName, String category, long C_ID,long B_ID, int quantity, int year, int month, int day){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
      String expDate=year+"-"+month+"-"+day;
        // These create an object with column rows and associated values
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME,itemName);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT,category);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID,C_ID);
        contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID,B_ID);
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
      public void updateQuantity(long S_ID,String itemName, String category, long C_ID,long B_ID, int quantity, String expDate){
          SQLiteDatabase db=this.getWritableDatabase();
          ContentValues contentValues = new ContentValues();
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_ID,S_ID);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME,itemName);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT,category);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID,C_ID);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID,B_ID);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY,quantity);
          contentValues.put(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE,expDate);
          String ID= toString().valueOf(S_ID);
          db.update(SensorObjectContract.SensorObjectEntry.TABLE_NAME,contentValues, SensorObjectContract.SensorObjectEntry.COLUMN_S_ID+" =?",new String[]{ID});
      }

    /**
     * Method to get all the details of item
     * @return ArrayList which contains the details such as itemName, category,quantity, expiry date
     * @param detailsArrayList
     */
      public ArrayList<details> getAllData(ArrayList<details> detailsArrayList){
          String category="vegetable", brand="amul";
          SQLiteDatabase db=this.getReadableDatabase();
          Cursor cursor=db.rawQuery("SELECT "+SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME +", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY+", "+
                  SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE +
                  " FROM "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME+";",null);
           while(cursor.moveToNext()){
               String itemName=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME));
               String mainCategoty=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT));
               long C_ID =cursor.getLong(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID));
               long B_ID =cursor.getLong(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID));
               int quantity=cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY));
               String expDate=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE));
               int ID=Integer.parseInt(toString().valueOf(C_ID));
               switch (ID){
                   case 1:category="vegetable";
                           break;
                   case 2:category="fruit";
                           break;
                   case 3:category="cake";
                       break;
                   case 4:category="chocolate";
                          break;
                   case 5:category="bread";
                       break;
                   case 6:category="softDrink";
                       break;
                   case 7:category="coldDrink";
                       break;
                   case 8:category="juice";
                       break;
                   case 9:category="milkshake";
                       break;
                   case 10:category="water";
                       break;
                   case 11:category="milk";
                            break;
                   case 12:category="pudding";
                       break;
                   case 13:category="iceCream";
                       break;
                   case 14:category="sauce";
                       break;
                   case 15:category="jam";
                       break;
                   case 16:category="butter";
                       break;
               }

               ID=Integer.parseInt(toString().valueOf(B_ID));
               switch (ID){
                   case 1:brand="cadbury";
                       break;
                   case 2:brand="hershey";
                       break;
                   case 3:brand="nestle";
                       break;
                   case 4:brand="amul";
                       break;
                   case 5:brand="brittania";;
                       break;
                   case 6:brand="harvestGold";
                       break;
                   case 7:brand="sprite";
                       break;
                   case 8:brand="thumbsUp";
                       break;
                   case 9:brand="pepsi";
                       break;
                   case 10:brand="cococola";
                       break;
                   case 11:brand="mirinda";
                       break;
                   case 12:brand="fanta";
                       break;
                   case 13:brand="mountainDew";
                       break;
                   case 14:brand="Frooti";
                       break;
                   case 15:brand="tropicana";
                       break;
                   case 16:brand="nandini";
                       break;
                   case 17:brand="none";
               }
               details details=new details(itemName,mainCategoty,category,brand,quantity,expDate);
               detailsArrayList.add(details);
           }
           return detailsArrayList;
      }

    /**
     *
     * @param S_ID
     * @return quatity of parameter passed
     */
    public int getQuantity(long S_ID){
        int quantity=0;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT "+ SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY +" FROM "+
                SensorObjectContract.SensorObjectEntry.TABLE_NAME +" WHERE "+ SensorObjectContract.SensorObjectEntry.COLUMN_S_ID+" =? ;",new String[]{toString().valueOf(S_ID)});
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                quantity=cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY));
                return quantity;
            }

        }
        return quantity;
    }

    /**
     * Method to delete the item from the database
     * @param S_ID
     */
    public void deleteItem(long S_ID){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(SensorObjectContract.SensorObjectEntry.TABLE_NAME, SensorObjectContract.SensorObjectEntry.COLUMN_S_ID +"=?",new String[]{toString().valueOf(S_ID)});

    }
    public ArrayList<details> getItemsBasedOnMcategory(String mainCategory){
        ArrayList<details> detailsArrayList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String category="vegetable",brand="amul";
        Cursor cursor=db.rawQuery("SELECT * FROM "+ SensorObjectContract.SensorObjectEntry.TABLE_NAME +" WHERE "+ SensorObjectContract.SensorObjectEntry.COLUMN_S_MCAT +" =?",new String[]{mainCategory});
        while(cursor.moveToNext()){
            String itemName=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_NAME));
            int C_ID=cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_CAT_ID));
            int B_ID=cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_BRAND_ID));
            int quantity =cursor.getInt(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_QUANTITY));
            String expDate=cursor.getString(cursor.getColumnIndex(SensorObjectContract.SensorObjectEntry.COLUMN_S_EXP_DATE));
            switch (C_ID){
                case 1:category="vegetable";
                    break;
                case 2:category="fruit";
                    break;
                case 3:category="cake";
                    break;
                case 4:category="chocolate";
                    break;
                case 5:category="bread";
                    break;
                case 6:category="softDrink";
                    break;
                case 7:category="coldDrink";
                    break;
                case 8:category="juice";
                    break;
                case 9:category="milkshake";
                    break;
                case 10:category="water";
                    break;
                case 11:category="milk";
                    break;
                case 12:category="pudding";
                    break;
                case 13:category="iceCream";
                    break;
                case 14:category="sauce";
                    break;
                case 15:category="jam";
                    break;
                case 16:category="butter";
                    break;

            }
            switch (B_ID){
                case 1:brand="cadbury";
                    break;
                case 2:brand="hershey";
                    break;
                case 3:brand="nestle";
                    break;
                case 4:brand="amul";
                    break;
                case 5:brand="brittania";;
                    break;
                case 6:brand="harvestGold";
                    break;
                case 7:brand="sprite";
                    break;
                case 8:brand="thumbsUp";
                    break;
                case 9:brand="pepsi";
                    break;
                case 10:brand="cococola";
                    break;
                case 11:brand="mirinda";
                    break;
                case 12:brand="fanta";
                    break;
                case 13:brand="mountainDew";
                    break;
                case 14:brand="Frooti";
                    break;
                case 15:brand="tropicana";
                    break;
                case 16:brand="nandini";
                    break;
                case 17:brand="none";
            }
            details d=new details(itemName,mainCategory,category,brand,quantity,expDate);
            detailsArrayList.add(d);
        }
        return detailsArrayList;
    }

}
