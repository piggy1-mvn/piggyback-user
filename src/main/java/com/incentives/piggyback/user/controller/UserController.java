package com.incentives.piggyback.user.controller;

import com.google.auth.oauth2.UserCredentials;
import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.model.UserCredential;
import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.debug("User Service: Received POST request for creating new user.");
        ResponseEntity<User> responseEntity = userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .query(responseEntity.getBody().getId().toString())
                .buildAndExpand(responseEntity.getBody().getId().toString()).toUri();
        return ResponseEntity.created(location).body(responseEntity.getBody());
    }

    @GetMapping("/user")
    public Iterable<User> getAllUser() {
        log.debug("User Service: Received GET request for getting all users.");
        return userService.getAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.debug("User Service: Received GET request for getting user with userid."+ id);
        return userService.getUserById(id);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        log.debug("User Service: Received PUT request for updating user with userid."+ id);
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("User Service: Received DELETE request for deleting user with userid."+ id);
        return userService.deleteUser(id);
    }

    @GetMapping("/user/roles")
    public ResponseEntity getAllUserRoles() {
        log.debug("User Service: Received GET request for getting all roles available.");
        return userService.getAllUserRoles();
    }

    @PatchMapping("/user/interest/{id}")
    public ResponseEntity updateUserInterest(@RequestBody UserInterest userInterest, @PathVariable Long id) {
        log.debug("User Service: Received PATCH request for updating user interest.");
        return userService.updateUserInterest(userInterest,id);
    }

    @PostMapping("/user/login")
    public ResponseEntity userLogin( @Valid @RequestBody UserCredential userCredential) {
        log.debug("User Service: Received POST request for login.");
        return userService.userLogin(userCredential);
    }

}
