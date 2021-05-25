package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.blocadmin.core.dao.ExpenseDAO;
import com.blocadmin.core.dao.HouseholdDAO;
import com.blocadmin.core.dao.UserDAO;
import com.blocadmin.core.dto.HouseholdDTO;
import com.blocadmin.core.entity.Expense;
import com.blocadmin.core.entity.Household;

@Named
public class HouseholdServiceImpl implements HouseholdService{
	
	@Inject
	private HouseholdDAO householdDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private ExpenseDAO expenseDAO;

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholds() {
		List<HouseholdDTO> dtos = new ArrayList<>();

		List<Household> entities = householdDAO.getHouseholds();
		for (Household entity : entities) {
			HouseholdDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional
	@Override
	public List<HouseholdDTO> getHouseholdsWithDebt() {
		List<HouseholdDTO> dtos = new ArrayList<>();

		List<Household> entities = householdDAO.getHouseholdsWithDebt();
		for (Household entity : entities) {
			HouseholdDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private HouseholdDTO getDTO(Household entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		HouseholdDTO entityDTO = new HouseholdDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setAppartmentNr(entity.getAppartmentNr());
		entityDTO.setBuildingNr(entity.getBuildingNr());
		entityDTO.setNrCurrentOccupants(entity.getNrCurrentOccupants());
		entityDTO.setRoomsNr(entity.getRoomsNr());
		entityDTO.setTotalCapacity(entity.getTotalCapacity());
		entityDTO.setSelectedOwner(entity.getOwner());
		entityDTO.setOwnerId(entity.getOwner().getId());
		entityDTO.setOwnerName(entity.getOwner().getFirstName().concat(" ").concat(entity.getOwner().getLastName()));
		
		List<Double> expensesSum = new ArrayList<Double>();
		List<Long> expensesIds = new ArrayList<Long>();
	
		double totalDebt = entity.getExpenses().stream().filter(o-> !o.isPayedInFull() && o.getLeftoverSum() > 0.0).mapToDouble(o -> o.getLeftoverSum()).sum();
		entityDTO.setExpensesSum(expensesSum);
		entityDTO.setExpensesIds(expensesIds);
		entityDTO.setTotalDebt(totalDebt);
		
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveHousehold(HouseholdDTO householdDTO) {
		if (householdDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Household household = new Household();
		household = getEntity(householdDTO);
		Household existentEntity = null;
		if (householdDTO.getId() != null)
			existentEntity = householdDAO.find(householdDTO.getId());
		if (existentEntity == null) {
			householdDAO.create(household);
		} else {
			existentEntity.setAppartmentNr(householdDTO.getAppartmentNr());
			existentEntity.setBuildingNr(householdDTO.getBuildingNr());
			existentEntity.setNrCurrentOccupants(householdDTO.getNrCurrentOccupants());
			existentEntity.setOwner(userDAO.find(householdDTO.getSelectedOwner().getId()));
			existentEntity.setRoomsNr(householdDTO.getRoomsNr());
			existentEntity.setTotalCapacity(householdDTO.getTotalCapacity());
			
			if(householdDTO.getDetails() != null && !householdDTO.getDetails().isEmpty())
				existentEntity.setDetails(householdDTO.getDetails());
			
			List<Expense> expenses = new ArrayList<Expense>();
			if(householdDTO.getExpensesIds() != null && householdDTO.getExpensesIds().isEmpty() == false){
				for(Long expenseId: householdDTO.getExpensesIds()){
					Expense expense = expenseDAO.getReference(Expense.class, expenseId);
					expenses.add(expense);
				}
			}
			
			existentEntity.setExpenses(expenses);
			householdDAO.update(existentEntity);
		}
	}

	private Household getEntity(HouseholdDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Household entity = new Household();
		entity.setAppartmentNr(entityDTO.getAppartmentNr());
		entity.setBuildingNr(entityDTO.getBuildingNr());
		entity.setNrCurrentOccupants(entityDTO.getNrCurrentOccupants());
		entity.setOwner(userDAO.find(entityDTO.getSelectedOwner().getId()));
		entity.setRoomsNr(entityDTO.getRoomsNr());
		entity.setTotalCapacity(entityDTO.getTotalCapacity());
		
		if(entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		
		List<Expense> expenses = new ArrayList<Expense>();
		if(entityDTO.getExpensesIds() != null && entityDTO.getExpensesIds().isEmpty() == false){
			for(Long expenseId: entityDTO.getExpensesIds()){
				Expense expense = expenseDAO.getReference(Expense.class, expenseId);
				expenses.add(expense);
			}
		}
		
		entity.setExpenses(expenses);

		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteHousehold(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		householdDAO.delete(id);
	}

	@Override
	public HouseholdDTO getHousehold(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Household entity = householdDAO.find(id);
		HouseholdDTO entityDTO = getDTO(entity);
		return entityDTO;
	}
}
