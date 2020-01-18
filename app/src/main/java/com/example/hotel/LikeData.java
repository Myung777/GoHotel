package com.example.hotel;

import java.io.File;

public class LikeData {
    private File hotelImage;
    private String nameText;

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

    public LikeData(File hotelImage, String nameText) {
        this.hotelImage = hotelImage;
        this.nameText = nameText;
    }

}
