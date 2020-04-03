package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.digitalproductmarketplace.boundary.AdvertisementDAO;
import com.example.digitalproductmarketplace.entity.AdvertisementPost;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerViewData;
    AdvertisementDAO advertisementDAO;
    String category;
    ArrayList<AdvertisementPost> advertisementPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // get the intent and the bundle
        Intent thisIntent = getIntent();
        Bundle myBundle = thisIntent.getExtras();

        //here bundle carries the value of clicked button from Categories activity class
        //stores into category
        // if the intent and the bundle are null
        if ( thisIntent != null && myBundle != null ){
            // get the string
            category = myBundle.getString("CATEGORY", null);
        }

        // initialize the advertisement dao
        advertisementDAO = new AdvertisementDAO(getApplicationContext());

        // get the recycler data
        recyclerViewData = findViewById(R.id.recyclerViewData);

        // if category is not null
        if (category != null) {
            //  if the user wants to browse a particular category, get all the posts related to that category
            advertisementPosts = advertisementDAO.getPostsForCategory(category);
        } else {
            // get all the posts regardless of the category
            advertisementPosts = advertisementDAO.getAllAds();
        }

        // initialize an adapter
        RecyclerViewCustomAdapter myRecyclerAdapter = new RecyclerViewCustomAdapter(advertisementPosts);

        // set the a layoutManager
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));

        // set the adapte
        recyclerViewData.setAdapter(myRecyclerAdapter);
    }
}
