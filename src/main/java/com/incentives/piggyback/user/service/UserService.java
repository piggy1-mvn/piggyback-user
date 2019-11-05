package com.incentives.piggyback.user.service;

import com.incentives.piggyback.user.model.UserInterest;
import com.incentives.piggyback.user.model.UserPartnerDto;
import com.incentives.piggyback.user.model.UserRoles;
import com.incentives.piggyback.user.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

     ResponseEntity<Users> createUser(Users user);

     Iterable<Users> getAllUser();

     ResponseEntity<Users> getUserById(Long id);

     ResponseEntity getUsersInRole(UserRoles role);

     ResponseEntity<Users> updateUser(Long id, Users user);

     ResponseEntity<Users> deleteUser(Long id);

     ResponseEntity<String> getAllUserRoles();

     ResponseEntity<Users> updateUserInterest(UserInterest userInterest,Long id);

     Users partialUpdate(UserInterest userInterest, Long id);

	ResponseEntity<List<Users>> getUserWithParticularInterest(String users, String interest);

    void updateUserwithPartnerId(UserPartnerDto userPartnerDto);
}
