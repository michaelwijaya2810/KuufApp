package com.michaelwijaya.kuufapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.michaelwijaya.kuufapp.databinding.ActivityMainBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mb;
    ArrayList<TransactionHistory> transHistoryList;
    public User user;

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
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            MainActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.store){
            startActivity(new Intent(MainActivity.this, StoreActivity.class));
            MainActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.profile){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            MainActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.logout){
            editor = sp.edit();
            editor.remove("isAutoLogin");
            editor.apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            MainActivity.this.finish();
            MainActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mb.getRoot();
        setContentView(view);

        sp = getApplicationContext().getSharedPreferences("user", 0);

        Gson gson = new Gson();
        String jsonLoad = sp.getString("userData", "");
        user = gson.fromJson(jsonLoad, User.class);

        mb.tvWelcome.setText("Welcome, " + user.getUsername() + "!");
        mb.tvWallet.setText("Rp" + user.getWallet());

        jsonLoad = sp.getString("transHistory", null);
        Type type = new TypeToken<ArrayList<TransactionHistory>>(){}.getType();
        transHistoryList = gson.fromJson(jsonLoad, type);

        if(transHistoryList == null){
            transHistoryList = new ArrayList<>();
        }

        RecyclerView rvTransHistory = mb.rvTransHistory;
        TransHistoryAdapter adapter = new TransHistoryAdapter(transHistoryList);
        rvTransHistory.setAdapter(adapter);
        rvTransHistory.setLayoutManager(new LinearLayoutManager(this));

        if(rvTransHistory.getAdapter().getItemCount() == 0){
            mb.tvEmptyHistory.setVisibility(View.VISIBLE);
            mb.rvTransHistory.setVisibility(View.GONE);
        }else{
            mb.tvEmptyHistory.setVisibility(View.GONE);
            mb.rvTransHistory.setVisibility(View.VISIBLE);
        }

        mb.btnAbout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));

    }
}