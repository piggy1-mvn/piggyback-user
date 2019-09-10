package com.incentives.piggyback.user.repository;

import com.incentives.piggyback.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
}
