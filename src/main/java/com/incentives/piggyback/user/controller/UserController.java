package com.incentives.piggyback.user.controller;

import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/piggyback-user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    ResponseEntity<User> create(@Valid @RequestBody User user) {
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
    ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        log.debug("User Service: Received PUT request for updating user with userid."+ id);
        if(userService.findById(id).isPresent()) {
            return ResponseEntity.ok(userService.save(user));
        }
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/user/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) {
        log.debug("User Service: Received DELETE request for deleting user with userid."+ id);
        if(userService.findById(id).isPresent()) {
             userService.deleteById(id);
             return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

}
