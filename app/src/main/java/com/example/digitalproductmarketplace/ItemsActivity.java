package com.example.digitalproductmarketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerViewData;
    private List<String> itemName = new ArrayList<>(Arrays.asList(
            "1. Item", "2. Item", "3. Item", "4.Item", "5.Item", "6.Item", "7.Item"
    ));
    private List<String> itemDescription = new ArrayList<>(Arrays.asList(
            "1. Item des the thedes the thedes the thedes the thedes the thedes the thedes the thedes the the",
            "2. Item des the the", "3. Item des the the", "4.Item des the the", "5.Item des the the", "6.Item des the the", "7.Item des the the"
    ));
    private List<Integer> itemPic = new ArrayList<>(Arrays.asList(
            R.drawable.p1, R.drawable.pp, R.drawable.rejisterationbg,
            R.drawable.logo_transparent, R.drawable.lll, R.drawable.ic_cake_black_24dp, R.drawable.grr
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        recyclerViewData = findViewById(R.id.recyclerViewData);
        recyclerViewCustomAdapter myRecyclerAdapter = new recyclerViewCustomAdapter(itemName, itemPic, itemDescription);
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewData.setAdapter(myRecyclerAdapter);
    }
}
