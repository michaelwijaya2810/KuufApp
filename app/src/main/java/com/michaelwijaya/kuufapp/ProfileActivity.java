package com.michaelwijaya.kuufapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michaelwijaya.kuufapp.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding pb;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    int topUpCharge = 0;
    String pass;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home){
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            ProfileActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.store){
            startActivity(new Intent(ProfileActivity.this, StoreActivity.class));
            ProfileActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.profile){
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            ProfileActivity.this.finish();
            return true;
        }else if(item.getItemId() == R.id.logout){
            editor.remove("isAutoLogin");
            editor.apply();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            ProfileActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pb = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = pb.getRoot();
        setContentView(view);

        sp = getApplication().getSharedPreferences("user", 0);
        editor = sp.edit();

        Gson gson = new Gson();
        String jsonLoad = sp.getString("userData", "");
        User user = gson.fromJson(jsonLoad, User.class);

        pb.tvUsername.setText("Username: " + user.getUsername());
        pb.tvGender.setText("Gender: " + user.getGender());
        pb.tvPhone.setText("Phone Number: " + user.getPhone());
        pb.tvWallet.setText("Wallet: Rp" + user.getWallet());
        pb.tvDob.setText("Date of Birth: " + user.getDob());

        pb.rgTopUp.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.rb_top_up_1){
                topUpCharge = 250000;
            }else if(checkedId == R.id.rb_top_up_2){
                topUpCharge = 500000;
            }else if(checkedId == R.id.rb_top_up_3){
                topUpCharge = 1000000;
            }
        });

        pb.btnConfirm.setOnClickListener(v -> {
            pass = pb.etPassword.getEditableText().toString();
            if(pass.isEmpty()){
                pb.etPassword.setError("Password required for confirmation!");
            }else if(pass.equals(user.getPass())){
                if(topUpCharge != 0){
                    user.setWallet(topUpCharge);
                    String jsonSave = gson.toJson(user);
                    editor.putString("userData", jsonSave);
                    editor.apply();

                    Toast.makeText(ProfileActivity.this, "Top up successful! Rp"+topUpCharge+" has been added to your wallet!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    ProfileActivity.this.finish();
                }else{
                    Toast.makeText(ProfileActivity.this, "Top Up Nominal must be chosen!", Toast.LENGTH_SHORT).show();
                }
            }else{
                pb.etPassword.setError("Password does not match with user credential!");
            }
        });
    }
}