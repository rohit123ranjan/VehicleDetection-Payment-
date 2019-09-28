package com.example.rohitranjan.vehiclepayment;

public class pojoProfile {

    private String  userEmail, userId, userName, userPhone, userCar;

    public pojoProfile(String userEmail, String userId, String userName, String userPhone, String userCar) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userCar = userCar;
    }

    public pojoProfile() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCar() {
        return userCar;
    }

    public void setUserCar(String userCar) {
        this.userCar = userCar;
    }
}
