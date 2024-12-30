package com.phoenix.PhoenixHotelMongo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.PhoenixHotelMongo.dto.LoginRequest;
import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.entity.User;
import com.phoenix.PhoenixHotelMongo.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;           // Since only one UserService is implementing it, that is why we do not have to use @Qualifier to specify which implementation of IUserSerice we want

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user){

        System.out.println("Hitting AuthController /register");

        Response response = userService.register(user);
        System.out.println("Final Response in aut/register: ");
        System.out.println(response);

        System.out.println("Checking Response serialzation before sending to user");
//        System.out.println(new ObjectMapper().writeValueAsString(response));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        System.out.println("Hitting AuthController /login");
        Response response = userService.login(loginRequest);
        System.out.println(response);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
