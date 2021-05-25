package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.blocadmin.core.dao.ExpenseDAO;
import com.blocadmin.core.dao.HouseholdDAO;
import com.blocadmin.core.dto.ExpenseDTO;
import com.blocadmin.core.entity.Expense;
import com.blocadmin.core.entity.Household;
import com.blocadmin.core.utils.Constants;

@Named
public class ExpenseServiceImpl implements ExpenseService{
	
	@Inject
	private ExpenseDAO expenseDAO;
	
	@Inject
	private HouseholdDAO householdDAO;

	@Transactional
	@Override
	public List<ExpenseDTO> getExpenses() {
		List<ExpenseDTO> dtos = new ArrayList<>();

		List<Expense> entities = expenseDAO.getExpenses();
		for (Expense entity : entities) {
			ExpenseDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private ExpenseDTO getDTO(Expense entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		
		ExpenseDTO entityDTO = new ExpenseDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setDueDate(entity.getDueDate());
		entityDTO.setExpenseType(Constants.EXPENSE_TYPE.getNameByCode(entity.getExpenseType()));
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setPayedInFull(entity.isPayedInFull());
	
		List<Long> householdIds = new ArrayList<Long>();
		List<String> householdsAddresses = new ArrayList<String>();
		if(entity.getHouseholdExpenses() != null && !entity.getHouseholdExpenses().isEmpty()) {
			for(Household household: entity.getHouseholdExpenses()){
				householdIds.add(household.getId());
				String address = "B.".concat(String.valueOf(household.getBuildingNr()).concat(", Ap.").concat(String.valueOf(household.getAppartmentNr())));
				householdsAddresses.add(address);
			}
		}
		entityDTO.setHouseholdIds(householdIds);
		entityDTO.setHouseholdsAddresses(householdsAddresses);
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveExpense(ExpenseDTO expenseDTO) {
		if (expenseDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Expense expense = new Expense();
		expense = getEntity(expenseDTO);
		Expense existentEntity = null;
		if (expense.getId() != null)
			existentEntity = expenseDAO.find(expense.getId());
		if (existentEntity == null) {
			expenseDAO.create(expense);
		} else {
			existentEntity.setLeftoverSum(expenseDTO.getLeftoverSum());
			
			if(expenseDTO.getDetails() != null && !expenseDTO.getDetails().isEmpty())
				existentEntity.setDetails(expenseDTO.getDetails());
			existentEntity.setTotalSum(expenseDTO.getTotalSum());
			existentEntity.setExpenseType(Constants.EXPENSE_TYPE.valueOfLabel(expenseDTO.getExpenseType()).getType());
			existentEntity.setDueDate(expenseDTO.getDueDate());
			existentEntity.setPayedInFull(expenseDTO.isPayedInFull());
			
			List<Household> households = new ArrayList<Household>();
			if(expenseDTO.getHouseholdIds() != null && expenseDTO.getHouseholdIds().isEmpty() == false){
				for(Long houseId: expenseDTO.getHouseholdIds()){
					Household household = householdDAO.getReference(Household.class, houseId);
					households.add(household);
				}
			}
			
			existentEntity.setHouseholdExpenses(households);
			expenseDAO.update(existentEntity);
		}
	}

	private Expense getEntity(ExpenseDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Expense entity = new Expense();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());
		
		if(entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setExpenseType(Constants.EXPENSE_TYPE.valueOfLabel(entityDTO.getExpenseType()).getType());
		entity.setDueDate(entityDTO.getDueDate());
		entity.setPayedInFull(entityDTO.isPayedInFull());
		
		List<Household> households = new ArrayList<Household>();
		if(entityDTO.getHouseholdIds() != null && entityDTO.getHouseholdIds().isEmpty() == false){
			for(Long houseId: entityDTO.getHouseholdIds()){
				Household household = householdDAO.getReference(Household.class, houseId);
				households.add(household);
			}
		}
		
		entity.setHouseholdExpenses(households);
		
		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteExpense(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		expenseDAO.delete(id);
	}

	@Override
	public ExpenseDTO getExpense(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Expense entity = expenseDAO.find(id);
		ExpenseDTO entityDTO = getDTO(entity);
		return entityDTO;
	}
}
