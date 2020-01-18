package com.example.hotel;

import java.io.File;

public class LoveHotel {

    private String userID;
    private String hotelName;
    private File hotelImage;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public File getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(File hotelImage) {
        this.hotelImage = hotelImage;
    }

    public LoveHotel(String userID, String hotelName, File hotelImage) {
        this.userID = userID;
        this.hotelName = hotelName;
        this.hotelImage = hotelImage;
    }
}
