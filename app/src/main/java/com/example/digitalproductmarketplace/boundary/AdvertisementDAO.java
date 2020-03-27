package com.example.digitalproductmarketplace.Boundary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.digitalproductmarketplace.Entity.AdvertisementPost;

import java.util.ArrayList;

public class AdvertisementDAO implements AdvertisementPostDAOInterface {


    private final String LOG_TAG = "Advertisement DAO";
    private DBHelper _dbHelper;
    private SQLiteDatabase _db;


    public AdvertisementDAO (Context context) {
        _dbHelper = new DBHelper(context);
    }

//
//    // column names for adPosts table
//    public static final String AD_POST_TABLE = "advertisements";
//    public static final String AD_ID = "id";
//    public static final String ITEM_ID_FK = "itemId";
//    public static final String DATE_POSTED = "datePosted";
//    public static final String DATE_UPDATED = "lastUpdated";

    @Override
    public AdvertisementPost getAdd(int adId) {
        try {

            // get the database
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.AD_ID,
                    DBHelper.ITEM_ID_FK,
                    DBHelper.USER_ID,
                    DBHelper.DATE_POSTED,
                    DBHelper.DATE_UPDATED
            };

            // filter the results to return users with the given email
            String selection = DBHelper.AD_ID + " = ?";
            String[] selectionArgs = {String.valueOf(adId)};

            // get the results
            Cursor cursor = _db.query(
                    DBHelper.AD_POST_TABLE,  // The table to query
                    projection, // the array of columns to return
                    selection,  // the columns for the where clause
                    selectionArgs, // arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    null    // don't sort since only one result expected
            );

            // instantiate a new user
            AdvertisementPost requestedAd = new AdvertisementPost();

            // if the cursor has a next row, move to the first one and get all the required infor
            if (cursor.moveToNext()) {
                cursor.moveToFirst();

                long postId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.AD_ID));
                long adItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ITEM_ID_FK));
                long adUserId= cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                long datePosted = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_POSTED));
                long dateUpdated = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_UPDATED));

                // create a user object from the database values
                requestedAd.set_postId(postId);
                requestedAd.set_itemId(adItemId);
                requestedAd.set_userId(adUserId);
                requestedAd.set_datePostedEpoch(datePosted);
                requestedAd.set_dateUpdatedEpoch(dateUpdated);

                // if there are no rows in the cursor, return null
            } else {
                requestedAd = null;
            }

            // return the user
            return requestedAd;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<AdvertisementPost> getAllAds() {
        ArrayList<AdvertisementPost> allPosts = new ArrayList<>();
        try {
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.AD_ID,
                    DBHelper.ITEM_ID_FK,
                    DBHelper.USER_ID,
                    DBHelper.DATE_POSTED,
                    DBHelper.DATE_UPDATED
            };


            // get the results
            Cursor cursor = _db.query(
                    DBHelper.AD_POST_TABLE,  // The table to query
                    projection, // the array of columns to return
                    null,  // don't filter
                    null, // no filter arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    DBHelper.DATE_UPDATED  // sort by last updated date
            );

            // instantiate a new user
            AdvertisementPost nextPost = new AdvertisementPost();

            while (cursor.moveToNext()) {
                long postId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.AD_ID));
                long adItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ITEM_ID_FK));
                long adUserId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                long datePosted = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_POSTED));
                long dateUpdated = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_UPDATED));

                // create a user object from the database values
                nextPost.set_postId(postId);
                nextPost.set_itemId(adItemId);
                nextPost.set_userId(adUserId);
                nextPost.set_datePostedEpoch(datePosted);
                nextPost.set_dateUpdatedEpoch(dateUpdated);
                allPosts.add(nextPost);

            }

            // return the user
            return allPosts;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }

    @Override
    public long insertAdvertisement(AdvertisementPost newAd) {
        try {
            // Gets the data repository in writable mode
            _db = _dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys

            ContentValues values = new ContentValues();


            //    column names for adPosts table
            //    public static final String AD_POST_TABLE = "advertisements";
            //    public static final String AD_ID = "id";
            //    public static final String ITEM_ID_FK = "itemId";
            //    public static final String DATE_POSTED = "datePosted";
            //    public static final String DATE_UPDATED = "lastUpdated";

            // create a hash map of values to be inserted in the database
            values.put(DBHelper.ITEM_ID_FK, newAd.get_itemId());
            values.put(DBHelper.DATE_POSTED, newAd.get_datePostedEpoch());
            values.put(DBHelper.DATE_UPDATED, newAd.get_dateUpdatedEpoch());
            values.put(DBHelper.USER_ID, newAd.get_userId());

            // get the row if of newly inserted user
            long newRowId = _db.insert(DBHelper.AD_POST_TABLE, null, values);

            // insert the row id
            return newRowId;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return -1;
        }
    }

    @Override
    public int updateAdvertisement(AdvertisementPost updatedPost) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // create a ContentValues with the updated values
            ContentValues values = new ContentValues();

            // create a hash map of values to be inserted in the database
            values.put(DBHelper.ITEM_ID_FK, updatedPost.get_itemId());
            values.put(DBHelper.DATE_POSTED, updatedPost.get_datePostedEpoch());
            values.put(DBHelper.DATE_UPDATED, updatedPost.get_dateUpdatedEpoch());
            values.put(DBHelper.USER_ID, updatedPost.get_userId());

