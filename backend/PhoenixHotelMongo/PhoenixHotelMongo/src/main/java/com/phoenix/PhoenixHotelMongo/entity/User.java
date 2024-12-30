package com.phoenix.PhoenixHotelMongo.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//To get getters and setters
@Data

// collection = table, which will contains rows and columns
@Document(collection = "users")


// UserDetails is a in-built class which we are inheriting along with its functions(methods)
public class User implements UserDetails {

    @Id
    private String id;
    @NotBlank(message = "Email is required")
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String role;

    @DBRef              // Referencing bookings with user
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(@NotBlank(message = "Email is required") String email) {
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

    public String getId() {
        return id;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
