package com.example.rohitranjan.vehiclepayment;

public class User {
    String  email, userId;

    public User(){

    }

    public User(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
