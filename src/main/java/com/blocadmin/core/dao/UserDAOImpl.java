package com.blocadmin.core.dao;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.User;

@Named
public class UserDAOImpl extends CommonDAOImpl<User> implements UserDAO {

	@Override
	public List<User> getUsers() {
		TypedQuery<User> tq = em.createQuery("SELECT DISTINCT u FROM User u", User.class);
		List<User> results = tq.getResultList();
		if (results == null || results.isEmpty()) {
			return new ArrayList<User>();
		}
		return results;
	}
}
