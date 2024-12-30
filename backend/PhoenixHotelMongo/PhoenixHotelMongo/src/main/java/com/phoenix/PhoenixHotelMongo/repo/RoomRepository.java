package com.phoenix.PhoenixHotelMongo.repo;

import com.phoenix.PhoenixHotelMongo.entity.Room;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

// Extending with Document Room and primary key as String type
public interface RoomRepository extends MongoRepository<Room, String> {

    // Query: Select all room types based on their uniqueness
    @Aggregation("{ $group: {_id: '$roomType'}}")
    List<String> findDistinctRoomType();

    // Find rooms that has no bookings
    @Query("{'bookings': {$size: 0 }}")
    List<Room> findAllAvailableRoom();


    // These are the functions which starts with findBy are handled by MongoRepository and their implementation is handled by MongoRepository itself based on the full name of the function, So no need to define it explicitely. Their implementaion is done by spring at the runtime
    // Find rooms who does not have these booking ids
    List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> bookedRoomIds);


}
