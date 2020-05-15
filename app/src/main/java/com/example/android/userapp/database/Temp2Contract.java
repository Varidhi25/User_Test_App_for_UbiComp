package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class Temp2Contract {
    public static abstract class Temp2Entry implements BaseColumns {
        public static final String TABLE_NAME="Temp1";
        public static final String COLUMN_T2_NAME="item_Name";
        public static final String COLUMN_T2_MCAT="mCategory";
        public static final String COLUMN_T2_CAT="Category";
        public static final String COLUMN_T2_BRAND="BrandID";
        public static final String COLUMN_T2_QUANTITY="Quantity";
        public static final String COLUMN_T2_EXP_DATE="ExpDate";
    }
}
