package com.example.digitalproductmarketplace.Boundary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {



    private static final String DB_NAME = "marketplace";
    private static final int DB_VERSION = 1;
    public static final String _ID = "id";
    public static final String USERS_TABLE_NAME = "users";
    public static final String FIRST_NAME_COLUMN = "firstName";
    public static final String LAST_NAME_COLUMN = "lastName";
    public static final String EMAIL_COLUMN = "email";
    public static final String PASSWORD_COLUMN = "password";

    public DBHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE " + USERS_TABLE_NAME
                + "( "
                + _ID + " INTEGER PRIMARY KEY, "
                + FIRST_NAME_COLUMN + " TEXT NOT NULL, "
                + LAST_NAME_COLUMN + " TEXT NOT NULL, "
                + EMAIL_COLUMN +" TEXT UNIQUE NOT NULL, "
                + PASSWORD_COLUMN + " TEXT NOT NULL);";

        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        onCreate(db);
    }
}
