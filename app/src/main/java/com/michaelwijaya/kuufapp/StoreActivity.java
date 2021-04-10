package com.michaelwijaya.kuufapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.michaelwijaya.kuufapp.databinding.ActivityStoreBinding;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    ActivityStoreBinding sb;
    ArrayList<Product> productData;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home){
            startActivity(new Intent(StoreActivity.this, MainActivity.class));
            StoreActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.store){
            startActivity(new Intent(StoreActivity.this, StoreActivity.class));
            StoreActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.profile){
            startActivity(new Intent(StoreActivity.this, ProfileActivity.class));
            StoreActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.logout){
            editor = sp.edit();
            editor.remove("isAutoLogin");
            editor.apply();
            startActivity(new Intent(StoreActivity.this, LoginActivity.class));
            StoreActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sb = ActivityStoreBinding.inflate(getLayoutInflater());
        View view = sb.getRoot();
        setContentView(view);

        sp = getApplicationContext().getSharedPreferences("user", 0);

        RecyclerView rvStore = sb.rvStoreList;

        productData = new ArrayList<>();
        Product product1 = new Product("Cards Against Humanity", 2, 4, 182500, "108.126810", "-7.586037", R.drawable.cah);
        Product product2 = new Product("Exploding Kitten", 2, 5, 250000, "106.265139", "-6.912035", R.drawable.exp_kittens);
        productData.add(product1);
        productData.add(product2);

        StoreAdapter adapter = new StoreAdapter(productData);
        rvStore.setAdapter(adapter);
        rvStore.setLayoutManager(new LinearLayoutManager(this));
    }
}