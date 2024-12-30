package com.phoenix.PhoenixHotelMongo.service.interfac;

import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.entity.Booking;

public interface IBookingService {

    Response saveBooking(String rooId, String userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(String bookingId);
}
