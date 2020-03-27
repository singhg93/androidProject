package com.example.digitalproductmarketplace.Entity;

import java.util.Date;

/**
 * This class stores the information about a AdvertisementPost.
 * this class stores the description of Item,name of Item,
 * date on which advertisement was posted,
 * the date on which changes made on advertisement
 */
public class AdvertisementPost {

    //atrributes of AdvertisementPost
    private Item _item;
    private Date _datePosted;
    private Date _lastUpdated;

    //default constructor
    public AdvertisementPost() {
    }

    /**
     * @param @return name of the Item
     */
    public Item get_item() {
        return _item;

    }

    /**
     * @param _item of the posted advertisement
     */
    public void set_item(Item _item) {
        this._item = _item;
    }

    /**
     * @param @returns date on which advertisement was posted
     */
    public Date get_datePosted() {
        return _datePosted;
    }

    /**
     * @param _datePosted on which advertisement was posted
     */
    public void set_datePosted(Date _datePosted) {
        this._datePosted = _datePosted;
    }

    /**
     * @param @returns the last updated date
     */
    public Date get_dateUpdated() {
        return _lastUpdated;
    }

    /**
     * @param _dateUpdated updation date
     */
    public void set_dateUpdated(Date _dateUpdated) {
        this._lastUpdated = _dateUpdated;
    }
}
