package com.phoenix.PhoenixHotelMongo.service.interfac;

import com.phoenix.PhoenixHotelMongo.dto.LoginRequest;
import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUSerBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
