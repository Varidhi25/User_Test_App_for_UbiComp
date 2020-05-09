package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class BrandContract {
    public static abstract class BrandEntry implements BaseColumns {
        public static final String TABLE_NAME="Brand";
        public static final String COLUMN_B_ID="B_id";
        public static final String COLUMN_B_NAME="B_Name";
    }
}
