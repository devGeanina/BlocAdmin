package com.blocadmin.core.dao;

import java.util.List;

import com.blocadmin.core.entity.User;

public interface UserDAO extends CommonDAO<User> {
	public abstract List<User> getUsers();
}
