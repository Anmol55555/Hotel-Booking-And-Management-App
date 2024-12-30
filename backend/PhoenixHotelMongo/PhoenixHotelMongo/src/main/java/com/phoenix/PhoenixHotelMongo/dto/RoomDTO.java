package com.phoenix.PhoenixHotelMongo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data                           //To get getters and setters
@JsonInclude(JsonInclude.Include.NON_NULL)              // Exclude this which have NULL values
public class RoomDTO {

    private String id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private List<BookingDTO> bookings = new ArrayList<>();


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

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
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

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id='" + id + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
