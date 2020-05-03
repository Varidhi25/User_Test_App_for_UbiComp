package com.example.android.userapp.database;

import android.provider.BaseColumns;

/* Contract related to the Register Form database
 * It contains all the constants related to the Register Form */
public class RegisterContract {
    public static abstract class RegisterEntry implements BaseColumns {
        public static final String TABLE_NAME="Register";
        public static final String COLUMN_R_ID="R_id";
        public static final String COLUMN_R_NAME="R_Name";
        public static final String COLUMN_R_EMAIL="R_Email";
        public static final String COLUMN_R_PHONE="R_PhoneNo";
        public static final String COLUMN_R_PASSWORD="R_Password";

    }

}
