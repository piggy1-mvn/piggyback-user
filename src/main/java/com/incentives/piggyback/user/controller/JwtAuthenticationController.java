package com.incentives.piggyback.user.controller;

import java.net.URI;
import java.util.Objects;

import com.incentives.piggyback.user.model.JwtResponse;
import com.incentives.piggyback.user.model.UserCredential;
import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.repository.UserServiceRepository;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.config.springSecurityConfig.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@Slf4j
@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserServiceRepository userServiceRepo;

	@PostMapping("/user/login")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody UserCredential authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getUser_password());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		Users user = userServiceRepo.findByEmail(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails,user.getUser_role());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/user/create")
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
		log.debug("User Service: Received POST request for creating new user.");
		ResponseEntity<Users> responseEntity = userService.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.query(responseEntity.getBody().getId().toString())
				.buildAndExpand(responseEntity.getBody().getId().toString()).toUri();
		return ResponseEntity.created(location).body(responseEntity.getBody());
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

}
