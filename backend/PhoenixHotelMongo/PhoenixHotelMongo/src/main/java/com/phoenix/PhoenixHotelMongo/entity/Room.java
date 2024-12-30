package com.phoenix.PhoenixHotelMongo.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data                               //To get getters and setters
@Document(collection = "rooms")     // collection = table, which will contains rows and columns
public class Room {

    @Id
    private String id;

    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;


    @DBRef      // Referencing bookings with rooms
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                '}';
    }


    public String getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }

    public BigDecimal getRoomPrice() {
        return roomPrice;
    }

    public String getRoomPhotoUrl() {
        return roomPhotoUrl;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setRoomPrice(BigDecimal roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setRoomPhotoUrl(String roomPhotoUrl) {
        this.roomPhotoUrl = roomPhotoUrl;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}