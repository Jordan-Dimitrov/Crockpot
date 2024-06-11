package com.example.crockpot.models;

public class UserInfo {
    public UserInfo(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserId() {
        return userId;
    }
    public String email;
    public String userId;
}
