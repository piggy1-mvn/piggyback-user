package com.incentives.piggyback.user.repository;

import com.incentives.piggyback.user.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceRepository extends CrudRepository<Users, Long>, UserRepositoryCustom {
}
