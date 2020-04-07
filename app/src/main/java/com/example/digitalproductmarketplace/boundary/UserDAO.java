package com.example.digitalproductmarketplace.boundary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.digitalproductmarketplace.entity.User;

import java.util.ArrayList;

/**
 *
 */
public class UserDAO implements UserDAOInterface {

    private final String LOG_TAG = "USER DAO";
    private DBHelper _dbHelper;
    private SQLiteDatabase _db;

    /**
     * The default constructor
     * @param context
     */
    public UserDAO (Context context) {
        _dbHelper = new DBHelper(context);
    }

    @Override
    public long insertUser(User newUser) {

        try {
            // Gets the data repository in writable mode
            _db = _dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys

            ContentValues values = new ContentValues();

            // create a hash map of values to be inserted in the database
            values.put(DBHelper.FIRST_NAME_COLUMN, newUser.get_firstName());
            values.put(DBHelper.LAST_NAME_COLUMN, newUser.get_lastName());
            values.put(DBHelper.EMAIL_COLUMN, newUser.get_email());
            values.put(DBHelper.PASSWORD_COLUMN, newUser.get_hashedPassword());

            // get the row if of newly inserted user
            long newRowId = _db.insert(DBHelper.USERS_TABLE_NAME, null, values);

            // insert the row id
            return newRowId;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return -1;
        }

    }

    @Override
    public User getUser(String userEmail) {

        try {

            // get the database
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.ID,
                    DBHelper.FIRST_NAME_COLUMN,
                    DBHelper.LAST_NAME_COLUMN,
                    DBHelper.EMAIL_COLUMN,
                    DBHelper.PASSWORD_COLUMN
            };

            // filter the results to return users with the given email
            String selection = DBHelper.EMAIL_COLUMN + " = ?";
            String[] selectionArgs = {userEmail};

            // get the results
            Cursor cursor = _db.query(
                    DBHelper.USERS_TABLE_NAME,  // The table to query
                    projection, // the array of columns to return
                    selection,  // the columns for the where clause
                    selectionArgs, // arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            User requestedUser = new User();


            // if the cursor has a next row, move to the first one and get all the required infor
            if (cursor.moveToNext()) {
                cursor.moveToFirst();
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIRST_NAME_COLUMN));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.LAST_NAME_COLUMN));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_COLUMN));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PASSWORD_COLUMN));

                // create a user object from the database values
                requestedUser.set_id(userId);
                requestedUser.set_firstName(firstName);
                requestedUser.set_lastName(lastName);
                requestedUser.set_email(email);
                requestedUser.set_hashedPassword(password);

                // if there are no rows in the curson, return null
            } else {
                requestedUser = null;
            }

            // return the user
            return requestedUser;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }

    }

    public User getUser(long userId) {

        try {

            // get the database
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.ID,
                    DBHelper.FIRST_NAME_COLUMN,
                    DBHelper.LAST_NAME_COLUMN,
                    DBHelper.EMAIL_COLUMN,
                    DBHelper.PASSWORD_COLUMN
            };

            // filter the results to return users with the given email
            String selection = DBHelper.USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            // get the results
            Cursor cursor = _db.query(
                    DBHelper.USERS_TABLE_NAME,  // The table to query
                    projection, // the array of columns to return
                    selection,  // the columns for the where clause
                    selectionArgs, // arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            User requestedUser = new User();


            // if the cursor has a next row, move to the first one and get all the required infor
            if (cursor.moveToNext()) {
                cursor.moveToFirst();
                long newUserId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIRST_NAME_COLUMN));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.LAST_NAME_COLUMN));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_COLUMN));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PASSWORD_COLUMN));

                // create a user object from the database values
                requestedUser.set_id(newUserId);
                requestedUser.set_firstName(firstName);
                requestedUser.set_lastName(lastName);
                requestedUser.set_email(email);
                requestedUser.set_hashedPassword(password);

                // if there are no rows in the curson, return null
            } else {
                requestedUser = null;
            }

            // return the user
            return requestedUser;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }

    }

    @Override
    public ArrayList<User> getAllUsers() {


        ArrayList<User> allUsers = new ArrayList<>();
        try {
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.ID,
                    DBHelper.FIRST_NAME_COLUMN,
                    DBHelper.LAST_NAME_COLUMN,
                    DBHelper.EMAIL_COLUMN,
                    DBHelper.PASSWORD_COLUMN
            };


            // get the results
            Cursor cursor = _db.query(
                    DBHelper.USERS_TABLE_NAME,  // The table to query
                    projection, // the array of columns to return
                    null,  // don't filter
                    null, // no filter arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            User requestedUser = new User();

            while (cursor.moveToNext()) {
                long userId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIRST_NAME_COLUMN));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.LAST_NAME_COLUMN));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL_COLUMN));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.PASSWORD_COLUMN));
                requestedUser.set_id(userId);
                requestedUser.set_firstName(firstName);
                requestedUser.set_lastName(lastName);
                requestedUser.set_email(email);
                requestedUser.set_hashedPassword(password);
                allUsers.add(requestedUser);
            }

            // return the user
            return allUsers;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }

    }

    @Override
    public int updateUser(User updatedUser) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // create a ContentValues with the updated values
            ContentValues values = new ContentValues();
            values.put(DBHelper.FIRST_NAME_COLUMN, updatedUser.get_firstName());
            values.put(DBHelper.LAST_NAME_COLUMN, updatedUser.get_lastName());
            values.put(DBHelper.PASSWORD_COLUMN, updatedUser.get_hashedPassword());
            values.put(DBHelper.EMAIL_COLUMN, updatedUser.get_email());

// Which row to update, based on the title
            String selection = DBHelper.ID + " = ?";
            String[] selectionArgs = {String.valueOf(updatedUser.get_id())};

            int count = _db.update(
                    DBHelper.USERS_TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            return count;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return 0;
        }
    }

    @Override
    public int deleteUser(User userToBeDeleted) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = DBHelper.EMAIL_COLUMN + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {userToBeDeleted.get_email()};

            // Issue SQL statement.
            int deletedRows = _db.delete(DBHelper.USERS_TABLE_NAME, selection, selectionArgs);
            return deletedRows;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return 0;
        }
    }
}
