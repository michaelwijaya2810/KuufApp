package com.michaelwijaya.kuufapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.michaelwijaya.kuufapp.databinding.ActivityDetailBinding;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding db;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ArrayList<TransactionHistory> transHistoryList;
    String jsonLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = db.getRoot();
        setContentView(view);

        sp = getApplicationContext().getSharedPreferences("user", 0);
        editor = sp.edit();

        Intent intent = getIntent();
        Gson gson = new Gson();
        jsonLoad = intent.getStringExtra("productSelected");
        Product product = gson.fromJson(jsonLoad, Product.class);

        db.tvProductName.setText(product.getName());
        db.ivProductImage.setImageResource(product.getImage());
        db.tvPlayerCount.setText(product.getMinPlayer() + " - " + product.getMaxPlayer() + " player(s)");
        db.tvProductPrice.setText("Rp" + product.getPrice());

        db.btnBuyProduct.setOnClickListener(v -> {
            jsonLoad = sp.getString("userData", "");
            User user = gson.fromJson(jsonLoad, User.class);

            if(user.getWallet() >= product.getPrice()){
                user.setWallet(-product.getPrice());
                jsonLoad = sp.getString("transHistory", null);
                Type type = new TypeToken<ArrayList<TransactionHistory>>(){}.getType();
                transHistoryList = gson.fromJson(jsonLoad, type);

                if(transHistoryList == null){
                    transHistoryList = new ArrayList<>();
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = dateFormat.format(calendar.getTime());

                transHistoryList.add(new TransactionHistory(product.getName(), date, product.getPrice()));

                String jsonSave = gson.toJson(transHistoryList);
                editor.putString("transHistory", jsonSave);
                jsonSave = gson.toJson(user);
                editor.putString("userData", jsonSave);
                editor.apply();

                Toast.makeText(this, "Product purchased, details added to transaction history!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }else{
                Toast.makeText(DetailActivity.this, "Not enough to buy product!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}