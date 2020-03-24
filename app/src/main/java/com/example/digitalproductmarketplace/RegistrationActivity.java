package com.example.digitalproductmarketplace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalproductmarketplace.Boundary.UserDAO;
import com.example.digitalproductmarketplace.Entity.User;


import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    Toast _registrationToast;
    EditText _firstName;
    EditText _lastName;
    EditText _emailTxt;
    EditText _passwordTxt;
    EditText _rePasswordTxt;
    Button _createAccBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        // get all the fields from their ids
        final TextView backtologin=findViewById(R.id.signUpTxt);
        _firstName = findViewById(R.id.firstNameTxt);
        _lastName = findViewById(R.id.lastNameTxt);
        _emailTxt = findViewById(R.id.login_email);
        _passwordTxt = findViewById(R.id.login_password);
        _rePasswordTxt = findViewById(R.id.rePasswordTxt);
        _createAccBtn = findViewById(R.id.createAccBtn);

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
        // when the sign up button is clicked do the following
        _createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate the data in the fields
                if (validateData()) {
                    if (createUser()) {
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        });

    }

    private boolean validateData(){


        Pattern namePattern = Pattern.compile("^[a-zA-Z\\s]*$");

        // password must contain following
        // a lower case letter, an uppercase letter, a numeric, a special character
        // no whitespace and at least 8 characters
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

        String email = _emailTxt.getText().toString();
        String firstNameTxt = _firstName.getText().toString();
        String lastNameTxt = _lastName.getText().toString();
        String password = _passwordTxt.getText().toString();
        String passwordConfirm = _rePasswordTxt.getText().toString();

        String toastMsg;

        //check to see that none of the fields are empty
        if((firstNameTxt.equals("")) || lastNameTxt.equals("")|| (email.equals(""))
            || (password.equals("")) || (passwordConfirm.equals(""))){

            // make the toast message
            toastMsg = "All the fields are required!!!";

            // show the toast
            showToast(toastMsg);

            return false;

            // check to see if the email is valid
        } else if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {


            _emailTxt.setError("Please enter a valid email!");

            return false;

        } else if ( !namePattern.matcher(firstNameTxt).matches()) {

            _firstName.setError("Should only contain letters and spaces!");

            return false;

        }  else if (!namePattern.matcher(lastNameTxt).matches()) {


            _lastName.setError("Should only contain letters and spaces!");

            return false;

            // check if the passwords in both the fields match
        } else if ( !password.equals(passwordConfirm) ) {

            _rePasswordTxt.setError("Both password fields must match!");
            _rePasswordTxt.requestFocus();

            return false;

            // check if the password meets the minimum requirements
        } else if ( !passwordPattern.matcher(password).matches() ) {
            // toast message
            toastMsg = "The password must contain the following\n" +
                    "a lowercase alphabet letter\n" +
                    "an uppercase alphabet letter\n" +
                    "a special character\n" +
                    "a numeric character\n" +
                    "and no white space";

            _passwordTxt.setError(toastMsg);
            _passwordTxt.requestFocus();

            return false;
        } else {
            return true;
        }
    }

    private void showToast(String message) {
        // if there is already a toast being displayed, remove it
        if ( _registrationToast != null) {
            _registrationToast.cancel();
        }

        // make a new toast and show it
        _registrationToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        _registrationToast.show();
    }

    private boolean createUser() {

        long rowId = 0;
        // initialize a new user and the userDAO
        User newUser = new User();
        UserDAO userDAO = new UserDAO(getApplicationContext());

        // get the values in each field
        String firstName = _firstName.getText().toString();
        String lastName = _lastName.getText().toString();
        String email = _emailTxt.getText().toString();
        String password = _passwordTxt.getText().toString();

        // check if the user with the given email already exists
        if ( userDAO.getUser(email) != null ) {
            _emailTxt.setError("A user with the given email already exists, please change the email or sign in");
        } else {

            // populate the user with the values
            newUser.set_firstName(firstName);
            newUser.set_lastName(lastName);
            newUser.set_email(email);
            newUser.set_password(password);

            // save the user information in the database
            rowId = userDAO.insertUser(newUser);
        }

        return rowId > 0;
    }



}