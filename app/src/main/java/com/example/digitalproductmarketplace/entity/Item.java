package com.example.digitalproductmarketplace.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class is for storing the details of an Item of a post and the attributes are
 * description, price and _catagory
 */
public class Item {

    //attributes of this class
    private long _id;
    private String _description;
    private double _price;
    private String _category;
    private long _userId;
    private String _picName;
    private String _fileUrl;
    private long _datePostedEpoch;
    private long _lastUpdatedEpoch;



    //default constructor
    public Item() {
    }


    /**
     *
     * @return
     */
    public long get_id() {
        return _id;
    }

    /**
     *
     * @param _id
     */
    public void set_id(long _id) {
        this._id = _id;
    }
    /**
     * @return the description of the selected Item
     */
    public String get_description() {
        return _description;
    }

    /**
     * @param _description sets description of an Item
     */
    public void set_description(String _description) {
        this._description = _description;
    }

    /**
     * @return price of an Item
     */
    public double get_price() {
        return _price;
    }

    /**
     * @param _price sets price of an Item
     */
    public void set_price(double _price) {
        this._price = _price;
    }

    /**
     * @return Item of which _catagory
     */
    public String get_catagory() {
        return _category;
    }

    /**
     * @param _category sets _catagory of an Item
     */
    public void set_catagory(String _category) {
        this._category = _category;
    }

    /**
     *
     * @param userId
     */
    public void set_userId( long userId ) {
        _userId = userId;
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
     * @return
     */
    public String get_picName() {
        return _picName;
    }

    /**
     *
     * @param _picName
     */
    public void set_picName(String _picName) {
        this._picName = _picName;
    }

    /**
     *
     * @return
     */
    public String get_fileUrl() {
        return _fileUrl;
    }

    /**
     *
     * @param _fileUrl
     */
    public void set_fileUrl(String _fileUrl) {
        this._fileUrl = _fileUrl;
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
    public void set_lastUpdatedEpoch(long _dateUpdatedEpoch) {
        this._lastUpdatedEpoch = _dateUpdatedEpoch;
    }
}
