package com.example.digitalproductmarketplace.boundary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {



    private static final String DB_NAME = "marketplace";
    private static final int DB_VERSION = 1;
    public static final String ID = "id";
    public static final String USERS_TABLE_NAME = "users";
    public static final String FIRST_NAME_COLUMN = "firstName";
    public static final String LAST_NAME_COLUMN = "lastName";
    public static final String EMAIL_COLUMN = "email";
    public static final String PASSWORD_COLUMN = "password";

    // column names for items table
    public static final String ITEMS_TABLE_NAME = "items";
    public static final String ITEM_ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
    public static final String USER_ID = "userId";
    public static final String PICTURE = "picName";
    public static final String FILE_URL = "fileUrl";

    // column names for adPosts table
    public static final String AD_POST_TABLE = "advertisements";
    public static final String AD_ID = "id";
    public static final String ITEM_ID_FK = "itemId";
    public static final String DATE_POSTED = "datePosted";
    public static final String DATE_UPDATED = "lastUpdated";


    public DBHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE " + USERS_TABLE_NAME
                + "( "
                + ID + " INTEGER PRIMARY KEY, "
                + FIRST_NAME_COLUMN + " TEXT NOT NULL, "
                + LAST_NAME_COLUMN + " TEXT NOT NULL, "
                + EMAIL_COLUMN +" TEXT UNIQUE NOT NULL, "
                + PASSWORD_COLUMN + " TEXT NOT NULL);";

        db.execSQL(sqlCreate);

        String sqlItemsCreate = "CREATE TABLE " + ITEMS_TABLE_NAME
                + "( "
                + ITEM_ID + " INTEGER PRIMARY KEY, "
                + DESCRIPTION + " TEXT NOT NULL, "
                + PRICE + " REAL NOT NULL, "
                + CATEGORY +" TEXT NOT NULL, "
                + USER_ID + " INTEGER NOT NULL,"
                + PICTURE + " TEXT NOT NULL,"
                + FILE_URL + " TEXT NOT NULL,"
                + "FOREIGN KEY (" + USER_ID + ") REFERENCES "
                + USERS_TABLE_NAME + "(id)" +
                ");";

        db.execSQL(sqlItemsCreate);
//
//        public static final String AD_POST_TABLE = "advertisements;";
//        public static final String AD_ID = "id";
//        public static final String ITEM_ID_FK = "itemId";
//        public static final String DATE_POSTED = "datePosted";
//        public static final String DATE_UPDATED = "lastUpdated";

        String sqlAdsCreate = "CREATE TABLE " + AD_POST_TABLE
                + "( "
                + AD_ID + " INTEGER PRIMARY KEY, "
                + ITEM_ID_FK + " INTEGER NOT NULL, "
                + USER_ID + " INTEGER NOT NULL, "
                + PRICE + " REAL NOT NULL, "
                + DATE_POSTED +" INTEGER NOT NULL, "
                + DATE_UPDATED + " INTEGER NOT NULL,"
                + "FOREIGN KEY (" + USER_ID + ") REFERENCES "
                + USERS_TABLE_NAME+ "(id),"
                + "FOREIGN KEY (" + ITEM_ID_FK + ") REFERENCES "
                + ITEMS_TABLE_NAME+ "(id)"
                + ");";

        db.execSQL(sqlAdsCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + AD_POST_TABLE + ";");
        onCreate(db);
    }
}
