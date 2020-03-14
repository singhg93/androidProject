package com.example.digitalproductmarketplace;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ConstraintLayout registrationLayout = findViewById(R.id.registrationLayout);
        //https://digitalsynopsis.com/design/beautiful-color-ui-gradients-backgrounds/
        registrationLayout.setBackgroundResource(R.drawable.bluebg);

        
    }
}