// Which row to update, based on the title
            String selection = DBHelper.AD_ID + " = ?";
            String[] selectionArgs = {String.valueOf(updatedPost.get_postId())};

            int count = _db.update(
                    DBHelper.AD_POST_TABLE,
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
    public int deleteAdvertisement(AdvertisementPost adToBeDeleted) {
        try {
            // get the database
            _db = _dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = DBHelper.AD_ID + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {String.valueOf(adToBeDeleted.get_postId())};

            // Issue SQL statement.
            int deletedRows = _db.delete(DBHelper.AD_POST_TABLE, selection, selectionArgs);
            return deletedRows;
        } catch ( Exception ex ) {
            Log.e(LOG_TAG, ex.getMessage());
            return 0;
        }
    }

    @Override
    public ArrayList<AdvertisementPost> getPostsForCategory(String category) {
        ArrayList<AdvertisementPost> postsForCategories = new ArrayList<>();
        try {
            _db = _dbHelper.getReadableDatabase();

            // list all the columns that should be returned from the database
            String[] projection = {
                    DBHelper.AD_ID,
                    DBHelper.ITEM_ID_FK,
                    DBHelper.USER_ID,
                    DBHelper.DATE_POSTED,
                    DBHelper.DATE_UPDATED
            };

            String selection = DBHelper.CATEGORY + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {category};


            // get the results
            Cursor cursor = _db.query(
                    DBHelper.AD_POST_TABLE,  // The table to query
                    projection, // the array of columns to return
                    selection,  // don't filter
                    selectionArgs, // no filter arguments for where clause
                    null,   // don't group the results
                    null,   // don't filter by row groups
                    DBHelper.DATE_UPDATED  // sort by last updated date
            );

            // instantiate a new user
            AdvertisementPost nextPost = new AdvertisementPost();

            while (cursor.moveToNext()) {
                long postId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.AD_ID));
                long adItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ITEM_ID_FK));
                long adUserId = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.USER_ID));
                long datePosted = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_POSTED));
                long dateUpdated = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.DATE_UPDATED));

                // create a user object from the database values
                nextPost.set_postId(postId);
                nextPost.set_itemId(adItemId);
                nextPost.set_userId(adUserId);
                nextPost.set_datePostedEpoch(datePosted);
                nextPost.set_dateUpdatedEpoch(dateUpdated);
                postsForCategories.add(nextPost);
            }

            // return the user
            return postsForCategories;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage());
            return null;
        }
    }


}
