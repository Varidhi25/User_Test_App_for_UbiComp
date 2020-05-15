package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class ItemContract {
    public static abstract class ItemtEntry implements BaseColumns {
        public static final String TABLE_NAME = "Item";
        public static final String COLUMN_I_NAME = "I_Name";
        public static final String COLUMN_I_MCAT = "I_mCategory";
        public static final String COLUMN_I_CAT = "I_Category";
        public static final String COLUMN_I_BRAND = "I_Brand";
    }
}
