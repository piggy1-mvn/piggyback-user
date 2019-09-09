package com.incentives.piggyback.user.repository;

import com.incentives.piggyback.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserServiceRepository extends CrudRepository<User, Long> {

}
