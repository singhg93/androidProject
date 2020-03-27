package com.example.digitalproductmarketplace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewCustomAdapter extends RecyclerView.Adapter<recyclerViewCustomAdapter.myViewHolder> {

    //private List<Item> myItemInfo;
    private List<String> itemName = new ArrayList<>();
    private List<Integer> itemPic = new ArrayList<>();
    private List<String> itemDescription = new ArrayList<>();

    public recyclerViewCustomAdapter(List<String> itemName, List<Integer> itemPic, List<String> itemDescription) {
        this.itemName = itemName;
        this.itemPic = itemPic;
        this.itemDescription = itemDescription;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.layout_items, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.itemTxt.setText(itemName.get(position));
        holder.itemDescriptionTxt.setText(itemDescription.get(position));
        holder.itemImage.setImageResource(itemPic.get(position));
    }

    @Override
    public int getItemCount() {
        return itemName.size();
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
