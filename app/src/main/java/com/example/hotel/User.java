package com.example.hotel;

import java.io.File;

public class User {
    private String userID;
    private String userPw;
    private String userEmail;
    private String userPhone;
    private String userName;
    private File profile;
    private String like;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public File getProfile() {
        return profile;
    }

    public void setProfile(File profile) {
        this.profile = profile;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public User(String userID, String userName, String userPw, String userEmail, String userPhone, File profile,String like) {
        this.userID = userID;
        this.userPw = userPw;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userName = userName;
        this.profile = profile;
        this.like = like;
    }
}

