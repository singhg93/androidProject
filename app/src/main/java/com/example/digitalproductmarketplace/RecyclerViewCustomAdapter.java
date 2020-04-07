package com.example.digitalproductmarketplace;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalproductmarketplace.entity.Item;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.myViewHolder> {

    //private List<Item> myItemInfo;
    private ArrayList<Item> items;
    private Context activityContext;
    private View view;
    private OnItemClicked onClick;


    // to set the click listener
    public interface OnItemClicked {
        void onItemClick(long position);
    }

    public RecyclerViewCustomAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.activityContext = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.layout_items, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        final Item currentItem = items.get(position);

        DecimalFormat dc = new DecimalFormat("$#.##");
        holder.itemPrice.setText(dc.format(currentItem.get_price()));
        holder.itemDescriptionTxt.setText(currentItem.get_description());
        holder.itemCategory.setText(currentItem.get_catagory().toUpperCase());
//        Picasso.get().
//                load("https://orangebucket111948-android.s3.amazonaws.com/public/images/" + currentItem.get_picName()).
//                into(holder.itemImage);
        Picasso.get().
                load("https://robohash.org/" + currentItem.get_picName() + "?set=set3").
                into(holder.itemImage);

        holder.itemDescriptionTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(currentItem.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView itemPrice;
        TextView itemDescriptionTxt;
        ImageView itemImage;
        TextView itemCategory;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemDescriptionTxt = itemView.findViewById(R.id.item_description_txt);
            itemImage = itemView.findViewById(R.id.item_Image);
            itemCategory = itemView.findViewById(R.id.item_category);
        }
    }
    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
}
