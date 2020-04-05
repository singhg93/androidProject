package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.digitalproductmarketplace.boundary.AdvertisementDAO;
import com.example.digitalproductmarketplace.boundary.ItemDAO;
import com.example.digitalproductmarketplace.entity.AdvertisementPost;
import com.example.digitalproductmarketplace.entity.Item;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity implements RecyclerViewCustomAdapter.OnItemClicked {

    RecyclerView recyclerViewData;
    ItemDAO itemDAO;
    String category;
    ArrayList<Item> items;
    Button goBack;
    Button goBackToProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        goBack = findViewById(R.id.go_to_categories);
        goBackToProfile = findViewById(R.id.go_back_to_profile);
        // get the intent and the bundle
        Intent thisIntent = getIntent();
        Bundle myBundle = thisIntent.getExtras();

        //here bundle carries the value of clicked button from Categories activity class
        //stores into category
        // if the intent and the bundle are null
        if ( thisIntent != null && myBundle != null ){
            // get the string
            category = myBundle.getString("CATEGORY", null );
        }

        // initialize the advertisement dao
        itemDAO = new ItemDAO(getApplicationContext());

        // get the recycler data
        recyclerViewData = findViewById(R.id.recyclerViewData);

        // if category is not null
        if (category != null) {
            //  if the user wants to browse a particular category, get all the posts related to that category
            items = itemDAO.getItemsForCategory(category);
        } else {
            // get all the posts regardless of the category
            items = itemDAO.getAllItems();
        }

        // initialize an adapter
        RecyclerViewCustomAdapter myRecyclerAdapter = new RecyclerViewCustomAdapter(items, ItemsActivity.this);

        // set the a layoutManager
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));

        // set the adapter
        recyclerViewData.setAdapter(myRecyclerAdapter);

        // bind the on click
        myRecyclerAdapter.setOnClick(ItemsActivity.this);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCategoriesIntent = new Intent(ItemsActivity.this, Categories.class);
                startActivity(goToCategoriesIntent);
            }
        });

        goBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfileIntent = new Intent(ItemsActivity.this, ProfileActivity.class);
                startActivity(goToProfileIntent);
            }
        });

    }

    @Override
    public void onItemClick(long position) {

        Log.e("Recycler Clicked", String.valueOf(position));
        Bundle myBundle = new Bundle();
        myBundle.putLong("ITEM_ID", position);
        Intent itemDetailIntent = new Intent(ItemsActivity.this, ItemDescription.class);
        itemDetailIntent.putExtras(myBundle);
        startActivity(itemDetailIntent);

    }
}
