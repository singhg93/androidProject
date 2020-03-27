package com.example.digitalproductmarketplace.Entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class stores the information about a AdvertisementPost.
 * this class stores the description of Item,name of Item,
 * date on which advertisement was posted,
 * the date on which changes made on advertisement
 */
public class AdvertisementPost {


    //atrributes of AdvertisementPost
    private long _postId;
    private long _itemId;
    private long _userId;
    private long _datePostedEpoch;
    private long _lastUpdatedEpoch;

    //default constructor
    public AdvertisementPost() {
    }

    /**
     * @param @return id of the Item
     */
    public long get_itemId() {
        return _itemId;

    }

    /**
     *
     * @param _itemId
     */
    public void set_itemId(long _itemId) {
        this._itemId = _itemId;
    }

    /**
     *
     * @return
     */
    public long get_postId() {
        return _postId;
    }

    /**
     *
     * @param _postId
     */
    public void set_postId(long _postId) {
        this._postId = _postId;
    }


    /**
     *
     * @param _lastUpdatedEpoch
     */
    public void set_lastUpdatedEpoch(long _lastUpdatedEpoch) {
        this._lastUpdatedEpoch = _lastUpdatedEpoch;
    }

    /**
     * @param @returns date on which advertisement was posted
     */
    public long get_datePostedEpoch() {
        return _datePostedEpoch;
    }

    public String get_datePosted() {
        Date lastUpdated = new Date(_datePostedEpoch);
        DateFormat simpleDateFormate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        return (simpleDateFormate.format(lastUpdated));
    }

    /**
     * @param _datePostedEpoch on which advertisement was posted
     */
    public void set_datePostedEpoch(long _datePostedEpoch) {
        this._datePostedEpoch = _datePostedEpoch;
    }

    /**
     * @param @returns the last updated date
     */
    public long get_dateUpdatedEpoch() {
        return _lastUpdatedEpoch;
    }


    public String get_dateUpdated() {
        Date lastUpdated = new Date(_lastUpdatedEpoch);
        DateFormat simpleDateFormate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        return (simpleDateFormate.format(lastUpdated));
    }

    /**
     * @param _dateUpdatedEpoch updation date
     */
    public void set_dateUpdatedEpoch(long _dateUpdatedEpoch) {
        this._lastUpdatedEpoch = _dateUpdatedEpoch;
    }


    /**
     *
     * @return
     */
    public long get_userId() {
        return _userId;
    }

    /**
     *
     * @param _userId
     */
    public void set_userId(long _userId) {
        this._userId = _userId;
    }

}
