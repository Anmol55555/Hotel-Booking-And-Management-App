package com.phoenix.PhoenixHotelMongo.entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data                                       //To get getters and setters
@Document(collection = "bookings")          // collection = table, which will contains rows and columns
// will be used as a bookingRequest
public class Booking {

    @Id
    private String id;

    @NotBlank(message = "Check in date is required")
    private LocalDate checkInDate;

    @NotBlank(message = "Check out date is required")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "Number of Adults should not be less than one")
    private int numOfAdults;
    @Min(value = 1, message = "Number of Adults should not be less than zero")
    private int numOfChildren;
    private int totalNumOfGuest;

    private String bookingConfirmationCode;

    @DBRef      // Will create a reference between the 2 collections and hence just store here the id of user
    private User user;

    @DBRef
    private Room room;

    public void calculateTotalNumbersOfGuests() {
        this.totalNumOfGuest = this.numOfAdults + this.numOfChildren;
    }

//    public void setNumOfAdults(@Min(value = 1, message = "Number of Adults should not be less than one") int numOfAdults) {
//        this.numOfAdults = numOfAdults;
//          calculateTotalNumbersOfGuests();
//    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
        calculateTotalNumbersOfGuests();
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
        calculateTotalNumbersOfGuests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numOfAdults=" + numOfAdults +
                ", numOfChildren=" + numOfChildren +
                ", totalNumOfGuest=" + totalNumOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }


    public String getId() {
        return id;
    }

    public @NotBlank(message = "Check in date is required") LocalDate getCheckInDate() {
        return checkInDate;
    }

    public @NotBlank(message = "Check out date is required") LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Min(value = 1, message = "Number of Adults should not be less than one")
    public int getNumOfAdults() {
        return numOfAdults;
    }

    @Min(value = 1, message = "Number of Adults should not be less than zero")
    public int getNumOfChildren() {
        return numOfChildren;
    }

    public int getTotalNumOfGuest() {
        return totalNumOfGuest;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCheckInDate(@NotBlank(message = "Check in date is required") LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(@NotBlank(message = "Check out date is required") LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalNumOfGuest(int totalNumOfGuest) {
        this.totalNumOfGuest = totalNumOfGuest;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}



