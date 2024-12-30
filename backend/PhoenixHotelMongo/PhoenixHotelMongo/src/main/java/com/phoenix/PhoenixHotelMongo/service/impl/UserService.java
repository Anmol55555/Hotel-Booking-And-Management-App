package com.phoenix.PhoenixHotelMongo.service.impl;

import com.phoenix.PhoenixHotelMongo.dto.LoginRequest;
import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.dto.UserDTO;
import com.phoenix.PhoenixHotelMongo.entity.User;
import com.phoenix.PhoenixHotelMongo.exception.OurException;
import com.phoenix.PhoenixHotelMongo.repo.UserRepository;
import com.phoenix.PhoenixHotelMongo.service.interfac.IUserService;
import com.phoenix.PhoenixHotelMongo.utils.JWTUtils;
import com.phoenix.PhoenixHotelMongo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {

        Response response = new Response();

        try{
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }

            System.out.println("Hitting UserService register");


            if(userRepository.existsByEmail(user.getEmail())){
                System.out.println("Hitting Email Already exist");
                throw new OurException("Email Already Exist");
            }

            System.out.println("Password: "+ user.getPassword());
            System.out.println("Encoded Password: " + passwordEncoder.encode(user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            System.out.println(user);
            System.out.println(savedUser);

            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            System.out.println(userDTO);
            response.setStatusCode(200);
            response.setMessage("successfull");

            response.setUser(userDTO);

            System.out.println(response);

        }
        catch(OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while registering the user: " + e.getMessage());
        }

        System.out.println("Final Response: ");
        System.out.println(response);
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try{
            System.out.println("In login service1");
            System.out.println("The Password in the login request: " + loginRequest.getPassword());

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            System.out.println("In login service2");
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User Not Found"));
            System.out.println(user);
            var token = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");

        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while logging in the user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();

        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setUserList(userDTOList);

        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while getting all the user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUSerBookingHistory(String userId) {
        Response response = new Response();

        try{

            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setUser(userDTO);
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while getting user history: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();

        try{
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

            userRepository.deleteById(userId);

            response.setStatusCode(200);
            response.setMessage("successfull");
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while deleting the user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();

        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setUser(userDTO);
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while getting the user by id: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("successfull");
            response.setUser(userDTO);
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error while getting the user by email: " + e.getMessage());
        }

        return response;
    }
}
