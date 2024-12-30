package com.phoenix.PhoenixHotelMongo.repo;

import com.phoenix.PhoenixHotelMongo.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Extending with Document Booking and primary key as String type
public interface BookingRepository extends MongoRepository<Booking, String> {

    Optional<Booking> findByBookingConfirmationCode(String ConfirmationCode);

    // Finding all bookings whose checkInDate is less than or equal to  ?1(checkOutDate)
    // and checkOutDate is greater than or equal to ?0(checkInDate)
    @Query("{ 'checkInDate': { $lte: ?1 }, 'checkOutDate': { $gte: ?0 } }")
    List<Booking> findBookingsByDateRange(LocalDate checkInDate, LocalDate checkOutDate);





}
