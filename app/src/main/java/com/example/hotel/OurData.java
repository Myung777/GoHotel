package com.example.hotel;

import java.io.File;

public class OurData {
    private File hotelImage;
    private String nameText,priceText,priceText1,locationText,facilitiesText,hotelContext,latitude,longitude;

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

    public String getPriceText() {
        return priceText;
    }

    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }

    public String getPriceText1() {
        return priceText1;
    }

    public void setPriceText1(String priceText1) {
        this.priceText1 = priceText1;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public String getFacilitiesText() {
        return facilitiesText;
    }

    public void setFacilitiesText(String facilitiesText) {
        this.facilitiesText = facilitiesText;
    }

    public String getHotelContext() {
        return hotelContext;
    }

    public void setHotelContext(String hotelContext) {
        this.hotelContext = hotelContext;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public OurData(File hotelImage, String nameText, String priceText, String priceText1, String locationText, String facilitiesText, String hotelContext,String latitude, String longitude) {
        this.hotelImage = hotelImage;
        this.nameText = nameText;
        this.priceText = priceText;
        this.priceText1 = priceText1;
        this.locationText = locationText;
        this.facilitiesText = facilitiesText;
        this.hotelContext = hotelContext;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
