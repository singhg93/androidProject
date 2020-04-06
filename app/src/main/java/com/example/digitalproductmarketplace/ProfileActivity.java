package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalproductmarketplace.boundary.UserDAO;
import com.example.digitalproductmarketplace.entity.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileActivity extends AppCompatActivity {

    User _signedInUser;
    UserDAO _userDAO;
    TextView _firstName;
    TextView _lastName;
    TextView _email;
    Button _logOut;
    SharedPreferences _sharedPref;
    GoogleSignInClient _myGoogleSignOutClient;
    GoogleSignInOptions _gso;
    Button _addPostButton;
    Button _browseCategories;
    Button _browseAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _userDAO = new UserDAO(getApplicationContext());
        _firstName = findViewById(R.id.profileFirstName);
        _lastName = findViewById(R.id.profileLastName);
        _email = findViewById(R.id.profileEmail);
        _logOut = findViewById(R.id.logOutBtn);
        _addPostButton = findViewById(R.id.add_post_button);
        _browseCategories = findViewById(R.id.list_categories);
        _browseAllItems = findViewById(R.id.list_all_items);


        _gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        _myGoogleSignOutClient = GoogleSignIn.getClient(this, _gso);


        _sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        // if the user wants to add a new post
        _addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start the add post activity
                switch (v.getId()) {
                    // if the add post button is clicked
                    case R.id.add_post_button:
                        Intent addPostIntent = new Intent(ProfileActivity.this, AddPostActivity.class);
                        startActivity(addPostIntent);
                        break;
                }

            }
        });

        //log out button clear the sharedPreferences and
        //make the user take out to login activity
        _logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
                alertDialog.setTitle("Are you sure to Log Out?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = _sharedPref.edit();
                        editor.clear();
                        editor.commit();
                        signOut();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });


        //
        if (_sharedPref.contains("EMAIL")) {
            String userEmail = _sharedPref.getString("EMAIL", "");
            _signedInUser = _userDAO.getUser(userEmail);
        } else {

            Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        if (_signedInUser == null ) {
//            Log.e("Show Login", "user is null");
            Intent loginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            if (!_signedInUser.get_firstName().equals("null")) {
                _firstName.setText(_signedInUser.get_firstName());
            }
            if (!_signedInUser.get_lastName().equals("null")) {
                _lastName.setText(_signedInUser.get_lastName());
            }

            if (_signedInUser.get_lastName() == null && _signedInUser.get_firstName() == null) {
                _firstName.setText("No name given");
            }

            _email.setText(_signedInUser.get_email());
        }

        _browseCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoriesActivityIntent = new Intent(ProfileActivity.this, Categories.class);
                startActivity(categoriesActivityIntent);
            }
        });

        _browseAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allItemsIntent = new Intent(ProfileActivity.this, ItemsActivity.class);
                startActivity(allItemsIntent);
            }
        });
    }

    //signOut() method clears the connected google account to the application
    //revokeAccess() method will disconnect the google account
    //as it clears the information of account
    private void signOut(){
        _myGoogleSignOutClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                revokeAccess();

            }
        });
    }

    // revoke the access from the
    private void revokeAccess(){
        _myGoogleSignOutClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent loginIntent =
                        new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}
