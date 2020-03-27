package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _userDAO = new UserDAO(getApplicationContext());
        _firstName = findViewById(R.id.profileFirstName);
        _lastName = findViewById(R.id.profileLastName);
        _email = findViewById(R.id.profileEmail);
        _logOut = findViewById(R.id.logOutBtn);


        _gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        _myGoogleSignOutClient = GoogleSignIn.getClient(this, _gso);


        _sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //log out button clear the sharedPreferences and
        //make the user take out to login activity
        _logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = _sharedPref.edit();
                editor.clear();
                editor.commit();
                signOut();
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
