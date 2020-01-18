package com.example.hotel;

import java.io.File;

public class PopularData {
    private File hotelImage;
    private String nameText;
    private int like;

    public File getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(File hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public PopularData(File hotelImage, String nameText, int like) {
        this.hotelImage = hotelImage;
        this.nameText = nameText;
        this.like = like;
    }
}
