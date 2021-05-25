package com.blocadmin.core.service;

import com.blocadmin.core.entity.User;

public interface LoginService {
	
	public abstract User login(String user, String password);

	public abstract void logout(Long userId);
}
