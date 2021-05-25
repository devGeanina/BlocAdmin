package com.blocadmin.core.service;

import java.util.List;
import com.blocadmin.core.dto.BudgetDTO;

public interface BudgetService {

	public abstract List<BudgetDTO> getBudgets();

	public abstract void saveBudget(BudgetDTO budgetDTO);

	public abstract void deleteBudget(Long budgetId);

	public abstract BudgetDTO getBudget(Long budgetId);
}
