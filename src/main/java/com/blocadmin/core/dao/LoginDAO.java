package com.blocadmin.core.dao;

import com.blocadmin.core.entity.User;

public interface LoginDAO extends CommonDAO<User> {
	public abstract User login(String user, String password);
}
