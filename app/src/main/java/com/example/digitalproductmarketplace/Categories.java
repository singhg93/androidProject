package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {

    Button _audioCatBtn;
    Button _videoCatBtn;
    Button _ebookCatBtn;
    Button _grapicCatBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        _audioCatBtn = findViewById(R.id.ButtonAudio);
        _videoCatBtn = findViewById(R.id.ButtonVideo);
        _ebookCatBtn = findViewById(R.id.ButtonEbooks);
        _grapicCatBtn = findViewById(R.id.ButtonGraphics);

        //
    }
}
