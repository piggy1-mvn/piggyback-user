package com.incentives.piggyback.user.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.incentives.piggyback.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.incentives.piggyback.user.exception.UserNotFoundException;
import com.incentives.piggyback.user.publisher.KafkaMessageProducer;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.service.JwtUserDetailsService;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.CommonUtility;
import com.incentives.piggyback.user.util.constants.Constant;
import com.incentives.piggyback.user.util.constants.Roles;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserServiceRepository userServiceRepo;

	private final KafkaMessageProducer kafkaMessageProducer;

	public UserServiceImpl(KafkaMessageProducer kafkaMessageProducer) {
		this.kafkaMessageProducer = kafkaMessageProducer;
	}

	@Autowired
	private JwtUserDetailsService userDetailsService;

	public ResponseEntity<Users> createUser(Users user) {
		if((user.getUser_password()!=null && user.getUser_type()==Roles.USER_TYPE_FB.toString()) || (user.getUser_type()==null && user.getUser_password()==null)){

			return ResponseEntity.badRequest().build();
		}
		Users newUser = userDetailsService.save(user);
		kafkaMessageProducer.send(
				CommonUtility.stringifyEventForPublish(
						newUser.toString(),
						Constant.USER_CREATED_EVENT,
						Calendar.getInstance().getTime().toString(),
						"",
						Constant.USER_SOURCE_ID
				)
		);
		return ResponseEntity.ok(newUser);
	}


	public Iterable<Users> getAllUser() {
		return userServiceRepo.findAll();
	}

	public ResponseEntity<Users> getUserById(Long id) {
		return ResponseEntity.ok(userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id)));
	}

	public ResponseEntity<List<Users>> getUsersInRole(UserRoles roles) {
		List<Users> usersInRole = new ArrayList<>();
		Iterable<Users> users = userServiceRepo.findAll();
		roles.getUser_roles().forEach(role ->
		users.forEach(user -> {
			if(user.getUser_role().equals(role))
				usersInRole.add(user);
		}));

		if (!CommonUtility.isValidList(usersInRole))
			throw new UserNotFoundException(roles.toString());

		return ResponseEntity.ok(usersInRole);
	}

	public ResponseEntity<Users> updateUser(Long id, Users user) {
		userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
		Users updatedUser = userDetailsService.updateUser(user);
		kafkaMessageProducer.send(
				CommonUtility.stringifyEventForPublish(
						updatedUser.toString(),
						Constant.USER_UPDATED_EVENT,
						Calendar.getInstance().getTime().toString(),
						"",
						Constant.USER_SOURCE_ID
				)
		);
		return ResponseEntity.ok(updatedUser);
	}

	public ResponseEntity<Users> deleteUser(Long id) {
		userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
		userServiceRepo.deleteById(id);
		kafkaMessageProducer.send(
				CommonUtility.stringifyEventForPublish(
						id.toString(),
						Constant.USER_DEACTIVATED_EVENT,
						Calendar.getInstance().getTime().toString(),
						"",
						Constant.USER_SOURCE_ID
				)
		);
		return ResponseEntity.ok().build();
	}

	public ResponseEntity<String> getAllUserRoles() {
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		map.put("user_role", Roles.getAllRoles());
		return  ResponseEntity.ok(new Gson().toJson(map));
	}

	public ResponseEntity<Users> updateUserInterest(UserInterest userInterest, Long id) {
		Users updatedUser =partialUpdate(userInterest,id);
		kafkaMessageProducer.send(
				CommonUtility.stringifyEventForPublish(
						id.toString(),
						Constant.USER_UPDATED_EVENT,
						Calendar.getInstance().getTime().toString(),
						"",
						Constant.USER_SOURCE_ID
				)
		);
		return ResponseEntity.ok(updatedUser);
	}

	public Users partialUpdate(UserInterest userInterest, Long id) {
		Users user= userServiceRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
		user.setUser_interests(userInterest.getUser_interests());
		return userServiceRepo.save(user);
	}

	@Override
	public ResponseEntity<List<Users>> getUserWithParticularInterest(String users, String interest) {
		List<Long> usersIdList = Arrays.asList(users.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		Iterable<Users> userDataList = userServiceRepo.findAllById(usersIdList);
		List<Users> matchedUsersList = new ArrayList<Users>();
		userDataList.forEach(user -> {
			if (CommonUtility.isValidList(user.getUser_interests())) {
				if (user.getUser_interests().contains(interest)) {
					matchedUsersList.add(user);
				}
			}
		});
		if (! CommonUtility.isValidList(matchedUsersList)) throw new UserNotFoundException("No preferences matched");
		return ResponseEntity.ok(matchedUsersList);
	}

	@Override
	public void updateUserwithPartnerId(UserPartnerDto userPartnerDto) {
		Users user= userServiceRepo.findById(userPartnerDto.getUserId()).orElseThrow(()->new UserNotFoundException(userPartnerDto.getUserId()));
		user.setUser_partner_id(userPartnerDto.getPartnerId());
		userServiceRepo.save(user);
	}

}
