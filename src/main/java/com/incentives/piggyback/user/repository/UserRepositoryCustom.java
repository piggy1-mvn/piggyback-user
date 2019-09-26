package com.incentives.piggyback.user.repository;

import java.util.List;

import com.incentives.piggyback.user.model.Users;

public interface UserRepositoryCustom {
    Users findByEmail(String user_email);
    List<Users> findByUserIds(List<Long> ids);
}
