package com.example.digitalproductmarketplace.src.com.marketplace.Entity;

import com.example.digitalproductmarketplace.src.org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author singhg93
 *
 * This class stores the information about a user. Each user has a username, first name, last name,
 * email and a hashed password.
 */
public class User {

    // user attributes
    private String _username;
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _password; // the password in stored in encrypted format

    /**
     *
     * The default constructor to create a user
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    // default constructor
    public User(String username, String firstName, String lastName, String email, String password) {
        _username = username;
        _firstName = firstName;
        _lastName = lastName;
        _email = email;
        _password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    /**
     *
     * @param passwordToCheck the password to check
     * @return true if the given password was correct, false otherwise
     */
    // check if the given password was correct
    public boolean checkAuthenticity(String passwordToCheck) {
        return BCrypt.checkpw(passwordToCheck, _password);
    }

    /**
     *
     * @return the username of the user
     */
    public String get_username() {
        return _username;
    }


    /**
     *
     * @return the firstname of the user
     */
    public String get_firstName() {
        return _firstName;
    }

    /**
     *
     * @return the last name of the user
     */
    public String get_lastName() {
        return _lastName;
    }

    /**
     *
     * @return the email of the user
     */
    public String get_email() {
        return _email;
    }

    /**
     *
     * @param _email the email to set
     */
    public void set_email(String _email) {
        this._email = _email;
    }


    /**
     *
     * @param newPassword the new password to store
     */
    public void set_password(String newPassword) {

        // hash the new password and store it
        this._password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }
}
