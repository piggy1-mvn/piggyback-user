package com.incentives.piggyback.user.controller;

import com.google.gson.Gson;
import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.debug("User Service: Received POST request for creating new user.");
        //check no conflict
        User newUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .query(newUser.getId().toString())
                .buildAndExpand(newUser.getId().toString()).toUri();
        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping("/user")
    public Iterable<User> getAllUser() {
        log.debug("User Service: Received GET request for getting all users.");
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.debug("User Service: Received GET request for getting user with userid."+ id);
        return ResponseEntity.ok(userService.findById(id).orElseThrow(()->new UserNotFoundException(id)));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        log.debug("User Service: Received PUT request for updating user with userid."+ id);
        if(userService.findById(id).isPresent()) {
            return ResponseEntity.ok(userService.save(user));
        }
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("User Service: Received DELETE request for deleting user with userid."+ id);
        if(userService.findById(id).isPresent()) {
             userService.deleteById(id);
             return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/roles")
    public ResponseEntity getAllUserRoles() {
        log.debug("User Service: Received GET request for getting all roles available.");
        List<Roles> roles = Roles.getAllRoles();
        return  ResponseEntity.ok(new Gson().toJson(roles));
    }

}
