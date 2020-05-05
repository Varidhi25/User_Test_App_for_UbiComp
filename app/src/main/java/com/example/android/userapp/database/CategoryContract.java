package com.example.android.userapp.database;

import android.provider.BaseColumns;

/* Contract related to the Category database
 * It contains all the constants related to Category */
public class CategoryContract {
    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME="Category";
        public static final String COLUMN_C_ID="C_id";
        public static final String COLUMN_C_NAME="C_Name";
    }
}
