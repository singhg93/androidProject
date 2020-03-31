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

    CardView _audioCard;
    CardView _videoCard;
    CardView _ebookCard;
    CardView _graphicCard;

    Intent itemIntent;
    Bundle itemBundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        _audioCardBtn = findViewById(R.id.ButtonAudio);
        _videoCardBtn = findViewById(R.id.ButtonVideo);
        _ebookCardBtn = findViewById(R.id.ButtonEbooks);
        _grapicCardBtn = findViewById(R.id.ButtonGraphics);


        _audioCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","_audioCard");
                startActivity();
            }
        });
        _videoCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","_videoCard");
                startActivity();
            }
        });
        _ebookCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","_ebookCard");
                startActivity();
            }
        });
        _grapicCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBundle.putString("CATEGORY","_graphicCard");
                startActivity();
            }
        });

    }

    private void startActivity(){
        itemIntent = new Intent(Categories.this, ItemsActivity.class);
        itemIntent.putExtras(itemBundle);
        startActivity(itemIntent);
    }
}
