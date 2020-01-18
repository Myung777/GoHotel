package com.example.hotel;

import java.io.File;

public class ReservationData {

    File hotelImage;
    String hotelName,day,dayEnd,room,resName,resPrice,resId;

    public File getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(File hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResPrice() {
        return resPrice;
    }

    public void setResPrice(String resPrice) {
        this.resPrice = resPrice;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public ReservationData(File hotelImage, String hotelName, String day, String dayEnd, String room, String resName, String resPrice, String resId) {
        this.hotelImage = hotelImage;
        this.hotelName = hotelName;
        this.day = day;
        this.dayEnd = dayEnd;
        this.room = room;
        this.resName = resName;
        this.resPrice = resPrice;
        this.resId = resId;
    }
}
