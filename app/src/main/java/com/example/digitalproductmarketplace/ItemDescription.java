package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDescription extends AppCompatActivity {

    Button _buyNow;
   TextView _imageDescription;
    Button _goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        _buyNow=findViewById(R.id.buy_button);
        _imageDescription=findViewById(R.id.description_text);
        _goBack=findViewById(R.id.back_button);


        _goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackProfile = new Intent(ItemDescription.this, AddPostActivity.class);
                startActivity(goBackProfile);
            }
        });



    }
}
