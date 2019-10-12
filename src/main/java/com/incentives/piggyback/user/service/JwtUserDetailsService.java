package com.incentives.piggyback.user.service;

import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.util.constants.Roles;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {


	@Autowired
	private UserServiceRepository userServiceRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Users user = userServiceRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		if(user.getUser_password()==null){
			return new User(user.getEmail(), RandomStringUtils.random(7, true, true),new ArrayList<>());
		}
		return new User(user.getEmail(), user.getUser_password(),
				new ArrayList<>());
	}

	public Users save(Users user) {
		Users newUser = new Users();
		newUser.setFirst_name(user.getFirst_name());
		newUser.setLast_name(user.getLast_name());
		newUser.setUser_interests(user.getUser_interests());
		newUser.setMobile_number(user.getMobile_number());
		newUser.setMobile_verified(user.getMobile_verified());
		newUser.setEmail(user.getEmail());
		if ((user.getUser_password() == null)) {
			newUser.setUser_password(null);
		} else {
			newUser.setUser_password(bcryptEncoder.encode(user.getUser_password()));
		}
		newUser.setUser_role(user.getUser_role());
		newUser.setUser_type(user.getUser_type());
		newUser.setDevice_id(user.getDevice_id());
		newUser.setUser_rsa(user.getUser_rsa());
		newUser.setUser_partner_id(user.getUser_partner_id());
		return userServiceRepo.save(newUser);
	}

	public Users updateUser(Users user) {
		Users newUser = new Users();
		newUser.setId(user.getId());
		newUser.setFirst_name(user.getFirst_name());
		newUser.setLast_name(user.getLast_name());
		newUser.setUser_interests(user.getUser_interests());
		newUser.setMobile_number(user.getMobile_number());
		newUser.setMobile_verified(user.getMobile_verified());
		newUser.setEmail(user.getEmail());
		if(user.getUser_role().equals(Roles.USER_TYPE_FB.toString())) {
			newUser.setUser_password(null);
		} else {
			Users oldUser = userServiceRepo.findByEmail(user.getEmail());
			newUser.setUser_password(oldUser.getUser_password());
		}
		newUser.setUser_role(user.getUser_role());
		newUser.setUser_type(user.getUser_type());
		newUser.setDevice_id(user.getDevice_id());
		newUser.setUser_rsa(user.getUser_rsa());
		newUser.setUser_partner_id(user.getUser_partner_id());
		return userServiceRepo.save(newUser);
	}

}