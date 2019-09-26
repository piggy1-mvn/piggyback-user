package com.incentives.piggyback.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.model.UserRoles;
import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public Iterable<Users> getAllUser() {
        log.debug("User Service: Received GET request for getting all users.");
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        log.debug("User Service: Received GET request for getting user with userid."+ id);
        return userService.getUserById(id);
    }

    @PostMapping("/user/role")
    public ResponseEntity<Users> getUsersInRoles(@RequestBody UserRoles userRoles) {
        log.debug("User Service: Received POST request for getting users in roles." + userRoles.toString());
        return userService.getUsersInRole(userRoles);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users user) {
        log.debug("User Service: Received PUT request for updating user with userid."+ id);
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        log.debug("User Service: Received DELETE request for deleting user with userid."+ id);
        return userService.deleteUser(id);
    }

    @GetMapping("/user/roles")
    public ResponseEntity<String> getAllUserRoles() {
        log.debug("User Service: Received GET request for getting all roles available.");
        return userService.getAllUserRoles();
    }
    
    @PatchMapping("/user/interest/{id}")
    public ResponseEntity<Users> updateUserInterest(@RequestBody UserInterest userInterest, @PathVariable Long id) {
        log.debug("User Service: Received PATCH request for updating user interest.");
        return userService.updateUserInterest(userInterest,id);
    }
    
    @GetMapping("/user/particular/interest")
    public ResponseEntity<List<Users>> getUserWithParticularInterest(
    		@RequestParam(value = "users", required = true) String users,
    		@RequestParam(value = "interest", required = true) String interest) {
        log.debug("User Service: Received GET request for getting all users with particular interest.");
        return userService.getUserWithParticularInterest(users, interest);
    }

}
