package com.example.digitalproductmarketplace.entity;

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
     * @param hashedPassword the hashed password retrieved from the database
     */
    public void set_hashedPassword(String hashedPassword) {

        // only for retrieving the hashed password from the database
        this._password = hashedPassword;
    }

    /**
     *
     * @return the hash of the password
     */
    public String get_hashedPassword() {
        return _password;
    }

    /**
     *
     * @param newPassword the new password to set
     */
    public void set_password( String newPassword) {
        _password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }



}
