package com.phoenix.PhoenixHotelMongo.exception;

public class OurException extends RuntimeException{

    // Constructor
    public OurException(String message){
        super(message);     // Will call the constructor of parent class
    }
}
