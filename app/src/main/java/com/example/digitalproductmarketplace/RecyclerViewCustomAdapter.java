package com.example.digitalproductmarketplace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalproductmarketplace.Boundary.ItemDAO;
import com.example.digitalproductmarketplace.Entity.AdvertisementPost;
import com.example.digitalproductmarketplace.Entity.Item;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.myViewHolder> {

    //private List<Item> myItemInfo;
    private ArrayList<AdvertisementPost> advertisementPosts;

    ItemDAO itemDAO;

    public RecyclerViewCustomAdapter(ArrayList<AdvertisementPost> advertisementPosts) {
        this.advertisementPosts = advertisementPosts;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        itemDAO = new ItemDAO(parent.getContext());

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.layout_items, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        AdvertisementPost advertisementPost = advertisementPosts.get(position);
        Item currentItem = itemDAO.getItem(advertisementPost.get_itemId());
        holder.itemTxt.setText(currentItem.get_catagory());
        holder.itemDescriptionTxt.setText(currentItem.get_description());
//        holder.itemImage.setImageResource(itemPic.get(position));
    }

    @Override
    public int getItemCount() {
        return advertisementPosts.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView itemTxt;
        TextView itemDescriptionTxt;
        ImageView itemImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.item_Txt);
            itemDescriptionTxt = itemView.findViewById(R.id.item_Description_Txt);
            itemImage = itemView.findViewById(R.id.item_Image);
        }
    }
}
