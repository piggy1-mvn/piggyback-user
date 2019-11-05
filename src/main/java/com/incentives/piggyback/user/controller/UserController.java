package com.incentives.piggyback.user.controller;

import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.model.UserRoles;
import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Iterable<Users> getAllUser() {
        log.info("User Service: Received GET request for getting all users.");
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        log.info("User Service: Received GET request for getting user with userid."+ id);
        return userService.getUserById(id);
    }

    @PostMapping("/user/role")
    public ResponseEntity<Users> getUsersInRoles(@RequestBody UserRoles userRoles) {
        log.info("User Service: Received POST request for getting users in roles." + userRoles.toString());
        return userService.getUsersInRole(userRoles);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users user) {
        log.info("User Service: Received PUT request for updating user with userid."+ id);
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        log.info("User Service: Received DELETE request for deleting user with userid."+ id);
        return userService.deleteUser(id);
    }

    @GetMapping("/user/roles")
    public ResponseEntity<String> getAllUserRoles() {
        log.info("User Service: Received GET request for getting all roles available.");
        return userService.getAllUserRoles();
    }
    
    @PatchMapping("/user/interest/{id}")
    public ResponseEntity<Users> updateUserInterest(@RequestBody UserInterest userInterest, @PathVariable Long id) {
        log.info("User Service: Received PATCH request for updating user interest.");
        return userService.updateUserInterest(userInterest,id);
    }

    @GetMapping("/user/particular/interest")
    public ResponseEntity<List<Users>> getUserWithParticularInterest(
    		@RequestParam(value = "users", required = true) String users,
    		@RequestParam(value = "interest", required = true) String interest) {
        log.info("User Service: Received GET request for getting all users with particular interest.");
        return userService.getUserWithParticularInterest(users, interest);
    }

    @RequestMapping(path = "/user",method = RequestMethod.HEAD)
    public ResponseEntity<String> isValidUser() {
        return ResponseEntity.ok("ValidUser");
    }


}
