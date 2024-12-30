package com.phoenix.PhoenixHotelMongo.repo;

import com.phoenix.PhoenixHotelMongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
                                                // Extending with Document User and primary key as String type
public interface UserRepository extends MongoRepository<User, String> {

    // Query method name from MongoRepository
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String  email);



}
