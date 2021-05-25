package com.blocadmin.core.service;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;
import com.blocadmin.core.dao.LoginDAO;
import com.blocadmin.core.entity.User;


@Named
public class LoginServiceImpl implements LoginService {

	@Inject
	private LoginDAO loginDAO;
	
	@Transactional
	@Override
	public User login(String user, String password) {
		User userDB = loginDAO.login(user, password);
		if (userDB != null) {
			return userDB;
		} else {
			return null;
		}
	}

	@Override
	public void logout(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot logout because the user id is null.");
		}
	}
}
