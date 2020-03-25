package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.digitalproductmarketplace.Boundary.UserDAO;
import com.example.digitalproductmarketplace.Entity.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    EditText _loginEmail;
    EditText _loginPassword;
    Button _loginButton;
    SignInButton _googleSignInButton;
    TextView _invalidLogin;
    TextView _noAccount;
    GoogleSignInOptions _gso;
    GoogleSignInClient _myGoogleSignInClient;
    GoogleSignInAccount _googleAccount;
    User _signedInUser;
    UserDAO _userDAO = new UserDAO(LoginActivity.this);
    final int RC_SIGN_IN = 1599;
    final String SIGN_IN_TAG = "Sign in error";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        _loginEmail = findViewById(R.id.login_email);
        _loginPassword = findViewById(R.id.login_password);
        _loginButton = findViewById(R.id.login_button);
        _invalidLogin = findViewById(R.id.invalid_login);
        _noAccount = findViewById(R.id.no_account);
        _signedInUser = new User();
        _googleSignInButton = findViewById(R.id.google_sign_in);

        SharedPreferences sharedPref
                = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPref.contains("EMAIL")){
            String userEmail = sharedPref.getString("EMAIL","");
            _signedInUser = _userDAO.getUser(userEmail);

            if (_signedInUser != null ) {
                Log.e("userEmail", _signedInUser.get_email());
                signIn(_signedInUser);
            }

        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        _gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        _myGoogleSignInClient = GoogleSignIn.getClient(this, _gso);

        _googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_sign_in:
                        googleSignIn();
                        break;
                }
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginCredentials()) {
                    authenticateUser();
                }
            }
        });

        _noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        _googleAccount = GoogleSignIn.getLastSignedInAccount(this);


        // start the profile activity
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignIn(task);
        }
    }

    private boolean validateLoginCredentials() {
        String email = _loginEmail.getText().toString();
        if (email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginEmail.setError("Please enter a valid email!");
            return false;
        } else if (_loginPassword.getText().toString().equals("")) {
            _loginPassword.setError("Please enter a password!");
            return false;
        } else {
            return true;
        }
    }

    private void authenticateUser() {
        String loginEmail = _loginEmail.getText().toString();
        String loginPassword = _loginPassword.getText().toString();
        User requestedUser = _userDAO.getUser(loginEmail);
        if (requestedUser != null && requestedUser.checkAuthenticity(loginPassword)) {
            _signedInUser = requestedUser;
            signIn(_signedInUser);
        } else {
            _invalidLogin.setTextColor(Color.RED);
            _invalidLogin.setText("Invalid Credentials");
        }
    }

    private void googleSignIn(){

        Intent signInIntent = _myGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleGoogleSignIn(Task<GoogleSignInAccount> completedTask) {
        try {
            _googleAccount = completedTask.getResult(ApiException.class);

            // check if a user has signed in previously with this account
            User alreadyUser = _userDAO.getUser(_googleAccount.getEmail());

            // if the user has not previously signed in using this account before,
            // add the user to the database
            if (alreadyUser == null) {
                _signedInUser = new User();
                _signedInUser.set_firstName(_googleAccount.getGivenName());
                _signedInUser.set_lastName(_googleAccount.getFamilyName());
                _signedInUser.set_email(_googleAccount.getEmail());
                _userDAO.insertUser(_signedInUser);
            } else {
                _signedInUser = alreadyUser;
            }

            // sign the user in
            signIn(_signedInUser);
        } catch (ApiException ex) {
            Log.w(SIGN_IN_TAG, ex.getMessage());
        }
    }


    private void signIn(User signedInUser) {
        SharedPreferences sharedPref
                = PreferenceManager.getDefaultSharedPreferences
                (LoginActivity.this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", signedInUser.get_email());
        editor.commit(); //if not there, shared pref will not work!!
        Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
        finish();
    }

    private void signOut(){

    }
}
