package com.blocadmin.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import com.blocadmin.core.dao.BudgetDAO;
import com.blocadmin.core.dto.BudgetDTO;
import com.blocadmin.core.entity.Budget;
import com.blocadmin.core.utils.Constants;

@Named
public class BudgetServiceImpl implements BudgetService{
	
	@Inject
	private BudgetDAO budgetDAO;

	@Override
	public List<BudgetDTO> getBudgets() {
		List<BudgetDTO> dtos = new ArrayList<>();

		List<Budget> entities = budgetDAO.getBudgets();
		for (Budget entity : entities) {
			BudgetDTO dto = getDTO(entity);
			dtos.add(dto);
		}
		return dtos;
	}

	private BudgetDTO getDTO(Budget entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		BudgetDTO entityDTO = new BudgetDTO();
		entityDTO.setDetails(entity.getDetails());
		entityDTO.setLeftoverSum(entity.getLeftoverSum());
		entityDTO.setTotalSum(entity.getTotalSum());
		entityDTO.setType(Constants.BUDGET_TYPE.getNameByCode(entity.getType()));
		entityDTO.setId(entity.getId());
		return entityDTO;
	}

	@Override
	public void saveBudget(BudgetDTO budgetDTO) {
		if (budgetDTO == null) {
			throw new IllegalArgumentException("Cannot save the item because it's null.");
		}
		Budget budget = new Budget();
		budget = getEntity(budgetDTO);
		Budget existentEntity = null;
		if (budget.getId() != null)
			existentEntity = budgetDAO.find(budget.getId());
		if (existentEntity == null) {
			budgetDAO.create(budget);
		} else {
			existentEntity.setLeftoverSum(budgetDTO.getLeftoverSum());
			
			if(budgetDTO.getDetails() != null && !budgetDTO.getDetails().isEmpty())
				existentEntity.setDetails(budgetDTO.getDetails());
			existentEntity.setTotalSum(budgetDTO.getTotalSum());
			existentEntity.setType(Constants.BUDGET_TYPE.valueOfLabel(budgetDTO.getType()).getType());
			budgetDAO.update(existentEntity);
		}
	}

	private Budget getEntity(BudgetDTO entityDTO) {
		if (entityDTO == null) {
			throw new IllegalArgumentException("Cannot convert the item because it's null.");
		}
		Budget entity = new Budget();
		entity.setLeftoverSum(entityDTO.getLeftoverSum());
		
		if(entityDTO.getDetails() != null && !entityDTO.getDetails().isEmpty())
			entity.setDetails(entityDTO.getDetails());
		entity.setTotalSum(entityDTO.getTotalSum());
		entity.setType(Constants.BUDGET_TYPE.valueOfLabel(entityDTO.getType()).getType());
		if (entityDTO.getId() != null)
			entity.setId(entityDTO.getId());
		return entity;
	}

	@Override
	public void deleteBudget(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot delete the item because the id is null.");
		}
		budgetDAO.delete(id);
	}

	@Override
	public BudgetDTO getBudget(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Cannot retrieve the item because the id is null.");
		}

		Budget entity = budgetDAO.find(id);
		BudgetDTO entityDTO = getDTO(entity);
		return entityDTO;
	}
}
