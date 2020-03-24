package com.example.digitalproductmarketplace.Entity;

/**
 * This class is for storing the details of an item of a post and the attributes are
 * description, price and catagory
 */
public class item extends advertisementPost {

    //attributes of this class
    private String _description;
    private double _price;
    private String catagory;

    //default constructor
    public item() {
    }

    /**
     * @return the description of the selected item
     */
    @Override
    public String get_description() {
        return _description;
    }

    /**
     * @param _description sets description of an item
     */
    @Override
    public void set_description(String _description) {
        this._description = _description;
    }

    /**
     * @return price of an item
     */
    public double get_price() {
        return _price;
    }

    /**
     * @param _price sets price of an item
     */
    public void set_price(double _price) {
        this._price = _price;
    }

    /**
     * @return item of which catagory
     */
    public String getCatagory() {
        return catagory;
    }

    /**
     * @param catagory sets catagory of an item
     */
    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
