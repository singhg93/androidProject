package com.example.digitalproductmarketplace.Entity;

import java.util.Date;

/*

 This class stores the information about a advertisementPost.
this class stores the description of item,name of item,
date on which advertisement was posted,
the date on which changes made on advertisement
 */
public class advertisementPost extends User {

    //atrributes of advertisementPost
    private String _description;
    private String _item;
    private Date _datePosted;
    private Date _lastUpdated;

    //default constructor
    public advertisementPost() {
    }

    /**
     * @param @returns description of the item
     */
    public String get_description() {
        return _description;
    }


    /**
     * @param @description of the item
     */
    public void set_description(String _description) {
        this._description = _description;

    }

    /**
     * @param @return name of the item
     */
    public String get_item() {
        return _item;

    }

    /**
     * @param _item of the posted advertisement
     */
    public void set_item(String _item) {
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
