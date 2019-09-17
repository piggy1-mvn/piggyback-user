package com.incentives.piggyback.user.service;

import org.springframework.http.ResponseEntity;

import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.model.Users;



public interface UserService {

     ResponseEntity<Users> createUser(Users user);

     Iterable<Users> getAllUser();

     ResponseEntity<Users> getUserById(Long id);

     ResponseEntity<Users> updateUser(Long id, Users user);

     ResponseEntity<Users> deleteUser(Long id);

     ResponseEntity<String> getAllUserRoles();

     ResponseEntity<Users> updateUserInterest(UserInterest userInterest,Long id);

     Users partialUpdate(UserInterest userInterest, Long id);
}
