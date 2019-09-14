package com.incentives.piggyback.user.serviceImpl;

import com.incentives.piggyback.user.model.User;
import com.incentives.piggyback.user.repository.UserRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT em.* FROM users as em " +
                "WHERE em.email = ?", User.class);
        query.setParameter(1, email);
        return (User) query.getSingleResult();
    }
}
