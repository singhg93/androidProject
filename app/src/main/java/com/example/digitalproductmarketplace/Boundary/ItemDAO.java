package com.example.digitalproductmarketplace.Boundary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.digitalproductmarketplace.Entity.Item;

import java.util.ArrayList;

/**
 *
 */
public class ItemDAO implements ItemDAOInterface {


    private final String LOG_TAG = "Item DAO";
    private DBHelper _dbHelper;
    private SQLiteDatabase _db;

    /**
     * The default constructor
     * @param context
     */
    public ItemDAO (Context context) {
        _dbHelper = new DBHelper(context);
    }


    //    public static final String ITEMS_TABLE_NAME = "items;";
//    public static final String ITEM_ID = "id";
//    public static final String DESCRIPTION = "description";
//    public static final String PRICE = "price";
//    public static final String CATEGORY = "category";
//    public static final String USER_ID = "userId";
//    public static final String PICTURE = "picName";
//    public static final String FILE_URL = "fileUrl";

    @Override
    public Item getItem(int itemId) {

        try {

            // get the database
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.ITEM_ID,
                    DBHelper.DESCRIPTION,
                    DBHelper.PRICE,
                    DBHelper.CATEGORY,
                    DBHelper.USER_ID,
                    DBHelper.PICTURE,
                    DBHelper.FILE_URL
            };

            // filter the results to return users with the given email
            String selection = DBHelper.ITEM_ID + " = ?";
            String[] selectionArgs = {String.valueOf(itemId)};

            // get the results
            Cursor cursor = _db.query(
                    DBHelper.ITEMS_TABLE_NAME,  // The table to query
                    projection, // the array of columns to return
                    selection,  // the columns for the where clause
                    selectionArgs, // arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            Item requestedItem = new Item();


            // if the cursor has a next row, move to the first one and get all the required infor
            if (cursor.moveToNext()) {
                cursor.moveToFirst();
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID));
                String itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DESCRIPTION));
                double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.PRICE));
                String itemCategory = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CATEGORY));
                long itemUserId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                String itemPicture = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PICTURE));
                String itemFile = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FILE_URL));

                // create a user object from the database values
                requestedItem.set_id(id);
                requestedItem.set_description(itemDescription);
                requestedItem.set_price(itemPrice);
                requestedItem.set_catagory(itemCategory);
                requestedItem.set_userId(itemUserId);
                requestedItem.set_picName(itemPicture);
                requestedItem.set_fileUrl(itemFile);

                // if there are no rows in the curson, return null
            } else {
                requestedItem = null;
            }

            // return the user
            return requestedItem;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> allItems= new ArrayList<>();
        try {
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.ITEM_ID,
                    DBHelper.DESCRIPTION,
                    DBHelper.PRICE,
                    DBHelper.CATEGORY,
                    DBHelper.USER_ID,
                    DBHelper.PICTURE,
                    DBHelper.FILE_URL
            };


            // get the results
            Cursor cursor = _db.query(
                    DBHelper.ITEMS_TABLE_NAME,  // The table to query
                    projection, // the array of columns to return
                    null,  // don't filter
                    null, // no filter arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            Item nextItem = new Item();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID));
                String itemDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DESCRIPTION));
                double itemPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.PRICE));
                String itemCategory = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.CATEGORY));
                long itemUserId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                String itemPicture = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PICTURE));
                String itemFile = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FILE_URL));

                nextItem.set_id(id);
                nextItem.set_description(itemDescription);
                nextItem.set_price(itemPrice);
                nextItem.set_catagory(itemCategory);
                nextItem.set_userId(itemUserId);
                nextItem.set_picName(itemPicture);
                nextItem.set_fileUrl(itemFile);
            }

            // return the user
            return allItems;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }

    }


    @Override
    public long insertItem(Item newItem) {

        try {
            // Gets the data repository in writable mode
            _db = _dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys

            ContentValues values = new ContentValues();

            // create a hash map of values to be inserted in the database
            values.put(DBHelper.DESCRIPTION, newItem.get_description());
            values.put(DBHelper.PRICE, newItem.get_price());
            values.put(DBHelper.CATEGORY, newItem.get_catagory());
            values.put(DBHelper.USER_ID, newItem.get_userId());
            values.put(DBHelper.PICTURE, newItem.get_picName());
            values.put(DBHelper.FILE_URL, newItem.get_fileUrl());

            // get the row if of newly inserted user
            long newRowId = _db.insert(DBHelper.ITEMS_TABLE_NAME, null, values);

            // insert the row id
            return newRowId;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return -1;
        }
    }

    @Override
    public int updateItem(Item updatedItem) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // create a ContentValues with the updated values
            ContentValues values = new ContentValues();
            values.put(DBHelper.DESCRIPTION, updatedItem.get_description());
            values.put(DBHelper.PRICE, updatedItem.get_price());
            values.put(DBHelper.CATEGORY, updatedItem.get_catagory());
            values.put(DBHelper.USER_ID, updatedItem.get_userId());
            values.put(DBHelper.PICTURE, updatedItem.get_picName());
            values.put(DBHelper.FILE_URL, updatedItem.get_fileUrl());

// Which row to update, based on the title
            String selection = DBHelper.ID + " = ?";
            String[] selectionArgs = {String.valueOf(updatedItem.get_id())};

            int count = _db.update(
                    DBHelper.ITEMS_TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            return count;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return 0;
        }    }

    @Override
    public int deleteItem(Item itemToBeDeleted) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = DBHelper.ITEM_ID + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {String.valueOf(itemToBeDeleted.get_id())};

            // Issue SQL statement.
            int deletedRows = _db.delete(DBHelper.ITEMS_TABLE_NAME, selection, selectionArgs);
            return deletedRows;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return 0;
        }
    }
}
