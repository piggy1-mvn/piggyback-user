package com.incentives.piggyback.user.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4956708689004541625L;

	public UserNotFoundException(Long id) {
        super("Could not find user with Id " + id);
    }

    public UserNotFoundException(String user_role) {
        super("Could not find user in role " + user_role);
    }

}