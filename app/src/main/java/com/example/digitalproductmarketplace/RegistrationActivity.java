package com.example.digitalproductmarketplace;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText emailTxt;
    EditText passwordTxt;
    EditText rePasswordTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstName = findViewById(R.id.firstNameTxt);
        lastName = findViewById(R.id.lastNameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        rePasswordTxt = findViewById(R.id.rePasswordTxt);

        Button createAccBtn = findViewById(R.id.createAccBtn);
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanData();
            }
        });
    }

    private void scanData(){

        //Comparing of string is empaty
        if((firstName.getText().toString().equals("")) || (emailTxt.getText().toString().equals(""))
            || (passwordTxt.getText().toString().equals("")) || (rePasswordTxt.getText().toString().equals(""))){
            Toast.makeText(RegistrationActivity.this, "Please enter the data", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(RegistrationActivity.this,
                    "Full Name :"+ firstName.getText() + " " + lastName.getText()
                            +"\nEmail :" +emailTxt.getText()
                            +"\nPassword :" +passwordTxt.getText()
                            +"\nRe-Pass :" +rePasswordTxt.getText()
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
