package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalproductmarketplace.Boundary.UserDAO;
import com.example.digitalproductmarketplace.Entity.User;

public class ProfileActivity extends AppCompatActivity {

    User _signedInUser;
    UserDAO _userDAO;
    TextView _firstName;
    TextView _lastName;
    TextView _email;
    Button _logOut;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.logout_item_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logOutMenu:
//
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _userDAO = new UserDAO(getApplicationContext());
        _firstName = findViewById(R.id.profileFirstName);
        _lastName = findViewById(R.id.profileLastName);
        _email = findViewById(R.id.profileEmail);
        _logOut = findViewById(R.id.logOutBtn);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPref.contains("EMAIL")) {
            String userEmail = sharedPref.getString("EMAIL", "");
            _signedInUser = _userDAO.getUser(userEmail);
        } else {
            Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        if (_signedInUser == null ) {
            Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {

            _firstName.setText(_signedInUser.get_firstName());
            _lastName.setText(_signedInUser.get_lastName());
            _email.setText(_signedInUser.get_email());
        }

//        _logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }
}
