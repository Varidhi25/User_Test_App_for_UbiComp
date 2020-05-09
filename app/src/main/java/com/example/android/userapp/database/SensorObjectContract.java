package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class SensorObjectContract {
    public static abstract class SensorObjectEntry implements BaseColumns {
        public static final String TABLE_NAME="SensorObject";
        public static final String COLUMN_S_ID="S_id";
        public static final String COLUMN_S_NAME="S_Name";
        public static final String COLUMN_S_MCAT="S_mCategory";
        public static final String COLUMN_S_CAT_ID="S_CategoryID";
        public static final String COLUMN_S_BRAND_ID="S_BrandID";
        public static final String COLUMN_S_QUANTITY="S_Quantity";
        public static final String COLUMN_S_EXP_DATE="S_ExpDate";
    }
}
