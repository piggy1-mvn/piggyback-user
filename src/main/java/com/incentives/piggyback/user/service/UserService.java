package com.incentives.piggyback.user.service;

import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.model.UserInterest;
import org.springframework.http.ResponseEntity;


 public interface UserService {

     ResponseEntity<User> createUser(User user);

     Iterable<User> getAllUser();

     ResponseEntity<User> getUserById(Long id);

     ResponseEntity<User> updateUser(Long id, User user);

     ResponseEntity<User> deleteUser(Long id);

     ResponseEntity getAllUserRoles();

     ResponseEntity updateUserInterest(UserInterest userInterest,Long id);

     User partialUpdate(UserInterest userInterest, Long id);
 }
