package com.incentives.piggyback.user.service;

import com.incentives.piggyback.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService extends CrudRepository<User, Long> {

}
