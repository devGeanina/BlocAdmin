package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import com.blocadmin.core.dao.RequestDAO;
import com.blocadmin.core.dao.UserDAO;
import com.blocadmin.core.dto.RequestDTO;
import com.blocadmin.core.entity.Request;
import com.blocadmin.core.utils.Constants;

@Named
public class RequestServiceImpl implements RequestService{
	
	@Inject
	private RequestDAO requestDAO;
	
	@Inject
	private UserDAO userDAO;

	@Override
	public List<RequestDTO> getRequests() {
		List<RequestDTO> dtos = new ArrayList<>();

		List<Request> entities = requestDAO.getRequests();
		for (Request entity : entities) {
			RequestDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private RequestDTO getDTO(Request entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		RequestDTO entityDTO = new RequestDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setResolved(entity.isResolved());
		entityDTO.setName(entity.getName());
		entityDTO.setRequestType(Constants.HOUSEHOLD_REQUEST_TYPE.getNameByCode(entity.getRequestType()));
		entityDTO.setOwnerId(entity.getOwner().getId());
		entityDTO.setOwnerName(entity.getOwner().getFirstName().concat(" ").concat(entity.getOwner().getLastName()));
		entityDTO.setSelectedOwner(entity.getOwner());
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveRequest(RequestDTO requestDTO) {
		if (requestDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Request request = new Request();
		request = getEntity(requestDTO);
		Request existentEntity = null;
		if (request.getId() != null)
			existentEntity = requestDAO.find(request.getId());
		if (existentEntity == null) {
			requestDAO.create(request);
		} else {
			existentEntity.setDueDate(requestDTO.getDueDate());
			existentEntity.setName(requestDTO.getName());
			existentEntity.setResolved(requestDTO.isResolved());
			existentEntity.setRequestType(Constants.HOUSEHOLD_REQUEST_TYPE.valueOfLabel(requestDTO.getRequestType()).getType());
			existentEntity.setOwner(userDAO.find(requestDTO.getSelectedOwner().getId()));
			
			if(requestDTO.getDetails() != null && !requestDTO.getDetails().isEmpty())
				existentEntity.setDetails(requestDTO.getDetails());
			requestDAO.update(existentEntity);
		}
	}

	private Request getEntity(RequestDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		
		Request entity = new Request();
		entity.setDueDate(entityDTO.getDueDate());
		entity.setName(entityDTO.getName());
		entity.setResolved(entityDTO.isResolved());
		entity.setRequestType(Constants.HOUSEHOLD_REQUEST_TYPE.valueOfLabel(entityDTO.getRequestType()).getType());
		entity.setOwner(userDAO.find(entityDTO.getSelectedOwner().getId()));
		
		if(entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteRequest(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		requestDAO.delete(id);
	}

	@Override
	public RequestDTO getRequest(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Request entity = requestDAO.find(id);
		RequestDTO entityDTO = getDTO(entity);
		return entityDTO;
	}
}
