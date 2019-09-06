package com.incentives.piggyback.user.controller;

import com.google.gson.Gson;
import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.publisher.UserEventPublisher;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.CommonUtility;
import com.incentives.piggyback.user.util.constants.Constant;
import com.incentives.piggyback.user.util.constants.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventPublisher.PubsubOutboundGateway messagingGateway;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.debug("User Service: Received POST request for creating new user.");
        User newUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .query(newUser.getId().toString())
                .buildAndExpand(newUser.getId().toString()).toUri();
        //PUSHING MESSAGES TO GCP
        messagingGateway.sendToPubsub(
                CommonUtility.stringifyEventForPublish(
                        newUser.toString(),
                        Constant.USER_CREATED_EVENT,
                        Calendar.getInstance().getTime().toString(),
                        HttpStatus.CREATED.toString(),
                        Constant.USER_SOURCE_ID
                ));
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
            User updatedUser = userService.save(user);
            //PUSHING MESSAGES TO GCP
            messagingGateway.sendToPubsub(
                    CommonUtility.stringifyEventForPublish(
                            updatedUser.toString(),
                            Constant.USER_CREATED_EVENT,
                            Calendar.getInstance().getTime().toString(),
                            HttpStatus.OK.toString(),
                            Constant.USER_SOURCE_ID
                    ));
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
            //PUSHING MESSAGES TO GCP
            messagingGateway.sendToPubsub(
                    CommonUtility.stringifyEventForPublish(
                            id.toString(),
                            Constant.USER_DEACTIVATED_EVENT,
                            Calendar.getInstance().getTime().toString(),
                            HttpStatus.OK.toString(),
                            Constant.USER_SOURCE_ID
                    ));
             return ResponseEntity.ok().build();
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/roles")
    public ResponseEntity getAllUserRoles() {
        log.debug("User Service: Received GET request for getting all roles available.");
        List<String> roles = Roles.getAllRoles();
        return  ResponseEntity.ok(new Gson().toJson(roles));
    }

}
