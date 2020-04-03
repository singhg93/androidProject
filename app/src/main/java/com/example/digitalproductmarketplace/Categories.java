package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Categories extends AppCompatActivity {

    Button _audioCardBtn;
    Button _videoCardBtn;
    Button _ebookCardBtn;
    Button _grapicCardBtn;
    Button _goBack;

    Intent itemIntent;
    Bundle itemBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        _audioCardBtn = findViewById(R.id.ButtonAudio);
        _videoCardBtn = findViewById(R.id.ButtonVideo);
        _ebookCardBtn = findViewById(R.id.ButtonEbooks);
        _grapicCardBtn = findViewById(R.id.ButtonGraphics);
        _goBack = findViewById(R.id.profile_category_button);
        itemBundle = new Bundle();

        itemIntent = new Intent(Categories.this, ItemsActivity.class);
        _audioCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","audio");
                itemIntent.putExtras(itemBundle);
                startActivity(itemIntent);
            }
        });
        _videoCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","video");
                itemIntent.putExtras(itemBundle);
                startActivity(itemIntent);
            }
        });
        _ebookCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","ebooks");
                itemIntent.putExtras(itemBundle);
                startActivity(itemIntent);
            }
        });
        _grapicCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","graphicDesign");
                itemIntent.putExtras(itemBundle);
                startActivity(itemIntent);
            }
        });

        _goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackProfile = new Intent(Categories.this, ProfileActivity.class);
                startActivity(goBackProfile);
            }
        });

    }

    private void startActivity(){
        itemIntent = new Intent(Categories.this, ItemsActivity.class );
        itemIntent.putExtras(itemBundle);
        startActivity(itemIntent);
    }
}
