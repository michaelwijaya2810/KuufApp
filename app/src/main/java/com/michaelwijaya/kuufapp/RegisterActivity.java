package com.michaelwijaya.kuufapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michaelwijaya.kuufapp.databinding.ActivityRegisterBinding;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding rb;
    String TAG = "RegisterActivityLog";

    String username;
    String phone;
    String pass;
    String confirmPass;
    String dob;
    String gender;
    int userID;

    public int id;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rb = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = rb.getRoot();
        setContentView(view);

        sp = getApplicationContext().getSharedPreferences("user", 0);
        editor = sp.edit();

        id = sp.getInt("idCount", 0);

        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dob = dayOfMonth+"-"+month+"-"+year;
            rb.etDob.setText(dob);
        };

        rb.etDob.setOnClickListener(v -> new DatePickerDialog(RegisterActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());

        rb.btnRegister.setOnClickListener(v -> {
            if(isRegistrationValid()){
                id++;
                userID = id;
                editor.putInt("idCount", id);

                User user = new User(userID, username, phone, pass, dob, gender, 0);
                Log.i(TAG, Integer.toString(userID));
                Log.i(TAG, username);
                Log.i(TAG, phone);
                Log.i(TAG, pass);
                Log.i(TAG, dob);
                Log.i(TAG, gender);

                Gson gson = new Gson();
                String jsonSave = gson.toJson(user);
                editor.remove("userData");
                editor.remove("transHistory");
                editor.putString("userData", jsonSave);
                editor.apply();

                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();

                goToLogin();
            }
        });

        rb.btnLogin.setOnClickListener(v -> goToLogin());
    }

    protected boolean isRegistrationValid(){
        username = rb.etUsername.getEditableText().toString();
        phone = rb.etPhoneNum.getEditableText().toString();
        pass = rb.etPassword.getEditableText().toString();
        confirmPass = rb.etConfirmPass.getEditableText().toString();
        dob = rb.etDob.getEditableText().toString();

        if(!username.isEmpty()){
            if(username.length()>=6 && username.length()<=12){
                if(!phone.isEmpty()){
                    if(phone.length()>=10 && phone.length()<=12){
                        if(!pass.isEmpty()){
                            if(pass.length()>8){
                                if(confirmPass.matches(pass)){
                                    if(!dob.isEmpty()){
                                        if(rb.rgGender.getCheckedRadioButtonId()!=-1) {
                                            if(rb.rbGenderMale.isChecked()){
                                                gender = "Male";
                                            }else if(rb.rbGenderFemale.isChecked()){
                                                gender = "Female";
                                            }else if(rb.rbGenderOther.isChecked()){
                                                gender = "Other";
                                            }
                                            if(rb.cbAgreement.isChecked()){
                                                Log.i(TAG, "isRegistrationValid returns true value");
                                                return true;
                                            }else{
                                                Toast.makeText(this, "You must agree with Kuuf Terms and Conditions to register your account!", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(this, "Gender must be selected", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        rb.etDob.setError("Date of Birth must be filled!");
                                    }
                                }else{
                                    rb.etConfirmPass.setError("Password confirmation does not match!");
                                }
                            }else{
                                rb.etPassword.setError("Password must be more than 8 characters!");
                            }
                        }else{
                            rb.etPassword.setError("Password must be filled!");
                        }
                    }else{
                        rb.etPhoneNum.setError("Phone number must be between 10 and 12 digits!");
                    }
                }else{
                    rb.etPhoneNum.setError("Phone number must be filled!");
                }
            }else{
                rb.etUsername.setError("Username must be between 6 and 12 characters");
            }
        }else{
            rb.etUsername.setError("Username must be filled!");
        }
        Log.i(TAG, "isRegistrationValid returns false value");
        return false;
    }

    protected void goToLogin(){
        intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        Log.i(TAG, "Moving to LoginActivity!");
    }
}

