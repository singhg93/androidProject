package com.example.digitalproductmarketplace.Boundary;

import com.example.digitalproductmarketplace.Entity.Item;

import java.util.ArrayList;

public interface ItemDAOInterface {

    /**
     * Retrives the details of the item with the given id from the database
     */
    public Item getItem(long itemId);

    /**
     *
     * Retrives a list of all items from the database
     */
    public ArrayList<Item> getAllItems();

    /**
     * Save the details of a new item in the database
     */
    public long insertItem( Item newItem );

    /**
     * Update the details of an already existing item in the database
     */

    public int updateItem( Item updatedItem );

    /**
     * Delete a item from the database
     */

    public int deleteItem( Item itemToBeDeleted );
}
