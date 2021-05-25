package com.blocadmin.core.service;

import java.util.List;
import com.blocadmin.core.dto.UserDTO;

public interface UserService {

	public abstract List<UserDTO> getUsers();

	public abstract void saveUser(UserDTO userDTO);

	public abstract void deleteUser(Long userId);

	public abstract UserDTO getUser(Long userId);
}
