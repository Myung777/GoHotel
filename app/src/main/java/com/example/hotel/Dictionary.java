package com.example.hotel;

import java.io.File;

public class Dictionary {
    private String title;
    private File Picture;
    private String Content;
    private String Time;
    private String Id;
    private File Profile;
    private String hotel;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getPicture() {
        return Picture;
    }

    public void setPicture(File picture) {
        Picture = picture;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public File getProfile() {
        return Profile;
    }

    public void setProfile(File profile) {
        Profile = profile;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Dictionary(String title, File picture, String content, String time, String id, File profile,String hotel) {
        this.title = title;
        Picture = picture;
        Content = content;
        Time = time;
        Id = id;
        Profile = profile;
        this.hotel=hotel;
    }
}
