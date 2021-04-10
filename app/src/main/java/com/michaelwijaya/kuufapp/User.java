package com.michaelwijaya.kuufapp;

public class User {
    private int userID;
    private String username;
    private String phone;
    private String pass;
    private String dob;
    private String gender;
    private int wallet = 0;

    public User(int userID, String username, String phone, String pass, String dob, String gender, int wallet) {
        this.userID = userID;
        this.username = username;
        this.phone = phone;
        this.pass = pass;
        this.dob = dob;
        this.gender = gender;
        this.wallet = wallet;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWallet() { return wallet; }

    public void setWallet(int wallet) {
        this.wallet += wallet;
        if(this.wallet < 0){
            this.wallet = 0;
        }
    }
}
