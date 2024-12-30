package com.phoenix.PhoenixHotelMongo.service.impl;

import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.dto.RoomDTO;
import com.phoenix.PhoenixHotelMongo.entity.Booking;
import com.phoenix.PhoenixHotelMongo.entity.Room;
import com.phoenix.PhoenixHotelMongo.exception.OurException;
import com.phoenix.PhoenixHotelMongo.repo.BookingRepository;
import com.phoenix.PhoenixHotelMongo.repo.RoomRepository;
import com.phoenix.PhoenixHotelMongo.service.AwsS3Service;
import com.phoenix.PhoenixHotelMongo.service.interfac.IRoomService;
import com.phoenix.PhoenixHotelMongo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try{
            System.out.println("Hitting Add new Room");
            String imageUrl = awsS3Service.saveImageToS3(photo);
            System.out.println(imageUrl);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);

            System.out.println(room);

            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

            System.out.println(roomDTO);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while saving the room: " + e.getMessage());
        }

        return response;
    }


    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try{
            List<Room> roomList = roomRepository.findAll();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);


            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting all the room: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteRoom(String roomId) {
        Response response = new Response();

        try{
            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);


            response.setStatusCode(200);
            response.setMessage("successful");

        }
        catch(OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while deleting the room: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateRoom(String roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();

        try{
            String imageUrl = null;
            if(photo != null && !photo.isEmpty())
                imageUrl = awsS3Service.saveImageToS3(photo);

            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            if(description != null) room.setRoomDescription(description);
            if(roomType != null) room.setRoomType(roomType);
            if(roomPrice != null) room.setRoomPrice(roomPrice);
            if(imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        }
        catch(OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while Updating the room: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getRoomById(String roomId) {
        Response response = new Response();

        try{
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));

            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        }
        catch(OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting the room by ID: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try{
            List<Booking> bookings = bookingRepository.findBookingsByDateRange(checkInDate, checkOutDate);
            List<String> bookedRoomsId = bookings.stream().map(booking -> booking.getRoom().getId()).toList();


            // So this will give all the available rooms whose booking IDs are not in the range of CheckIn and CheckOut Date
            List<Room> availableRooms = roomRepository.findByRoomTypeLikeAndIdNotIn(roomType, bookedRoomsId);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        }
        catch(OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting the room by checkin date and checkout date: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try{
            List<Room> roomList = roomRepository.findAllAvailableRoom();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        }
        catch(OurException e)
        {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while getting all the available rooms: " + e.getMessage());
        }

        return response;
    }
}
