package com.example.digitalproductmarketplace.Entity;

import com.example.digitalproductmarketplace.org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author singhg93
 *
 * This class stores the information about a user. Each user has a username, first name, last name,
 * email and a hashed password.
 */
public class User {

    // user attributes
    private long _id;
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _password; // the password in stored in encrypted format


    /**
     * The default constructor for user entity
     */
    public User() {}

    /**
     *
     * The default constructor to create a user
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    // default constructor
    public User(String username, String firstName, String lastName, String email, String password) {
        _firstName = firstName;
        _lastName = lastName;
        _email = email;
        _password = BCrypt.hashpw(password, BCrypt.gensalt());
    }


    /**
     *
     * @return the database id of the user
     */
    public long get_id() {
        return _id;
    }

    /**
     *
     * @param _id the database id of the user
     */
    public void set_id(long _id) {
        this._id = _id;
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
     * @return the firstname of the user
     */
    public String get_firstName() {
        return _firstName;
    }

    /**
     *
     * @param firstName the firstName to set
     */
    public void set_firstName(String firstName) {
        _firstName = firstName;
    }

    /**
     *
     * @return the last name of the user
     */
    public String get_lastName() {
        return _lastName;
    }


    public void set_lastName( String lastName ){
        _lastName = lastName;

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
     * @param newPassword the hashed password retrieved from the database
     */
    public void set_password(String hashedPassword) {

        // only for retrieving the hashed password from the database
        this._password = hashedPassword;
    }

    public String get_hashedPassword() {
        return _password;
    }

}
