package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class Temp1Contract {

    public static abstract class Temp1Entry implements BaseColumns {
        public static final String TABLE_NAME="Temp1";
        public static final String COLUMN_T1_NAME="item_Name";
        public static final String COLUMN_T1_MCAT="mCategory";
        public static final String COLUMN_T1_CAT="Category";
        public static final String COLUMN_T1_BRAND="BrandID";
        public static final String COLUMN_T1_QUANTITY="Quantity";
        public static final String COLUMN_T1_EXP_DATE="ExpDate";
    }
}
