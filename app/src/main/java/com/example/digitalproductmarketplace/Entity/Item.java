package com.example.digitalproductmarketplace.Entity;

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
}
