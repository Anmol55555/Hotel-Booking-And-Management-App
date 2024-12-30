package com.phoenix.PhoenixHotelMongo.utils;

import com.phoenix.PhoenixHotelMongo.dto.BookingDTO;
import com.phoenix.PhoenixHotelMongo.dto.RoomDTO;
import com.phoenix.PhoenixHotelMongo.dto.UserDTO;
import com.phoenix.PhoenixHotelMongo.entity.Booking;
import com.phoenix.PhoenixHotelMongo.entity.Room;
import com.phoenix.PhoenixHotelMongo.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {

    // will be used to select random character to create random string
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // For creating random string
    private static final SecureRandom secureRandom = new SecureRandom();

    // Generating Alpha-numeric Random Booking Confirmation Code
    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }

    public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;
    }

    // Add the room to roomDTO along with their booking to bookingDTO to be added in roomDTO
    public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());

        if (room.getBookings() != null) {
            // If any booking associated with the room, first gets all the list of bookings for that room, then convert those bookings to bookingDTO, then add to the roomDTO
            roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
        }

        return roomDTO;
    }

    // Add booking to bookingDTO along with its corresponding user and room to userDTO and roomDTO and finally added these userDTO and roomDTO to final bookingDTO
    public static BookingDTO mapBookingEntityToBookingDTOPlusBookedRooms(Booking booking, boolean mapUser) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setId(booking.getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        bookingDTO.setNumOfChildren(booking.getNumOfChildren());
        bookingDTO.setNumOfAdults(booking.getNumOfAdults());
        bookingDTO.setTotalNumOfGuest(booking.getTotalNumOfGuest());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());

        if (mapUser) {
            // convert corresponding user for that booking to UserDTO and set the userDTO to bookingDTO.user
            bookingDTO.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
        }

        if (booking.getRoom() != null) {

            RoomDTO roomDTO = new RoomDTO();

            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());

            bookingDTO.setRoom(roomDTO);
        }

        return bookingDTO;
    }

    // convert user to userDTO along with if all the bookings to bookingDTO and finally add the converted bookingDTO to final userDTO
    public static UserDTO mapUserEntityToUserDTOPlusUserBookingsAndRoom(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if (!user.getBookings().isEmpty()) {
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDTOPlusBookedRooms(booking, false)).collect(Collectors.toList()));
        }
        return userDTO;
    }

    // Converting a list of user, room, booking into corresponding userDTO, roomDTO, bookingDTO without their bookings into consideration
    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList) {
        return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
    }

    public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
    }
}
