package com.michaelwijaya.kuufapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michaelwijaya.kuufapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity{
    String TAG = "LoginActivityLog";
    ActivityLoginBinding lb;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lb = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = lb.getRoot();
        setContentView(view);

        sp = getApplicationContext().getSharedPreferences("user", 0);
        editor = sp.edit();

        lb.cbAutoLogin.setChecked(sp.getBoolean("isAutoLogin", false));

        if(lb.cbAutoLogin.isChecked()){
            Toast.makeText(this, "Auto login fired!", Toast.LENGTH_SHORT).show();
            toMain();
        }

        lb.btnLogin.setOnClickListener(v -> {
            if(isLoginValid()){
                if(lb.cbAutoLogin.isChecked()){
                    editor.putBoolean("isAutoLogin", true);
                    editor.apply();
                }
                Toast.makeText(this, "Login successful, welcome!", Toast.LENGTH_SHORT).show();
                toMain();
                Log.i(TAG, "Login Successful, moving to MainActivity!");
            }
        });
        lb.btnRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    protected boolean isLoginValid(){
        String username = lb.etUsername.getEditableText().toString();
        String pass = lb.etPassword.getEditableText().toString();

        if(!username.isEmpty()){
            if(!pass.isEmpty()) {
                String jsonLoad = sp.getString("userData", null);
                if(jsonLoad == null){
                    Toast.makeText(this, "Error, user not found in sharedPreferences!", Toast.LENGTH_SHORT).show();
                }else{
                    Gson gson = new Gson();
                    User user = gson.fromJson(jsonLoad, User.class);
                    Log.i(TAG, "User data retrieved from SharedPreferences!");
                    if(user.getUsername().equals(username) && user.getPass().equals(pass)){
                        Log.i(TAG, "isLoginValid return true value!");
                        return true;
                    }else{
                        Toast.makeText(this, "User not found, check username and password again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                lb.etPassword.setError("Password must be filled!");
            }
        }else{
            lb.etUsername.setError("Username must be filled!");
        }
        Log.i(TAG, "isLoginValid return false value!");
        return false;
    }

    public void toMain(){
        intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}