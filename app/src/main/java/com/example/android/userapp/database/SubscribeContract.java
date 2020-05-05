package com.example.android.userapp.database;

import android.provider.BaseColumns;

public class SubscribeContract {
    public static abstract class SubscribeEntry implements BaseColumns {
        public static final String TABLE_NAME="Subscribe";
        public static final String COLUMN_R_ID="R_id";
        public static final String COLUMN_C_ID="C_id";
    }
}
