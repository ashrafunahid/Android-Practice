package com.ashrafunahid.googleoauthsigninauthentication.Models;

public class UserModel {
    String name;
    String email;
    int totalCoin;

    public UserModel() {
    }

    public UserModel(String name, String email, int totalCoin) {
        this.name = name;
        this.email = email;
        this.totalCoin = totalCoin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotalCoin() {
        return totalCoin;
    }

    public void setTotalCoin(int totalCoin) {
        this.totalCoin = totalCoin;
    }
}
