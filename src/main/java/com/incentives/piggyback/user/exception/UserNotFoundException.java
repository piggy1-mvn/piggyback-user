package com.incentives.piggyback.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Could not find user with Id " + id);
    }
}
