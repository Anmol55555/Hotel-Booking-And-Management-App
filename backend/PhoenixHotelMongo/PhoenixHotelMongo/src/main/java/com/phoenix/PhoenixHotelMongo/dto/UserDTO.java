package com.phoenix.PhoenixHotelMongo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Data                           //To get getters and setters
@JsonInclude(JsonInclude.Include.NON_NULL)              // Exclude this which have NULL values
public class UserDTO {

    private String id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;
    private List<BookingDTO> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", bookings=" + bookings +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}
