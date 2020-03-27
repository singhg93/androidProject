package com.example.digitalproductmarketplace.Boundary;

import com.example.digitalproductmarketplace.Entity.AdvertisementPost;

import java.util.ArrayList;

/**
 *
 */
public interface AdvertisementPostDAOInterface {
    /**
     * Retrives the details of the item with the given id from the database
     */
    public AdvertisementPost getAdd(int adId);

    /**
     *
     * Retrives a list of all items from the database
     */
    public ArrayList<AdvertisementPost> getAllAds();

    /**
     * Save the details of a new item in the database
     */
    public long insertAdvertisement( AdvertisementPost newAd );

    /**
     * Update the details of an already existing item in the database
     */

    public int updateAdvertisement( AdvertisementPost updatedPost );

    /**
     * Delete a item from the database
     */

    public int deleteAdvertisement( AdvertisementPost adToBeDeleted );

}
