package com.example.digitalproductmarketplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.example.digitalproductmarketplace.boundary.AdvertisementDAO;
import com.example.digitalproductmarketplace.boundary.ItemDAO;
import com.example.digitalproductmarketplace.entity.AdvertisementPost;
import com.example.digitalproductmarketplace.entity.Item;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerViewData;
    ItemDAO itemDAO;
    String category;
    ArrayList<Item> items;


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

        // set the adapte
        recyclerViewData.setAdapter(myRecyclerAdapter);




//        recyclerViewData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ItemsActivity.this, MainActivity.class));
//            }
//        });
    }
}
