package com.example.digitalproductmarketplace.boundary;

import java.util.ArrayList;

import com.example.digitalproductmarketplace.entity.User;

public interface UserDAOInterface {


    /**
     * Retrives the details of the user with the given id from the database
     */
    public User getUser(String email);

    /**
     *
     * Retrives a list of all users from the database
     */
    public ArrayList<User> getAllUsers();

    /**
     * Save the details of a new user in the database
     */
    public long insertUser( User newUser );

    /**
     * Update the details of an already existing user in the database
     */

    public int updateUser( User updatedUser );

    /**
     * Delete a user from the database
     */

    public int deleteUser( User userToBeDeleted );
}
