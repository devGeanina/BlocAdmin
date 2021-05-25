package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.transaction.annotation.Transactional;

import com.blocadmin.core.dao.UserDAO;
import com.blocadmin.core.dto.UserDTO;
import com.blocadmin.core.entity.User;
import com.blocadmin.core.utils.Constants;


@Named
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;

	@Transactional
	@Override
	public List<UserDTO> getUsers() {
		List<UserDTO> users = new ArrayList<>();

		List<User> userEntities = userDAO.getUsers();
		for (User userEntity : userEntities) {
			UserDTO userDTO = getDTO(userEntity);
			users.add(userDTO);
		}
		return users;
	}

	private UserDTO getDTO(User userEntity) {
		if (userEntity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		UserDTO entityDTO = new UserDTO();
		entityDTO.setAppartmentNr(userEntity.getAppartmentNr());
		entityDTO.setBuildingNr(userEntity.getBuildingNr());
		entityDTO.setDetails(userEntity.getDetails());
		entityDTO.setFirstName(userEntity.getFirstName());
		entityDTO.setLastName(userEntity.getLastName());
		entityDTO.setUsername(userEntity.getUsername());
		
		if(userEntity.getPassword() != null)
			entityDTO.setPassword(userEntity.getPassword());
		
		entityDTO.setFullName(userEntity.getFirstName().concat(" ").concat(userEntity.getLastName()));
		entityDTO.setUserType(Constants.USER_TYPE.getNameByCode(userEntity.getUserType()));
		entityDTO.setId(userEntity.getId());
		return entityDTO;
	}

	private User getEntity(UserDTO userEntityDTO) {
		if (userEntityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		User userEntity = new User();
		userEntity.setAppartmentNr(userEntityDTO.getAppartmentNr());
		userEntity.setBuildingNr(userEntityDTO.getBuildingNr());
		
		if(userEntityDTO.getDetails() != null)
			userEntity.setDetails(userEntityDTO.getDetails());
		
		if(userEntityDTO.getPassword() != null)
			userEntity.setPassword(userEntityDTO.getPassword());
		
		userEntity.setFirstName(userEntityDTO.getFirstName());
		userEntity.setLastName(userEntityDTO.getLastName());
		
		if(userEntityDTO.getUsername() != null)
			userEntity.setUsername(userEntityDTO.getUsername());
		
		userEntity.setUserType(Constants.USER_TYPE.valueOfLabel(userEntityDTO.getUserType()).getType());
		
		if (userEntityDTO.getId() != null)
			userEntity.setId(userEntityDTO.getId());
		
		return userEntity;
	}

	@Override
	public void saveUser(UserDTO userDTO) {
		if (userDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		User user = new User();
		user = getEntity(userDTO);
		User existentEntity = null;
		if (user.getId() != null)
			existentEntity = userDAO.find(user.getId());
		if (existentEntity == null) {
			if(user.getUsername() == null)
				user.setUsername(user.getLastName().concat("_").concat(user.getFirstName()));
			userDAO.create(user);
		} else {
			existentEntity.setAppartmentNr(userDTO.getAppartmentNr());
			existentEntity.setBuildingNr(userDTO.getBuildingNr());
			
			if(userDTO.getDetails() != null)
				existentEntity.setDetails(userDTO.getDetails());
			
			if(userDTO.getPassword() != null)
				existentEntity.setPassword(userDTO.getPassword());
			
			existentEntity.setFirstName(userDTO.getFirstName());
			existentEntity.setLastName(userDTO.getLastName());
			
			if(userDTO.getUsername() != null)
				existentEntity.setUsername(userDTO.getUsername());
			
			existentEntity.setUserType(Constants.USER_TYPE.valueOfLabel(userDTO.getUserType()).getType());
			
			userDAO.update(existentEntity);
		}
	}

	@Override
	public void deleteUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		userDAO.delete(userId);
	}

	@Override
	public UserDTO getUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		User userEntity = userDAO.find(userId);
		UserDTO userEntityDTO = getDTO(userEntity);
		return userEntityDTO;
	}
}
