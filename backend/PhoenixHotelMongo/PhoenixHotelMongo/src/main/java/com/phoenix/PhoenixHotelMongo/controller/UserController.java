package com.phoenix.PhoenixHotelMongo.controller;

import com.phoenix.PhoenixHotelMongo.dto.Response;
import com.phoenix.PhoenixHotelMongo.service.interfac.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;   // Since only one UserService is implementing it, that is why we do not have to use @Qualifier to specify which implementation of IUserSerice we want

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")          // that means only admin can access this getAllUsers() function, for other user, it will throw 403 error
    public ResponseEntity<Response> getAllUsers(){
        System.out.println("Hitting get all users");
        Response response = userService.getAllUsers();
        System.out.println(response);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId){
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")              // that means only admin can access this getAllUsers() function, for other user, it will throw 403 error
    public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId) {
        Response response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // To get single user info of current logged in user
    @GetMapping("/get-logged-in-profile-info")
    public ResponseEntity<Response> getLoggedInUSerProfile() {
        // Here first the request is intercepted by JWTAuthFilter, which will first extract the token, validates it and set the authenticated user details into SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-user-bookings/{userId}")
    public ResponseEntity<Response> getUSerBookingHistory(@PathVariable("userId") String userId) {
        Response response = userService.getUSerBookingHistory(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
