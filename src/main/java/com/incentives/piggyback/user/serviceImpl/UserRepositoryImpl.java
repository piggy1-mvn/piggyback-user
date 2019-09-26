package com.incentives.piggyback.user.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.incentives.piggyback.user.model.Users;
import com.incentives.piggyback.user.repository.UserRepositoryCustom;

@Repository(value = "userRepositoryImpl")
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Users findByEmail(String email) {
		Query query = entityManager.createNativeQuery("SELECT em.* FROM users as em " +
				"WHERE em.email = ?", Users.class);
		query.setParameter(1, email);
		return (Users) query.getSingleResult();
	}

	@Override
	public List<Users> findByUserIds(List<Long> ids) {
		List<Users> resultList = entityManager.createQuery("SELECT p FROM users p WHERE p.id IN :ids").setParameter("ids", ids).getResultList();
		return resultList;
	}

}
