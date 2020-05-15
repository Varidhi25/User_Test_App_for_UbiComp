package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class Temp3Contract {
    public static abstract class Temp3Entry implements BaseColumns {
        public static final String TABLE_NAME="Temp1";
        public static final String COLUMN_T3_NAME="item_Name";
        public static final String COLUMN_T3_MCAT="mCategory";
        public static final String COLUMN_T3_CAT="Category";
        public static final String COLUMN_T3_BRAND="BrandID";
        public static final String COLUMN_T3_QUANTITY="Quantity";
        public static final String COLUMN_T3_EXP_DATE="ExpDate";
    }
}
