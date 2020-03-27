package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        advertisementDAO = new AdvertisementDAO(getApplicationContext());
        recyclerViewData = findViewById(R.id.recyclerViewData);

        if (category != null) {
            advertisementPosts = advertisementDAO.getPostsForCategory(category);
        } else {
            advertisementPosts = advertisementDAO.getAllAds();
        }


        RecyclerViewCustomAdapter myRecyclerAdapter = new RecyclerViewCustomAdapter(advertisementPosts);
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewData.setAdapter(myRecyclerAdapter);
    }
}
