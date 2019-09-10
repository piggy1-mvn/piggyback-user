package com.incentives.piggyback.user.repository;

import com.incentives.piggyback.user.model.User;

public interface UserRepositoryCustom {
    User findByEmail(String user_email);
}
