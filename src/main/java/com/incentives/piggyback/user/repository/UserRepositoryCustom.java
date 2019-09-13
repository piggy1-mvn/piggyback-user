package com.incentives.piggyback.user.repository;

import com.incentives.piggyback.user.model.Users;

public interface UserRepositoryCustom {
    Users findByEmail(String user_email);
}
