package com.michaelwijaya.kuufapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    public ArrayList<Product> productList;
    public StoreAdapter(ArrayList<Product> productData){
        productList = productData;
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder{
        CardView cvStoreItem;
        TextView tvProductName;
        TextView tvPlayerCount;
        TextView tvProductPrice;
        ImageView ivProductImage;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            cvStoreItem = itemView.findViewById(R.id.cv_store_item);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvPlayerCount = itemView.findViewById(R.id.tv_player_count);
            tvProductPrice = itemView.findViewById(R.id.tv_product_price);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
        }
    }

    @NonNull
    @Override
    public StoreAdapter.StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View storeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(storeView);
    }

    @Override
    public void onBindViewHolder(StoreAdapter.StoreViewHolder holder, int position){
        Product product = productList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvPlayerCount.setText(product.getMinPlayer() + " - " + product.getMaxPlayer() + " player(s)");
        holder.tvProductPrice.setText("Rp" + product.getPrice());
        holder.ivProductImage.setImageResource(product.getImage());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Gson gson = new Gson();
            String jsonSave = gson.toJson(product);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("productSelected", jsonSave);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){
        return productList.size();
    }
}
