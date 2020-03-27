package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {

    Button _audioCatBtn;
    Button _videoCatBtn;
    Button _ebookCatBtn;
    Button _grapicCatBtn;

    CardView _audioCard;
    CardView _videoCard;
    CardView _ebookCard;
    CardView _graphicCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        _audioCatBtn = findViewById(R.id.ButtonAudio);
        _videoCatBtn = findViewById(R.id.ButtonVideo);
        _ebookCatBtn = findViewById(R.id.ButtonEbooks);
        _grapicCatBtn = findViewById(R.id.ButtonGraphics);

        _audioCard = findViewById(R.id.audio_card);
        _videoCard = findViewById(R.id.video_card);
        _ebookCard = findViewById(R.id.video_card);
        _graphicCard = findViewById(R.id.graphic_card);

        _audioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a bundle with key CATEGORY, and value audio
                // do the same for other three cards
            }
        });

        //
    }
}
