package com.example.digitalproductmarketplace.Entity;

/**
 * This class is for storing the details of an Item of a post and the attributes are
 * description, price and _catagory
 */
public class Item {

    //attributes of this class
    private String _description;
    private double _price;
    private String _catagory;

    //default constructor
    public Item() {
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
        return _catagory;
    }

    /**
     * @param _catagory sets _catagory of an Item
     */
    public void set_catagory(String _catagory) {
        this._catagory = _catagory;
    }
}
