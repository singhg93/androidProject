package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.digitalproductmarketplace.Entity.AdvertisementPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerViewData;

    private ArrayList<AdvertisementPost> advertisementPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        recyclerViewData = findViewById(R.id.recyclerViewData);
        RecyclerViewCustomAdapter myRecyclerAdapter = new RecyclerViewCustomAdapter(advertisementPosts);
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewData.setAdapter(myRecyclerAdapter);
    }
}
