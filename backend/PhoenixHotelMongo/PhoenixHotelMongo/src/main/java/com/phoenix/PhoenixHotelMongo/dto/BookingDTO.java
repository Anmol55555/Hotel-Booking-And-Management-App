package com.phoenix.PhoenixHotelMongo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data                           //To get getters and setters
@JsonInclude(JsonInclude.Include.NON_NULL)              // Exclude this which have NULL values
public class BookingDTO {

    private String id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuest;

    private String bookingConfirmationCode;

    private UserDTO user;

    private RoomDTO room;


    public void setId(String id) {
        this.id = id;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public void setTotalNumOfGuest(int totalNumOfGuest) {
        this.totalNumOfGuest = totalNumOfGuest;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public int getTotalNumOfGuest() {
        return totalNumOfGuest;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public UserDTO getUser() {
        return user;
    }

    public RoomDTO getRoom() {
        return room;
    }
}
