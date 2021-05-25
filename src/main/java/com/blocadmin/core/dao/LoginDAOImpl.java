package com.blocadmin.core.dao;

import java.util.List;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import com.blocadmin.core.entity.User;

@Named
public class LoginDAOImpl extends CommonDAOImpl<User> implements LoginDAO {

	@Override
	public User login(String user, String password) {
		TypedQuery<User> q = em
				.createQuery("SELECT u FROM User u WHERE LOWER(username)=:username AND LOWER(password)=:password",
						User.class);
				//.setHint("org.hibernate.cacheable", true);
		q.setParameter("username", user.toLowerCase());
		q.setParameter("password", password.toLowerCase());
		//q.setParameter("userType", Constants.USER_TYPE.ADMIN.getType());
		List<User> users = q.getResultList();
		if (users.size() == 1) {
			return users.get(0);
		}
		return null;
	}
}
